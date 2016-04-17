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

import javax.inject.Named;

import sun.rmi.runtime.Log;

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
    /** A simple endpoint method that takes a name and says Hi back */
    @ApiMethod(name = "sayHi")
    public MyBean sayHi(@Named("name") String name) {
        MyBean response = new MyBean();
        response.setData("Hidasd, " + name);

        return response;
    }
    @ApiMethod(name = "sayNO")
    public MyBean sayNO(@Named("name") String name){
        MyBean response = new MyBean();
        response.setData("craep sdf " + name);
        return response;
    }
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
                //conn.createStatement().execute("INSERT INTO users VALUES('username', 'password') ");
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
}
