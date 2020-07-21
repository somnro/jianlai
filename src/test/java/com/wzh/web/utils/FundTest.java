package com.wzh.web.utils;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.db.DbUtil;
import cn.hutool.db.Entity;
import cn.hutool.json.JSONObject;
import com.wzh.web.db.DBUtil;
import com.wzh.web.po.Fund;
import com.wzh.web.po.FundAll;
import org.jsoup.nodes.Node;
import org.junit.Test;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.seimicrawler.xpath.JXDocument;
import org.seimicrawler.xpath.JXNode;

import java.io.File;
import java.nio.charset.Charset;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.*;

/**
 * @Author: wzh
 * @Date: 2020/7/10 16:21
 **/
public class FundTest {

    /**
     * 采集--开放基金排行、可购
     */
    @Test
    public void test2351(){
        String url = "http://fund.eastmoney.com/data/fundranking.html#tall;c0;r;szzf;pn10000;ddesc;qsd20190712;qed20200712;qdii;zq;gg;gzbd;gzfs;bbzt;sfbb";
        ChromeDriver driver = getChromeDriver();
        driver.get(url);
        new WebDriverWait(driver,1000*30);
        String pageSource = driver.getPageSource();
        String rootPath = System.getProperty("user.dir");
        File file = new File(rootPath + "/src/main/resources/" + System.currentTimeMillis() + ".html");
        FileUtil.writeUtf8String(pageSource,file);
        driver.close();
    }

    private ChromeDriver getChromeDriver() {
        ChromeDriver driver;
        if (System.getProperty("os.name").toLowerCase().startsWith("mac")) {
            driver = initDriverMac();
        }else {
            driver = initDriverWin();
        }
        return driver;
    }


    /**
     * 解析--开放基金排行数据存到mysql
     */
    @Test
    public void test1341() {
        String s = FileUtil.readString(System.getProperty("user.dir") + "/src/main/resources/1595236595592.html", Charset.defaultCharset());
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
                fund.setJzrq(new Date(DateUtil.parseDate("2020-" + nodeList.get(4).childNode(0).toString()).getTime()));
            } catch (Exception e) {
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
            } catch (Exception e) {
            }
            //可购
            fund.setMd(1L);

            DBUtil.insertFund(fund);

        }
        System.out.println(1);

    }

    /**
     * 所有基金按基金代码排序
     */
    @Test
    public void test1351() {
        String url = "http://fund.eastmoney.com/allfund.html";
        ChromeDriver driver = initDriverWin();
        driver.get(url);
        new WebDriverWait(driver, 1000 * 10);
        String pageSource = driver.getPageSource();
        JXDocument jxDocument = JXDocument.create(pageSource);
        List<JXNode> jxNodes = jxDocument.selN("//ul[@class='num_right']/li");
        for (JXNode jxNode : jxNodes) {
            List<Node> nodes = jxNode.asElement().childNodes();
            if (nodes.size() == 0) {
                continue;
            }
            List<Node> nodeList = nodes.get(0).childNodes();
            String name = nodeList.get(0).childNode(0).toString();
            String[] split = name.split("）");
            String fundCode = split[0].replace("（", "");
            String fundName = split[1];
            String archives = nodeList.get(4).attr("href");
            JSONObject jsonObject = new JSONObject().put("code", fundCode).put("name", fundName).put("archives", archives);
            if (DBUtil.findFundInfoByCode(jsonObject.getStr("code"))) {
                System.out.println("信息已存在 = " + jsonObject.toString());
            } else {
                DBUtil.insertFundInfo(jsonObject);
            }
        }
        driver.close();
    }

    /**
     * http://fund.eastmoney.com/000001.html
     */
    @Test
    public void test1031() throws InterruptedException {
        String url = "http://fund.eastmoney.com/666.html";
        List<Entity> fundInfo = DBUtil.findFundInfo();
        ChromeDriver driver = initDriverWin();
        for (Entity entity : fundInfo) {
            String code = entity.getStr("code");
            String name = entity.getStr("name");
            String newUrl = url.replace("666", code);
            driver.get(newUrl);
            new WebDriverWait(driver, 1000 * 5);
            Thread.sleep(1000 * 1);
            String pageSource = driver.getPageSource();
            JXDocument jxDocument = JXDocument.create(pageSource);
            List<Node> jxNodes = jxDocument.selN("//*[@id='Li1']/div[1]/table[@class='ui-table-hover']/tbody").get(0).asElement().childNodes();
            if (jxNodes.size() < 2) {
                System.out.println("当前无数据 = " + code);
                continue;
            }
            for (int i = 0; i < jxNodes.size(); i++) {
                if (i == 0) {
                    continue;
                }
                List<Node> nodes = jxNodes.get(i).childNodes();
                String rq = nodes.get(1).childNode(0).toString();
                double dwjz = Double.valueOf(nodes.get(3).childNode(0).toString());
                double ljjz = Double.valueOf(nodes.get(5).childNode(0).toString());
                String rzdfStr = nodes.get(7).childNode(0).childNode(0).toString();
                Double rzdf = Double.valueOf(rzdfStr.replace("%", ""));
                FundAll fundAll = new FundAll();
                fundAll.setCode(code);
                fundAll.setName(name);
                fundAll.setDwjz(dwjz);
                fundAll.setLjjz(ljjz);
                fundAll.setRzdf(rzdf);
                fundAll.setRq(new Date(DateUtil.parseDate("2020-" + rq).getTime()));
                fundAll.setCreate(new Timestamp(new DateTime().getTime()));
                DBUtil.insertFund_All(fundAll);
            }
        }
        driver.close();
    }

    @Test
    public void test2212() throws InterruptedException {
        String url = "http://fund.eastmoney.com/666.html";
        String code = "164402";
        String name = "164402";
        String newUrl = url.replace("666",code);
        ChromeDriver driver = getChromeDriver();
        driver.get(newUrl);
            new WebDriverWait(driver, 1000 * 5);
            Thread.sleep(1000 * 1);
            String pageSource = driver.getPageSource();
            JXDocument jxDocument = JXDocument.create(pageSource);
            List<Node> jxNodes = jxDocument.selN("//*[@id='Li1']/div[1]/table[@class='ui-table-hover']/tbody").get(0).asElement().childNodes();
            if (jxNodes.size() < 2) {
                System.out.println("当前无数据 = " + code);

            }
            for (int i = 0; i < jxNodes.size(); i++) {
                if (i == 0) {
                    continue;
                }
                List<Node> nodes = jxNodes.get(i).childNodes();
                String rq = nodes.get(1).childNode(0).toString();
                double dwjz = Double.valueOf(nodes.get(3).childNode(0).toString());
                double ljjz = Double.valueOf(nodes.get(5).childNode(0).toString());
                String rzdfStr = nodes.get(7).childNode(0).childNode(0).toString();
                Double rzdf = Double.valueOf(rzdfStr.replace("%", ""));
                FundAll fundAll = new FundAll();
                fundAll.setCode(code);
                fundAll.setName(name);
                fundAll.setDwjz(dwjz);
                fundAll.setLjjz(ljjz);
                fundAll.setRzdf(rzdf);
                fundAll.setRq(new Date(DateUtil.parseDate("2020-" + rq).getTime()));
                fundAll.setCreate(new Timestamp(new DateTime().getTime()));
                DBUtil.insertFund_All(fundAll);
            }

        driver.close();
    }

    /**
     * java.runtime.name:Java(TM) SE Runtime Environment
     * sun.boot.library.path:D:\jdk\jre\bin
     * java.vm.version:25.171-b11
     * java.vm.vendor:Oracle Corporation
     * java.vendor.url:http://java.oracle.com/
     * path.separator:;
     * java.vm.name:Java HotSpot(TM) 64-Bit Server VM
     * file.encoding.pkg:sun.io
     * user.country:CN
     * user.script:
     * sun.java.launcher:SUN_STANDARD
     * sun.os.patch.level:
     * java.vm.specification.name:Java Virtual Machine Specification
     * user.dir:D:\IDEAspace\jianlai
     * java.runtime.version:1.8.0_171-b11
     * java.awt.graphicsenv:sun.awt.Win32GraphicsEnvironment
     * java.endorsed.dirs:D:\jdk\jre\lib\endorsed
     * os.arch:amd64
     * java.io.tmpdir:C:\Users\dell0\AppData\Local\Temp\
     * line.separator:
     * <p>
     * java.vm.specification.vendor:Oracle Corporation
     * user.variant:
     * os.name:Windows 10
     * sun.jnu.encoding:GBK
     * java.library.path:D:\jdk\bin;C:\WINDOWS\Sun\Java\bin;C:\WINDOWS\system32;C:\WINDOWS;D:\install\elasticsearch_windows_auto_install-master\curl\win64;D:\jdk\bin;D:\develop\jdk-11.0.5\bin;C:\ProgramData\Oracle\Java\javapath;D:\Python27\;D:\Python27\Scripts\;D:\Program Files\Python36\Scripts\;D:\Program Files\Python36\;C:\Program Files (x86)\Intel\iCLS Client\;C:\Program Files\Intel\iCLS Client\;C:\WINDOWS\system32;C:\WINDOWS;C:\WINDOWS\System32\Wbem;C:\WINDOWS\System32\WindowsPowerShell\v1.0\;C:\Program Files (x86)\Intel\Intel(R) Management Engine Components\DAL;C:\Program Files\Intel\Intel(R) Management Engine Components\DAL;C:\Program Files (x86)\Intel\Intel(R) Management Engine Components\IPT;C:\Program Files\Intel\Intel(R) Management Engine Components\IPT;D:\Program Files\oracleclient;D:\seleniumDriver;D:\android-sdk_r24.4.1-windows\platform-tools;D:\android-sdk_r24.4.1-windows\tools;D:\jdk\bin;D:\jdk\jre\bin;C:\Program Files\MySQL\MySQL Utilities 1.6\;D:\Program Files (x86;D:\develop\apache-maven-3.5.3\bin;C:\Program Files\VanDyke Software\Clients\;D:\Program Files\UltraCompare;D:\Program Files\UltraCompare1;D:\Program Files\TortoiseGit\bin;D:\install\Git\cmd;D:\Program Files (x86)\Common Files\GTK\2.0\bin;D:\develop\nodejs\;C:\WINDOWS\system32;C:\WINDOWS;C:\WINDOWS\System32\Wbem;C:\WINDOWS\System32\WindowsPowerShell\v1.0\;C:\WINDOWS\System32\OpenSSH\;D:\install\go\bin;C:\Users\dell0\AppData\Local\Microsoft\WindowsApps;D:\Program Files\oracleclient;D:\Program Files\Fiddler;C:\Users\dell0\AppData\Local\BypassRuntm;C:\Users\dell0\AppData\Roaming\npm;C:\Users\dell0\AppData\Local\GitHubDesktop\bin;C:\Users\dell0\AppData\Local\Microsoft\WindowsApps;;.
     * java.specification.name:Java Platform API Specification
     * java.class.version:52.0
     * sun.management.compiler:HotSpot 64-Bit Tiered Compilers
     * os.version:10.0
     * user.home:C:\Users\dell0
     * user.timezone:
     * java.awt.printerjob:sun.awt.windows.WPrinterJob
     * file.encoding:UTF-8
     * java.specification.version:1.8
     * java.class.path:D:\IDEA2020\lib\idea_rt.jar;D:\IDEA2020\plugins\junit\lib\junit5-rt.jar;D:\IDEA2020\plugins\junit\lib\junit-rt.jar;D:\jdk\jre\lib\charsets.jar;D:\jdk\jre\lib\deploy.jar;D:\jdk\jre\lib\ext\access-bridge-64.jar;D:\jdk\jre\lib\ext\cldrdata.jar;D:\jdk\jre\lib\ext\dnsns.jar;D:\jdk\jre\lib\ext\jaccess.jar;D:\jdk\jre\lib\ext\jfxrt.jar;D:\jdk\jre\lib\ext\localedata.jar;D:\jdk\jre\lib\ext\nashorn.jar;D:\jdk\jre\lib\ext\sunec.jar;D:\jdk\jre\lib\ext\sunjce_provider.jar;D:\jdk\jre\lib\ext\sunmscapi.jar;D:\jdk\jre\lib\ext\sunpkcs11.jar;D:\jdk\jre\lib\ext\zipfs.jar;D:\jdk\jre\lib\javaws.jar;D:\jdk\jre\lib\jce.jar;D:\jdk\jre\lib\jfr.jar;D:\jdk\jre\lib\jfxswt.jar;D:\jdk\jre\lib\jsse.jar;D:\jdk\jre\lib\management-agent.jar;D:\jdk\jre\lib\plugin.jar;D:\jdk\jre\lib\resources.jar;D:\jdk\jre\lib\rt.jar;D:\IDEAspace\jianlai\target\test-classes;D:\IDEAspace\jianlai\target\classes;D:\develop\m2repository\org\springframework\boot\spring-boot-starter-web\2.3.0.RELEASE\spring-boot-starter-web-2.3.0.RELEASE.jar;D:\develop\m2repository\org\springframework\boot\spring-boot-starter\2.3.0.RELEASE\spring-boot-starter-2.3.0.RELEASE.jar;D:\develop\m2repository\org\springframework\boot\spring-boot\2.3.0.RELEASE\spring-boot-2.3.0.RELEASE.jar;D:\develop\m2repository\org\springframework\boot\spring-boot-autoconfigure\2.3.0.RELEASE\spring-boot-autoconfigure-2.3.0.RELEASE.jar;D:\develop\m2repository\org\springframework\boot\spring-boot-starter-logging\2.3.0.RELEASE\spring-boot-starter-logging-2.3.0.RELEASE.jar;D:\develop\m2repository\ch\qos\logback\logback-classic\1.2.3\logback-classic-1.2.3.jar;D:\develop\m2repository\ch\qos\logback\logback-core\1.2.3\logback-core-1.2.3.jar;D:\develop\m2repository\org\apache\logging\log4j\log4j-to-slf4j\2.13.2\log4j-to-slf4j-2.13.2.jar;D:\develop\m2repository\org\apache\logging\log4j\log4j-api\2.13.2\log4j-api-2.13.2.jar;D:\develop\m2repository\org\slf4j\jul-to-slf4j\1.7.30\jul-to-slf4j-1.7.30.jar;D:\develop\m2repository\jakarta\annotation\jakarta.annotation-api\1.3.5\jakarta.annotation-api-1.3.5.jar;D:\develop\m2repository\org\yaml\snakeyaml\1.26\snakeyaml-1.26.jar;D:\develop\m2repository\org\springframework\boot\spring-boot-starter-json\2.3.0.RELEASE\spring-boot-starter-json-2.3.0.RELEASE.jar;D:\develop\m2repository\com\fasterxml\jackson\core\jackson-databind\2.11.0\jackson-databind-2.11.0.jar;D:\develop\m2repository\com\fasterxml\jackson\core\jackson-annotations\2.11.0\jackson-annotations-2.11.0.jar;D:\develop\m2repository\com\fasterxml\jackson\core\jackson-core\2.11.0\jackson-core-2.11.0.jar;D:\develop\m2repository\com\fasterxml\jackson\datatype\jackson-datatype-jdk8\2.11.0\jackson-datatype-jdk8-2.11.0.jar;D:\develop\m2repository\com\fasterxml\jackson\datatype\jackson-datatype-jsr310\2.11.0\jackson-datatype-jsr310-2.11.0.jar;D:\develop\m2repository\com\fasterxml\jackson\module\jackson-module-parameter-names\2.11.0\jackson-module-parameter-names-2.11.0.jar;D:\develop\m2repository\org\springframework\boot\spring-boot-starter-tomcat\2.3.0.RELEASE\spring-boot-starter-tomcat-2.3.0.RELEASE.jar;D:\develop\m2repository\org\apache\tomcat\embed\tomcat-embed-core\9.0.35\tomcat-embed-core-9.0.35.jar;D:\develop\m2repository\org\glassfish\jakarta.el\3.0.3\jakarta.el-3.0.3.jar;D:\develop\m2repository\org\apache\tomcat\embed\tomcat-embed-websocket\9.0.35\tomcat-embed-websocket-9.0.35.jar;D:\develop\m2repository\org\springframework\spring-web\5.2.6.RELEASE\spring-web-5.2.6.RELEASE.jar;D:\develop\m2repository\org\springframework\spring-beans\5.2.6.RELEASE\spring-beans-5.2.6.RELEASE.jar;D:\develop\m2repository\org\springframework\spring-webmvc\5.2.6.RELEASE\spring-webmvc-5.2.6.RELEASE.jar;D:\develop\m2repository\org\springframework\spring-aop\5.2.6.RELEASE\spring-aop-5.2.6.RELEASE.jar;D:\develop\m2repository\org\springframework\spring-context\5.2.6.RELEASE\spring-context-5.2.6.RELEASE.jar;D:\develop\m2repository\org\springframework\spring-expression\5.2.6.RELEASE\spring-expression-5.2.6.RELEASE.jar;D:\develop\m2repository\org\projectlombok\lombok\1.18.12\lombok-1.18.12.jar;D:\develop\m2repository\org\springframework\boot\spring-boot-starter-test\2.3.0.RELEASE\spring-boot-starter-test-2.3.0.RELEASE.jar;D:\develop\m2repository\org\springframework\boot\spring-boot-test\2.3.0.RELEASE\spring-boot-test-2.3.0.RELEASE.jar;D:\develop\m2repository\org\springframework\boot\spring-boot-test-autoconfigure\2.3.0.RELEASE\spring-boot-test-autoconfigure-2.3.0.RELEASE.jar;D:\develop\m2repository\com\jayway\jsonpath\json-path\2.4.0\json-path-2.4.0.jar;D:\develop\m2repository\net\minidev\json-smart\2.3\json-smart-2.3.jar;D:\develop\m2repository\net\minidev\accessors-smart\1.2\accessors-smart-1.2.jar;D:\develop\m2repository\org\ow2\asm\asm\5.0.4\asm-5.0.4.jar;D:\develop\m2repository\jakarta\xml\bind\jakarta.xml.bind-api\2.3.3\jakarta.xml.bind-api-2.3.3.jar;D:\develop\m2repository\jakarta\activation\jakarta.activation-api\1.2.2\jakarta.activation-api-1.2.2.jar;D:\develop\m2repository\org\assertj\assertj-core\3.16.1\assertj-core-3.16.1.jar;D:\develop\m2repository\org\hamcrest\hamcrest\2.2\hamcrest-2.2.jar;D:\develop\m2repository\org\junit\jupiter\junit-jupiter\5.6.2\junit-jupiter-5.6.2.jar;D:\develop\m2repository\org\junit\jupiter\junit-jupiter-api\5.6.2\junit-jupiter-api-5.6.2.jar;D:\develop\m2repository\org\apiguardian\apiguardian-api\1.1.0\apiguardian-api-1.1.0.jar;D:\develop\m2repository\org\opentest4j\opentest4j\1.2.0\opentest4j-1.2.0.jar;D:\develop\m2repository\org\junit\platform\junit-platform-commons\1.6.2\junit-platform-commons-1.6.2.jar;D:\develop\m2repository\org\junit\jupiter\junit-jupiter-params\5.6.2\junit-jupiter-params-5.6.2.jar;D:\develop\m2repository\org\junit\jupiter\junit-jupiter-engine\5.6.2\junit-jupiter-engine-5.6.2.jar;D:\develop\m2repository\org\junit\platform\junit-platform-engine\1.6.2\junit-platform-engine-1.6.2.jar;D:\develop\m2repository\org\mockito\mockito-core\3.3.3\mockito-core-3.3.3.jar;D:\develop\m2repository\net\bytebuddy\byte-buddy\1.10.10\byte-buddy-1.10.10.jar;D:\develop\m2repository\net\bytebuddy\byte-buddy-agent\1.10.10\byte-buddy-agent-1.10.10.jar;D:\develop\m2repository\org\objenesis\objenesis\2.6\objenesis-2.6.jar;D:\develop\m2repository\org\mockito\mockito-junit-jupiter\3.3.3\mockito-junit-jupiter-3.3.3.jar;D:\develop\m2repository\org\skyscreamer\jsonassert\1.5.0\jsonassert-1.5.0.jar;D:\develop\m2repository\com\vaadin\external\google\android-json\0.0.20131108.vaadin1\android-json-0.0.20131108.vaadin1.jar;D:\develop\m2repository\org\springframework\spring-core\5.2.6.RELEASE\spring-core-5.2.6.RELEASE.jar;D:\develop\m2repository\org\springframework\spring-jcl\5.2.6.RELEASE\spring-jcl-5.2.6.RELEASE.jar;D:\develop\m2repository\org\springframework\spring-test\5.2.6.RELEASE\spring-test-5.2.6.RELEASE.jar;D:\develop\m2repository\org\xmlunit\xmlunit-core\2.7.0\xmlunit-core-2.7.0.jar;D:\develop\m2repository\cn\wanghaomiao\JsoupXpath\2.2\JsoupXpath-2.2.jar;D:\develop\m2repository\org\jsoup\jsoup\1.10.3\jsoup-1.10.3.jar;D:\develop\m2repository\org\apache\commons\commons-lang3\3.10\commons-lang3-3.10.jar;D:\develop\m2repository\org\antlr\antlr4-runtime\4.7\antlr4-runtime-4.7.jar;D:\develop\m2repository\org\slf4j\slf4j-api\1.7.30\slf4j-api-1.7.30.jar;D:\develop\m2repository\cn\hutool\hutool-all\5.2.1\hutool-all-5.2.1.jar;D:\develop\m2repository\mysql\mysql-connector-java\5.1.48\mysql-connector-java-5.1.48.jar;D:\develop\m2repository\junit\junit\4.13\junit-4.13.jar;D:\develop\m2repository\org\hamcrest\hamcrest-core\2.2\hamcrest-core-2.2.jar;D:\IDEA2020\lib\idea_rt.jar
     * user.name:dell0
     * java.vm.specification.version:1.8
     * sun.java.command:com.intellij.rt.junit.JUnitStarter -ideVersion5 -junit4 com.wzh.web.utils.FundTest,test0951
     * java.home:D:\jdk\jre
     * sun.arch.data.model:64
     * user.language:zh
     * java.specification.vendor:Oracle Corporation
     * awt.toolkit:sun.awt.windows.WToolkit
     * java.vm.info:mixed mode
     * java.version:1.8.0_171
     * java.ext.dirs:D:\jdk\jre\lib\ext;C:\WINDOWS\Sun\Java\lib\ext
     * sun.boot.class.path:D:\jdk\jre\lib\resources.jar;D:\jdk\jre\lib\rt.jar;D:\jdk\jre\lib\sunrsasign.jar;D:\jdk\jre\lib\jsse.jar;D:\jdk\jre\lib\jce.jar;D:\jdk\jre\lib\charsets.jar;D:\jdk\jre\lib\jfr.jar;D:\jdk\jre\classes
     * java.vendor:Oracle Corporation
     * file.separator:\
     * java.vendor.url.bug:http://bugreport.sun.com/bugreport/
     * idea.test.cyclic.buffer.size:10485760
     * sun.io.unicode.encoding:UnicodeLittle
     * sun.cpu.endian:little
     * sun.desktop:windows
     * sun.cpu.isalist:amd64
     *
     * ————————Mac————————
     * gopherProxySet:false
     * awt.toolkit:sun.lwawt.macosx.LWCToolkit
     * java.specification.version:11
     * sun.cpu.isalist:
     * sun.jnu.encoding:UTF-8
     * java.class.path:/Applications/IntelliJ IDEA.app/Contents/lib/idea_rt.jar:/Applications/IntelliJ IDEA.app/Contents/plugins/junit/lib/junit5-rt.jar:/Applications/IntelliJ IDEA.app/Contents/plugins/junit/lib/junit-rt.jar:/Users/somnr/IdeaProjects/git/jianlai/target/test-classes:/Users/somnr/IdeaProjects/git/jianlai/target/classes:/Users/somnr/Desktop/develop/install/repository/org/springframework/boot/spring-boot-starter-web/2.3.0.RELEASE/spring-boot-starter-web-2.3.0.RELEASE.jar:/Users/somnr/Desktop/develop/install/repository/org/springframework/boot/spring-boot-starter/2.3.0.RELEASE/spring-boot-starter-2.3.0.RELEASE.jar:/Users/somnr/Desktop/develop/install/repository/org/springframework/boot/spring-boot/2.3.0.RELEASE/spring-boot-2.3.0.RELEASE.jar:/Users/somnr/Desktop/develop/install/repository/org/springframework/boot/spring-boot-autoconfigure/2.3.0.RELEASE/spring-boot-autoconfigure-2.3.0.RELEASE.jar:/Users/somnr/Desktop/develop/install/repository/org/springframework/boot/spring-boot-starter-logging/2.3.0.RELEASE/spring-boot-starter-logging-2.3.0.RELEASE.jar:/Users/somnr/Desktop/develop/install/repository/ch/qos/logback/logback-classic/1.2.3/logback-classic-1.2.3.jar:/Users/somnr/Desktop/develop/install/repository/ch/qos/logback/logback-core/1.2.3/logback-core-1.2.3.jar:/Users/somnr/Desktop/develop/install/repository/org/apache/logging/log4j/log4j-to-slf4j/2.13.2/log4j-to-slf4j-2.13.2.jar:/Users/somnr/Desktop/develop/install/repository/org/apache/logging/log4j/log4j-api/2.13.2/log4j-api-2.13.2.jar:/Users/somnr/Desktop/develop/install/repository/org/slf4j/jul-to-slf4j/1.7.30/jul-to-slf4j-1.7.30.jar:/Users/somnr/Desktop/develop/install/repository/jakarta/annotation/jakarta.annotation-api/1.3.5/jakarta.annotation-api-1.3.5.jar:/Users/somnr/Desktop/develop/install/repository/org/yaml/snakeyaml/1.26/snakeyaml-1.26.jar:/Users/somnr/Desktop/develop/install/repository/org/springframework/boot/spring-boot-starter-json/2.3.0.RELEASE/spring-boot-starter-json-2.3.0.RELEASE.jar:/Users/somnr/Desktop/develop/install/repository/com/fasterxml/jackson/core/jackson-databind/2.11.0/jackson-databind-2.11.0.jar:/Users/somnr/Desktop/develop/install/repository/com/fasterxml/jackson/core/jackson-annotations/2.11.0/jackson-annotations-2.11.0.jar:/Users/somnr/Desktop/develop/install/repository/com/fasterxml/jackson/core/jackson-core/2.11.0/jackson-core-2.11.0.jar:/Users/somnr/Desktop/develop/install/repository/com/fasterxml/jackson/datatype/jackson-datatype-jdk8/2.11.0/jackson-datatype-jdk8-2.11.0.jar:/Users/somnr/Desktop/develop/install/repository/com/fasterxml/jackson/datatype/jackson-datatype-jsr310/2.11.0/jackson-datatype-jsr310-2.11.0.jar:/Users/somnr/Desktop/develop/install/repository/com/fasterxml/jackson/module/jackson-module-parameter-names/2.11.0/jackson-module-parameter-names-2.11.0.jar:/Users/somnr/Desktop/develop/install/repository/org/springframework/boot/spring-boot-starter-tomcat/2.3.0.RELEASE/spring-boot-starter-tomcat-2.3.0.RELEASE.jar:/Users/somnr/Desktop/develop/install/repository/org/apache/tomcat/embed/tomcat-embed-core/9.0.35/tomcat-embed-core-9.0.35.jar:/Users/somnr/Desktop/develop/install/repository/org/glassfish/jakarta.el/3.0.3/jakarta.el-3.0.3.jar:/Users/somnr/Desktop/develop/install/repository/org/apache/tomcat/embed/tomcat-embed-websocket/9.0.35/tomcat-embed-websocket-9.0.35.jar:/Users/somnr/Desktop/develop/install/repository/org/springframework/spring-web/5.2.6.RELEASE/spring-web-5.2.6.RELEASE.jar:/Users/somnr/Desktop/develop/install/repository/org/springframework/spring-beans/5.2.6.RELEASE/spring-beans-5.2.6.RELEASE.jar:/Users/somnr/Desktop/develop/install/repository/org/springframework/spring-webmvc/5.2.6.RELEASE/spring-webmvc-5.2.6.RELEASE.jar:/Users/somnr/Desktop/develop/install/repository/org/springframework/spring-aop/5.2.6.RELEASE/spring-aop-5.2.6.RELEASE.jar:/Users/somnr/Desktop/develop/install/repository/org/springframework/spring-context/5.2.6.RELEASE/spring-context-5.2.6.RELEASE.jar:/Users/somnr/Desktop/develop/install/repository/org/springframework/spring-expression/5.2.6.RELEASE/spring-expression-5.2.6.RELEASE.jar:/Users/somnr/Desktop/develop/install/repository/org/projectlombok/lombok/1.18.12/lombok-1.18.12.jar:/Users/somnr/Desktop/develop/install/repository/org/springframework/boot/spring-boot-starter-test/2.3.0.RELEASE/spring-boot-starter-test-2.3.0.RELEASE.jar:/Users/somnr/Desktop/develop/install/repository/org/springframework/boot/spring-boot-test/2.3.0.RELEASE/spring-boot-test-2.3.0.RELEASE.jar:/Users/somnr/Desktop/develop/install/repository/org/springframework/boot/spring-boot-test-autoconfigure/2.3.0.RELEASE/spring-boot-test-autoconfigure-2.3.0.RELEASE.jar:/Users/somnr/Desktop/develop/install/repository/com/jayway/jsonpath/json-path/2.4.0/json-path-2.4.0.jar:/Users/somnr/Desktop/develop/install/repository/net/minidev/json-smart/2.3/json-smart-2.3.jar:/Users/somnr/Desktop/develop/install/repository/net/minidev/accessors-smart/1.2/accessors-smart-1.2.jar:/Users/somnr/Desktop/develop/install/repository/org/ow2/asm/asm/5.0.4/asm-5.0.4.jar:/Users/somnr/Desktop/develop/install/repository/jakarta/xml/bind/jakarta.xml.bind-api/2.3.3/jakarta.xml.bind-api-2.3.3.jar:/Users/somnr/Desktop/develop/install/repository/jakarta/activation/jakarta.activation-api/1.2.2/jakarta.activation-api-1.2.2.jar:/Users/somnr/Desktop/develop/install/repository/org/assertj/assertj-core/3.16.1/assertj-core-3.16.1.jar:/Users/somnr/Desktop/develop/install/repository/org/hamcrest/hamcrest/2.2/hamcrest-2.2.jar:/Users/somnr/Desktop/develop/install/repository/org/junit/jupiter/junit-jupiter/5.6.2/junit-jupiter-5.6.2.jar:/Users/somnr/Desktop/develop/install/repository/org/junit/jupiter/junit-jupiter-api/5.6.2/junit-jupiter-api-5.6.2.jar:/Users/somnr/Desktop/develop/install/repository/org/apiguardian/apiguardian-api/1.1.0/apiguardian-api-1.1.0.jar:/Users/somnr/Desktop/develop/install/repository/org/opentest4j/opentest4j/1.2.0/opentest4j-1.2.0.jar:/Users/somnr/Desktop/develop/install/repository/org/junit/platform/junit-platform-commons/1.6.2/junit-platform-commons-1.6.2.jar:/Users/somnr/Desktop/develop/install/repository/org/junit/jupiter/junit-jupiter-params/5.6.2/junit-jupiter-params-5.6.2.jar:/Users/somnr/Desktop/develop/install/repository/org/junit/jupiter/junit-jupiter-engine/5.6.2/junit-jupiter-engine-5.6.2.jar:/Users/somnr/Desktop/develop/install/repository/org/junit/platform/junit-platform-engine/1.6.2/junit-platform-engine-1.6.2.jar:/Users/somnr/Desktop/develop/install/repository/org/mockito/mockito-core/3.3.3/mockito-core-3.3.3.jar:/Users/somnr/Desktop/develop/install/repository/net/bytebuddy/byte-buddy/1.10.10/byte-buddy-1.10.10.jar:/Users/somnr/Desktop/develop/install/repository/net/bytebuddy/byte-buddy-agent/1.10.10/byte-buddy-agent-1.10.10.jar:/Users/somnr/Desktop/develop/install/repository/org/objenesis/objenesis/2.6/objenesis-2.6.jar:/Users/somnr/Desktop/develop/install/repository/org/mockito/mockito-junit-jupiter/3.3.3/mockito-junit-jupiter-3.3.3.jar:/Users/somnr/Desktop/develop/install/repository/org/skyscreamer/jsonassert/1.5.0/jsonassert-1.5.0.jar:/Users/somnr/Desktop/develop/install/repository/com/vaadin/external/google/android-json/0.0.20131108.vaadin1/android-json-0.0.20131108.vaadin1.jar:/Users/somnr/Desktop/develop/install/repository/org/springframework/spring-core/5.2.6.RELEASE/spring-core-5.2.6.RELEASE.jar:/Users/somnr/Desktop/develop/install/repository/org/springframework/spring-jcl/5.2.6.RELEASE/spring-jcl-5.2.6.RELEASE.jar:/Users/somnr/Desktop/develop/install/repository/org/springframework/spring-test/5.2.6.RELEASE/spring-test-5.2.6.RELEASE.jar:/Users/somnr/Desktop/develop/install/repository/org/xmlunit/xmlunit-core/2.7.0/xmlunit-core-2.7.0.jar:/Users/somnr/Desktop/develop/install/repository/cn/wanghaomiao/JsoupXpath/2.2/JsoupXpath-2.2.jar:/Users/somnr/Desktop/develop/install/repository/org/jsoup/jsoup/1.10.3/jsoup-1.10.3.jar:/Users/somnr/Desktop/develop/install/repository/org/apache/commons/commons-lang3/3.10/commons-lang3-3.10.jar:/Users/somnr/Desktop/develop/install/repository/org/antlr/antlr4-runtime/4.7/antlr4-runtime-4.7.jar:/Users/somnr/Desktop/develop/install/repository/org/slf4j/slf4j-api/1.7.30/slf4j-api-1.7.30.jar:/Users/somnr/Desktop/develop/install/repository/cn/hutool/hutool-all/5.2.1/hutool-all-5.2.1.jar:/Users/somnr/Desktop/develop/install/repository/mysql/mysql-connector-java/5.1.48/mysql-connector-java-5.1.48.jar:/Users/somnr/Desktop/develop/install/repository/junit/junit/4.13/junit-4.13.jar:/Users/somnr/Desktop/develop/install/repository/org/hamcrest/hamcrest-core/2.2/hamcrest-core-2.2.jar
     * java.vm.vendor:Amazon.com Inc.
     * sun.arch.data.model:64
     * idea.test.cyclic.buffer.size:1048576
     * java.vendor.url:https://aws.amazon.com/corretto/
     * user.timezone:
     * os.name:Mac OS X
     * java.vm.specification.version:11
     * sun.java.launcher:SUN_STANDARD
     * user.country:CN
     * sun.boot.library.path:/Users/somnr/Library/Java/JavaVirtualMachines/corretto-11.0.7/Contents/Home/lib
     * sun.java.command:com.intellij.rt.junit.JUnitStarter -ideVersion5 -junit4 com.wzh.web.utils.FundTest,test0951
     * jdk.debug:release
     * sun.cpu.endian:little
     * user.home:/Users/somnr
     * user.language:zh
     * java.specification.vendor:Oracle Corporation
     * java.version.date:2020-04-14
     * java.home:/Users/somnr/Library/Java/JavaVirtualMachines/corretto-11.0.7/Contents/Home
     * file.separator:/
     * java.vm.compressedOopsMode:Zero based
     * line.separator:
     *
     * java.specification.name:Java Platform API Specification
     * java.vm.specification.vendor:Oracle Corporation
     * java.awt.graphicsenv:sun.awt.CGraphicsEnvironment
     * user.script:Hans
     * sun.management.compiler:HotSpot 64-Bit Tiered Compilers
     * java.runtime.version:11.0.7+10-LTS
     * user.name:somnr
     * path.separator::
     * os.version:10.15.2
     * java.runtime.name:OpenJDK Runtime Environment
     * file.encoding:UTF-8
     * java.vm.name:OpenJDK 64-Bit Server VM
     * java.vendor.version:Corretto-11.0.7.10.1
     * java.vendor.url.bug:https://github.com/corretto/corretto-11/issues/
     * java.io.tmpdir:/var/folders/2x/459dczss42ngd98lyj14_ctr0000gn/T/
     * java.version:11.0.7
     * user.dir:/Users/somnr/IdeaProjects/git/jianlai
     * os.arch:x86_64
     * java.vm.specification.name:Java Virtual Machine Specification
     * java.awt.printerjob:sun.lwawt.macosx.CPrinterJob
     * sun.os.patch.level:unknown
     * java.library.path:/Users/somnr/Library/Java/Extensions:/Library/Java/Extensions:/Network/Library/Java/Extensions:/System/Library/Java/Extensions:/usr/lib/java:.
     * java.vendor:Amazon.com Inc.
     * java.vm.info:mixed mode
     * java.vm.version:11.0.7+10-LTS
     * sun.io.unicode.encoding:UnicodeBig
     * java.class.version:55.0
     */
    @Test
    public void test0951() {
        Properties properties = System.getProperties();
        Set<Map.Entry<Object, Object>> entries = properties.entrySet();
        for (Map.Entry<Object, Object> entry : entries) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }
    }

    private Double stringToDouble(String s) {
        s = s.replace("-", "");
        if (StrUtil.isBlank(s)) {
            return 0d;
        }
        s = s.replace("%", "");
        try {
            return Double.valueOf(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0d;
    }

    @Test
    public void test1009() {
        ChromeDriver driver = initDriverMac();
        driver.get("http://www.2345.com");
        new WebDriverWait(driver, 1000 * 3);
        System.out.println("1 = " + 1);

    }


    public static ChromeDriver initDriverMac() {
        ChromeOptions chromeOptions = new ChromeOptions();
        //设置代理,请先验证代理是否有效
        //去掉提示
        chromeOptions.setExperimentalOption("useAutomationExtension", false);
        chromeOptions.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
        //设置驱动路径
        System.setProperty("webdriver.chrome.driver", "/Users/somnr/Desktop/develop/chromeDriver/chromedriver");
        //取消信息显示：[1589963848.022][SEVERE]: Timed out receiving message from renderer: 0.100
        System.setProperty(ChromeDriverService.CHROME_DRIVER_SILENT_OUTPUT_PROPERTY, "true");
        ChromeDriver driver = new ChromeDriver(chromeOptions);
        return driver;
    }

    public static ChromeDriver initDriverWin() {
        ChromeOptions chromeOptions = new ChromeOptions();
        //去掉提示
        chromeOptions.setExperimentalOption("useAutomationExtension", false);
        chromeOptions.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
//        //开启F12模式
//        chromeOptions.addArguments("--auto-open-devtools-for-tabs");
        ChromeDriver driver = new ChromeDriver(chromeOptions);
        return driver;
    }
}
