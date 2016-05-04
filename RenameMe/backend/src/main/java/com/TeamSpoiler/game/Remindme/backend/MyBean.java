package com.TeamSpoiler.game.Remindme.backend;

import java.util.*;

//import javafx.util.Pair;
/** The object model for the data we are sending through endpoints */
public class MyBean {

    //testing string collection
    private String myData ="";

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

    //for share category implementation
    private List<String> items;

    public void addItem(String item){items.add(item);}
    public List<String> getItems(){return items;}

    private Integer shareCat =-1;
    private String shareName = "";
    public void setShareName(String cat_name){shareName = cat_name;}
    public String getShareName(){return shareName;}
    public void setShareCat(Integer cat){shareCat = cat;}

    public Integer getShareCar(){return shareCat;}
}