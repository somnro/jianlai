package com.wzh.web.utils;

import org.springframework.scheduling.annotation.Scheduled;

/**
 * @Author: wzh
 * @Date: 2020/6/1 17:19
 **/

public class ScheduledMessage {
    private static String pageNum = "";
    private static String pageSize = "";
    private static String url = "https://m.zongheng.com/h5/ajax/chapter/list?h5=1&bookId=672340&pageNum="+pageNum+"&pageSize="+pageSize;
    public static void main(String[] args) {

    }

    /**
     *
     * 链接：https://m.zongheng.com/h5/ajax/chapter/list?h5=1&bookId=672340&pageNum=1&pageSize=20&chapterId=0&asc=0&callback=jsonp4
     * 链接优化 https://m.zongheng.com/h5/ajax/chapter/list?h5=1&bookId=672340&pageNum=65&pageSize=2
     * 返回数据：
     *  {"chapterlist":{"asc":0,"chapters":[{"level":1,"chapterId":39200257,"chapterName":"第一百二十五章 一剑破法","orderNum":82,"tomeName":null,"tomeId":1543696},{"level":1,"chapterId":39213878,"chapterName":"第一百二十六章 陆地剑仙","orderNum":84,"tomeName":null,"tomeId":1543696}],"chapterCount":770,"pageSize":2,"pageNum":65,"currentChapterIndex":null},"ajaxResult":{"code":1,"message":"有数据"}}
     *
     *
     *
     *
     *  1.每45-50分钟请求一次
     */
    @Scheduled
    public void test1129(){

    }
}
