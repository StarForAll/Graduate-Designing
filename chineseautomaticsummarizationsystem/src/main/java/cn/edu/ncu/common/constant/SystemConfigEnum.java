package cn.edu.ncu.common.constant;


import java.util.Arrays;
import java.util.Optional;

/**
 * @Author: XiongZhiCong
 * @Description: [ 系统配置常量类 ]
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
public class SystemConfigEnum {

    public enum Group {
        BACK,
        GIT_LOG
    }

    public enum Key {
        /**
         * 超管id
         */
        EMPLOYEE_SUPERMAN(SystemConfigDataType.TEXT),
        /**
         * 阿里云OSS配置项
         */
        ALI_OSS(SystemConfigDataType.JSON),
        /**
         * 七牛云OSS配置项
         */
        QI_NIU_OSS(SystemConfigDataType.JSON),
        /**
         * 本地文件上传url前缀
         */
        LOCAL_UPLOAD_URL_PREFIX(SystemConfigDataType.URL),
        /**
         * 邮件配置
         */
        EMAIL_CONFIG(SystemConfigDataType.JSON),
        /**
         * git-log 插件
         */
        GIT_LOG_PLUGIN(SystemConfigDataType.JSON),


        WORDS_TYPE(SystemConfigDataType.TEXT),
        UPLOAD_PAPER_IMAGE(SystemConfigDataType.URL),
        UPLOAD_PAPER_TXT(null);
        private SystemConfigDataType dataType;

        Key(SystemConfigDataType dataType) {
            this.dataType = dataType;
        }


        public SystemConfigDataType getDataType() {
            return dataType;
        }

        public static Key selectByKey(String key) {
            Key[] values = Key.values();
            Optional<Key> first = Arrays.stream(values).filter(e -> e.name().equalsIgnoreCase(key)).findFirst();
            return first.orElse(null);
        }
    }

}
