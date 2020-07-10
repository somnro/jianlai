package com.wzh.web.utils;

import cn.hutool.core.io.FileUtil;
import org.jsoup.nodes.Node;
import org.junit.Test;
import org.seimicrawler.xpath.JXDocument;
import org.seimicrawler.xpath.JXNode;

import java.nio.charset.Charset;
import java.util.List;

/**
 * @Author: wzh
 * @Date: 2020/7/10 16:21
 **/
public class FundTest {



    @Test
    public void test1341() {
        String s = FileUtil.readString("C:\\Users\\dell0\\Desktop/1.html", Charset.defaultCharset());
        JXDocument jxDocument = JXDocument.create(s);
        List<JXNode> jxNodes = jxDocument.selN("//tbody");
        JXNode jxNode = jxNodes.get(3);
        List<Node> nodes = jxNode.asElement().childNodes();
        for (Node node : nodes) {
            System.out.println("node = " + node);
            List<Node> nodeList = node.childNodes();

        }
        System.out.println(1);

    }
}
