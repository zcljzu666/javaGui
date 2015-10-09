package com.zcl.entity;

/**
 * Created by Administrator on 2015/10/7.
 */
public class RecordCollection {

    private String userGid;  //催收员gid
    private String collectorNname;  //催收人
    private String collectDuration; //拨打时长
    private int  totalRecord ;  //总记录数
    private int  friendTotalRecord; //亲友总记录数

    public String getUserGid() {
        return userGid;
    }

    public void setUserGid(String userGid) {
        this.userGid = userGid;
    }

    public String getCollectorNname() {
        return collectorNname;
    }

    public void setCollectorNname(String collectorNname) {
        this.collectorNname = collectorNname;
    }

    public String getCollectDuration() {
        return collectDuration;
    }

    public void setCollectDuration(String collectDuration) {
        this.collectDuration = collectDuration;
    }

    public int getTotalRecord() {
        return totalRecord;
    }

    public void setTotalRecord(int totalRecord) {
        this.totalRecord = totalRecord;
    }

    public int getFriendTotalRecord() {
        return friendTotalRecord;
    }

    public void setFriendTotalRecord(int friendTotalRecord) {
        this.friendTotalRecord = friendTotalRecord;
    }
}
