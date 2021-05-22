package cn.edu.ncu.service;

import cn.edu.ncu.common.core.pojo.dto.PageResultDTO;
import cn.edu.ncu.common.core.pojo.dto.ResponseDTO;
import cn.edu.ncu.pojo.dto.EmployeeQueryDTO;
import cn.edu.ncu.pojo.dto.UserLoginLogDTO;
import cn.edu.ncu.pojo.dto.UserLoginLogQueryDTO;
import cn.edu.ncu.pojo.vo.EmployeeVO;
import org.springframework.transaction.annotation.Transactional;


/**
 * @Author: XiongZhiCong
 * @Description: 用户登录日志业务
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
public interface UserLoginLogService {

    /**
     * @author yandanyang
     * @description 分页查询
     * @date 2019-05-15 10:25:21
     */
     ResponseDTO<PageResultDTO<UserLoginLogDTO>> queryByPage(UserLoginLogQueryDTO queryDTO);

    /**
     * @author yandanyang
     * @description 删除
     * @date 2019-05-15 10:25:21
     */
    @Transactional(rollbackFor = Exception.class)
    ResponseDTO<String> delete(Long id) ;

    /**
     * 查询员工在线状态
     *
     * @param queryDTO
     * @return
     */
     ResponseDTO<PageResultDTO<EmployeeVO>> queryUserOnLine(EmployeeQueryDTO queryDTO) ;

}
