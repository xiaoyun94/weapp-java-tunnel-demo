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
 * WebSocket拦截器
 * @author WANG
 *
 */
public class SpringWebSocketHandlerInterceptor extends HttpSessionHandshakeInterceptor {
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
                                   Map<String, Object> attributes) throws Exception {
        // TODO Auto-generated method stub
        System.out.println("Before Handshake");
        if (request instanceof ServletServerHttpRequest) {
            System.out.println("是他的实例");
            ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
            String tunnelId = servletRequest.getServletRequest().getParameter("tunnelId");
            String tcId = servletRequest.getServletRequest().getParameter("tcId");
            if(tunnelId == null || tcId == null)
                return false;
            attributes.put("tunnelId", tunnelId);
            attributes.put("tcId", tcId);
        }
        return super.beforeHandshake(request, response, wsHandler, attributes);

    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
                               Exception ex) {
        super.afterHandshake(request, response, wsHandler, ex);
    }
}