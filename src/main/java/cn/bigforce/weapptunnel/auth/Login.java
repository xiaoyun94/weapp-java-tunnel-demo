package cn.bigforce.weapptunnel.auth;

import cn.bigforce.weapptunnel.conf.TunnelConfiguration;
import sun.misc.BASE64Decoder;

import java.io.IOException;

/**
 * Created by LAB520 on 2017/5/9.
 */
public class Login {
    public static boolean verifyUser(String authorization) throws IOException {
        //利用Base64作编码的转化
        authorization = authorization.substring(6);
        BASE64Decoder encoder = new BASE64Decoder();
        byte[] bytes = encoder.decodeBuffer(authorization);
        String decoded = new String(bytes);

        return TunnelConfiguration.users.contains(decoded);
    }
    public static boolean verifyUser(String user, String pass) throws IOException {
        return verifyUser("basic "+ user + ":" + pass);
    }
}
