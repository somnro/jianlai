package com.wzh.web.test;

import cn.hutool.core.thread.ThreadUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Collections;

/**
 * @Author: wzh
 **/
public class SeleniumSurpriseMT20200727 {

    /**
     * 设置领用的最小红包，红包一般是2-5元
     */
    static int maxSurprise = 5;
    static boolean flag = true;
    static String url = "https://offsiteact.meituan.com/ad/landing/v1?f=mtovunXplNAk4MOSUebuRIJTTDLvsmh6HYawLpHtj%2Fw%2FdoMUGrSHp8bL7%2BkMszGovbpXHBtWzNVteoQpT8Zi5Q%3D%3D#/";

    public static void main(String[] args) {
        ChromeOptions chromeOptions = new ChromeOptions();
        //去掉提示
        chromeOptions.setExperimentalOption("useAutomationExtension", false);
        chromeOptions.setExperimentalOption("excludeSwitches",Collections.singletonList("enable-automation"));
//        //开启F12模式
//        chromeOptions.addArguments("--auto-open-devtools-for-tabs");
            ChromeDriver driver = new ChromeDriver(chromeOptions);
        while (flag) {
            try {
                driver.get(url);
                new WebDriverWait(driver,1000*3);
                Thread.sleep(1000L*2);
                WebElement element = driver.findElement(By.xpath("//div[@class='modal3-close-button']"));
                Thread.sleep(1000L);
                element.click();
                Thread.sleep(1000L);
                String text = driver.findElements(By.xpath("//div[@class='coupons-item-amount-number']")).get(0).getText();
                String textFlag = driver.findElement(By.xpath("//div[@class='coupons-item-remarks-fontsize1']")).getText();
                if (Integer.valueOf(text) >= maxSurprise && "通用红包".equals(textFlag)) {
                    System.out.println("text = " + text);
                    flag = false;
                    ThreadUtil.sleep(1000L * 60 * 600);
                } else {
                    ThreadUtil.sleep(2000L);
                }
                driver.manage().deleteAllCookies();
                Thread.sleep(1000*2);
            }catch (Exception e){
                e.printStackTrace();
                driver.manage().deleteAllCookies();
            }finally {
//                driver.close();
            }
        }
    }
}
