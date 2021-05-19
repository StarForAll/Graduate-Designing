package cn.edu.ncu.common.util.basic;

import cn.edu.ncu.pojo.dto.ThesaurusDTO;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * @Author: XiongZhiCong
 * @Description:
 * @Date: Created in 10:13 2021/4/9
 * @Modified By:
 */
public class ListUtil {
    /**
     * @方法描述：获取两个ArrayList的差集
     * @param firstArrayList 第一个ArrayList
     * @param secondArrayList 第二个ArrayList
     * @return resultList 差集ArrayList
     */
    public static List<String> receiveDefectList(List<String> firstArrayList, List<String> secondArrayList) {
        // 大集合用linkedlist
        LinkedList<String> result = new LinkedList<>(firstArrayList);
        // 小集合用hashset
        HashSet<String> othHash = new HashSet<>(secondArrayList);
        // 采用Iterator迭代器进行数据的操作
        result.removeIf(othHash::contains);
        return new ArrayList<>(result);
    }

    /**
     * 去重重复的元素
     * @param list
     * @return
     */
    public static List<String> removeDuplication(List<String> list){
        Set<String> result = new HashSet<>(list);
        return new ArrayList(result);
    }

    /**
     * list转换成字符串
     * @param list 集合
     * @param separator 每个元素间的分隔符
     * @return
     */
    public static String listToString(List list, char separator){
        return StringUtils.join(list.toArray(), separator);
    }
}
