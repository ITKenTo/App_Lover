package com.example.assignment_ph26746.Model;

public class PlansModel {
    private String _id;
    private String title;
    private Accountmodel id_user;
    private LoverPModel id_lover;
    private String date_play;
    private String location;
    private String des;

    public PlansModel(String _id, String title, Accountmodel id_user, LoverPModel id_lover, String date_play, String location, String des) {
        this._id = _id;
        this.title = title;
        this.id_user = id_user;
        this.id_lover = id_lover;
        this.date_play = date_play;
        this.location = location;
        this.des = des;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Accountmodel getId_user() {
        return id_user;
    }

    public void setId_user(Accountmodel id_user) {
        this.id_user = id_user;
    }

    public LoverPModel getId_lover() {
        return id_lover;
    }

    public void setId_lover(LoverPModel id_lover) {
        this.id_lover = id_lover;
    }

    public String getDate_play() {
        return date_play;
    }

    public void setDate_play(String date_play) {
        this.date_play = date_play;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }
}
