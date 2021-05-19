package cn.edu.ncu.service;

import cn.edu.ncu.common.core.pojo.dto.PageResultDTO;
import cn.edu.ncu.common.core.pojo.dto.ResponseDTO;
import cn.edu.ncu.pojo.dto.EmailDTO;
import cn.edu.ncu.pojo.dto.EmailQueryDTO;
import cn.edu.ncu.pojo.vo.EmailVO;
import org.springframework.transaction.annotation.Transactional;


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
public interface EmailService {


    /**
     * @author yandanyang
     * @description 分页查询
     * @date 2019-05-13 17:10:16
     */
     ResponseDTO<PageResultDTO<EmailVO>> queryByPage(EmailQueryDTO queryDTO);

    /**
     * @author yandanyang
     * @description 添加
     * @date 2019-05-13 17:10:16
     */
     ResponseDTO<Long> add(EmailDTO addDTO) ;

    /**
     * @author yandanyang
     * @description 编辑
     * @date 2019-05-13 17:10:16
     */
    @Transactional(rollbackFor = Exception.class)
     ResponseDTO<Long> update(EmailDTO updateDTO) ;

    /**
     * @author yandanyang
     * @description 删除
     * @date 2019-05-13 17:10:16
     */
    @Transactional(rollbackFor = Exception.class)
     ResponseDTO<String> delete(Long id) ;

    /**
     * @author yandanyang
     * @description 根据ID查询
     * @date 2019-05-13 17:10:16
     */
     ResponseDTO<EmailVO> detail(Long id) ;

    /**
     * 发送某个已创建的邮件
     *
     * @param id
     * @return
     */
     ResponseDTO<String> send(Long id) ;

}
