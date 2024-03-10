package com.sleep.handle.security;

import com.alibaba.fastjson.JSON;
import com.sleep.domain.Result;
import com.sleep.domain.ResultEnum;
import com.sleep.utils.WebUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * 拒绝访问处理器impl
 *
 * @author manson
 * @date 2023/05/14
 */
@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        e.printStackTrace();

        Result<?> result = Result.fail(ResultEnum.NO_OPERATOR_AUTH);
        //响应发送到前端
        WebUtils.renderString(httpServletResponse, JSON.toJSONString(result));
    }
}
