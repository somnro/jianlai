package com.wzh.web.utils;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.wzh.web.db.DBUtil;
import com.wzh.web.po.Fund;
import org.jsoup.nodes.Node;
import org.junit.Test;
import org.seimicrawler.xpath.JXDocument;
import org.seimicrawler.xpath.JXNode;

import java.nio.charset.Charset;
import java.sql.Date;
import java.util.List;

/**
 * @Author: wzh
 * @Date: 2020/7/10 16:21
 **/
public class FundTest {



    @Test
    public void test1341() {
        String s = FileUtil.readString(System.getProperty("user.dir")+"/src/main/resources/1594654553962.html", Charset.defaultCharset());
        JXDocument jxDocument = JXDocument.create(s);
        List<JXNode> jxNodes = jxDocument.selN("//tbody");
        JXNode jxNode = jxNodes.get(3);
        List<Node> nodes = jxNode.asElement().childNodes();
        long time = new DateTime().getTime();
        for (Node node : nodes) {
            List<Node> nodeList = node.childNodes();
            System.out.println(nodeList.get(3).childNode(0).childNode(0).toString());
            Fund fund = new Fund();
            fund.setCreate(new Date(time));
            //基金代码
            fund.setCode(Long.valueOf(nodeList.get(2).childNode(0).childNode(0).toString()));
            //基金简称
            fund.setJc(nodeList.get(3).childNode(0).childNode(0).toString());
            //全称
            fund.setQc(nodeList.get(3).childNode(0).attr("title"));
            //日期
            try {
                fund.setJzrq(new Date(DateUtil.parseDate("2020-"+nodeList.get(4).childNode(0).toString()).getTime()));
            }catch (Exception e){
                fund.setJzrq(new Date(DateUtil.date().getTime()));
            }
            //单位净值
            fund.setDwjz(stringToDouble(nodeList.get(5).childNode(0).toString()));
            //累计净值
            fund.setLjjz(stringToDouble(nodeList.get(6).childNode(0).toString()));
            //日增长率
            fund.setRzdf(stringToDouble(nodeList.get(7).childNode(0).toString()));
            //近1周
            fund.setZzf(stringToDouble(nodeList.get(8).childNode(0).toString()));
            //近1月
            fund.setYzf1(stringToDouble(nodeList.get(9).childNode(0).toString()));
            //近3月
            fund.setYzf3(stringToDouble(nodeList.get(10).childNode(0).toString()));
            //近6月
            fund.setYzf6(stringToDouble(nodeList.get(11).childNode(0).toString()));
            //近1年
            fund.setNzf1(stringToDouble(nodeList.get(12).childNode(0).toString()));
            //近2年
            fund.setNzf2(stringToDouble(nodeList.get(13).childNode(0).toString()));
            //近3年
            fund.setNzf3(stringToDouble(nodeList.get(14).childNode(0).toString()));
            //今年来
            fund.setJnzf(stringToDouble(nodeList.get(15).childNode(0).toString()));
            //成立来
            fund.setLnzf(stringToDouble(nodeList.get(16).childNode(0).toString()));
            //自定义
            fund.setQjzf(0d);
            //手续费
            try {
                fund.setSxf(stringToDouble(nodeList.get(18).childNode(0).childNode(0).toString()));
            }catch (Exception e){
            }
            //可购
            fund.setMd(1L);

            DBUtil.insertFund(fund);

        }
        System.out.println(1);

    }
    private Double stringToDouble(String s){
        s = s.replace("-","");
        if (StrUtil.isBlank(s)){
            return 0d;
        }
        s = s.replace("%","");
        try {
            return Double.valueOf(s);
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0d;
    }
}
