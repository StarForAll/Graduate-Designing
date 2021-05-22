package cn.edu.ncu.service;

import cn.edu.ncu.common.constant.DataScopeTypeEnum;
import cn.edu.ncu.common.constant.DataScopeViewTypeEnum;
import java.util.List;

/**
 * @Author: XiongZhiCong
 * @Description: 数据范围试图
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
public interface DataScopeViewService {


    /**
     * 获取某人可以查看的所有人员信息
     *
     * @param dataScopeTypeEnum
     * @param employeeId
     * @return
     */
     List<Long> getCanViewEmployeeId(DataScopeTypeEnum dataScopeTypeEnum, Long employeeId) ;

    /**
     * 获取某人可以查看的所有部门信息
     *
     * @param dataScopeTypeEnum
     * @param employeeId
     * @return
     */
     List<Long> getCanViewDepartmentId(DataScopeTypeEnum dataScopeTypeEnum, Long employeeId) ;


    /**
     * 根据员工id 获取各数据范围最大的可见范围 map<dataScopeType,viewType></>
     *
     * @param employeeId
     * @return
     */
     DataScopeViewTypeEnum getEmployeeDataScopeViewType(DataScopeTypeEnum dataScopeTypeEnum, Long employeeId) ;


}
