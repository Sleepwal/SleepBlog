package com.sleep.service;

import com.sleep.domain.Result;

import java.net.UnknownHostException;
import java.security.NoSuchAlgorithmException;

/**
 * @author SleepWalker
 * @version 1.0
 * @date 2023/5/10 20:56
 */

public interface ToolsService {
    Result<?> ipAddressScanner(String startHost, String endHost);

    Result<?> tcpPortScan(String host, Integer startPort, Integer endPort);

    Result<?> udpPortScan(String host, Integer startPort, Integer endPort);

    Result<?> DNSLookup(String domainName);

    Result<?> ping(String domain) throws UnknownHostException;

    Result<?> md5Encrypt(String plaintext) throws NoSuchAlgorithmException;
}
