package com.topideal.dscp.cms.task;

import com.topideal.dscp.service.base.BaseMerchantService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Author: kongxj
 * @Date: 2022/8/16 14:58
 */
@Component
@Slf4j
public class testTask {

    // 添加定时任务
//    @Scheduled(cron = "0 */1 * * * ?")
    public void doTask() {
        System.out.println("我是定时任务~");
    }

    @Resource
    private BaseMerchantService baseMerchantService;

    //每周星期天晚上10点执行一次
//    @Scheduled(cron = "0 0 22 * * 7 ")
//    @Scheduled(cron = "0 0/2 * * * * ")  //五分钟
    public void merchantTask() throws Exception {
        //客商数据同步
//        baseMerchantService.initData();
    }


}
