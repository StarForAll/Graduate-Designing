1(Integer), 人员管理(String), /employee(String), 20(Integer), System(String), Employee(String),
1(Integer), 角色管理(String), /employee/r(String), 2
1(Integer), Employee(String), RoleManage(String), 
1(Integer), 岗位管理(String), /employee/position(String), 22(Integer), Employee(String), PositionList(String), 
1(Integer), 员工管理(String), /employee/role-employee-manage(String), 23(Integer), Employee(String), RoleEmployeeManage(String), 
1(Integer), 系统设置(String), /system-setting(String), 29(Integer), System(String), SystemSetting(String), 
1(Integer), 系统参数(String), /system-setting/system-config(String), 30(Integer), SystemSetting(String), SystemConfig(String), 
1(Integer), 菜单设置(String), /system-setting/system-privilege(String), 3
1(Integer), SystemSetting(String), SystemPrivilege(String), 
1(Integer), 消息管理(String), /notice(String), 10(Integer), Business(String), Notice(String), 
1(Integer), 通知管理(String), /notice/notice-list(String), 1
1(Integer), Notice(String), NoticeList(String), 
1(Integer), 个人消息(String), /notice/person-notice(String), 12(Integer), Notice(String), PersonNotice(String), 
1(Integer), 邮件管理(String), /email(String), 4(Integer), Business(String), Email(String), 
1(Integer), 邮件管理(String), /email/email-list(String), 5(Integer), Email(String), EmailList(String), 
1(Integer), 发送邮件(String), /email/send-mail(String), 6(Integer), Email(String), SendMail(String), 
1(Integer), 用户日志(String), /user-log(String), 26(Integer), System(String), UserLog(String), 
1(Integer), 用户操作日志(String), /user-log/user-operate-log(String), 27(Integer), UserLog(String), UserOperateLog(String), 
1(Integer), 用户登录日志(String), /user-log/user-login-log(String), 28(Integer), UserLog(String), UserLoginLog(String), 
1(Integer), 系统监控(String), /monitor(String), 37(Integer), Support(String), Monitor(String), 
1(Integer), 在线人数(String), /monitor/online-user(String), 38(Integer), Monitor(String), OnlineUser(String), 
1(Integer), SQL监控(String), /monitor/sql(String), 39(Integer), Monitor(String), Sql(String), 
1(Integer), 定时任务(String), /task(String), 42(Integer), Support(String), Task(String), 
1(Integer), 任务管理(String), /system-setting/task-list(String), 43(Integer), Task(String), TaskList(String), 
1(Integer), 动态加载(String), /reload(String), 40(Integer), Support(String), Reload(String), 
1(Integer), SmartReload(String), /reload/smart-reload-list(String), 4
1(Integer), Reload(String), SmartReloadList(String), 
1(Integer), 接口文档(String), /api-doc(String), 33(Integer), Support(String), ApiDoc(String), 
1(Integer), Swagger接口文档(String), /api-doc/swagger(String), 34(Integer), ApiDoc(String), Swagger(String), 
1(Integer), 三级路由(String), /three-router(String), 14(Integer), Business(String), ThreeRouter(String), 
1(Integer), 三级菜单(String), /three-router/level-two(String), 15(Integer), ThreeRouter(String), LevelTwo(String), 
1(Integer), 三级菜单子哈(String), /three-router/level-two/level-three2(String), 17(Integer), LevelTwo(String), RoleTwoTwo(String), 
1(Integer), 二级菜单(String), /three-router/level-two2(String), 18(Integer), ThreeRouter(String), RoleOneOne(String), 
1(Integer), KeepAlive(String), /keep-alive(String), 7(Integer), Business(String), KeepAlive(String), 
1(Integer), KeepAlive列表(String), /keep-alive/content-list(String), 8(Integer), KeepAlive(String), KeepAliveContentList(String), 
1(Integer), KeepAlive表单(String), /keep-alive/add-content(String), 9(Integer), KeepAlive(String), KeepAliveAddContent(String), 
1(Integer), 心跳服务(String), /heart-beat(String), 35(Integer), Support(String), HeartBeat(String), 
1(Integer), 心跳服务(String), /heart-beat/heart-beat-list(String), 36(Integer), HeartBeat(String), HeartBeatList(String), 
1(Integer), 文件服务(String), /file(String), 24(Integer), System(String), File(String), 
1(Integer), 文件列表(String), /file/file-list(String), 25(Integer), File(String), FileList(String), 
1(Integer), 业务功能(String), /business(String), 0(Integer), Business(String), 
1(Integer), 牡丹管理(String), /peony(String), 1(Integer), Business(String), Peony(String),
1(Integer), 牡丹花列表(String), /peony/peony-list(String), 2(Integer), Peony(String), PeonyList(String),
1(Integer), 牡丹花列表1(String), /peony/peony-list1(String), 3(Integer), Peony(String), PeonyList1(String),
1(Integer), 消息详情(String), /notice/notice-detail(String), 13(Integer), Notice(String), NoticeDetail(String), 
1(Integer), 三级菜单子颗粒(String), /three-router/level-two/level-three1(String), 16(Integer), LevelTwo(String), ThreeLevelRouterView(String), 
1(Integer), 系统设置(String), /system(String), 19(Integer), System(String), 
1(Integer), 开发专用(String), /support(String), 32(Integer), Support(String)

 
 
UPDATE `privilege` SET `type`=?,`name`=?,`url`=?,`sort`=?,parent_key=?,update_time=now() WHERE `key` = ?          ;
UPDATE `privilege` SET `type`=?,`name`=?,`url`=?,`sort`=?,parent_key=?,update_time=now() WHERE `key` = ?          ;
UPDATE `privilege` SET `type`=?,`name`=?,`url`=?,`sort`=?,parent_key=?,update_time=now() WHERE `key` = ?          ;
UPDATE `privilege` SET `type`=?,`name`=?,`url`=?,`sort`=?,parent_key=?,update_time=now() WHERE `key` = ?          ;
UPDATE `privilege` SET `type`=?,`name`=?,`url`=?,`sort`=?,parent_key=?,update_time=now() WHERE `key` = ?          ;
UPDATE `privilege` SET `type`=?,`name`=?,`url`=?,`sort`=?,parent_key=?,update_time=now() WHERE `key` = ?          ;
UPDATE `privilege` SET `type`=?,`name`=?,`url`=?,`sort`=?,parent_key=?,update_time=now() WHERE `key` = ?          ;
UPDATE `privilege` SET `type`=?,`name`=?,`url`=?,`sort`=?,parent_key=?,update_time=now() WHERE `key` = ?          ;
UPDATE `privilege` SET `type`=?,`name`=?,`url`=?,`sort`=?,parent_key=?,update_time=now() WHERE `key` = ?          ;
UPDATE `privilege` SET `type`=?,`name`=?,`url`=?,`sort`=?,parent_key=?,update_time=now() WHERE `key` = ?          ;
UPDATE `privilege` SET `type`=?,`name`=?,`url`=?,`sort`=?,parent_key=?,update_time=now() WHERE `key` = ?          ;
UPDATE `privilege` SET `type`=?,`name`=?,`url`=?,`sort`=?,parent_key=?,update_time=now() WHERE `key` = ?          ;
UPDATE `privilege` SET `type`=?,`name`=?,`url`=?,`sort`=?,parent_key=?,update_time=now() WHERE `key` = ?          ;
UPDATE `privilege` SET `type`=?,`name`=?,`url`=?,`sort`=?,parent_key=?,update_time=now() WHERE `key` = ?          ;
UPDATE `privilege` SET `type`=?,`name`=?,`url`=?,`sort`=?,parent_key=?,update_time=now() WHERE `key` = ?          ;
UPDATE `privilege` SET `type`=?,`name`=?,`url`=?,`sort`=?,parent_key=?,update_time=now() WHERE `key` = ?          ;
UPDATE `privilege` SET `type`=?,`name`=?,`url`=?,`sort`=?,parent_key=?,update_time=now() WHERE `key` = ?          ;
UPDATE `privilege` SET `type`=?,`name`=?,`url`=?,`sort`=?,parent_key=?,update_time=now() WHERE `key` = ?          ;
UPDATE `privilege` SET `type`=?,`name`=?,`url`=?,`sort`=?,parent_key=?,update_time=now() WHERE `key` = ?          ;
UPDATE `privilege` SET `type`=?,`name`=?,`url`=?,`sort`=?,parent_key=?,update_time=now() WHERE `key` = ?          ;
UPDATE `privilege` SET `type`=?,`name`=?,`url`=?,`sort`=?,parent_key=?,update_time=now() WHERE `key` = ?          ;
UPDATE `privilege` SET `type`=?,`name`=?,`url`=?,`sort`=?,parent_key=?,update_time=now() WHERE `key` = ?          ;
UPDATE `privilege` SET `type`=?,`name`=?,`url`=?,`sort`=?,parent_key=?,update_time=now() WHERE `key` = ?          ;
UPDATE `privilege` SET `type`=?,`name`=?,`url`=?,`sort`=?,parent_key=?,update_time=now() WHERE `key` = ?          ;
UPDATE `privilege` SET `type`=?,`name`=?,`url`=?,`sort`=?,parent_key=?,update_time=now() WHERE `key` = ?          ;
UPDATE `privilege` SET `type`=?,`name`=?,`url`=?,`sort`=?,parent_key=?,update_time=now() WHERE `key` = ?          ;
UPDATE `privilege` SET `type`=?,`name`=?,`url`=?,`sort`=?,parent_key=?,update_time=now() WHERE `key` = ?          ;
UPDATE `privilege` SET `type`=?,`name`=?,`url`=?,`sort`=?,parent_key=?,update_time=now() WHERE `key` = ?          ;
UPDATE `privilege` SET `type`=?,`name`=?,`url`=?,`sort`=?,parent_key=?,update_time=now() WHERE `key` = ?          ;
UPDATE `privilege` SET `type`=?,`name`=?,`url`=?,`sort`=?,parent_key=?,update_time=now() WHERE `key` = ?          ;
UPDATE `privilege` SET `type`=?,`name`=?,`url`=?,`sort`=?,parent_key=?,update_time=now() WHERE `key` = ?          ;
UPDATE `privilege` SET `type`=?,`name`=?,`url`=?,`sort`=?,parent_key=?,update_time=now() WHERE `key` = ?          ;
UPDATE `privilege` SET `type`=?,`name`=?,`url`=?,`sort`=?,parent_key=?,update_time=now() WHERE `key` = ?          ;
UPDATE `privilege` SET `type`=?,`name`=?,`url`=?,`sort`=?,parent_key=?,update_time=now() WHERE `key` = ?          ;
UPDATE `privilege` SET `type`=?,`name`=?,`url`=?,`sort`=?,parent_key=?,update_time=now() WHERE `key` = ?          ;
UPDATE `privilege` SET `type`=?,`name`=?,`url`=?,`sort`=?,parent_key=?,update_time=now() WHERE `key` = ?          ;
UPDATE `privilege` SET `type`=?,`name`=?,`url`=?,`sort`=?,update_time=now() WHERE `key` = ?          ;
1(Integer), 牡丹管理(String), /peony(String), 1(Integer), Business(String), Peony(String),

    UPDATE `privilege` SET `type`=?,`name`=?,`url`=?,`sort`=?,parent_key=?,update_time=now() WHERE `key` = ?          ;
UPDATE `privilege` SET `type`=?,`name`=?,`url`=?,`sort`=?,parent_key=?,update_time=now() WHERE `key` = ?          ;
UPDATE `privilege` SET `type`=?,`name`=?,`url`=?,`sort`=?,parent_key=?,update_time=now() WHERE `key` = ?          ;
UPDATE `privilege` SET `type`=?,`name`=?,`url`=?,`sort`=?,parent_key=?,update_time=now() WHERE `key` = ?          ;
UPDATE `privilege` SET `type`=?,`name`=?,`url`=?,`sort`=?,parent_key=?,update_time=now() WHERE `key` = ?          ;
UPDATE `privilege` SET `type`=?,`name`=?,`url`=?,`sort`=?,update_time=now() WHERE `key` = ?          ;
UPDATE `privilege` SET `type`=?,`name`=?,`url`=?,`sort`=?,update_time=now() WHERE `key` = ?
