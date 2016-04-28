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

    //category storage
    private List CategoryNames = new ArrayList();

    public void AddCategories(String name ){CategoryNames.add(name);}

    public List getCategories(){return  CategoryNames;}

    //item storage
    private List ItemNames = new ArrayList();

    public void AddItems(String name) {ItemNames.add(name);}

    public List getItems(){return  ItemNames;}

    //the current user id
    private Integer user_id;

    public void setID(Integer id) {user_id = id;}

}