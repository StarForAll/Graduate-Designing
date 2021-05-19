package cn.edu.ncu.service;

import cn.edu.ncu.pojo.vo.PrivilegeRequestUrlVO;
import java.util.List;

/**
 * [ 初始化 分离前后台权限URL ]
 *
 * @author yandanyang
 * @version 1.0
 * @company 1024lab.net
 * @copyright (c) 2018 1024lab.netInc. All rights reserved.
 * @date 2019/3/28 0028 上午 9:13
 * @since JDK1.8
 */
public interface PrivilegeRequestUrlService {
     List<PrivilegeRequestUrlVO> getPrivilegeList();
}
