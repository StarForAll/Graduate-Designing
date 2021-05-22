package cn.edu.ncu.common.constant;

/**
 * @Author: XiongZhiCong
 * @Description: 部门返回信息常量类
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
public class DepartmentResponseCodeConst extends ResponseCodeConst {

    /**
     * 部门不存在 1001
     */
    public static final DepartmentResponseCodeConst DEPT_NOT_EXISTS = new DepartmentResponseCodeConst(2001, "部门不存在");

    /**
     * 当前部门有子级部门 不能删除 10003
     */
    public static final DepartmentResponseCodeConst CANNOT_DEL_DEPARTMENT_WITH_CHILD = new
            DepartmentResponseCodeConst(2002, "当前部门有子级部门,无法删除！");

    /**
     * 当前部门有员工 不能删除 10004
     */
    public static final DepartmentResponseCodeConst CANNOT_DEL_DEPARTMENT_WITH_EMPLOYEE = new
            DepartmentResponseCodeConst(2003, "当前部门有员工,无法删除！");

    /**
     *
     */
    public static final DepartmentResponseCodeConst PARENT_ID_ERROR = new DepartmentResponseCodeConst(2004, "上级部门id不能等于当前部门id");

    public DepartmentResponseCodeConst(int code, String msg) {
        super(code, msg);
    }
}
