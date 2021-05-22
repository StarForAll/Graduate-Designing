package cn.edu.ncu.service.impl;

import cn.edu.ncu.common.util.bean.BeanUtil;
import cn.edu.ncu.dao.entity.UserLoginLog;
import cn.edu.ncu.dao.mapper.UserLoginLogMapper;
import cn.edu.ncu.service.EmployeeService;
import cn.edu.ncu.service.UserLoginLogService;
import cn.edu.ncu.service.WebSocketServer;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.edu.ncu.common.core.pojo.dto.PageResultDTO;
import cn.edu.ncu.common.core.pojo.dto.ResponseDTO;
import cn.edu.ncu.pojo.dto.EmployeeQueryDTO;
import cn.edu.ncu.pojo.dto.UserLoginLogDTO;
import cn.edu.ncu.pojo.dto.UserLoginLogQueryDTO;
import cn.edu.ncu.pojo.vo.EmployeeVO;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: XiongZhiCong
 * @Description: [ 用户登录日志 ]
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
@Service
public class UserLoginLogServiceImpl implements UserLoginLogService {

    @Resource
    private UserLoginLogMapper userLoginLogMapper;

    @Autowired
    private EmployeeService employeeService;

    /**
     * @author yandanyang
     * @description 分页查询
     * @date 2019-05-15 10:25:21
     */
    @Override
    public ResponseDTO<PageResultDTO<UserLoginLogDTO>> queryByPage(UserLoginLogQueryDTO queryDTO) {
        PageResultDTO<UserLoginLogDTO> pageResultDTO = new PageResultDTO<>();
        int total=0;
        Boolean searchCount = queryDTO.getSearchCount();
        if(searchCount ==null||searchCount){
            total=userLoginLogMapper.queryCount(queryDTO);
        }
        long pages=total/queryDTO.getPageSize()+1;
        pageResultDTO.setPageNum(Long.valueOf(queryDTO.getPageNum()));
        pageResultDTO.setPages(pages);
        pageResultDTO.setTotal((long) total);
        pageResultDTO.setPageSize(Long.valueOf(queryDTO.getPageSize()));

        List<UserLoginLog> entities = userLoginLogMapper.queryByPage(queryDTO);
        List<UserLoginLogDTO> dtoList = BeanUtil.copyList(entities, UserLoginLogDTO.class);
        pageResultDTO.setList(dtoList);
        return ResponseDTO.succData(pageResultDTO);
    }

    /**
     * @author yandanyang
     * @description 删除
     * @date 2019-05-15 10:25:21
     */
    @Override
    public ResponseDTO<String> delete(Long id) {
        userLoginLogMapper.deleteById(id);
        return ResponseDTO.succ();
    }

    /**
     * 查询员工在线状态
     *
     * @param queryDTO
     * @return
     */
    @Override
    public ResponseDTO<PageResultDTO<EmployeeVO>> queryUserOnLine(EmployeeQueryDTO queryDTO) {
        List<Long> onLineUserList = WebSocketServer.getOnLineUserList();
        if (CollectionUtils.isEmpty(onLineUserList)) {
            return ResponseDTO.succ();
        }
        queryDTO.setEmployeeIds(onLineUserList);
        ResponseDTO<PageResultDTO<EmployeeVO>> employeeList = employeeService.selectEmployeeList(queryDTO);
        return employeeList;
    }

}
