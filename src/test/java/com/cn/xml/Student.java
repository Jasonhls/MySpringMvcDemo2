package com.cn.xml;

/**
 * @description:
 * @author: helisen
 * @create: 2021-02-25 18:01
 **/
public class Student {

//    <Student examId="23">
//<Name>香香</Name>
//<Num>1006010066</Num>
//<Classes>眼视光5</Classes>
//<Address>浙江温州</Address>
//<Tel>123890</Tel>
//</Student>
    private String examId;
    private String name;
    private String num;
    private String classes;
    private String address;
    private String tel;

    public String getExamId() {
        return examId;
    }

    public void setExamId(String examId) {
        this.examId = examId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getClasses() {
        return classes;
    }

    public void setClasses(String classes) {
        this.classes = classes;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }
}
