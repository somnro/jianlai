package com.wzh.web.utils;

import cn.hutool.http.HttpUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;

/**
 * @Author: wzh
 * @Date: 2020/6/8 12:46
 **/
class ScheduledMessage2Test {

    @Test
    void test1733() {
        ScheduledMessage2.test1733();
    }

    @Test
    void test1303() {
        ScheduledMessage2.test1303();
    }

    @Test
    public void test1748(){
//        String url = "http://fund.eastmoney.com/data/fundranking.html";
        String url = "http://fund.eastmoney.com/data/fundranking.html#tall;c0;r;szzf;pn50;ddesc;qsd20190709;qed20200709;qdii;zq;gg;gzbd;gzfs;bbzt;sfbb";
        Document parse = Jsoup.parse(url);
        System.out.println("parse = " + parse);
    }
}