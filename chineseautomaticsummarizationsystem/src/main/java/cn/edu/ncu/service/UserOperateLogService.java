package cn.edu.ncu.service;

import cn.edu.ncu.common.core.pojo.dto.PageResultDTO;
import cn.edu.ncu.common.core.pojo.dto.ResponseDTO;
import cn.edu.ncu.pojo.dto.UserOperateLogDTO;
import cn.edu.ncu.pojo.dto.UserOperateLogQueryDTO;
import org.springframework.transaction.annotation.Transactional;


/**
 * @Author: XiongZhiCong
 * @Description: 用户操作日志业务
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
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
