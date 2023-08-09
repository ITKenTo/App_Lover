package com.example.assignment_ph26746.Model;

public class Accountmodel {
    private String _id;
    private String username;
    private String password;
    private String fullname;
    private String image;

    public Accountmodel(String _id, String username, String password, String fullname, String image) {
        this._id = _id;
        this.username = username;
        this.password = password;
        this.fullname = fullname;
        this.image = image;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
