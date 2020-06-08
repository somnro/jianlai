package com.wenzhiheng.jianlai.db;

import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import cn.hutool.db.ds.DSFactory;
import com.wenzhiheng.jianlai.constant.Constant;

import javax.sql.DataSource;
import java.sql.SQLException;

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
}
