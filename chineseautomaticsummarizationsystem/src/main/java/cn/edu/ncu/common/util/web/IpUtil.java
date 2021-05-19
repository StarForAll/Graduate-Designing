package cn.edu.ncu.common.util.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.*;

/**
* @Description:    ip工具类
* @Author:         sbq
* @CreateDate:     2019/8/8 10:33
* @Version:        1.0
*/
public class IpUtil {
    public static final String IP_URL = "http://ip.taobao.com/service/getIpInfo.php";
    public static final String UNKNOWN = "unknown";
    public static String getLocalHostIP() {
        // 本地IP，如果没有配置外网IP则返回它
        String localIp = null;
        // 外网IP
        String netIp = null;
        try {
            Enumeration<NetworkInterface> netInterfaces = NetworkInterface.getNetworkInterfaces();
            InetAddress ip = null;
            // 是否找到外网IP
            boolean finded = false;
            while (netInterfaces.hasMoreElements() && ! finded) {
                NetworkInterface ni = netInterfaces.nextElement();
                Enumeration<InetAddress> address = ni.getInetAddresses();
                while (address.hasMoreElements()) {
                    ip = address.nextElement();
                    // 外网IP
                    if (! ip.isSiteLocalAddress() && ! ip.isLoopbackAddress() && !ip.getHostAddress().contains(":")) {
                        netIp = ip.getHostAddress();
                        finded = true;
                        break;
                    } else if (ip.isSiteLocalAddress() && ! ip.isLoopbackAddress() && !ip.getHostAddress().contains(":")) {
                        // 内网IP
                        localIp = ip.getHostAddress();
                    }
                }
            }
        } catch (SocketException e) {
            e.getMessage();
        }
        if (netIp != null && ! "".equals(netIp)) {
            return netIp;
        } else {
            return localIp;
        }
    }

    public static String getRemoteIp(HttpServletRequest request) {
        // 获取请求主机IP地址,如果通过代理进来，则透过防火墙获取真实IP地址

        String ip = getXForwardedForIp(request);
        if (ipValid(ip)) {
            return realIp(ip);
        }
        ip = request.getHeader("Proxy-Client-IP");
        if (ipValid(ip)) {
            return realIp(ip);
        }
        ip = request.getHeader("HTTP_CLIENT_IP");
        if (ipValid(ip)) {
            return realIp(ip);
        }
        ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        if (ipValid(ip)) {
            return realIp(ip);
        }

        ip = request.getRemoteAddr();
        return realIp(ip);
    }

    private static String getXForwardedForIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        //ip 无效直接返回
        if (! ipValid(ip)) {
            return "";
        }
        if (ip.length() > 15) {
            String[] ips = ip.split(",");
            for (String strIp : ips) {
                if (! (UNKNOWN.equalsIgnoreCase(strIp))) {
                    ip = strIp;
                    break;
                }
            }
            return ip;
        }
        return ip;
    }

    private static Boolean ipValid(String ip) {
        if (StringUtils.isEmpty(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
            return false;
        }
        return true;
    }

    private static String realIp(String ip) {
        if (StringUtils.isEmpty(ip)) {
            return "";
        }
        return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : ip;
    }

    public static String getRemoteLocation(HttpServletRequest request) {
        String ip = getRemoteIp(request);
        return getIpLocation(ip);
    }

    public static String getIpLocation(String ip) {
        String location = "未知";
        if (StringUtils.isEmpty(ip)) {
            return location;
        }
        Map<String, String> param = new HashMap<>();
        param.put("ip", ip);

        try {
            String rspStr = HttpUtil.sendGet(IP_URL, param, null);
            if (StringUtils.isEmpty(rspStr)) {
                return location;
            }
            JSONObject jsonObject = JSON.parseObject(rspStr);
            String data = jsonObject.getString("data");
            JSONObject jsonData = JSON.parseObject(data);
            String region = jsonData.getString("region");
            String city = jsonData.getString("city");
            location = region + " " + city;
            if (location.contains("内网IP")) {
                location = "内网(" + ip + ")";
            }
        } catch (Exception e) {

        }
        return location;
    }
    /**
     * 获得服务器的IP地址
     */
    public static String getLocalIP() {
        String sIP = "";
        InetAddress ip = null;
        try {
            boolean bFindIP = false;
            Enumeration<NetworkInterface> netInterfaces = NetworkInterface
                    .getNetworkInterfaces();
            while (netInterfaces.hasMoreElements()) {
                if (bFindIP) {
                    break;
                }
                NetworkInterface ni = netInterfaces
                        .nextElement();
                Enumeration<InetAddress> ips = ni.getInetAddresses();
                while (ips.hasMoreElements()) {
                    ip = ips.nextElement();
                    if (!ip.isLoopbackAddress()
                            && ip.getHostAddress().matches(
                            "(\\d{1,3}\\.){3}\\d{1,3}")) {
                        bFindIP = true;
                        break;
                    }
                }
            }
        } catch (Exception e) {
        }
        if (null != ip) {
            sIP = ip.getHostAddress();
        }
        return sIP;
    }

    /**
    * @Description:    获得服务器的IP地址(多网卡)
    * @Author:         sbq
    * @CreateDate:     2019/8/8 10:34
    * @Version:        1.0
    */
    public static List<String> getLocalIPS() {
        InetAddress ip = null;
        List<String> ipList = new ArrayList<String>();
        try {
            Enumeration<NetworkInterface> netInterfaces = (Enumeration<NetworkInterface>) NetworkInterface
                    .getNetworkInterfaces();
            while (netInterfaces.hasMoreElements()) {
                NetworkInterface ni = (NetworkInterface) netInterfaces
                        .nextElement();
                Enumeration<InetAddress> ips = ni.getInetAddresses();
                while (ips.hasMoreElements()) {
                    ip = (InetAddress) ips.nextElement();
                    if (!ip.isLoopbackAddress()
                            && ip.getHostAddress().matches(
                            "(\\d{1,3}\\.){3}\\d{1,3}")) {
                        ipList.add(ip.getHostAddress());
                    }
                }
            }
        } catch (Exception e) {
        }
        return ipList;
    }

}