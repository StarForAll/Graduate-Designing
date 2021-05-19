package mybatis.generator;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @class OMP_BaseData_Generator
 * @classdesc
 * @author Administrator
 * @date 2020-6-25  8:29
 * @version 1.0.0
 * @see
 * @since
 */
@Slf4j
public class OMP_BaseData_Generator {
    public static InputStream getResourceAsStream(String path){
        return Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
    }
    /**
     * @param: 系统传输
     * @return: 无
     * @see
     * @since
     */
    public static void main(String[] args) throws Exception {
        List<String> warnings = new ArrayList<String>();
        boolean overwrite = true;
        ConfigurationParser cp = new ConfigurationParser(warnings);
        //read local config file in resource directory
        Configuration config = cp.parseConfiguration(getResourceAsStream("mybatis/generatorConfig.xml"));
        DefaultShellCallback callback = new DefaultShellCallback(overwrite);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
        // generat code in pre-define file which on resource/mybatis/generator-config.xml
        myBatisGenerator.generate(null);
        for (String warning : warnings) {
            log.info(warning);
        }
    }
}