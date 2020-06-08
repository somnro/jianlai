//package com.wenzhiheng.jianlai.utils;
//
//import cn.hutool.core.date.DateUtil;
//import com.sun.corba.se.spi.orbutil.threadpool.ThreadPool;
//
//import java.util.concurrent.Executors;
//import java.util.concurrent.ScheduledExecutorService;
//import java.util.concurrent.TimeUnit;
//
///**
// * @Author: wzh
// * @Date: 2020/6/1 17:19
// **/
//
//public class ScheduledMessage {
//    public static void main(String[] args) {
//        Runnable runnable = new Runnable() {
//            @Override
//            public void run() {
//                System.out.println(DateUtil.now()+Thread.currentThread().getName() + " : 延迟1秒,间隔3秒");
//            }
//        };
//        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
//        service.scheduleAtFixedRate(runnable, 1000L, 3000L, TimeUnit.MILLISECONDS);
//
//    }
//}
