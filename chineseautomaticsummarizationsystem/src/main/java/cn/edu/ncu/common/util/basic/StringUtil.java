package cn.edu.ncu.common.util.basic;

import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * @Author: XiongZhiCong
 * @Description: 字符串操作工具类
 * @Date: Created in 14:54 2021/4/8
 * @Modified By:
 */
public class StringUtil {

    /**
     * 字符串为空判断
     * @param str 字符串
     * @return
     */
    public static boolean isEmpty(String str){
        return StringUtils.isEmpty(str);
    }
    /**
     * 字符串非空判断
     * @param str
     * @return
     */
    public static boolean isNotEmpty(String str){
        return StringUtils.isNotEmpty(str);
    }

    /**
     * 字符串数组中只有有一个字符串为空则返回true
     * @param strings
     * @return
     */
    public static boolean isAnyEmpty(String... strings){
        return StringUtils.isAnyEmpty();
    }

    /**
     * 当所有的字符串不为空时返回true
     * @param strings
     * @return
     */
    public static boolean isNoneEmpty(String... strings){
        return StringUtils.isNoneEmpty();
    }

    /**
     * 判断字符串是否为""
     * @param str
     * @return
     */
    public static boolean isBlank(String str){
        return StringUtils.isBlank(str);
    }

    /**
     * 字符串是否非""
     * @param str
     * @return
     */
    public static boolean isNotBlank(String str){
        return StringUtils.isNotBlank(str);
    }


    /**
     * 任意一个字符串对象为空字符串的话，返回true，都不为空字符串返回false
     * @param strings
     * @return
     */
    public static boolean isAnyBlank(String... strings){
        return StringUtils.isAnyBlank(strings);
    }

    /**
     * 任意一个字符串对象为空字符串的话，返回false，都不为空字符串，返回true
     * @param strings
     * @return
     */
    public static boolean isNoneBlank(String... strings){
        return StringUtils.isNoneBlank(strings);
    }

    /**
     * 移除字符串两端的空字符串
     * @param str
     * @return
     */
    public static String trim(String str){
        return StringUtils.trim(str);
    }

    /**
     * 字符串比对方法，两个比较的字符串都能为空，不会报空指针异常
     * @param str1
     * @param str2
     * @return
     */
    public static boolean equals(String str1, String str2){
        return StringUtils.equals(str1, str2);
    }

    /**
     * 字符串比对方法，忽略大小写
     * @param str1
     * @param str2
     * @return
     */
    public static boolean equalsIgnoreCase(String str1, String str2){
        return StringUtils.equalsIgnoreCase(str1, str2);
    }

    /**
     * 查找字符对应字符串中首次出现的下标位置
     * @param str
     * @param searchChar
     * @return
     */
    public static int indexOf(String str, int searchChar){
        return StringUtils.indexOf(str, searchChar);
    }

    /**
     * 字符串在另外一个字符串里，出现第ordinal次的位置
     * @param str
     * @param searchStr
     * @param ordinal
     * @return
     */
    public static int ordinalIndexOf(String str, String searchStr, int ordinal){
        return  StringUtils.ordinalIndexOf(str, searchStr, ordinal);
    }

    /**
     * 字符最后一次出现的位置
     * @param str
     * @param searchChar
     * @return
     */
    public static int lastIndexOf(String str, int searchChar){
        return StringUtils.lastIndexOf(str, searchChar);
    }
    /**
     * 字符串searchStr在str里面出现倒数第ordinal出现的位置
     * @param str
     * @param searchStr
     * @param ordinal
     * @return
     */
    public static int lastOrdinalIndexOf(String str, String searchStr, int ordinal){
        return StringUtils.lastOrdinalIndexOf(str, searchStr, ordinal);
    }

    //字符串str是否包含searchChar
    public static boolean contains(String str, String searchChar){
        return StringUtils.contains(str, searchChar);
    }


    //字符串str包含后面数组中的任意对象，返回true
    public static boolean containsAny(String str, char... searchChars){
        return StringUtils.containsAny(str, searchChars);
    }

    //字符串截取
    public static String substring(String str, int start){
        return StringUtils.substring(str, start);
    }

    //字符串截取
    public static String substring(String str, int start, int end){
        return StringUtils.substring(str, start, end);
    }

    //字符串分割
    public static String[] split(String str, String separatorChars){
        return StringUtils.split(str, separatorChars);
    }

    //字符串连接
    public static <T> String join(T... elements){
        return StringUtils.join(elements);
    }
    public static String join(Iterable<?> iterable, String separator) {
        return iterable == null ? null : join(iterable.iterator(), separator);
    }


    public static String join(Iterator<?> iterator, String separator) {
        if (iterator == null) {
            return null;
        } else if (!iterator.hasNext()) {
            return "";
        } else {
            Object first = iterator.next();
            if (!iterator.hasNext()) {
                String result = toString(first);
                return result;
            } else {
                StringBuilder buf = new StringBuilder(256);
                if (first != null) {
                    buf.append(first);
                }

                while(iterator.hasNext()) {
                    if (separator != null) {
                        buf.append(separator);
                    }

                    Object obj = iterator.next();
                    if (obj != null) {
                        buf.append(obj);
                    }
                }

                return buf.toString();
            }
        }
    }
    public static String toString(Object obj) {
        return obj == null ? "" : obj.toString();
    }
    //将数组相邻元素间插入特定字符并返回所得字符串
    public static String join(Object[] array, char separator){
        return StringUtils.join(array, separator);
    }

    //将数组相邻元素间插入特定字符串并返回所得字符串
    public static String join(Object[] array, String separator){
        return StringUtils.join(array, separator);
    }

    //删除空格
    public static String deleteWhitespace(String str){
        return StringUtils.deleteWhitespace(str);
    }

    //删除以特定字符串开头的字符串，如果没有的话，就不删除
    public static String removeStart(String str, String remove){
        return StringUtils.removeStart(str, remove);
    }

    //字符串右边自动以padChar补齐
    public static String rightPad(String str,int size,char padChar){
        return StringUtils.rightPad(str,size,padChar);
    }

    //左边自动补齐
    public static String leftPad(String str, int size,char padChar){
        return StringUtils.leftPad(str, size, padChar);
    }

    //首字母大写
    public static String capitalize(String str){
        return StringUtils.capitalize(str);
    }

    //反向大小写
    public static String swapCase(String str){
        return StringUtils.swapCase(str);
    }

    //判断字符串是否由字母组成
    public static boolean isAlpha(String str){
        return StringUtils.isAlpha(str);
    }

    //字符串翻转
    public static String reverse(String str){
        return StringUtils.reverse(str);
    }

    // 包装，用后面的字符串对前面的字符串进行包装
    public static String wrap(String str, char wrapWith){
        return StringUtils.wrap(str, wrapWith);
    }

    // ===============split =======================

    public static Set<String> splitConvertToSet(String str, String split) {
        if (isEmpty(str)) {
            return new HashSet<String>();
        }
        String[] splitArr = str.split(split);
        HashSet<String> set = new HashSet<String>(splitArr.length);
        for (String string : splitArr) {
            set.add(string);
        }
        return set;
    }

    public static List<String> splitConvertToList(String str, String split) {
        if (isEmpty(str)) {
            return new ArrayList<String>();
        }
        String[] splitArr = str.split(split);
        ArrayList<String> list = new ArrayList<String>(splitArr.length);
        for (String string : splitArr) {
            list.add(string);
        }
        return list;
    }

    // ===============split Integer=======================

    public static List<Integer> splitConverToIntList(String str, String split, int defaultVal) {
        if (isEmpty(str)) {
            return new ArrayList<Integer>();
        }
        String[] strArr = str.split(split);
        List<Integer> list = new ArrayList<Integer>(strArr.length);
        for (int i = 0; i < strArr.length; i++) {
            try {
                int parseInt = Integer.parseInt(strArr[i]);
                list.add(parseInt);
            } catch (NumberFormatException e) {
                list.add(defaultVal);
                continue;
            }
        }
        return list;
    }

    public static Set<Integer> splitConverToIntSet(String str, String split, int defaultVal) {
        if (isEmpty(str)) {
            return new HashSet<Integer>();
        }
        String[] strArr = str.split(split);
        HashSet<Integer> set = new HashSet<Integer>(strArr.length);
        for (int i = 0; i < strArr.length; i++) {
            try {
                int parseInt = Integer.parseInt(strArr[i]);
                set.add(parseInt);
            } catch (NumberFormatException e) {
                set.add(defaultVal);
                continue;
            }
        }
        return set;
    }

    public static Set<Integer> splitConverToIntSet(String str, String split) {
        return splitConverToIntSet(str, split, 0);
    }

    public static List<Integer> splitConverToIntList(String str, String split) {
        return splitConverToIntList(str, split, 0);
    }

    public static int[] splitConvertToIntArray(String str, String split, int defaultVal) {
        if (isEmpty(str)) {
            return new int[0];
        }
        String[] strArr = str.split(split);
        int[] result = new int[strArr.length];
        for (int i = 0; i < strArr.length; i++) {
            try {
                result[i] = Integer.parseInt(strArr[i]);
            } catch (NumberFormatException e) {
                result[i] = defaultVal;
                continue;
            }
        }
        return result;
    }

    public static int[] splitConvertToIntArray(String str, String split) {
        return splitConvertToIntArray(str, split, 0);
    }

    // ===============split 2 Long=======================

    public static List<Long> splitConverToLongList(String str, String split, long defaultVal) {
        if (isEmpty(str)) {
            return new ArrayList<Long>();
        }
        String[] strArr = str.split(split);
        List<Long> list = new ArrayList<Long>(strArr.length);
        for (int i = 0; i < strArr.length; i++) {
            try {
                long parseLong = Long.parseLong(strArr[i]);
                list.add(parseLong);
            } catch (NumberFormatException e) {
                list.add(defaultVal);
                continue;
            }
        }
        return list;
    }

    public static List<Long> splitConverToLongList(String str, String split) {
        return splitConverToLongList(str, split, 0L);
    }

    public static long[] splitConvertToLongArray(String str, String split, long defaultVal) {
        if (isEmpty(str)) {
            return new long[0];
        }
        String[] strArr = str.split(split);
        long[] result = new long[strArr.length];
        for (int i = 0; i < strArr.length; i++) {
            try {
                result[i] = Long.parseLong(strArr[i]);
            } catch (NumberFormatException e) {
                result[i] = defaultVal;
                continue;
            }
        }
        return result;
    }

    public static long[] splitConvertToLongArray(String str, String split) {
        return splitConvertToLongArray(str, split, 0L);
    }

    // ===============split convert byte=======================

    public static List<Byte> splitConverToByteList(String str, String split, byte defaultVal) {
        if (isEmpty(str)) {
            return new ArrayList<Byte>();
        }
        String[] strArr = str.split(split);
        List<Byte> list = new ArrayList<Byte>(strArr.length);
        for (int i = 0; i < strArr.length; i++) {
            try {
                byte parseByte = Byte.parseByte(strArr[i]);
                list.add(parseByte);
            } catch (NumberFormatException e) {
                list.add(defaultVal);
                continue;
            }
        }
        return list;
    }

    public static List<Byte> splitConverToByteList(String str, String split) {
        return splitConverToByteList(str, split, (byte) 0);
    }

    public static byte[] splitConvertToByteArray(String str, String split, byte defaultVal) {
        if (isEmpty(str)) {
            return new byte[0];
        }
        String[] strArr = str.split(split);
        byte[] result = new byte[strArr.length];
        for (int i = 0; i < strArr.length; i++) {
            try {
                result[i] = Byte.parseByte(strArr[i]);
            } catch (NumberFormatException e) {
                result[i] = defaultVal;
                continue;
            }
        }
        return result;
    }

    public static byte[] splitConvertToByteArray(String str, String split) {
        return splitConvertToByteArray(str, split, (byte) 0);
    }

    // ===============split convert double=======================

    public static List<Double> splitConverToDoubleList(String str, String split, double defaultVal) {
        if (isEmpty(str)) {
            return new ArrayList<Double>();
        }
        String[] strArr = str.split(split);
        List<Double> list = new ArrayList<Double>(strArr.length);
        for (int i = 0; i < strArr.length; i++) {
            try {
                double parseByte = Double.parseDouble(strArr[i]);
                list.add(parseByte);
            } catch (NumberFormatException e) {
                list.add(defaultVal);
                continue;
            }
        }
        return list;
    }

    public static List<Double> splitConverToDoubleList(String str, String split) {
        return splitConverToDoubleList(str, split, 0);
    }

    public static double[] splitConvertToDoubleArray(String str, String split, double defaultVal) {
        if (isEmpty(str)) {
            return new double[0];
        }
        String[] strArr = str.split(split);
        double[] result = new double[strArr.length];
        for (int i = 0; i < strArr.length; i++) {
            try {
                result[i] = Double.parseDouble(strArr[i]);
            } catch (NumberFormatException e) {
                result[i] = defaultVal;
                continue;
            }
        }
        return result;
    }

    public static double[] splitConvertToDoubleArray(String str, String split) {
        return splitConvertToDoubleArray(str, split, 0);
    }

    // ===============solit convert float=======================

    public static List<Float> splitConverToFloatList(String str, String split, float defaultVal) {
        if (isEmpty(str)) {
            return new ArrayList<Float>();
        }
        String[] strArr = str.split(split);
        List<Float> list = new ArrayList<Float>(strArr.length);
        for (int i = 0; i < strArr.length; i++) {
            try {
                float parseByte = Float.parseFloat(strArr[i]);
                list.add(parseByte);
            } catch (NumberFormatException e) {
                list.add(defaultVal);
                continue;
            }
        }
        return list;
    }

    public static List<Float> splitConverToFloatList(String str, String split) {
        return splitConverToFloatList(str, split, 0f);
    }

    public static float[] splitConvertToFloatArray(String str, String split, float defaultVal) {
        if (isEmpty(str)) {
            return new float[0];
        }
        String[] strArr = str.split(split);
        float[] result = new float[strArr.length];
        for (int i = 0; i < strArr.length; i++) {
            try {
                result[i] = Float.parseFloat(strArr[i]);
            } catch (NumberFormatException e) {
                result[i] = defaultVal;
                continue;
            }
        }
        return result;
    }

    public static float[] splitConvertToFloatArray(String str, String split) {
        return splitConvertToFloatArray(str, split, 0f);
    }

    // ===============upperCase=======================

    /**
     * 将首字母大写
     *
     * @param str
     * @return
     */
    public static String upperCaseFirstChar(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        char firstChar = str.charAt(0);
        if (Character.isUpperCase(firstChar)) {
            return str;
        }
        char[] values = str.toCharArray();
        values[0] = Character.toUpperCase(firstChar);
        return new String(values);
    }

}
