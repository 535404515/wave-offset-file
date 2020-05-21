package com.nannar.threads;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ThreadFactory;

/**
 * @author Caiyuhui
 * @date 2020/5/15
 * @time 9:19
 */
public class ThreadInvokerFactory implements ThreadFactory {
    /**
     * 线程计数器
     */
    private int counter;
    /**
     * 线程名称前缀
     */
    private String name;
    /**
     * 创建线程的日志
     */
    private List<String> stats;

    /**
     * @param runnable
     * @return
     * @author : hangui_zhang
     * @create by     : 2018年12月12日 上午10:21:37
     * @see java.util.concurrent.ThreadFactory#newThread(java.lang.Runnable)
     */
    @Override
    public Thread newThread(Runnable runnable) {
        Thread t = new Thread(runnable, name + "-Thread_" + counter);
        counter++;
        stats.add(String.format("Created thread %d with name %s on %s \n", t.getId(), t.getName(), new Date()));
        return t;
    }

    public ThreadInvokerFactory(String name) {
        counter = 1;
        this.name = name;
        stats = new ArrayList<String>();
    }


    public String getStats() {
        StringBuffer buffer = new StringBuffer();
        Iterator<String> it = stats.iterator();
        while (it.hasNext()) {
            buffer.append(it.next());
        }
        return buffer.toString();
    }
}
