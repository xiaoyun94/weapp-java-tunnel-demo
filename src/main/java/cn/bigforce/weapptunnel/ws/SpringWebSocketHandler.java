package cn.bigforce.weapptunnel.ws;

/**
 * Created by LAB520 on 2017/3/10.
 */

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bigforce.weapptunnel.bean.HostConfig;
import cn.bigforce.weapptunnel.request.HttpPost;
import cn.bigforce.weapptunnel.tool.Hash;
import org.json.JSONObject;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;


public class SpringWebSocketHandler extends TextWebSocketHandler {

    /**
     * 同来储存请求但还没有建立的信道ID
     */
    private static final List<String> newTunnelList = new ArrayList<>();
    /**
     * 用来存储当前的seesion，key为tunnelId，value为session实例
     */
    private static final Map<String, WebSocketSession> sessionMap = new HashMap();

    /**
     * 用来存储业务服务器地址，key为tcId（每个host唯一），value为业务服务器地址
     */
    private static final Map<String, HostConfig> businessServerMap = new HashMap();


    public SpringWebSocketHandler() {
        // TODO Auto-generated constructor stub
    }
    /**
     * 检验该信道iD是否为凭空制造
     * @param tunnelId
     * @return
     */
    public static boolean checkTunnelId(String tunnelId){
        boolean verified = newTunnelList.contains(tunnelId);
        newTunnelList.remove(tunnelId);
        return verified;
    }

    /**
     * 添加准入的tunnelId
     * @param tunnelId
     */
    public static void addTunnelId(String tunnelId){
        newTunnelList.add(tunnelId);
    }

    /**
     * 检验该信道iD是否为凭空制造
     * @param tcId
     * @return
     */
    public static boolean checkTcId(String tcId){
        return  SpringWebSocketHandler.getBusinessServer(tcId)!=null;
    }
    /**
     * 添加业务服务器
     * @param key
     * @param value
     */
    public static void addBusinessServer(String key, HostConfig value){
        businessServerMap.put(key, value);
    }

    /**
     * 获得业务服务器
     * @param key
     * @return
     */
    public static HostConfig getBusinessServer(String key){
        return businessServerMap.get(key);
    }

    /**
     * 建立链接调用的方法
     * @param session
     * @throws Exception
     */
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String tunnelId = (String) session.getAttributes().get("tunnelId");
        String tcId = (String) session.getAttributes().get("tcId");

        //将session、放入到全局静态表中
        //System.out.println("连接建立"+tunnelId+","+tcId);

        if(sessionMap.containsKey(tunnelId)){
            sessionMap.remove(tunnelId).close();
        }
        sessionMap.put(tunnelId, session);

        JSONObject json = buildMessageAndRequest(tunnelId, null, "connect", tcId);
        //System.out.println("建立连接" + json);
    }

    /**
     * 关闭连接触发
     * @param session
     * @param closeStatus
     * @throws Exception
     */
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        String tunnelId = (String) session.getAttributes().get("tunnelId");
        String tcId = (String) session.getAttributes().get("tcId");

        //只删除自己的session
        sessionMap.remove(tunnelId, session);
        JSONObject json = buildMessageAndRequest(tunnelId, null, "close", tcId);
        //System.out.println(json);
    }

    /**
     * 客户端发送消息
     * @param session
     * @param message
     * @throws Exception
     */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

        String content =  message.getPayload();
        if(content == null)
            return;

        //微信客户端会发送ping的心跳包，这里需要回复pong
        else if("ping".equals(content)){
            session.sendMessage(new TextMessage("pong"));
            return;
        }
        //如果消息前缀有message，那就提取出来
        else if(content.startsWith("message:")){

            content = content.substring(8);
            String tunnelId = (String) session.getAttributes().get("tunnelId");
            String tcId = (String) session.getAttributes().get("tcId");

            JSONObject json = buildMessageAndRequest(tunnelId, content, "message", tcId);
            //System.out.println(json);
        }else{
            //TO 不支持的消息格式
        }



    }

    /**
     * 处理错误
     * @param session
     * @param exception
     * @throws Exception
     */
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        if(session.isOpen()){
            session.close();
        }
    }

    /**
     * ???
     * @return
     */
    public boolean supportsPartialMessages() {
        return false;
    }


    /**
     * 给某个用户发送消息
     * @param tunnelId
     * @param message
     */
    public boolean sendMessageByTunnelId(String tunnelId, String message) {
        //微信发送消息的时候总会在前面加上“message:”前缀
        TextMessage textMessage = new TextMessage("message:"+message);
        WebSocketSession session = sessionMap.get(tunnelId);
        if(session==null)
            return false;
        try {
            if (session.isOpen()) {
                session.sendMessage(textMessage);
                return true;
            }else{
                sessionMap.remove(tunnelId);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 构建信息模板并请求发送给业务服务器
     * @param tunnelId
     * @param content
     * @param type
     * @param tcId
     * @return
     */
    private JSONObject buildMessageAndRequest(String tunnelId, String content, String type, String tcId){
        JSONObject json = new JSONObject();
        json.put("tunnelId", tunnelId);
        json.put("type", type);
        json.put("content", content);

        HostConfig host = businessServerMap.get(tcId);

        //System.out.println("host");
        //System.out.println(host == null);
        if(host==null)
            return null;


        String tcKey = host.getTcKey();
        String data = json.toString();
        String signature = Hash.sha1(data+tcKey);
        //System.out.println("tckey"+tcKey);
        JSONObject request = new JSONObject();
        request.put("data", data);
        request.put("signature", signature);


        return sendMessageToBusinessServer(tcId, request);
    }

    /**
     * 发送消息给业务服务器
     * @param tcId
     * @param json
     * @return
     */
    private JSONObject sendMessageToBusinessServer(String tcId, JSONObject json){
        //System.out.println("sendMessageToBusinessServer4" + json);

        HostConfig host = businessServerMap.get(tcId);
        if (host == null)
            return null;

        try {
            HttpPost httpPost = new HttpPost(host.getReceiveUrl());
            String response =  httpPost.setAttribute(json).request();
            return new JSONObject(response);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }


}
