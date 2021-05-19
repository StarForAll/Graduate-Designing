package cn.edu.ncu.service;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;


/**
 * @Author: XiongZhiCong
 * @Description: 特殊情况下Autowired注解不会起作用 
 * @Date: Created in 14:16 2021/4/8
 * @Modified By:
 */
@Component
public class ApplicationContextHolder implements ApplicationContextAware {
    /**
     * 上下文对象实例
     */
    private static ApplicationContext applicationContext = null;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if(ApplicationContextHolder.applicationContext == null){

            ApplicationContextHolder.applicationContext  = applicationContext;

        }
    }

    /**
     * 获取applicationContext
     * @return
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 通过name获取 Bean.
     * @param name
     * @return
     */
    public static Object getBean(String name){
        ApplicationContext applicationContext = getApplicationContext();
        if(applicationContext == null){
            return null;
        }
        return applicationContext.getBean(name);
    }

    /**
     * 通过class获取Bean.
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T getBean(Class<T> clazz){
        ApplicationContext applicationContext = getApplicationContext();
        if(applicationContext == null){
            return null;
        }
        return applicationContext.getBean(clazz);
    }

    /**
     * 通过name,以及Clazz返回指定的Bean
     * @param name
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T getBean(String name,Class<T> clazz){
        ApplicationContext applicationContext = getApplicationContext();
        if(applicationContext == null){
            return null;
        }
        return applicationContext.getBean(name, clazz);
    }
}