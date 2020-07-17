package com.wzh.web.po;

/**
 * @Author: wzh
 * @Date: 2020/7/16 10:46
 **/
public class FundAll {


    private long id;
    private String code;
    private String name;
    private double dwjz;
    private double ljjz;
    private double rzdf;
    private java.sql.Date rq;
    private java.sql.Timestamp create;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public double getDwjz() {
        return dwjz;
    }

    public void setDwjz(double dwjz) {
        this.dwjz = dwjz;
    }

    public double getLjjz() {
        return ljjz;
    }

    public void setLjjz(double ljjz) {
        this.ljjz = ljjz;
    }


    public double getRzdf() {
        return rzdf;
    }

    public void setRzdf(double rzdf) {
        this.rzdf = rzdf;
    }


    public java.sql.Date getRq() {
        return rq;
    }

    public void setRq(java.sql.Date rq) {
        this.rq = rq;
    }


    public java.sql.Timestamp getCreate() {
        return create;
    }

    public void setCreate(java.sql.Timestamp create) {
        this.create = create;
    }
}
