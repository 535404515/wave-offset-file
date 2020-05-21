package com.nannar.entity;

import java.io.Serializable;
/**
 * @author Caiyuhui
 * @date 2020/5/15
 * @time 9:38
 */
public abstract class BasePojo implements Serializable {
    /**
     * 获取主键字段值
     * @author        : hangui_zhang
     * @create by     : 2018年12月12日 下午5:29:57
     * @return
     */
    public abstract Serializable getId();
    private String message;
    private boolean success = false;
    public String getMessage() {
        return message;
    }

    public void setErrMsg(String errMsg){
        this.success = false;
        this.message = errMsg;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public boolean isSuccess() {
        return success;
    }
    public void setSuccess(boolean success) {
        this.success = success;
    }

    @Override
    public String toString() {
        BufferedString bs = new BufferedString();
        bs.append("[");
        bs.append("success").append("=").append(this.success+"")
                .append(",")
                .append("message").append("=").append(this.message);
        bs.append("]");
        return bs.toString();
    }
}
