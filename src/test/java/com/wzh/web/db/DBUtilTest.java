package com.wzh.web.db;

import com.wzh.web.po.Fund;
import org.junit.jupiter.api.Test;

import java.sql.Date;

/**
 * @Author: wzh
 * @Date: 2020/6/8 10:04
 **/
class DBUtilTest {

    @Test
    void insertJiTang() {
        System.out.println(DBUtil.insertJiTang("123","","1"));
    }

    @Test
    void existJiTang() {
        System.out.println(DBUtil.existJiTang("123"));
    }

    @Test
    void insertFund() {
        Fund fund = new Fund();
        fund.setCode("1111");
        fund.setDwjz(13.244);
        fund.setLjjz(12.23123123);
        fund.setJc("ceshi");
        fund.setJzrq(new Date(1242414124124L));
        System.out.println(DBUtil.insertFund(fund));
    }
}