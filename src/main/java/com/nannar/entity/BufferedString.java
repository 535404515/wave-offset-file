package com.nannar.entity;

/**
 * @author Caiyuhui
 * @date 2020/5/15
 * @time 9:38
 */
public class BufferedString {
    private String lineEnd = "\n";
    private StringBuffer sb = null;

    public BufferedString() {
        sb = new StringBuffer();
    }

    public void clear(){
        sb.delete(0,sb.length());
    }

    public BufferedString(CharSequence seq) {
        sb = new StringBuffer(seq);
    }

    public BufferedString(int capacity) {
        sb = new StringBuffer(capacity);
    }

    public BufferedString(String str) {
        sb = new StringBuffer(str);
    }

    public BufferedString(StringBuffer str) {
        sb = new StringBuffer();
        if(null != str) {
            sb.append(str);
        }
    }

    public BufferedString(BufferedString bstr) {
        sb = new StringBuffer();
        if(null != bstr && null != bstr.sb) {
            sb.append(bstr.sb);
        }
    }

    public synchronized BufferedString append(int paramInt) {
        sb.append(paramInt);
        return this;
    }
    public synchronized BufferedString append(String str) {
        sb.append(str);
        return this;
    }
    public synchronized BufferedString append(StringBuffer str) {
        sb.append(str);
        return this;
    }
    public synchronized BufferedString append(BufferedString bstr) {
        if(null != bstr && null != bstr.sb){
            append(bstr.sb);
        }
        return this;
    }

    public BufferedString appendLine(){
        sb.append(lineEnd);
        return this;
    }
    public synchronized BufferedString appendLine(int paramInt) {
        sb.append(paramInt);
        return appendLine();
    }
    public synchronized BufferedString appendLine(String str) {
        sb.append(str);
        return appendLine();
    }
    public synchronized BufferedString appendLine(StringBuffer str) {
        sb.append(str);
        return appendLine();
    }
    public synchronized BufferedString appendLine(BufferedString bstr) {
        if(null != bstr && null != bstr.sb){
            append(bstr.sb);
        }
        return appendLine();
    }

    public StringBuffer getBuffer(){
        return sb;
    }

    public int length() {
        return sb.length();
    }
    /**
     *
     * @author        : hangui_zhang
     * @create by     : 2018年12月12日 下午4:30:29
     * @see java.lang.Object#toString()
     * @return
     */
    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return sb.toString();
    }
}
