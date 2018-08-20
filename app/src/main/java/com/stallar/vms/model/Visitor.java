package com.stallar.vms.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by lucie on 1/25/2018.
 */

public class Visitor {
    private int id;
    private String id_no;
    private String name;
    private String phone;

    public Visitor(JSONObject object){
        try {
            this.id = object.getInt("id");
            this.id_no = object.getString("id_no");
            this.name = object.getString("name");
            this.phone = object.getString("phone");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getId_no() {
        return id_no;
    }

    public void setId_no(String id_no) {
        this.id_no = id_no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
