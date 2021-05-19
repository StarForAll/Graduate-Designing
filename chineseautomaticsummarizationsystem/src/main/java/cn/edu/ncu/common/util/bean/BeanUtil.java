package cn.edu.ncu.common.util.bean;

/**
 * @Author: XiongZhiCong
 * @Description:
 * @Date: Created in 15:13 2021/4/8
 * @Modified By:
 */
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.edu.ncu.common.core.exception.BeanException;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.beanutils.converters.IntegerConverter;
import org.apache.commons.collections.CollectionUtils;
import org.junit.Assert;
import org.springframework.beans.BeanUtils;
import com.alibaba.fastjson.JSON;
/**
 * @Author: XiongZhiCong
 * @Description: 对象转化工具类
 * @Date: Created in 14:54 2021/4/8
 * @Modified By:
 */
public class BeanUtil {

    /**
     * 方法说明：将bean转化为另一种bean实体
     * @param object
     * @param entityClass
     * @param <T>
     * @return
     */
    public static <T> T convertBean(Object object, Class<T> entityClass) {
        if(null == object) {
            return null;
        }
        return JSON.parseObject(JSON.toJSONString(object), entityClass);
    }


    /**
     * 方法说明：对象转换
     *
     * @param source 原对象
     * @param target 目标对象
     * @return
     */
    public static <T> T copy(Object source, Class<T> target){
        T targetInstance = null;
        try {
            targetInstance = target.newInstance();
        } catch (Exception e) {
            throw new BeanException("错误", e);
        }
        BeanUtils.copyProperties(source, targetInstance);

        return targetInstance;
    }


    public static <T> void copy(Object source, Object target){
        try {
            Assert.assertNotNull("源对象不允许为空",source);
            Assert.assertNotNull("目标对象不允许为空",target);
            BeanUtils.copyProperties(source, target);
        } catch (Exception e) {
            throw new BeanException("copyProperties 错误", e);
        }

    }

    /**
     *  如果空对象则不拷贝，如果目标空 源不空则可以考虑，否则执行基础拷贝
     * @param source
     * @param target
     * @param targetClass
     * @param <T>
     */
    public static <T> void copy(Object source, Object target,Class<T> targetClass){
        try {
            if(source==null || target==null) {
                return;
            }
            // 完成拷贝
            if(source!=null && target==null){
                target=copy(source,targetClass);
                return ;
            }
            BeanUtils.copyProperties(source, target);
        } catch (Exception e) {
            throw new BeanException("copyProperties 错误", e);
        }

    }
    /**
     * 方法说明：对象转换(List)
     * fix: 2020/11/22 修复了分页信息total字段不准确的bug
     *      调用PageHelper的分页函数后，进行数据库查询返回的List
     *      实际是Page对象不是ArrayList对象，Page类是List的一个实现类
     *      该类扩展了一些分页信息，所以使用copyList的时候不能直接new ArrayList
     *      否则会导致分页信息丢失，导致total字段永远等于 ArrayList.size()
     * @param list 原对象
     * @param target 目标对象
     * @return 可能为空
     */
    public static <T, E> List<T> copyList(List<E> list, Class<T> target){
        List targetList = copy(list, list.getClass());
        targetList.clear();
        if(CollectionUtils.isEmpty(list)) {
            return targetList;
        }
        for(E e : list) {
            T object=copy(e, target);
            targetList.add(object);
        }
        return targetList;
    }

    /**
     * 允许用户自定义深度拷贝
     */
    public static interface CopyCallback{
        /**
         *  改接口在拷贝过程被回调
         * @param source 原对象
         * @param target 目标对象
         */
        void copy(Object source,Object target);
    }

    /**
     *
     * @param list 原链表
     * @param target 目标对象类型
     * @param copyCallback 链表内部对象拷贝自定义
     * @param <T>
     * @param <E>
     * @return
     */
    public static <T, E> List<T> copyList(List<E> list, Class<T> target,CopyCallback copyCallback){
        Assert.assertNotNull("原对象列表不允许为空",list);
        List<T> targetList = new ArrayList<>();
        if(CollectionUtils.isEmpty(list)) {
            return targetList;
        }
        for(E e : list) {
            T object=copy(e, target);
            targetList.add(object);
            if(copyCallback!=null) {
                copyCallback.copy(e,object);
            }
        }
        return targetList;
    }

    /**
     * 方法说明：map转化为对象
     *
     * @param map
     * @param t
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    public static <T> T mapToObject(Map<String, Object> map, Class<T> t) {
        T instance = null;
        try {
            instance = t.newInstance();
        } catch (Exception e) {
            throw new BeanException("错误", e);
        }
        //时间数据格式对象
        DateConverter converter=new DateConverter();
        String[] pattern=new String[3];
        pattern[0]="yyyy-MM-dd HH:mm:ss";
        pattern[1]="yyyy-MM-dd";
        pattern[2]="yyyy/MM/dd";
        converter.setPatterns(pattern);
        //注册Date时间对象格式 之后可String --> Date
        ConvertUtils.register(converter, Date.class);

        IntegerConverter integerConverter=new IntegerConverter(null);
        ConvertUtils.register(integerConverter, Integer.class);

        //开始复制数据信息
        //遍历map<key, value>中的key，如果bean中有这个属性，就把这个key对应的value值赋给bean的属性
        try{
            org.apache.commons.beanutils.BeanUtils.populate(instance, map);
        }catch(Exception e){
            throw new BeanException("错误", e);
        }

        return instance;
    }

    /**
     * 方法说明：对象转化为Map
     *
     * @param object
     * @return
     */
    public static Map<?, ?> objectToMap(Object object){
        return convertBean(object, Map.class);
    }
}


