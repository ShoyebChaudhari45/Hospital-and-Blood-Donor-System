package com.example.hospitalproject;

public class POJOGetAllUserDetails {


    String id,name,mobile_no,date_of_birth,blood_group,email_id,username;

    public POJOGetAllUserDetails(String id, String name,String mobile_no,String date_of_birth, String blood_group, String email_id,
                                 String username)

    {
        this.id=id;
        this.name=name;
        this.mobile_no=mobile_no;
        this.date_of_birth=date_of_birth;
        this.blood_group=blood_group;
        this.email_id=email_id;
        this.username=username;



    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile_no() {
        return mobile_no;
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }

    public String getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(String date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public String getBlood_group() {
        return blood_group;
    }

    public void setBlood_group(String blood_group) {
        this.blood_group = blood_group;
    }

    public String getEmail_id() {
        return email_id;
    }

    public void setEmail_id(String email_id) {
        this.email_id = email_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
