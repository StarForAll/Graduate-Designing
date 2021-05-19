package cn.edu.ncu.service;

import cn.edu.ncu.common.core.pojo.dto.PageResultDTO;
import cn.edu.ncu.common.core.pojo.dto.ResponseDTO;
import cn.edu.ncu.pojo.dto.UserOperateLogDTO;
import cn.edu.ncu.pojo.dto.UserOperateLogQueryDTO;
import org.springframework.transaction.annotation.Transactional;


/**
 * [  ]
 *
 * @author yandanyang
 * @version 1.0
 * @company 1024lab.net
 * @copyright (c) 2019 1024lab.netInc. All rights reserved.
 * @date 2019-05-15 11:32:14
 * @since JDK1.8
 */
public interface UserOperateLogService {


    /**
     * @author yandanyang
     * @description 分页查询
     * @date 2019-05-15 11:32:14
     */
     ResponseDTO<PageResultDTO<UserOperateLogDTO>> queryByPage(UserOperateLogQueryDTO queryDTO);

    /**
     * @author yandanyang
     * @description 添加
     * @date 2019-05-15 11:32:14
     */
     ResponseDTO<String> add(UserOperateLogDTO addDTO);

    /**
     * @author yandanyang
     * @description 编辑
     * @date 2019-05-15 11:32:14
     */
    @Transactional(rollbackFor = Exception.class)
     ResponseDTO<String> update(UserOperateLogDTO updateDTO) ;

    /**
     * @author yandanyang
     * @description 删除
     * @date 2019-05-15 11:32:14
     */
    @Transactional(rollbackFor = Exception.class)
    ResponseDTO<String> delete(Long id) ;

    /**
     * @author yandanyang
     * @description 根据ID查询
     * @date 2019-05-15 11:32:14
     */
     ResponseDTO<UserOperateLogDTO> detail(Long id);
}
