package com.example.assignment_ph26746.Model;

public class LoverPModel {
    private String _id;
    private String name;
    private String phone;
    private String date;
    private String id_type;
    private String des;
    private String image;

    public LoverPModel(String _id, String name, String phone, String date, String id_type, String des, String image) {
        this._id = _id;
        this.name = name;
        this.phone = phone;
        this.date = date;
        this.id_type = id_type;
        this.des = des;
        this.image = image;
    }

    public LoverPModel() {
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getId_type() {
        return id_type;
    }

    public void setId_type(String id_type) {
        this.id_type = id_type;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
