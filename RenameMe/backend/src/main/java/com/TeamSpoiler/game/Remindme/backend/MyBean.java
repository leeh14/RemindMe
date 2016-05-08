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
    private ArrayList<String>  items = new ArrayList<String>();
    public void addItem(String item){items.add(item);}
    public ArrayList<String>  getItems(){return items;}

    //Sharing multiple categories
    private ArrayList<String> categories = new ArrayList<String>();
    public void addCategory(String cat ) {categories.add(cat);}
    public ArrayList<String>  getCategories(){return categories;}

}