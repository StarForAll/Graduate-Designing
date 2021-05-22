package cn.edu.ncu.service.impl;

import cn.edu.ncu.common.constant.CommonConst;
import cn.edu.ncu.common.constant.EmployeeResponseCodeConst;
import cn.edu.ncu.common.constant.EmployeeStatusEnum;
import cn.edu.ncu.common.constant.JudgeEnum;
import cn.edu.ncu.common.redis.service.RedisService;
import cn.edu.ncu.common.util.basic.DESUtil;
import cn.edu.ncu.common.util.bean.BeanUtil;
import cn.edu.ncu.common.util.web.IpUtil;
import cn.edu.ncu.dao.entity.Department;
import cn.edu.ncu.dao.entity.Privilege;
import cn.edu.ncu.dao.entity.UserLoginLog;
import cn.edu.ncu.dao.mapper.DepartmentMapper;
import cn.edu.ncu.dao.mapper.EmployeeMapper;
import cn.edu.ncu.pojo.dto.EmployeeDTO;
import cn.edu.ncu.service.LogService;
import cn.edu.ncu.service.LoginService;
import cn.edu.ncu.service.LoginTokenService;
import cn.edu.ncu.service.PrivilegeEmployeeService;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import eu.bitwalker.useragentutils.UserAgent;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import cn.edu.ncu.common.core.pojo.dto.ResponseDTO;
import cn.edu.ncu.pojo.bo.RequestTokenBO;
import cn.edu.ncu.pojo.dto.EmployeeLoginFormDTO;
import cn.edu.ncu.pojo.dto.LoginPrivilegeDTO;
import cn.edu.ncu.pojo.vo.KaptchaVO;
import cn.edu.ncu.pojo.vo.LoginDetailVO;
/**
 * @Author: XiongZhiCong
 * @Description: 登录业务
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
@Slf4j
@Service
public class LoginServiceImpl implements LoginService {

    private static final String VERIFICATION_CODE_REDIS_PREFIX = "vc_%s";

    @Resource
    private EmployeeMapper employeeMapper;

    @Resource
    private DepartmentMapper departmentMapper;

    @Autowired
    private PrivilegeEmployeeService privilegeEmployeeService;

    @Autowired
    private LoginTokenService loginTokenService;

    @Autowired
    private LogService logService;

    @Autowired
    private DefaultKaptcha defaultKaptcha;

    @Resource
    private RedisService redisService;

    /**
     * 登陆
     *
     * @param loginForm 登录名 密码
     * @return 登录用户基本信息
     */
    @Override
    public ResponseDTO<LoginDetailVO> login(@Valid EmployeeLoginFormDTO loginForm, HttpServletRequest request) {
        String redisVerificationCode = (String) redisService.get(loginForm.getCodeUuid());
        //增加删除已使用的验证码方式 频繁登录
        redisService.del(loginForm.getCodeUuid());
        if (StringUtils.isEmpty(redisVerificationCode)||!redisVerificationCode.equalsIgnoreCase(loginForm.getCode())) {
            return ResponseDTO.wrap(EmployeeResponseCodeConst.VERIFICATION_CODE_INVALID);
        }
        String loginPwd = DESUtil.encrypt(CommonConst.Password.SALT_FORMAT, loginForm.getLoginPwd());
        EmployeeDTO employeeDTO = employeeMapper.login(loginForm.getLoginName(), loginPwd);
        if (null == employeeDTO) {
            return ResponseDTO.wrap(EmployeeResponseCodeConst.LOGIN_FAILED);
        }
        if (EmployeeStatusEnum.DISABLED.equalsValue(employeeDTO.getIsDisabled())) {
            return ResponseDTO.wrap(EmployeeResponseCodeConst.IS_DISABLED);
        }
        //jwt token赋值
        String compactJws = loginTokenService.generateToken(employeeDTO);

        LoginDetailVO loginDTO = BeanUtil.copy(employeeDTO, LoginDetailVO.class);

        //获取前端功能权限
        loginDTO.setPrivilegeList(initEmployeePrivilege(employeeDTO.getId()));

        loginDTO.setXAccessToken(compactJws);
        Department departmentEntity = departmentMapper.selectByPrimaryKey(employeeDTO.getDepartmentId());
        loginDTO.setDepartmentName(departmentEntity.getName());

        //判断是否为超管
        Boolean isSuperman = privilegeEmployeeService.isSuperman(loginDTO.getId());
        loginDTO.setIsSuperMan(isSuperman);
        //登陆操作日志
        UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
        UserLoginLog logEntity =
                UserLoginLog.builder()
                        .userId(employeeDTO.getId())
                        .userName(employeeDTO.getActualName())
                        .remoteIp(IpUtil.getRemoteIp(request))
                        .remotePort(request.getRemotePort())
                        .remoteBrowser(userAgent.getBrowser().getName())
                        .remoteOs(userAgent.getOperatingSystem().getName())
                        .loginStatus(JudgeEnum.YES.getValue()).build();
        logService.addLog(logEntity);
        return ResponseDTO.succData(loginDTO);
    }

    /**
     * 退出登陆，清除token缓存
     *
     * @param requestToken
     * @return 退出登陆是否成功，bool
     */
    @Override
    public ResponseDTO<Boolean> logoutByToken(RequestTokenBO requestToken) {
        privilegeEmployeeService.removeCache(requestToken.getRequestUserId());
        return ResponseDTO.succ();
    }

    /**
     * 获取验证码
     *
     * @return
     */
    @Override
    public ResponseDTO<KaptchaVO> verificationCode() {
        KaptchaVO kaptchaVO = new KaptchaVO();
        String uuid = buildVerificationCodeRedisKey(UUID.randomUUID().toString());
        String kaptchaText = defaultKaptcha.createText();

        String base64Code = "";

        BufferedImage image = defaultKaptcha.createImage(kaptchaText);
        ByteArrayOutputStream outputStream = null;
        try {
            outputStream = new ByteArrayOutputStream();
            ImageIO.write(image, "jpg", outputStream);
            base64Code = Base64.encodeBase64String(outputStream.toByteArray());
        } catch (Exception e) {
            log.error("verificationCode exception .{}", e);
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (Exception e) {
                    log.error("verificationCode outputStream close exception .{}", e);
                }
            }
        }
        kaptchaVO.setUuid(uuid);
        kaptchaVO.setCode("data:image/png;base64," + base64Code);
        redisService.set(uuid,kaptchaText,60);
        return ResponseDTO.succData(kaptchaVO);
    }

    private String buildVerificationCodeRedisKey(String uuid) {
        return String.format(VERIFICATION_CODE_REDIS_PREFIX, uuid);
    }

    /**
     * 初始化员工权限
     *
     * @param employeeId
     * @return
     */
    @Override
    public List<LoginPrivilegeDTO> initEmployeePrivilege(Long employeeId) {
        List<Privilege> privilegeList = privilegeEmployeeService.getPrivilegesByEmployeeId(employeeId);
        privilegeEmployeeService.updateCachePrivilege(employeeId, privilegeList);
        return BeanUtil.copyList(privilegeList, LoginPrivilegeDTO.class);
    }

    @Override
    public LoginDetailVO getSession(RequestTokenBO requestUser) {
        LoginDetailVO loginDTO = BeanUtil.copy(requestUser.getEmployeeBO(), LoginDetailVO.class);
        List<Privilege> privilegeEntityList = privilegeEmployeeService.getEmployeeAllPrivilege(requestUser.getRequestUserId());
        //======  开启缓存   ======
        if (privilegeEntityList == null) {
            List<LoginPrivilegeDTO> loginPrivilegeDTOS = initEmployeePrivilege(requestUser.getRequestUserId());
            loginDTO.setPrivilegeList(loginPrivilegeDTOS);
        } else {
            loginDTO.setPrivilegeList(BeanUtil.copyList(privilegeEntityList, LoginPrivilegeDTO.class));
        }

        //======  不开启缓存   ======
//        List<LoginPrivilegeDTO> loginPrivilegeDTOS = initEmployeePrivilege(requestUser.getRequestUserId());
//        loginDTO.setPrivilegeList(loginPrivilegeDTOS);

        //判断是否为超管
        Boolean isSuperman = privilegeEmployeeService.isSuperman(loginDTO.getId());
        loginDTO.setIsSuperMan(isSuperman);
        return loginDTO;
    }
}
