package com.sleep.controller;

import com.sleep.domain.Result;
import com.sleep.service.ToolsService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author SleepWalker
 * @version 1.0
 * @date 2023/5/10 20:53
 */

@Validated
@RestController
@RequestMapping("/tools")
public class ToolsController {
    @Autowired
    private ToolsService toolsService;

    /**
     * ip地址扫描
     *
     * @param startHost 启动主机
     * @param endHost   终端主机
     * @return {@link Result}<{@link ?}>
     */
    @GetMapping("/ipAddress")
    public Result<?> ipAddressScanner(@NotBlank(message = "起始Host值不能为空") String startHost,
                                      @NotBlank(message = "结束Host值不能为空") String endHost)  {
            return toolsService.ipAddressScanner(startHost, endHost);
    }

    /**
     * 端口扫描
     *
     * @param host      主机
     * @param startPort 启动端口
     * @param endPort   端端口
     * @return {@link Result}<{@link ?}>
     */
    @GetMapping("/tcpPortScan")
    public Result<?> tcpPortScan(@NotBlank(message = "Host值不能为空")String host,
                              @NotNull(message = "起始端口不能为空") Integer startPort,
                              @NotNull(message = "结束端口不能为空")Integer endPort) {
        return toolsService.tcpPortScan(host.trim(), startPort, endPort);
    }

    /**
     * udp端口扫描
     *
     * @param host      主机
     * @param startPort 启动端口
     * @param endPort   端端口
     * @return {@link Result}<{@link ?}>
     */
    @GetMapping("/udpPortScan")
    public Result<?> udpPortScan(@NotBlank(message = "Host值不能为空")String host,
                                 @NotNull(message = "起始端口不能为空")Integer startPort,
                                 @NotNull(message = "结束端口不能为空")Integer endPort) {
        return toolsService.udpPortScan(host.trim(), startPort, endPort);
    }

    @GetMapping("/DNSLookup")
    public Result<?> DNSLookup(@NotBlank(message = "域名为空") String domainName) {
        return toolsService.DNSLookup(domainName);
    }

    @SneakyThrows
    @GetMapping("/ping/{domain}")
    public Result<?> ping(@NotBlank(message = "域名为空")
                              @PathVariable String domain) {
        return toolsService.ping(domain);
    }

    @SneakyThrows
    @GetMapping("/md5Encrypt")
    public Result<?> md5Encrypt(@NotBlank(message = "明文为空") String plaintext) {
        return toolsService.md5Encrypt(plaintext);
    }
}
