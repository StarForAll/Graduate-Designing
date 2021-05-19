package cn.edu.ncu.dao.mapper;

import cn.edu.ncu.common.annotation.DataScope;
import cn.edu.ncu.common.constant.DataScopeWhereInTypeEnum;
import cn.edu.ncu.common.core.dao.CommonMapper;
import cn.edu.ncu.dao.entity.Notice;
import cn.edu.ncu.pojo.dto.NoticeQueryDTO;
import cn.edu.ncu.pojo.dto.NoticeReadCountDTO;
import cn.edu.ncu.pojo.dto.NoticeReceiveDTO;
import cn.edu.ncu.pojo.dto.NoticeReceiveQueryDTO;
import cn.edu.ncu.pojo.vo.NoticeDetailVO;
import cn.edu.ncu.pojo.vo.NoticeVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * [  ]
 *
 * @author yandanyang
 * @version 1.0
 * @company 1024lab.net
 * @copyright (c) 2018 1024lab.netInc. All rights reserved.
 * @date 2019-07-11 16:19:48
 * @since JDK1.8
 */
@Mapper
public interface NoticeMapper extends CommonMapper<Notice> {
    void update(Notice notice);
    @DataScope(joinSql = "n.create_user in (#employeeIds)", whereInType = DataScopeWhereInTypeEnum.EMPLOYEE)
    Integer queryCount(@Param("queryDTO") NoticeQueryDTO queryDTO);
    /**
     * 分页查询
     * @param queryDTO
     * @return NoticeEntity
    */
    @DataScope(joinSql = "n.create_user in (#employeeIds)", whereInType = DataScopeWhereInTypeEnum.EMPLOYEE)
    List<NoticeVO> queryByPage(@Param("queryDTO") NoticeQueryDTO queryDTO);

    @DataScope(joinSql = "e.department_id in (#departmentIds)", whereInType = DataScopeWhereInTypeEnum.DEPARTMENT)
    Integer queryUnreadCount(@Param("employeeId") Long employeeId, @Param("sendStatus") Integer sendStatus);
    /**
     * 获取某人的未读消息
     * @param employeeId
     * @return
     */
    @DataScope(joinSql = "e.department_id in (#departmentIds)", whereInType = DataScopeWhereInTypeEnum.DEPARTMENT)
    List<NoticeVO> queryUnreadByPage(@Param("employeeId") Long employeeId, @Param("sendStatus") Integer sendStatus);

    Integer queryReceiveCount(@Param("queryDTO") NoticeReceiveQueryDTO queryDTO);
    /**
     * 获取
     * @param queryDTO
     * @return
     */
    List<NoticeReceiveDTO> queryReceiveByPage( @Param("queryDTO") NoticeReceiveQueryDTO queryDTO);

    /**
     * 详情
     * @param id
     * @return
     */
    NoticeDetailVO detail(@Param("id") Long id);

    /**
     * 根据id删除 逻辑删除
     * @param id
     * @param deletedFlag
     */
    void logicDeleteById(@Param("id") Long id, @Param("deletedFlag") Integer deletedFlag);



    /**
     * 批量逻辑删除
     * @param idList
     * @param deletedFlag
     * @return
    */
    void logicDeleteByIds(@Param("idList") List<Long> idList, @Param("deletedFlag") Integer deletedFlag);

    /**
     * 获取消息总数
     * @return
     */
    Integer noticeCount(@Param("sendStatus") Integer sendStatus);


    /**
     * 获取已读消息数
     * @param employeeIds
     * @return
     */
    List<NoticeReadCountDTO> readCount(@Param("employeeIds") List<Long> employeeIds);


    /**
     * 获取某人的未读消息数
     * @param employeeId
     * @param sendStatus
     * @return
     */
    Integer noticeUnreadCount(@Param("employeeId") Long employeeId, @Param("sendStatus") Integer sendStatus);

}
