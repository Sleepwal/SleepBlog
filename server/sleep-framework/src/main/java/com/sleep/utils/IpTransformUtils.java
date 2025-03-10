package com.sleep.utils;

/**
 * @author SleepWalker
 * @version 1.0
 * @date 2023/5/14 12:01
 */

public class IpTransformUtils {

    /**
     * 分段ip转换为十进制数
     *
     * @param ip String  分段ip
     * @return int
     */
    public static int ipToLong(String ip) {
        String[] ipArray = ip.split("\\.");
        int num = 0;
        for (int i = 0; i < ipArray.length; i++) {
            int valueOfSection = Integer.parseInt(ipArray[i]);//将8位字符串转化为整型
            //对每段ip值左移，再进行按位或运算，最后得到一个十进制数值
            num = (valueOfSection << 8 * (3 - i)) | num;
        }
        return num;
    }


    /**
     * 十进制数转化为分段IP
     *
     * @param num int 十进制数IP
     * @return {@link String}
     */
    public static String longToIp(int num) {
        String[] ipString = new String[4];
        for (int i = 0; i < 4; i++) {
            //将数值每8位跟 11111111 按位与运算，得到8位的二进制数
            int and = num & (255 << (8 * (3 - i)));
            //再将每段进行无符号右移，得到每段的ip值
            ipString[i] = String.valueOf(and >>> (8 * (3 - i)));
        }
        String ip = String.join(".", ipString);//将字符串数组拼接为IP地址形式
        return ip;
    }
}
