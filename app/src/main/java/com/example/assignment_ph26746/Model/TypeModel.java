package com.example.assignment_ph26746.Model;

import java.io.Serializable;

public class TypeModel implements Serializable {
    private String _id;
    private String name;
    private String des;

    private String id_user;

    public TypeModel(String _id, String name, String des) {
        this._id = _id;
        this.name = name;
        this.des = des;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public TypeModel() {
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }
}
