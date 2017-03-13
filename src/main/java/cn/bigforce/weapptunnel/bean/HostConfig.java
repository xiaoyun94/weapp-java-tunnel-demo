package cn.bigforce.weapptunnel.bean;

/**
 * Created by LAB520 on 2017/3/12.
 */
public class HostConfig {
    private String receiveUrl;
    private String tcKey;

    public HostConfig(String receiveUrl, String tcKey) {
        this.receiveUrl = receiveUrl;
        this.tcKey = tcKey;
    }

    public String getReceiveUrl() {
        return receiveUrl;
    }

    public void setReceiveUrl(String receiveUrl) {
        this.receiveUrl = receiveUrl;
    }

    public String getTcKey() {
        return tcKey;
    }

    public void setTcKey(String tcKey) {
        this.tcKey = tcKey;
    }
}
