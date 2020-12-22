package com.graduation.smart_site_inspection_system.Bean;

public class ProjectCheckBean {

    private int ck_id;  //检查id
    private int pj_id;  //项目id
    private int group_id;  //检查组id
    private int checksys_id;//检查体系id
    private String ck_createtime;  //创建时间
    private String ck_finshtime;  //ck_finshtime
    private int ck_risk;  //结果风险

    private String ck_description;  //结果描述
    private int ck_isexam;  //是否审核
    private int ck_ispass;  //是否通过
    private String im_url;  //图片url

    public String getIm_url() {
        return im_url;
    }

    public void setIm_url(String im_url) {
        this.im_url = im_url;
    }

    public int getCk_id() {
        return ck_id;
    }

    public void setCk_id(int ck_id) {
        this.ck_id = ck_id;
    }

    public int getPj_id() {
        return pj_id;
    }

    public void setPj_id(int pj_id) {
        this.pj_id = pj_id;
    }

    public int getGroup_id() {
        return group_id;
    }

    public void setGroup_id(int group_id) {
        this.group_id = group_id;
    }

    public int getChecksys_id() {
        return checksys_id;
    }

    public void setChecksys_id(int checksys_id) {
        this.checksys_id = checksys_id;
    }

    public String getCk_createtime() {
        return ck_createtime;
    }

    public void setCk_createtime(String ck_createtime) {
        this.ck_createtime = ck_createtime;
    }

    public String getCk_finshtime() {
        return ck_finshtime;
    }

    public void setCk_finshtime(String ck_finshtime) {
        this.ck_finshtime = ck_finshtime;
    }

    public int getCk_risk() {
        return ck_risk;
    }

    public void setCk_risk(int ck_risk) {
        this.ck_risk = ck_risk;
    }

    public String getCk_description() {
        return ck_description;
    }

    public void setCk_description(String ck_description) {
        this.ck_description = ck_description;
    }

    public int getCk_isexam() {
        return ck_isexam;
    }

    public void setCk_isexam(int ck_isexam) {
        this.ck_isexam = ck_isexam;
    }

    public int getCk_ispass() {
        return ck_ispass;
    }

    public void setCk_ispass(int ck_ispass) {
        this.ck_ispass = ck_ispass;
    }
}
