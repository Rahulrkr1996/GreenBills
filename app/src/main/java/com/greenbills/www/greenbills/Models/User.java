package com.greenbills.www.greenbills.Models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rahul Kumar on 5/29/2016.
 */

@Table(name = "User")
public class User extends Model{

    @Expose
    @Column(name = "user_id")
    public int user_id;

    @Expose
    @Column(name = "user_name")
    public String user_name;

    @Expose
    @Column(name = "user_email")
    public String user_email;

    @Expose
    @Column(name = "user_photoURL")
    public String user_photoURL;

    @Expose
    @Column(name = "user_photoPath")    //Path in Ext. hard disk
    public String user_photoPath;

    public User() {
        this.user_id = 0;
        this.user_name = null;
        this.user_email = null;
        this.user_photoURL = null;
        this.user_photoPath = null;
    }

    public User(int userid,String username, String useremail, String userphotoURL) {
        super();
        this.user_name = username;
        this.user_id = userid;
        this.user_email = useremail;
        this.user_photoURL = userphotoURL;
    }

    public List<User> getAllUsers() {
        Select select = new Select();
        ArrayList<User> user_list;
        user_list = select.from(User.class).execute();
        return user_list;
    }


    @Override
    public String toString() {
        return user_name;
    }

}
