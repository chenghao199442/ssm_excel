package com.chenghao.poi.domain;

public class Emp {

    private Integer id;
    private String name;
    private String sex;
    private String email;
    private Integer deptId;
    private Dept deptName;

    public Emp(){super();}

    public Emp(Integer id, String name, String sex, String email, Integer deptId) {
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.email = email;
        this.deptId = deptId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public Dept getDeptName() {
        return deptName;
    }

    public void setDeptName(Dept deptName) {
        this.deptName = deptName;
    }
}
