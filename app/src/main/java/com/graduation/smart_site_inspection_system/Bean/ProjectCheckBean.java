package com.graduation.smart_site_inspection_system.Bean;

public class ProjectCheckBean {

    private int id;  //检查id
    private int projectId;  //项目id
    private int groupId;  //检查组id
    private int checksys_id;//检查体系id
    private String createtime;  //创建时间
    private String finishDateTime;  //
    private int grade;  //结果风险

    private String description;  //结果描述
    private int examState;  //是否审核
    private int passState;  //是否通过
    private String im_url;  //图片url

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getChecksys_id() {
        return checksys_id;
    }

    public void setChecksys_id(int checksys_id) {
        this.checksys_id = checksys_id;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getFinishDateTime() {
        return finishDateTime;
    }

    public void setFinishDateTime(String finishDateTime) {
        this.finishDateTime = finishDateTime;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getExamState() {
        return examState;
    }

    public void setExamState(int examState) {
        this.examState = examState;
    }

    public int getPassState() {
        return passState;
    }

    public void setPassState(int passState) {
        this.passState = passState;
    }

    public String getIm_url() {
        return im_url;
    }

    public void setIm_url(String im_url) {
        this.im_url = im_url;
    }
}
