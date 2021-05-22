package cn.edu.ncu.service;

import cn.edu.ncu.pojo.vo.PrivilegeRequestUrlVO;
import java.util.List;

/**
 * @Author: XiongZhiCong
 * @Description: 初始化 分离前后台权限URL
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
public interface PrivilegeRequestUrlService {
     List<PrivilegeRequestUrlVO> getPrivilegeList();
}
