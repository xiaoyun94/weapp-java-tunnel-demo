package cn.bigforce.weapptunnel.bean;

/**
 * Created by LAB520 on 2017/3/12.
 */
public class ResponseObject {
    private int code;
    private String data;
    private String message;

    public ResponseObject(String data) {
        this.code = 0;
        this.data = data;
        this.message = "ok";
    }
    public ResponseObject(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

