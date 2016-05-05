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
                //conn.createStatement().execute("DROP TABLE sharecategories;");
                //conn.createStatement().execute("CREATE TABLE sharecategories (friend_id INTEGER , user_id INTEGER , cat_id INTEGER, cat_name VARCHAR(255), PRIMARY KEY (friend_id, user_id)) ;");
                //insertinginto a table
                //conn.createStatement().execute("INSERT INTO users(username, password) VALUES('sdd','sdd')");
                //inserting into values
                //conn.createStatement().execute("INSERT INTO users(username, password) VALUES('sdd','sdd')");

                //createing the Categories  table
                //conn.createStatement().execute("CREATE TABLE categories (cat_id INTEGER AUTO_INCREMENT, u_id INTEGER NOT NULL, cat_name VARCHAR(255) NOT NULL, PRIMARY KEY (cat_id,u_id)) ;");

                //conn.createStatement().execute("DROP TABLE item");
                //createing the item  table
                //conn.createStatement().execute("CREATE TABLE items (item_id INTEGER AUTO_INCREMENT, u_id INTEGER NOT NULL, item_name VARCHAR(255) NOT NULL, cat_id INTEGER NOT NULL REFERENCES categories(cat_id) ON DELETE CASCADE, expiration_date DATETIME NOT NULL, note VARCHAR(255),  PRIMARY KEY (item_id,u_id)) ;");
                //conn.createStatement().execute("CREATE TABLE friends (friend_id INTEGER AUTO_INCREMENT, friend_name VARCHAR(255) NOT NULL , friend_username VARCHAR(255) NOT NULL, userid INTEGER , PRIMARY KEY (friend_id,userid )) ;");
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
                    if(result.getString(1).equals("true")){
                        response.setData("true");
                        response.setID(result.getInt(2));
                        break;
                    }
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
    //Method to delete category
    @ApiMethod(name = "DeleteCategory")
    public MyBean DeleteCategory(@Named("cat_id") Integer cat_id){
        MyBean response = new MyBean();
        String url = null;
        response.setData("DeleteCategory");
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
                conn.createStatement().execute("DELETE FROM categories WHERE cat_id = '" + cat_id + "',)");

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
                conn.createStatement().execute("INSERT INTO items(item_id,u_id, item_name,cat_id, expiration_date, note) VALUES('" + item_id + "','" + userid + " ','" + item_name + "','" + cat_id + "',STR_TO_DATE('" + expire + "','%Y-%m-%d %h:%i:%s'),'" + note + "')" );
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
                ResultSet result =  statetype.executeQuery("SELECT i.item_name,i.cat_id, i.expiration_date, i.note,item_id, u_id FROM items i WHERE i.u_id ='" + userid + "'AND item_id = '" + item_id + "';");
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
    //Deleting an item to the server database
    @ApiMethod(name = "DeleteItem")
    public MyBean DeleteItem(@Named("item_id") Integer item_id) {
        MyBean response = new MyBean();
        String url = null;
        response.setData("DeleteItem");
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
                response.setData("Deleteing");
                //MYSQL insert statement
                conn.createStatement().execute("DELETE FROM items WHERE item_id = '" + item_id + ",)");
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
                conn.createStatement().execute("INSERT INTO users(username, password) VALUES('" + user_name + "','" + password + "')");
            }finally {
                conn.close();
            }
        }catch(SQLException e){
            response.setData(e.toString());
        }
        return response;
    }
    //Method to add a friend
    @ApiMethod(name = "AddFriend")
    public MyBean AddFriend(@Named("friend_name") String friend_name,@Named("friend_username") String friendusername , @Named("user_id") Integer user_id) {
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
                //grabbing the friend id
                ResultSet result =  conn.createStatement().executeQuery("SELECT u.u_id FROM users u  WHERE u.username = '" +friendusername+"'; ");
                //if not already included insert the new category
                result.absolute(1);
                Integer friend_id =  result.getInt("u_id");

                //adding in the friend info
                conn.createStatement().execute("INSERT INTO friends(friend_id, friend_name, friend_username, userid) VALUES('"+ friend_id+ "','" + friend_name + "','"+friendusername +"','" +user_id+"')");
            }finally {
                conn.close();
            }
        }catch(SQLException e){
            response.setData(e.toString());
        }
        return response;
    }
    //Method to add a shared cateogory
    @ApiMethod(name = "ShareCategory")
    public MyBean ShareCategory(@Named("friend_name") String friend_name, @Named("user_id") Integer user_id, @Named ("cat_id") Integer cat_id, @Named ("cat_name") String cat_name) {
        MyBean response = new MyBean();
        String url = null;
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
                ResultSet result =  conn.createStatement().executeQuery("select u.u_id FROM users u WHERE u.username = '"+friend_name +"'");
                result.absolute(1);
                Integer friend_id = result.getInt("u_id");
                conn.createStatement().execute("INSERT INTO sharecategories(friend_id,user_id, cat_id, cat_name) VALUES('"+ friend_id+ "','" + user_id + "','"+cat_id+"','" + cat_name + "')");
            }finally {
                conn.close();
            }
        }catch(SQLException e){
            response.setData(e.toString());
        }
        return response;
    }
    //Method to add a Check if shared cateogory is already added
    @ApiMethod(name = "CheckShareCategory")
    public MyBean CheckShareCategory(@Named("user_id") Integer user_id) {
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
                ResultSet result =  conn.createStatement().executeQuery("select case when (sc.user_id = '"+user_id+ "' OR sc.friend_id  = '"+ user_id+"') then 'true' else 'false' end, sc.cat_id, sc.cat_name from sharecategories sc");
                //grab the user id on the server and whether or not username password exist
                while(result.next()) {
                    if(result.getString(1).equals("true")) {
                        response.setData(result.getString(1));
                        response.setShareCat(result.getInt(3));
                        response.setShareName(result.getString(4));
                        break;
                    }
                }

            }finally {
                conn.close();
            }
        }catch(SQLException e){
            response.setData(e.toString());
        }
        return response;
    }
    //Method to add the share category items
    @ApiMethod(name = "AddShareItems")
    public MyBean AddShareItems(@Named("cat_id") Integer cat_id) {
        MyBean response = new MyBean();
        String url = null;
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
                ResultSet result =  conn.createStatement().executeQuery("select * from categories c WHERE c.cat_id = '" + cat_id+"')");
                //grab the user id on the server and whether or not username password exist
                while(result.next()) {
                    //item string is name + expirationdate + note
                    String item = result.getString(3) + "|" + result.getString(5) + "|" + result.getString(6);
                    response.addItem(item);
                }
            }finally {
                conn.close();
            }
        }catch(SQLException e){
            response.setData(e.toString());
        }
        return response;
    }
}
