package com.nannar.controller;

import com.nannar.conf.QuartzConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Caiyuhui
 * @date 2020/5/18
 * @time 14:13
 */
@Controller
public class ScheduleController {
    private final QuartzConfig quartzConfig;
    Logger logger = LoggerFactory.getLogger(this.getClass());
    public ScheduleController(QuartzConfig quartzConfig) {
        this.quartzConfig = quartzConfig;
    }
    @RequestMapping("startAll")
    @ResponseBody
    public String startAll() {
        try{
            quartzConfig.startJob();
        }catch (Exception e){
            logger.error("错误消息:"+e);
            return "failure";
        }
        return "success";
    }
    @RequestMapping("pauseAll")
    @ResponseBody
    public String pauseAllJob(){
        try{
            quartzConfig.pauseAllJob();
        }catch (Exception e){
            logger.error("错误消息:"+e);
            return "failure";
        }
        return "success";
    }
}
