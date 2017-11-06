package com.savor.operation.bean;

import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by hezd on 2017/9/5.
 */

public class LoginResponse implements Serializable {
    /**
     * userid : 1
     * username : admin
     * nickname : Admin
     */

    private String userid;
    private String username;
    private String nickname;
    private String groupId;
    /**是否带队安装 1：是 0：否*/
    private String is_lead_install;
    private SkillList skill_list;

    @Override
    public String toString() {
        return "LoginResponse{" +
                "userid='" + userid + '\'' +
                ", username='" + username + '\'' +
                ", nickname='" + nickname + '\'' +
                ", groupId='" + groupId + '\'' +
                ", is_lead_install='" + is_lead_install + '\'' +
                ", skill_list=" + skill_list +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LoginResponse that = (LoginResponse) o;

        if (userid != null ? !userid.equals(that.userid) : that.userid != null) return false;
        if (username != null ? !username.equals(that.username) : that.username != null)
            return false;
        if (nickname != null ? !nickname.equals(that.nickname) : that.nickname != null)
            return false;
        if (groupId != null ? !groupId.equals(that.groupId) : that.groupId != null) return false;
        if (is_lead_install != null ? !is_lead_install.equals(that.is_lead_install) : that.is_lead_install != null)
            return false;
        return skill_list != null ? skill_list.equals(that.skill_list) : that.skill_list == null;
    }

    @Override
    public int hashCode() {
        int result = userid != null ? userid.hashCode() : 0;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (nickname != null ? nickname.hashCode() : 0);
        result = 31 * result + (groupId != null ? groupId.hashCode() : 0);
        result = 31 * result + (is_lead_install != null ? is_lead_install.hashCode() : 0);
        result = 31 * result + (skill_list != null ? skill_list.hashCode() : 0);
        return result;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getIs_lead_install() {
        return is_lead_install;
    }

    public void setIs_lead_install(String is_lead_install) {
        this.is_lead_install = is_lead_install;
    }

    public SkillList getSkill_list() {
        return skill_list;
    }

    public void setSkill_list(SkillList skill_list) {
        this.skill_list = skill_list;
    }
}
