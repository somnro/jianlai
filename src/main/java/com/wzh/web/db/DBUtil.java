package com.wzh.web.db;

import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import com.wzh.web.constant.Constant;
import com.wzh.web.po.Fund;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;

/**
 * @Author: wzh
 * @Date: 2020/6/8 9:32
 **/
public class DBUtil {
    public static void main(String[] args) {

    }

    public static int insertJiTang(String title,String content, String source){
        try {
            return Db.use("group_test").insert(
                    Entity.create("jitang").set(Constant.TITLE,title)
                    .set(Constant.CONTENT,content)
                    .set(Constant.SOURCE,source)
            );
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return -1;
    }
    public static Boolean existJiTang(String title){
        Boolean flag = false;
        try {
            flag = Db.use("group_test").query("select 1 from jitang where title = ?", title).size()>0;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return flag;
    }

    public static int insertFund (Fund fund){
        try {
            return Db.use("group_test").insert(Entity.create("fund").parseBean(fund));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return -1;
    }
}
