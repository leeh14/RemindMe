/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Endpoints Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
*/

package com.TeamSpoiler.game.Remindme.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;

import javax.inject.Named;

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

}
