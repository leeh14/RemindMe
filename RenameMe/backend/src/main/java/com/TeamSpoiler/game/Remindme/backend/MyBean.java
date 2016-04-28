package com.TeamSpoiler.game.Remindme.backend;

import java.util.*;

//import javafx.util.Pair;
/** The object model for the data we are sending through endpoints */
public class MyBean {

    //testing string collection
    private String myData;

    public String getData() {
        return myData;
    }

    public void setData(String data) {
        myData = data;
    }


    //the current user id
    private Integer user_id;

    public void setID(Integer id) {user_id = id;}

    public Integer getUser_id(){return user_id;}
}