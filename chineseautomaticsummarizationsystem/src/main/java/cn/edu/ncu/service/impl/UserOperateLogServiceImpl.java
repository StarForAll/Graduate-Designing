package cn.edu.ncu.service.impl;

import cn.edu.ncu.common.util.bean.BeanUtil;
import cn.edu.ncu.common.util.dao.IdGeneratorUtil;
import cn.edu.ncu.dao.entity.UserOperateLog;
import cn.edu.ncu.dao.mapper.UserOperateLogMapper;
import cn.edu.ncu.service.UserOperateLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

import cn.edu.ncu.common.core.pojo.dto.PageResultDTO;
import cn.edu.ncu.common.core.pojo.dto.ResponseDTO;
import cn.edu.ncu.pojo.dto.UserOperateLogDTO;
import cn.edu.ncu.pojo.dto.UserOperateLogQueryDTO;
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
@Service
public class UserOperateLogServiceImpl implements UserOperateLogService {
    @Autowired
    private IdGeneratorUtil idGeneratorUtil;
    @Resource
    private UserOperateLogMapper userOperateLogMapper;

    /**
     * @author yandanyang
     * @description 分页查询
     * @date 2019-05-15 11:32:14
     */
    @Override
    public ResponseDTO<PageResultDTO<UserOperateLogDTO>> queryByPage(UserOperateLogQueryDTO queryDTO) {
        PageResultDTO<UserOperateLogDTO> pageResultDTO = new PageResultDTO<>();
        int total=0;
        Boolean searchCount = queryDTO.getSearchCount();
        if(searchCount ==null||searchCount){
            total=userOperateLogMapper.queryCount(queryDTO);
        }
        long pages=total/queryDTO.getPageSize()+1;
        pageResultDTO.setPageNum(Long.valueOf(queryDTO.getPageNum()));
        pageResultDTO.setPages(pages);
        pageResultDTO.setTotal((long) total);
        pageResultDTO.setPageSize(Long.valueOf(queryDTO.getPageSize()));

        List<UserOperateLog> entities = userOperateLogMapper.queryByPage( queryDTO);
        List<UserOperateLogDTO> dtoList = BeanUtil.copyList(entities, UserOperateLogDTO.class);
        pageResultDTO.setList(dtoList);
        return ResponseDTO.succData(pageResultDTO);
    }

    /**
     * @author yandanyang
     * @description 添加
     * @date 2019-05-15 11:32:14
     */
    @Override
    public ResponseDTO<String> add(UserOperateLogDTO addDTO) {
        UserOperateLog entity = BeanUtil.copy(addDTO, UserOperateLog.class);
        if(entity.getId()==null){
            entity.setId(idGeneratorUtil.snowflakeId());
        }
        Date date=new Date(System.currentTimeMillis());
        if(entity.getUpdateTime()==null){
            entity.setUpdateTime(date);
        }
        if(entity.getCreateTime()==null){
            entity.setCreateTime(date);
        }
        userOperateLogMapper.insert(entity);
        return ResponseDTO.succ();
    }

    /**
     * @author yandanyang
     * @description 编辑
     * @date 2019-05-15 11:32:14
     */
    @Override
    public ResponseDTO<String> update(UserOperateLogDTO updateDTO) {
        UserOperateLog entity = BeanUtil.copy(updateDTO, UserOperateLog.class);
        userOperateLogMapper.update(entity);
        return ResponseDTO.succ();
    }

    /**
     * @author yandanyang
     * @description 删除
     * @date 2019-05-15 11:32:14
     */
    @Override
    public ResponseDTO<String> delete(Long id) {
        userOperateLogMapper.deleteById(id);
        return ResponseDTO.succ();
    }

    /**
     * @author yandanyang
     * @description 根据ID查询
     * @date 2019-05-15 11:32:14
     */
    @Override
    public ResponseDTO<UserOperateLogDTO> detail(Long id) {
        UserOperateLog entity = userOperateLogMapper.selectByPrimaryKey(id);
        UserOperateLogDTO dto = BeanUtil.copy(entity, UserOperateLogDTO.class);
        return ResponseDTO.succData(dto);
    }
}
