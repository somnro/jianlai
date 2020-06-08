package com.wenzhiheng.jianlai.db;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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
}