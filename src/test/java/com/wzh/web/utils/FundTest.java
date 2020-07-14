package com.wzh.web.utils;

import cn.hutool.core.io.FileUtil;
import org.jsoup.nodes.Node;
import org.junit.Test;
import org.seimicrawler.xpath.JXDocument;
import org.seimicrawler.xpath.JXNode;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

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
     *
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
     * Process finished with exit code 0
     */
    @Test
    public void test0951() {
        Properties properties = System.getProperties();
        Set<Map.Entry<Object, Object>> entries = properties.entrySet();
        for (Map.Entry<Object, Object> entry : entries) {
            System.out.println(entry.getKey()+":"+entry.getValue());
        }
    }
}
