package cn.edu.ncu.common.util.basic;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.http.entity.ContentType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * @Author: XiongZhiCong
 * @Description: 文件操作工具类
 * @Date: Created in 15:13 2021/4/8
 * @Modified By:
 */
public class FileUtil {
    public static MultipartFile fileToMultipartFile(File file) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(file);
        return new MockMultipartFile(file.getName(), file.getName(),
                ContentType.APPLICATION_OCTET_STREAM.toString(), fileInputStream);
    }
    /**
     * 验证文件是否存在，如果不存在则抛出异常
     *
     * @param filePath
     * @throws IOException
     */
    public static void isFileExistThrowException(String filePath) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            throw new FileNotFoundException(filePath);
        }
    }
    /**
     * 将url内容复制到文件
     *
     * @param source            url
     * @param destination       文件目录
     * @param connectionTimeout 连接超时，单位毫秒
     * @param readTimeout       读取超时，单位毫秒
     * @throws IOException
     */
    public static void copyUrlToFile(String source, String destination, int connectionTimeout, int readTimeout) throws IOException {
        FileUtils.copyURLToFile(new URL(source), new File(destination), connectionTimeout, readTimeout);
    }

    /**
     * 将文件A的内容复制到文件B
     *
     * @param fromPath
     * @param toPath
     * @throws IOException
     */
    public static void copyFile(String fromPath, String toPath) throws IOException {
        InputStream inputStream;
        File file = new File(toPath);
        //用字节流的方式cope文件
        File file2 = new File(fromPath);
        FileUtils.copyFile(file2, file);
    }

    /**
     * 将文件A的复制到文件B
     *
     * @param fromPath
     * @param toPath
     * @param preserveFileDate 是否保留日期,true保留日期，false不保留日期
     * @throws IOException
     */
    public static void copyFile(String fromPath, String toPath, boolean preserveFileDate) throws IOException {
        FileUtils.copyFile(new File(fromPath), new File(toPath), preserveFileDate);
    }

    /**
     * 将文件复制到其他文件夹中
     *
     * @param sourceFile
     * @param destinationDir
     * @param preserveFileDate 是否保留日期,true保留日期，false不保留日期
     * @throws IOException
     */
    public static void copyFileToDirectory(String sourceFile, String destinationDir, boolean preserveFileDate) throws IOException {
        FileUtils.copyFileToDirectory(new File(sourceFile), new File(destinationDir), preserveFileDate);
    }

    /**
     * 复制文件夹
     *
     * @param srcDir           源文件夹
     * @param destDir          目标文件夹
     * @param preserveFileDate 是否保留日期,true保留日期，false不保留日期
     */
    public static void copyDirectory(String srcDir, String destDir, boolean preserveFileDate) throws IOException {
        FileUtils.copyDirectory(new File(srcDir), new File(destDir), preserveFileDate);
    }

    /**
     * 在给定目录（及其子目录）中查找与扩展名数组匹配的文件
     *
     * @param folderPath 文件夹路径
     * @param suffix     要查找的文件后缀名
     * @param b          是否查找子目录
     * @return 符合条件的文件路径
     */
    public static File[] searchListFilesAboutSuffix(String folderPath, String[] suffix, boolean b) {
        File file = new File(folderPath);
        return FileUtils.convertFileCollectionToFileArray(FileUtils.listFiles(file, suffix, b));
    }

    /**
     * 在给定目录（及其子目录）中查找文件
     *
     * @param folderPath    文件夹路径
     * @param fileName      要查找的文件名
     * @param subfolderName 如果需要在子目录查找，输入子目录的文件夹名，不需要的话传入null
     * @return 符合条件的文件路径
     */
    public static File[] searchListFilesAboutFileName(String folderPath, String fileName, String subfolderName) {
        File file = new File(folderPath);
        if (subfolderName == null) {
            return FileUtils.convertFileCollectionToFileArray(FileUtils.listFiles(file, FileFilterUtils.nameFileFilter(fileName), null));
        }
        return FileUtils.convertFileCollectionToFileArray(FileUtils.listFiles(file, FileFilterUtils.nameFileFilter(fileName), FileFilterUtils.nameFileFilter(subfolderName)));

    }

    /**
     * 删除目录下的所有文件及文件夹，不删除目录本身
     * @param folderPath 文件夹路径
     * @throws IOException 抛出io流异常
     */
    public static void cleanDirectory(String folderPath) throws IOException {
        File f = new File(folderPath);
        if (f.exists()) {
            FileUtils.cleanDirectory(f);
        }
    }

    /**
     * 删除目录本身，以及里面的所有内容：递归删除
     *
     * @param folderPath
     * @throws IOException
     */
    public static void deleteDirectory(String folderPath) throws IOException {
        File f = new File(folderPath);
        if (f.exists()) {
            FileUtils.deleteDirectory(f);
        }
    }

    /**
     * 删除文件,会抛出异常
     * @param filePath
     * @throws IOException
     */
    public static void forceDelete(String filePath) throws IOException {
        File f = new File(filePath);
        if (f.exists()) {
            FileUtils.forceDelete(f);
        }
    }


    /**
     * 删除文件,不会抛出异常
     *
     * @param filePath
     * @throws IOException
     */
    public static void deleteFile(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            FileUtils.deleteQuietly(file);
        }
    }

    /**
     * 确定child元素是否存在于directory目录中，child可以是文件或文件夹
     *
     * @param directory
     * @param child
     * @return
     * @throws IOException
     */
    public static boolean directoryContains(String directory, String child) throws IOException {
        return FileUtils.directoryContains(new File(directory), new File(child));
    }

    /**
     * 创建文件夹，如果他的父目录不存也会创建
     *
     * @param directory 文件夹路径
     * @throws IOException
     */
    public static void forceMkdir(String directory) throws IOException {
        FileUtils.forceMkdir(new File(directory));
    }


    /**
     * 返回用户主目录
     *
     * @return
     */
    public static File getUserDirectory() {
        return FileUtils.getUserDirectory();
    }

    /**
     * 比较文件filePath的修改时间（不是创建时间）是否比object指定的时间早
     *
     * @param filePath
     * @param object   3种类型  Date File Long
     * @return
     */
    public static boolean isFileNewer(String filePath, Object object) {
        if (object.getClass().equals(Date.class)) {
            return FileUtils.isFileNewer(new File(filePath), (Date) object);
        } else if (object.getClass().equals(File.class)) {
            return FileUtils.isFileNewer(new File(filePath), (File) object);
        } else if (object.getClass().equals(Long.class)) {
            return FileUtils.isFileNewer(new File(filePath), (Long) object);
        } else {
            System.out.println(object.getClass().getName() + "对象类型不符合要求" + object.toString());
        }
        return false;
    }

    /**
     * 比较文件filePath的修改时间（不是创建时间）是否比object指定的时间晚
     * @param filePath
     * @param object   3种类型  Date File Long
     * @return
     */
    public static boolean isFileOlder(String filePath, Object object) {
        if (object.getClass().equals(Date.class)) {
            return FileUtils.isFileOlder(new File(filePath), (Date) object);
        } else if (object.getClass().equals(File.class)) {
            return FileUtils.isFileOlder(new File(filePath), (File) object);
        } else if (object.getClass().equals(Long.class)) {
            return FileUtils.isFileOlder(new File(filePath), (Long) object);
        } else {
            System.out.println(object.getClass().getName() + "对象类型不符合要求" + object.toString());
        }
        return false;
    }

    /**
     * 比较两个文件的内容是否相同
     * @param path1 文件A路径
     * @param path2 文件B路径
     * @param boo   是否忽略换行符
     * @return 返回布尔值
     * @throws IOException
     */
    public static boolean contentEquals(String path1, String path2, boolean boo) throws IOException {
        File file1 = new File(path1);
        File file2 = new File(path2);
        if (file1.exists() && file2.exists()) {
            //忽略换行符
            if (boo) {
                return FileUtils.contentEqualsIgnoreEOL(file1, file2, "utf-8");
            } else {
                return FileUtils.contentEquals(file1, file2);
            }
        } else {
            return false;
        }
    }

    /**
     * 确定指定的文件是链接而不是实际文件
     * @param file
     * @return
     */
    public static boolean isSymlink(String file) throws IOException{
        return FileUtils.isSymlink(new File(file));
    }

    /**
     * 移动目录 新目录可以重命名，且必须是不存在的目录
     * @param src
     * @param destDir
     * @throws IOException
     */
    public static void moveDirectory(String src, String destDir) throws IOException {
        FileUtils.moveDirectory(new File(src), new File(destDir));
    }

    /**
     * 将目录移动到另一个父目录下，另一个父目录必须存在
     * @param src
     * @param destDir
     * @param createDestDir
     * @throws IOException
     */
    public static void moveDirectoryToDirectory(String src, String destDir, boolean createDestDir) throws IOException {
        FileUtils.moveDirectoryToDirectory(new File(src), new File(destDir), createDestDir);
    }

    /**
     * 移动文件
     * @param src     源文件，不存在会报错
     * @param destDir 目标文件，可以重命名
     * @throws IOException
     */
    public static void moveFile(String src, String destDir) throws IOException {
        FileUtils.moveFile(new File(src), new File(destDir));
    }

    /**
     * 移动文件到目录
     * @param src           源文件，不存在会报错
     * @param destDir       目标文件夹，不存在会自动创建
     * @param createDestDir 保留日期
     * @throws IOException
     */
    public static void moveFileToDirectory(String src, String destDir, boolean createDestDir) throws IOException {
        FileUtils.moveFileToDirectory(new File(src), new File(destDir), createDestDir);
    }

    /**
     * 将文件或文件夹移动到目标目录
     * @param src           文件、文件夹，不存在会报错
     * @param destDir       目标文件夹，不存在会自动创建
     * @param createDestDir 保留日期
     * @throws IOException
     */
    public static void moveToDirectory(String src, String destDir, boolean createDestDir) throws IOException {
        FileUtils.moveToDirectory(new File(src), new File(destDir), createDestDir);
    }

    /**
     * 读取文件,返回字符串,继承自InputStream
     *
     * @param path
     * @param charsetName
     * @return
     * @throws IOException
     */
    public static String readFileToString(String path, Charset charsetName) throws IOException {
        return FileUtils.readFileToString(new File(path), charsetName);
    }

    /**
     * 按行读取文件,返回一个list数组
     *
     * @param path
     * @param charset
     * @return
     * @throws IOException
     */
    public static List<String> readLines(String path, Charset charset) throws IOException {
        return FileUtils.readLines(new File(path), charset);
    }

    /**
     * 返回指定文件或目录的大小（字节数）
     *
     * @param file
     * @return 返回BigInteger类型数值，超出64位时使用
     */
    public static BigInteger sizeOfAsBigInteger(String file) {
        return FileUtils.sizeOfAsBigInteger(new File(file));
    }

    /**
     * 返回指定文件或目录的大小（字节数）
     *
     * @param file
     * @return 返回long类型数值，未超出64位时使用
     */
    public static long sizeOf(String file) {
        return FileUtils.sizeOf(new File(file));
    }

    /**
     * 返回目录的大小（字节数）
     *
     * @param directory
     * @return
     */
    public static BigInteger sizeOfDirectoryAsBigInteger(String directory) {
        return FileUtils.sizeOfDirectoryAsBigInteger(new File(directory));
    }

    /**
     * 返回目录的大小（字节数）
     *
     * @param directory
     * @return
     */
    public static long sizeOfDirectory(String directory) {
        return FileUtils.sizeOfDirectory(new File(directory));
    }

    /**
     * 根据URL获取文件
     * 获取文件后，可以使用readFileToString方法读取文件内容
     *
     * @param url 比如：file:/Users/Shared/666/selenium
     * @return
     * @throws MalformedURLException
     */
    public static File toFile(URL url) throws MalformedURLException {
        return FileUtils.toFile(url);
    }

    /**
     * 根据URL获取文件
     *
     * @param url 可以传入多个new URL("file:/Users/Shared/666/selenium/api.txt")参数
     * @return 返回文件数组
     * @throws MalformedURLException
     */
    public static File[] toFiles(URL... url) {
        return FileUtils.toFiles(url);
    }


    /**
     * 修改文件、文件夹的修改时间（不是创建时间）
     * 文件不存在则新建
     * 文件夹不存在则新建父级目录
     *
     * @param file
     * @throws IOException
     */
    public static void touch(String file) throws IOException {
        FileUtils.touch(new File(file));
    }

    /**
     * File转换为URL
     * 可以传入多个new File()
     *
     * @param file
     * @return
     * @throws IOException
     */
    public static URL[] toUrls(File... file) throws IOException {
        return FileUtils.toURLs(file);
    }

    /**
     * 等待确定目标文件、文件夹是否创建
     * 在seconds秒内创建完成返回true
     *
     * @param file    文件、文件夹
     * @param seconds 秒
     * @return
     * @throws IOException
     */
    public static boolean waitFor(String file, int seconds) throws IOException {
        return FileUtils.waitFor(new File(file), seconds);
    }

    /**
     * 字符流写入文件，写字符串文本，用字符流
     * 继承自Writer,将FileOutputStream字符流转换为字节流写入文件
     * FileOutputStream参数true为追加写
     * 字节流写入中文不会有乱码
     * Charset传入编码格式StandardCharsets.UTF_8
     */
    public static void writeStringToFileWithIo(String file, String data, String charset, boolean append) throws IOException {
        OutputStreamWriter os = new OutputStreamWriter(new FileOutputStream(new File(file), append), charset);
        os.write(data);
        os.close();
    }

    /**
     * 字符串写入文件，优先使用此方法
     *
     * @param file
     * @param data
     * @param charset
     * @param append
     * @throws IOException
     */
    public static void writeStringToFile(String file, String data, String charset, boolean append) throws IOException {
        FileUtils.writeStringToFile(new File(file), data, charset, append);
    }

    /**
     * 字符串写入文件
     * 可以接收CharSequence类型的数据
     *
     * @param file        文件不存在自动创建
     * @param data
     * @param charsetName
     * @param append      是否追加写入
     * @throws IOException
     */
    public static void write(String file, CharSequence data, String charsetName, boolean append) throws IOException {
        FileUtils.write(new File(file), data, charsetName, append);
    }

    /**
     * 字符数组写入文件
     *
     * @param file
     * @param data   字符数组
     * @param append 是否追加
     * @throws IOException
     */
    public static void writeByteArrayToFile(String file, byte[] data, boolean append) throws IOException {
        FileUtils.writeByteArrayToFile(new File(file), data, append);
    }



    /**
     * 将集合中的值逐行写入文件，自动换行
     *
     * @param file
     * @param lines  集合类型，如：List、Set 和 Queue
     * @param append 是否追加
     * @throws IOException
     */
    public static void writeLines(String file, String charsetName, Collection<?> lines, boolean append) throws IOException {
        FileUtils.writeLines(new File(file), charsetName, lines, append);
    }

    /**
     * 将集合中的值写入文件，不自动换行
     *
     * @param file
     * @param charsetName
     * @param lines
     * @param lineEnding  每行结尾可以自定义，如"\n"或"字符串"
     * @param append      是否追加
     * @throws IOException
     */
    public static void writeLines(String file, String charsetName, Collection<?> lines, String lineEnding, boolean append) throws IOException {
        FileUtils.writeLines(new File(file), charsetName, lines, lineEnding, append);
    }
}
