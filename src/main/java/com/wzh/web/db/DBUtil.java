package com.wzh.web.db;

import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import cn.hutool.json.JSONObject;
import com.wzh.web.constant.Constant;
import com.wzh.web.po.Fund;
import com.wzh.web.po.FundAll;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
    public static int insertFundInfo (JSONObject json){
        try {
            return Db.use("group_test").insert(Entity.create("fund_info")
                    .set("code",json.getStr("code"))
                    .set("name",json.getStr("name"))
                    .set("archives",json.getStr("archives")));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("存入信息失败=" + json);
        }
        return -1;
    }
    public static Boolean findFundInfoByCode (String code){
        try {
            return Db.use("group_test").queryNumber("select count(1) from fund_info where code = ?",code).intValue()>0;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }
    public static List<Entity> findFundInfo(){
        List<Entity> all = new ArrayList<>();
        try {
            all = Db.use("group_test").findAll("fund_info");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return all;
    }

    public static int insertFund_All (FundAll fundAll){
        try {
            return Db.use("group_test").insert(Entity.create("fund_all").parseBean(fundAll));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("数据库存入失败 = " + fundAll.toString());
        }
        return -1;
    }
    public static Boolean findFundAllByCodeAndRq (String code,String rq){
        try {
            return Db.use("group_test").queryNumber("select count(1) from fund_all where code = ? and rq=?",code,rq).intValue()>0;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return true;
    }

    public static void getFund(){
//        Db.use("group_test").query("")
    }
}
