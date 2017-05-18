<%--
  Created by IntelliJ IDEA.
  User: LAB520
  Date: 2017/5/18
  Time: 16:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>微信小程序信道服务器(第三方)DEMO - Java</title>
    <style type="text/css">

        ::selection { background-color: #E13300; color: white; }
        ::-moz-selection { background-color: #E13300; color: white; }

        body {
            background-color: #fff;
            margin: 40px;
            font: 13px/20px normal Helvetica, Arial, sans-serif;
            color: #4F5155;
        }

        a {
            color: #003399;
            background-color: transparent;
            font-weight: normal;
            text-decoration: none;
        }


        h1 {
            color: #444;
            background-color: transparent;
            border-bottom: 1px solid #D0D0D0;
            font-size: 19px;
            font-weight: normal;
            margin: 0 0 14px 0;
            padding: 14px 0;
        }

        p.footer {
            text-align: right;
            font-size: 11px;
            border-top: 1px solid #D0D0D0;
            line-height: 32px;
            padding: 0 10px 0 10px;
            margin: 20px 0 0 0;
        }
        p.tip {
            text-align: left;
            font-size: 11px;
            border-top: 1px solid #D0D0D0;
            line-height: 32px;
            padding: 0 10px 0 10px;
            margin: 20px 0 0 0;
        }

        #container {
            margin: 10px;
            padding: 10px 20px;
            border: 1px solid #D0D0D0;
            box-shadow: 0 0 8px #D0D0D0;
        }
    </style>
    <meta name="__hash__" content="6666cd76f96956469e7be39d750cc7d9_4f5dfaa532aeea74ecdd17151efeafb4" />
    <script>
        Date.prototype.format = function(fmt)
        { //author: meizz
            var o = {
                "M+" : this.getMonth()+1,                 //月份
                "d+" : this.getDate(),                    //日
                "h+" : this.getHours(),                   //小时
                "m+" : this.getMinutes(),                 //分
                "s+" : this.getSeconds(),                 //秒
                "q+" : Math.floor((this.getMonth()+3)/3), //季度
                "S"  : this.getMilliseconds()             //毫秒
            };
            if(/(y+)/.test(fmt))
                fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));
            for(var k in o)
                if(new RegExp("("+ k +")").test(fmt))
                    fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
            return fmt;
        }

        var socket;
        function Connect(){
            try{
                var link = document.getElementById("link").value;
                socket=new WebSocket(link);
            }catch(e){
                alert('error');
                return;
            }
            socket.onopen = sOpen;
            socket.onerror = sError;
            socket.onmessage= sMessage;
            socket.onclose= sClose;
        }
        function sOpen(){
            Print('CONNECT: OK');
        }
        function sError(e){
            for (var p in e) {
                Print("ERROR: " + p + "=" + e[p]);
            }

        }
        function sMessage(msg){
            Print('RECEIVE: ' + msg.data);
        }
        function sClose(e){
            Print("CLOSE: " + e.code);
        }
        function Send(){
            var message = document.getElementById("message").value;
            if(socket!=null){
                socket.send(message);
                Print("SEND: " + message);
            }else{
                Print("ERR: " + "Open socket first");
            }
        }
        function Ping(){
            if(socket!=null){
                socket.send("ping");
                Print("SEND: " + "ping");
            }else{
                Print("ERR: " + "Open socket first");
            }

        }
        function Exit(){
            session.invalidate();
        }
        function Close(){
            socket.close();
        }
        function Print(str){
            var date = (new Date()).format("MM-dd hh:mm:ss.S");
            str = date + "\n\t" + str;
            var text = document.getElementById("textarea");
            text.value = str + "\n" + text.value;
        }
    </script>
</head>
<body>
<div id="container">
    <h1>微信小程序信道服务器(第三方)DEMO - Java</h1>
    <p>信道服务Websocket</p>
    <ul>
        <li><a href="login">网页调试</a></li>
        <li><a href="https://github.com/xiaoyun94/weapp-java-tunnel-demo">查看源码</a></li>
        <li><a href="https://www.bigforce.cn">作者主页</a></li>
    </ul>
    <p class="footer">作者：<strong>Bigforce</strong> 版本：<strong>2017-05-18</strong></p>


</div>
<%
    String schema = "ws";
    if(request.getScheme().equals("https")){
        schema = "wss";
    }
    String server = request.getServerName();
    schema += "://";
    schema += server;
    int port = request.getServerPort();
    if(port != 443 && port != 80){
        schema+=":";
        schema+=port;
    }
    String path = request.getContextPath();
    schema += path;
    schema += "/";
    schema += "websocket?debug=debug";
%>
</body>
</html>
