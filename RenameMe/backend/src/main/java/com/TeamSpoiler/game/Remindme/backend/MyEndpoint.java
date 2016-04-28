/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Endpoints Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
*/

package com.TeamSpoiler.game.Remindme.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;

import java.sql.*;
import java.io.*;
import javax.servlet.http.*;
import com.google.appengine.api.utils.SystemProperty;
import com.google.appengine.repackaged.org.joda.time.LocalDateTime;

import javax.inject.Named;

import sun.rmi.runtime.Log;
import sun.util.calendar.LocalGregorianCalendar;

/** An endpoint class we are exposing */
@Api(
  name = "myApi",
  version = "v1",
  namespace = @ApiNamespace(
    ownerDomain = "backend.Remindme.game.TeamSpoiler.com",
    ownerName = "backend.Remindme.game.TeamSpoiler.com",
    packagePath=""
  )
)
public class MyEndpoint {
    //connection function used for quick testing will be removed in the end
    @ApiMethod(name = "Connect")
    public MyBean Connect() {
        MyBean response = new MyBean();
        String url = null;
        response.setData("nothing");
        try {
            if (SystemProperty.environment.value() == SystemProperty.Environment.Value.Production) {
                Class.forName("com.mysql.jdbc.GoogleDriver");
                url = "jdbc:google:mysql://headsup-1260:headsup/Users?user=root";
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        try{
            Connection conn = DriverManager.getConnection(url);
            try {
                //dropping a table
                //conn.createStatement().execute("DROP TABLE users IF EXISTS;");
                //createing the user table
                //conn.createStatement().execute("CREATE TABLE users (u_id INTEGER AUTO_INCREMENT, username VARCHAR(255) NOT NULL, password VARCHAR(255) NOT NULL, PRIMARY KEY (u_id,username)) ;");
                //insertinginto a table
                //conn.createStatement().execute("INSERT INTO users(username, password) VALUES('sdd','sdd')");
                //inserting into values
                //conn.createStatement().execute("INSERT INTO users(username, password) VALUES('sdd','sdd')");

                //createing the Categories  table
                //conn.createStatement().execute("CREATE TABLE categories (cat_id INTEGER AUTO_INCREMENT, u_id INTEGER NOT NULL, cat_name VARCHAR(255) NOT NULL, PRIMARY KEY (cat_id,u_id)) ;");

                conn.createStatement().execute("DROP TABLE item");
                //createing the item  table
                conn.createStatement().execute("CREATE TABLE item (item_id INTEGER AUTO_INCREMENT, u_id INTEGER NOT NULL, item_name VARCHAR(255) NOT NULL, cat_id INTEGER NOT NULL, expiration_date DATETIME NOT NULL, note VARCHAR(255),  PRIMARY KEY (item_id,u_id)) ;");

                //returning framework for true and false
//                ResultSet result =  conn.createStatement().executeQuery("select case when u.uid = 'hi' then 'true' else 'false' end from users u");

//                while(result.next()) {
//                    response.setData(result.getString(1));
//                    //Log.W("Data", result.getString(2) );
//                }
                //response.setData(result.getString(2));
                //table needs primary key in table in order to work
//                ResultSet result =  conn.createStatement().executeQuery("select u.uid from users u where u.uid = 'username'");
//                result.absolute(1);
//                result.updateString(2, "wannabe");
//                result.updateRow();
            }finally {
                conn.close();
            }
        }catch(SQLException e){
            response.setData(e.toString());
        }
        return response;
    }
    //Authentication method
    @ApiMethod(name = "Authenticate")
    public MyBean Authenticate(@Named("uname") String u_name, @Named ("upass") String u_pass) {
        MyBean response = new MyBean();
        String url = null;
        response.setData("verify");
        try {
            if (SystemProperty.environment.value() == SystemProperty.Environment.Value.Production) {
                Class.forName("com.mysql.jdbc.GoogleDriver");
                url = "jdbc:google:mysql://headsup-1260:headsup/Users?user=root";
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        try{
            Connection conn = DriverManager.getConnection(url);
            try {
                ResultSet result =  conn.createStatement().executeQuery("select case when u.username = '"+u_name+ "' and u.password = '"+ u_pass+"'   then 'true' else 'false' end, u.u_id from users u");
                //grab the user id on the server and whether or not username password exist
                while(result.next()) {
                    response.setData(result.getString(1));
                    response.setID(result.getInt(2));
                }
            }finally {
                conn.close();
            }
        }catch(SQLException e){
            response.setData(e.toString());
        }
        return response;
    }
    //Method to help update the category
    //Method is used to help make sure that the server has the same categories as the phone
    @ApiMethod(name = "UpdateCategory")
    public MyBean UpdateCategory(@Named("cat_named") String cat_name, @Named ("userId") Integer userid){
        MyBean response = new MyBean();
        String url = null;
        response.setData("addCategory");
        try {
            if (SystemProperty.environment.value() == SystemProperty.Environment.Value.Production) {
                Class.forName("com.mysql.jdbc.GoogleDriver");
                url = "jdbc:google:mysql://headsup-1260:headsup/Users?user=root";
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        try{
            Connection conn = DriverManager.getConnection(url);
            try {
                ResultSet result =  conn.createStatement().executeQuery("SELECT c.cat_id FROM categories c  WHERE c.cat_id = '" +cat_name+"' AND c.u_id = '"+ userid+"'; ");
                //if not already included insert the new category
                if(!result.isBeforeFirst()){
                    conn.createStatement().execute("INSERT INTO categories(u_id, cat_name) VALUES('"+userid +" ','"+cat_name + "')");
                }
            }finally {
                conn.close();
            }
        }catch(SQLException e){
            response.setData(e.toString());
        }
        return response;
    }
    //Adding a category into the database
    @ApiMethod(name = "AddCategory")
    public MyBean AddCategory(@Named("cat_id") Integer cat_id , @Named("cat_name") String cat_name,@Named("userId") Integer userid ) {
        MyBean response = new MyBean();
        String url = null;
        response.setData("addCategory");
        try {
            if (SystemProperty.environment.value() == SystemProperty.Environment.Value.Production) {
                Class.forName("com.mysql.jdbc.GoogleDriver");
                url = "jdbc:google:mysql://headsup-1260:headsup/Users?user=root";
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        try{
            Connection conn = DriverManager.getConnection(url);
            try {
                //response set to determine whether or not the function called work
                response.setData("Adding");
                //MYSQL statement to insert a new category with the corresponding category id, user id, and the name of category
                conn.createStatement().execute("INSERT INTO categories(cat_id,u_id, cat_name) VALUES( '" + cat_id + "','" + userid + " ','" + cat_name + "')");
            }finally {
                conn.close();
            }
        }catch(SQLException e){
            response.setData(e.toString());
        }
        return response;
    }
    //Adding an item to the server database
    @ApiMethod(name = "AddItem")
    public MyBean AddItem(@Named("item_id") Integer item_id,@Named("item_name") String item_name,@Named("expiration") String expire, @Named("cat_id") Integer cat_id,@Named("userId") Integer userid, @Named("note") String note ) {
        MyBean response = new MyBean();
        String url = null;
        response.setData("addItem");
        try {
            if (SystemProperty.environment.value() == SystemProperty.Environment.Value.Production) {
                Class.forName("com.mysql.jdbc.GoogleDriver");
                url = "jdbc:google:mysql://headsup-1260:headsup/Users?user=root";
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        try{
            Connection conn = DriverManager.getConnection(url);
            try {
                //message to hold and return if there are any errors
                response.setData("Adding");
                //MYSQL insert statement
                conn.createStatement().execute("INSERT INTO item(item_id,u_id, item_name,cat_id, expiration_date, note) VALUES('" + item_id + "','" + userid + " ','" + item_name + "','" + cat_id + "',STR_TO_DATE('" + expire + "','%Y-%m-%d %h:%i:%s'),'" + note + "')" );
            }finally {
                conn.close();
            }
        }catch(SQLException e){
            response.setData(e.toString());
        }
        return response;
    }
    //Updating an item with new infomation
    @ApiMethod(name = "UpdateItem")
    public MyBean UpdateItem(@Named("item_name") String item_name,@Named("expiration") String expire, @Named("cat_id") Integer cat_id,@Named("userId") Integer userid, @Named("note") String note , @Named("item_id") Integer item_id) {
        MyBean response = new MyBean();
        String url = null;
        response.setData("addItem");
        try {
            if (SystemProperty.environment.value() == SystemProperty.Environment.Value.Production) {
                Class.forName("com.mysql.jdbc.GoogleDriver");
                url = "jdbc:google:mysql://headsup-1260:headsup/Users?user=root";
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        try{
            Connection conn = DriverManager.getConnection(url);
            try {
                //setting the result type allowing the program to modify the database
                Statement statetype = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
                ResultSet result =  statetype.executeQuery("SELECT i.item_name,i.cat_id, i.expiration_date, i.note,item_id, u_id FROM item i WHERE i.u_id ='" + userid + "'AND item_id = '" + item_id + "';");
                //the absolute is used to select the 1 and only row and tell the update row to update this row
                result.absolute(1);
                //Updating the item values with the newly added information
                result.updateString("item_name", item_name);
                result.updateInt("cat_id", cat_id);
                result.updateString("expiration_date", expire);
                result.updateString("note", note);
                result.updateRow();
            }finally {
                conn.close();
            }
        }catch(SQLException e){
            response.setData(e.toString());
        }
        return response;
    }
    //Method to create new users
    @ApiMethod(name = "AddUser")
    public MyBean AddUser(@Named("username") String user_name,@Named("password") String password) {
        MyBean response = new MyBean();
        String url = null;
        response.setData("addItem");
        try {
            if (SystemProperty.environment.value() == SystemProperty.Environment.Value.Production) {
                Class.forName("com.mysql.jdbc.GoogleDriver");
                url = "jdbc:google:mysql://headsup-1260:headsup/Users?user=root";
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        try{
            Connection conn = DriverManager.getConnection(url);
            try {
                conn.createStatement().execute("INSERT INTO users(username, password) VALUES('"+ user_name+ "','" + password+"')");
            }finally {
                conn.close();
            }
        }catch(SQLException e){
            response.setData(e.toString());
        }
        return response;
    }
}
