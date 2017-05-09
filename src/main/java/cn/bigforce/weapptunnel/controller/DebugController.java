package cn.bigforce.weapptunnel.controller;

import cn.bigforce.weapptunnel.auth.Login;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by LAB520 on 2017/5/9.
 */
@Controller
@RequestMapping(value = "/")
public class DebugController {
    @RequestMapping(value="login")
    public String login(HttpServletRequest request, HttpServletResponse response){
        System.out.println("5555555555555555555555");
        String auth = request.getHeader("Authorization");
        System.out.print(auth);
        if(auth == null){
            response.setStatus(401);
            response.setHeader("WWW-authenticate","Basic realm=\"呵呵\"");
            return null;
        }
        boolean verified = false;
        try {
            verified = Login.verifyUser(auth);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(verified){
            return "redirect:/debug.jsp";
        }else{
            response.setStatus(401);
            response.setHeader("WWW-authenticate","Basic realm=\"呵呵\"");
            return null;
        }
    }

}
