package com.se.httpwww.se;

/**
 * Created by Dell on 3/1/2018.
 */

public class CandidatesClass {

    private String id,name,event_id,pic;
    private int votesCount;

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public CandidatesClass(String i, String n, String eid, String p, int v)
    {
        id=i;
        name=n;
        event_id=eid;
        pic=p;
        votesCount=v;
    }

    public int getVotesCount() {
        return votesCount;
    }

    public void setVotesCount(int votesCount) {
        this.votesCount = votesCount;
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

    public String getEvent_id() {
        return event_id;
    }

    public void setEvent_id(String event_id) {
        this.event_id = event_id;
    }
}
