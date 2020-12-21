package com.se.httpwww.se;

/**
 * Created by Dell on 3/1/2018.
 */

public class VotingEventClass {

    private String id,name,desc,institute,pos;//,event_id,;

    public VotingEventClass(String i, String n,String d, String ins,String po)
    {
        id=i;
        name=n;
       // event_id=eid;
        desc=d;
        institute=ins;
        pos=po;
    }

    public String getPos() {
        return pos;
    }

    public void setPos(String pos) {
        this.pos = pos;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getInstitute() {
        return institute;
    }

    public void setInstitute(String institute) {
        this.institute = institute;
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

//    public String getEvent_id() {
//        return event_id;
//    }
//
//    public void setEvent_id(String event_id) {
//        this.event_id = event_id;
//    }
}
