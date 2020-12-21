package com.se.httpwww.se;

import java.util.ArrayList;

/**
 * Created by Dell on 7/13/2017.
 */

public class User {

    static User user =new User();
    private String name,pass,contact,fullname;
    private String tempusername;
    private String ip;

    ArrayList<VotingEventClass> votingEventsList=new ArrayList<>();
    ArrayList<CandidatesClass> candidatesList=new ArrayList<>();

    ArrayList<VotingEventClass> resultsEventsList=new ArrayList<>();

    public String getTempusername() {
        return tempusername;
    }

    public void setTempusername(String tempusername) {
        this.tempusername = tempusername;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }


    public void setTempSignUpUserName(String username)
    {
        tempusername=username;
    }

    public String getTempSignUpUserName()
    {
        return tempusername;
    }




    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        User.user = user;
    }



    public void myClear()
    {
        name=null;
        pass=null;
        contact=null;
        fullname=null;

    }

    public static User getInstance()
    {
        return user;
    }

    private User()
    {
        ip="192.168.1.110";
    }


}
