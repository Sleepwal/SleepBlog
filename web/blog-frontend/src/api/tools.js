import request from '@/utils/request'

// IP存活扫描请求
export function IPLiveScan(startHost, endHost) {
    return request({
        url: '/tools/ipAddress',
        method: 'get',
        headers: {
            isToken: false
        },
        params: { 'startHost':startHost,'endHost':endHost }
    })
}

//扫描存活端口
export function tcpPortScan(host, startPort, endPort) {
    return request({
        url: '/tools/tcpPortScan',
        method: 'get',
        headers: {
            isToken: false
        },
        params: { 'host':host,'startPort':startPort,'endPort':endPort }
    })
}

export function udpPortScan(host, startPort, endPort) {
    return request({
        url: '/tools/udpPortScan',
        method: 'get',
        headers: {
            isToken: false
        },
        params: { 'host':host,'startPort':startPort,'endPort':endPort }
    })
}

export function ping(domain) {
    return request({
        url: '/tools/ping/' + domain,
        method: 'get',
        headers: {
            isToken: false
        },
    })
}

