package cn.bigforce.weapptunnel.bean;

import org.springframework.web.bind.annotation.RequestBody;

/**
 * Created by LAB520 on 2017/3/12.
 */
public class TunnelRequestObject {

    String data;
    String dataEncode;
    String tcId;
    String tcKey;
    String signature;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getDataEncode() {
        return dataEncode;
    }

    public void setDataEncode(String dataEncode) {
        this.dataEncode = dataEncode;
    }

    public String getTcId() {
        return tcId;
    }

    public void setTcId(String tcId) {
        this.tcId = tcId;
    }

    public String getTcKey() {
        return tcKey;
    }

    public void setTcKey(String tcKey) {
        this.tcKey = tcKey;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}
