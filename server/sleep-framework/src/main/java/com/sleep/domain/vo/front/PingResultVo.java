package com.sleep.domain.vo.front;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PingResultVo {
    private String domain;
    private String ipAddress;
//    private String location;
    private Long pingTime;
}
