package cn.bigforce.weapptunnel.conf;

import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.ServletConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import java.io.*;

/**
 * Created by LAB520 on 2017/3/12.
 */
@WebServlet(name="Startup", urlPatterns = {}, loadOnStartup = 1)
public class TunnelConfiguration extends HttpServlet  {
    private static final long serialVersionUID = 1L;
    public static String host = null;

    public void init(ServletConfig config) {
        String data = readFile(getConfigFilePath());
        JSONObject json = new JSONObject(data);
        host = json.getString("BigforceTunnelHostLocation");
    }
    private static String getConfigFilePath() {
        String osName = System.getProperty("os.name").toLowerCase();
        String defaultConfigFilePath = null;
        boolean isWindows = osName.indexOf("windows") > -1;
        boolean isLinux = osName.indexOf("linux") > -1;

        if (isWindows) {
            defaultConfigFilePath = "C:\\qcloud\\sdk.config";
        }
        else if (isLinux) {
            defaultConfigFilePath = "/etc/qcloud/sdk.config";
        }
        return defaultConfigFilePath;
    }
    private static String readFile(String path){
        try {
            InputStreamReader isr = new InputStreamReader(new FileInputStream(path), "UTF-8");
            BufferedReader br = new BufferedReader(isr);
            String line = null;
            StringBuffer sb = new StringBuffer();
            while((line=br.readLine())!=null){
                sb.append(line);
            }
            return sb.toString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
