package com.sleep.async;

import com.sleep.utils.IpTransformUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * 异步
 * @author SleepWalker
 * @version 1.0
 * @date 2023/5/14 11:59
 */

@Component
public class ToolsAsync {
    /**
     * 连接ip，查看是否存活
     *
     * @param i
     */
    @Async
    public CompletableFuture<String> connectIp(int i) {
        boolean status = false;
        String ip = null;
        try {
            //转换为IP地址
            ip = IpTransformUtils.longToIp(i);
            InetAddress address = InetAddress.getByName(ip);
            status = address.isReachable(200);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (status) {//成功连接
            System.out.println("===============" + ip + " 成功连接");
            return CompletableFuture.completedFuture(ip);
        } else {
            System.out.println(ip + " 连接失败");
            return null;
        }
    }

    /**
     * tcp方式连接
     *
     * @param host 主机
     * @param port 端口
     * @return {@link Future}<{@link Integer}>
     */
    @Async
    public CompletableFuture<Integer> tcpConnectSocket(String host, Integer port) {
        Socket socket = null;
        try {
            socket = new Socket();
            socket.connect(new InetSocketAddress(host, port), 200);
            socket.close();
            System.out.println(port + " 已开启----------------");
            return CompletableFuture.completedFuture(port);
        } catch (IOException e) {
            System.out.println(port + " 已关闭==");
        }
        return null;
    }

    /**
     * 检测udp端口是否开放
     *
     * @param ip   IP
     * @param port 港口
     * @return boolean
     */
    @Async
    public CompletableFuture<Integer> udpIsPortOpen(String ip, int port) {
        try (DatagramSocket socket = new DatagramSocket()) {
            socket.setSoTimeout(100);
            byte[] buf = new byte[10];
            DatagramPacket packet = new DatagramPacket(buf, buf.length, InetAddress.getByName(ip), port);
            socket.send(packet);
            socket.receive(packet);
            System.out.println("-------------Port " + port + " is open on " + ip);
            return CompletableFuture.completedFuture(port);
        } catch (IOException e) {
            System.out.println("Port== " + port + " is close on " + ip);
            return null;
        }
    }
}
