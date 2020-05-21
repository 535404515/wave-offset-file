package com.nannar.threads;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.math.NumberUtils;

/**
 * @author Caiyuhui
 * @date 2020/5/15
 * @time 9:12
 */
public class ThreadInvoker implements Runnable{
    /**
     * ThreadPoolExecutor 调度线程池最大为200个。
     */
    private static final ExecutorService FIXED_THREAD_POOL = new ThreadPoolExecutor(50, 60, 0, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>(), new ThreadInvokerFactory("nlpms"));
    /**
     * 线程实现类
     */
    private ThreadInvokerEvent eventObject;
    /**
     * 线程方法参数
     */
    private Object[] params;
    /**
     * 调用时间间隔。>0时无限循环调用。<=0时只调用一次。
     */
    private Integer intervalMillisecond = 0;
    /**
     * 线程是否正在调度。为false时终止循环调用。
     */
    private boolean running = false;

    /**
     * 获取 线程方法参数
     *
     * @return params
     */
    public Object[] getParams() {
        return params;
    }

    /**
     * 创建一个线程调用对象
     *
     * @param eventObject         带参数的回调接口
     * @param intervalMillisecond 调用时间间隔。大于0时表示无限循环调用；小于或等于0时只调用一次。
     */
    public ThreadInvoker(ThreadInvokerEvent eventObject, Integer intervalMillisecond) {
        this.eventObject = eventObject;
        this.intervalMillisecond = intervalMillisecond;
    }

    public ThreadInvoker(ThreadInvokerEvent eventObject) {
        this(eventObject, 0);
    }

    public ThreadInvoker(ThreadInvokerEvent eventObject, Integer intervalMillisecond, Object... params) {
        this.eventObject = eventObject;
        this.params = params;
    }

    public ThreadInvoker(ThreadInvokerEvent eventObject, Object... params) {
        this(eventObject, 0, params);
    }

    /**
     * @author : hangui_zhang
     * @create by : 2018年12月12日 下午4:31:04
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {
        this.running = true;
        if (null != this.eventObject) {
            if (null != this.intervalMillisecond && this.intervalMillisecond > 0) {
                // 无限循环调用。
                while (this.running) {
                    callMethod();
                    try {
                        Thread.sleep(this.intervalMillisecond);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                //线程结束
                this.running = false;
                if(!Thread.currentThread().isInterrupted()) {
                    Thread.currentThread().interrupt();
                }
            } else {
                callMethod();
                //线程结束
                this.running = false;
                if(!Thread.currentThread().isInterrupted()) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    /**
     * 呼叫委托的方法
     */
    private void callMethod() {
        try {
            Object invoke = this.eventObject.doEvent();
            if (null != invoke && NumberUtils.isDigits(invoke.toString())) {
                if (null != this.endInvoker) {
                    Integer num = Integer.valueOf(invoke.toString());
                    this.endInvoker.theEnd(num);
                }
            } else {
                if (null != this.endInvoker) {
                    this.endInvoker.theEnd(null);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 停止循环调度
     */
    public void stopInterval() {
        this.running = false;
    }

    private TaskEndInvoker endInvoker;

    public void start() {
        this.endInvoker = null;
        // new Thread(this).start();
        FIXED_THREAD_POOL.execute(this);
    }

    public void start(TaskEndInvoker endInvoker) {
        this.endInvoker = endInvoker;
        // new Thread(this).start();
        FIXED_THREAD_POOL.execute(this);
    }
}
