package cn.bigforce.weapptunnel.ws;

/**
 * Created by LAB520 on 2017/3/10.
 */

import javax.servlet.http.HttpSession;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import java.util.Map;

/**
 * HTTP请求拦截操作
 */
public class SpringWebSocketHandlerInterceptor extends HttpSessionHandshakeInterceptor {
    /**
     * HTTP请求拦截，获取参数，校验
     * @param request
     * @param response
     * @param wsHandler
     * @param attributes
     * @return
     * @throws Exception
     */
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
                                   Map<String, Object> attributes) throws Exception {

        //System.out.println("Before Handshake");
        if (request instanceof ServletServerHttpRequest) {
            //System.out.println("是他的实例");
            ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
            String tunnelId = servletRequest.getServletRequest().getParameter("tunnelId");
            String tcId = servletRequest.getServletRequest().getParameter("tcId");
            String debug = servletRequest.getServletRequest().getParameter("debug");

            if("debug".equals(debug)){
                attributes.put("tunnelId", "debug");
                attributes.put("tcId", "debug");
            }else{
                //如果校验tunnelId是否为有效，并且tcId有效
                boolean tunnelIdValid = SpringWebSocketHandler.checkTunnelId(tunnelId);
                boolean tcIdValid = SpringWebSocketHandler.checkTcId(tcId);

                if(!tunnelIdValid || !tcIdValid )
                    return false;

                attributes.put("tunnelId", tunnelId);
                attributes.put("tcId", tcId);
            }
        }
        return super.beforeHandshake(request, response, wsHandler, attributes);

    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
                               Exception ex) {
        super.afterHandshake(request, response, wsHandler, ex);
    }
}