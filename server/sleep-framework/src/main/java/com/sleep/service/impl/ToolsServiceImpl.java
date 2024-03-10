package com.sleep.service.impl;

import com.sleep.async.ToolsAsync;
import com.sleep.domain.Result;
import com.sleep.domain.vo.front.PingResultVo;
import com.sleep.service.ToolsService;
import com.sleep.utils.IpTransformUtils;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

/**
 * @author SleepWalker
 * @version 1.0
 * @date 2023/5/10 20:56
 */

@Service
public class ToolsServiceImpl implements ToolsService {
    @Autowired
    ToolsAsync toolsAsync;

    @Override
    public Result<?> ipAddressScanner(String startHost, String endHost) {
        int startIp = IpTransformUtils.ipToLong(startHost);
        int endIp = IpTransformUtils.ipToLong(endHost);

        List<CompletableFuture<String>> futureList = new ArrayList<>();
        for (int i = startIp; i < endIp; i++) {
            futureList.add(toolsAsync.connectIp(i));
        }

        //等待全部完成
        CompletableFuture.allOf(futureList.toArray(new CompletableFuture[0])).join();

        //读取数据
        List<String> ipList = new ArrayList<>();
        for (CompletableFuture<String> future : futureList) {
            String s = future.getNow(null);
            if(Objects.nonNull(s))
                ipList.add(s);
        }

        return Result.success(ipList);
    }

    /**
     * 端口扫描
     *
     * @param host      主机
     * @param startPort 启动端口
     * @param endPort   结束端口
     * @param mode      模式
     * @return {@link List}<{@link Integer}>
     */
    private List<Integer> portScan(String host, Integer startPort, Integer endPort, String mode) {
        List<CompletableFuture<Integer>> futureList = new ArrayList<>();
        //选择模式
        if(Objects.equals(mode, "tcp")) { //tcp扫描
            for (int i = startPort; i <= endPort; i++)
                futureList.add(toolsAsync.tcpConnectSocket(host, i));
        } else { //udp扫描
            for (int i = startPort; i <= endPort; i++)
                futureList.add(toolsAsync.udpIsPortOpen(host, i));
        }

        //等待全部完成
        CompletableFuture.allOf(futureList.toArray(new CompletableFuture[0])).join();

        //读取数据
        List<Integer> portList = new ArrayList<>();
        for (CompletableFuture<Integer> future : futureList) {
            Integer port = future.getNow(null);
            if(Objects.nonNull(port))
                portList.add(port);
        }

        return portList;
    }

    @Override
    public Result<?> tcpPortScan(String host, Integer startPort, Integer endPort) {
        return Result.success(portScan(host, startPort, endPort, "tcp"));
    }

    @Override
    public Result<?> udpPortScan(String host, Integer startPort, Integer endPort) {
        return Result.success(portScan(host, startPort, endPort, "udp"));
    }

    @Override
    public Result<?> DNSLookup(String domainName) {
        List<String> list = new ArrayList<>();
        try {
            InetAddress[] addresses = InetAddress.getAllByName(domainName);
            System.out.println("Subdomains for " + domainName + ":");
            for (InetAddress address : addresses) {
                list.add(address.getHostAddress());
                System.out.println("  " + address.getHostName());
                System.out.println("  " + address.getHostAddress());
            }
        } catch (UnknownHostException e) {
            System.err.println("Failed to look up " + domainName + ": " + e.getMessage());
        }

        return Result.success(list);
    }

    @Override
    public Result<?> ping(String domain) throws UnknownHostException {
        String ipAddress = InetAddress.getByName(domain).getHostAddress();
        PingResultVo pingResult = new PingResultVo(domain, ipAddress, 0L);

        try {
            RetryConfig config = RetryConfig.custom()
                    .maxAttempts(3)
                    .waitDuration(Duration.ofSeconds(1))
                    .build();
            Retry retry = Retry.of("ping", config);

            Long pingTime = retry.executeSupplier(() -> {
                long startTime = System.currentTimeMillis();
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getForObject("http://" + domain, String.class);
                long endTime = System.currentTimeMillis();
                return endTime - startTime;
            });

            pingResult.setPingTime(pingTime);
        } catch (Exception e) {
            // Handle exception
            e.printStackTrace();
        }

        return Result.success(pingResult);
    }

    @Override
    public Result<?> md5Encrypt(String plaintext) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] hashedBytes = md.digest(plaintext.getBytes());
        String base64EncodedHashedBytes = Base64.getEncoder().encodeToString(hashedBytes);
        return Result.success(base64EncodedHashedBytes);
    }


}
