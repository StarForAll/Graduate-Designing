package cn.edu.ncu.service.impl;

import cn.edu.ncu.common.constant.EmailSendStatusEnum;
import cn.edu.ncu.common.constant.ResponseCodeConst;
import cn.edu.ncu.common.constant.SystemConfigEnum;
import cn.edu.ncu.common.util.bean.BeanUtil;
import cn.edu.ncu.common.util.dao.IdGeneratorUtil;
import cn.edu.ncu.common.util.mail.MailUtil;
import cn.edu.ncu.dao.entity.Email;
import cn.edu.ncu.dao.mapper.EmailMapper;
import cn.edu.ncu.pojo.dto.EmailConfigDTO;
import cn.edu.ncu.service.EmailService;
import cn.edu.ncu.service.SystemConfigService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.edu.ncu.common.core.pojo.dto.PageResultDTO;
import cn.edu.ncu.common.core.pojo.dto.ResponseDTO;
import cn.edu.ncu.pojo.dto.EmailDTO;
import cn.edu.ncu.pojo.dto.EmailQueryDTO;
import cn.edu.ncu.pojo.vo.EmailVO;
import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * [  ]
 *
 * @author yandanyang
 * @version 1.0
 * @company 1024lab.net
 * @copyright (c) 2019 1024lab.netInc. All rights reserved.
 * @date 2019-05-13 17:10:16
 * @since JDK1.8
 */
@Service
public class EmailServiceImpl implements EmailService {

    @Resource
    private EmailMapper emailMapper;
    @Autowired
    private IdGeneratorUtil idGeneratorUtil;
    @Autowired
    private SystemConfigService systemConfigService;

    /**
     * @author yandanyang
     * @description 分页查询
     * @date 2019-05-13 17:10:16
     */
    @Override
    public ResponseDTO<PageResultDTO<EmailVO>> queryByPage(EmailQueryDTO queryDTO) {
        PageResultDTO<EmailVO> pageResultDTO =new PageResultDTO<>();
        int total=0;
        Boolean searchCount = queryDTO.getSearchCount();
        if(searchCount ==null||searchCount){
            total=emailMapper.queryCount(queryDTO);
        }
        long pages=total/queryDTO.getPageSize()+1;
        pageResultDTO.setPageNum(Long.valueOf(queryDTO.getPageNum()));
        pageResultDTO.setPages(pages);
        pageResultDTO.setTotal((long) total);
        pageResultDTO.setPageSize(Long.valueOf(queryDTO.getPageSize()));
        List<Email> entities = emailMapper.queryByPage(queryDTO);
        List<EmailVO> dtoList = BeanUtil.copyList(entities, EmailVO.class);
        pageResultDTO.setList(dtoList);
        return ResponseDTO.succData(pageResultDTO);
    }

    /**
     * @author yandanyang
     * @description 添加
     * @date 2019-05-13 17:10:16
     */
    @Override
    public ResponseDTO<Long> add(EmailDTO addDTO) {
        Email entity = BeanUtil.copy(addDTO, Email.class);
        Long id = entity.getId();
        if(id!=null){
            emailMapper.deleteById(id);
        }
        if(entity.getSendStatus()==null){
            entity.setSendStatus(0);
        }
        id=idGeneratorUtil.snowflakeId();
        entity.setId(id);
        Date date=new Date(System.currentTimeMillis());
        if(entity.getUpdateTime()==null){
            entity.setUpdateTime(date);
        }
        if(entity.getCreateTime()==null){
            entity.setCreateTime(date);
        }
        emailMapper.insert(entity);
        return ResponseDTO.succData(id);
    }

    /**
     * @author yandanyang
     * @description 编辑
     * @date 2019-05-13 17:10:16
     */
    @Override
    public ResponseDTO<Long> update(EmailDTO updateDTO) {
        Email entity = BeanUtil.copy(updateDTO, Email.class);
        emailMapper.update(entity);
        return ResponseDTO.succData(entity.getId());
    }

    /**
     * @author yandanyang
     * @description 删除
     * @date 2019-05-13 17:10:16
     */
    @Override
    public ResponseDTO<String> delete(Long id) {
        emailMapper.deleteById(id);
        return ResponseDTO.succ();
    }

    /**
     * @author yandanyang
     * @description 根据ID查询
     * @date 2019-05-13 17:10:16
     */
    @Override
    public ResponseDTO<EmailVO> detail(Long id) {
        Email entity = emailMapper.selectByPrimaryKey(id);
        EmailVO dto = BeanUtil.copy(entity, EmailVO.class);
        return ResponseDTO.succData(dto);
    }

    /**
     * 发送某个已创建的邮件
     *
     * @param id
     * @return
     */
    @Override
    public ResponseDTO<String> send(Long id) {
        Email entity = emailMapper.selectByPrimaryKey(id);
        EmailConfigDTO emailConfig = systemConfigService.selectByKey2Obj(SystemConfigEnum.Key.EMAIL_CONFIG.name(), EmailConfigDTO.class);
        String toEmails = entity.getToEmails();
        if (StringUtils.isEmpty(toEmails)) {
            return ResponseDTO.wrap(ResponseCodeConst.ERROR_PARAM, "收件人信息为空");
        }
        String[] emails = toEmails.split(";");
        MailUtil.sendMail(emailConfig.getUsername(), emailConfig.getPassword(), emailConfig.getUsername(), emails, "", emailConfig.getSmtpHost(), entity.getTitle(), entity.getContent());
        entity.setSendStatus(EmailSendStatusEnum.SEND.getType());
        emailMapper.update(entity);
        return ResponseDTO.succ();
    }

}
