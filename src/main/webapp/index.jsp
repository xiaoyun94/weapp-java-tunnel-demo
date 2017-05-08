<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" %>
<html>
<head>
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
            Print("ERROR: " + e);
        }
        function sMessage(msg){
            Print('RECEIVE: ' + msg.data);
        }
        function sClose(e){
            Print("CLOSE: " + e.code);
        }
        function Send(){
            var message = document.getElementById("message").value;
            socket.send(message);
            Print("SEND: " + message);
        }
        function Ping(){
            socket.send("ping");
            Print("SEND: " + "ping");
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
<body >
<title>Weapp Tunnel Server Demo</title>
<style>
    body{text-align:center}
</style>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<h2>微信小程序信道服务器DEMO</h2>
<table width="600" border="0" align="center">
    <tr>
        <td colspan="3">这是一个微信小程序的信道服务器demo，你现在或许已经部署到自己的服务器中。</td>
    <tr>
        <td><a color="green" href="https://github.com/xiaoyun94/weapp-java-tunnel-demo">Github for this demo</a></td>
        <td><a color="green" href="https://www.bigforce.cn">Bigforce.cn</a></td>
    </tr>
</table>
</br>


<hr color="green" width="800"/>
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
<table width="300" border="0" align="center">
    <tr>
        <td colspan="3">Debug for WebSocket</td>
    <tr>
        <td>Link</td>
        <td><input type="text" id="link" size="40" value="<%=schema%>"></td>
    </tr>
    <tr>
        <td>Message</td>
        <td><input type="text" id="message" size="40" value="我能吞下doge而不伤身体"></td>
    </tr>

</table>

<button id="connect" onclick="Connect();">Connect</button>
<button id="send" onclick="Send();">Send</button>
<button id="ping" onclick="Ping();">Ping</button>
<button id="close" onclick="Close();">Close</button>
</br>
<textarea id="textarea" style="width:400px;height:400px;"></textarea>


</body>
</html>
