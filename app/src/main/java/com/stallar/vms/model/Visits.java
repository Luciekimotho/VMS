package com.stallar.vms.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by lucie on 3/12/2018.
 */

public class Visits {
    Integer id;
    Integer visitor;
    Integer business;
    Integer device;
    String user;
    Integer time_in;
    Integer time_out;
    String purpose;
    String code;

    public Visits(JSONObject object){
        try {
            this.id = object.getInt("id");
            this.visitor = object.getInt("visitor");
            this.business = object.getInt("business");
            this.device = object.getInt("device");
            this.purpose = object.getString("purpose");
            this.user = object.getString("user");
            this.time_in = object.getInt("time_in");
            this.time_out = object.getInt("time_out");
            this.code = object.getString("code");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public static ArrayList<Visits> fromJson(JSONArray jsonObjects) {
        ArrayList<Visits> users = new ArrayList<Visits>();
        for (int i = 0; i < jsonObjects.length(); i++) {
            try {
                users.add(new Visits(jsonObjects.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return users;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getVisitor() {
        return visitor;
    }

    public void setVisitor(Integer visitor) {
        this.visitor = visitor;
    }

    public Integer getBusiness() {
        return business;
    }

    public void setBusiness(Integer business) {
        this.business = business;
    }

    public Integer getDevice() {
        return device;
    }

    public void setDevice(Integer device) {
        this.device = device;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Integer getTime_in() {
        return time_in;
    }

    public void setTime_in(Integer time_in) {
        this.time_in = time_in;
    }

    public Integer getTime_out() {
        return time_out;
    }

    public void setTime_out(Integer time_out) {
        this.time_out = time_out;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
