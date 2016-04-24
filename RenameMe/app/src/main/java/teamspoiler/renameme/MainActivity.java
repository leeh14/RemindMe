package teamspoiler.renameme;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Pair;
import android.widget.Toast;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
//import com.google.api.server.spi.config.Api;
//import com.google.api.server.spi.config.ApiNamespace;
import com.teamspoiler.game.remindme.backend.myApi.MyApi;
//import com.google.android.gcm.server.Constants;
//import com.google.android.gcm.server.Message;
//import com.google.android.gcm.server.Result;
//import com.google.android.gcm.server.Sender;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

import android.util.Log;

/**
 * Created by game on 3/27/2016.
 */



//class Authenticate extends AsyncTask<Pair<String,String>, Void, Pair<String, Integer>> {
//    private static MyApi myApiService = null;
//    private Context context;
//
//    @Override
//    protected Pair<String, Integer> doInBackground(Pair<String, String>... params) {
//        if (myApiService == null) {
//            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
//                    .setRootUrl("https://headsup-1260.appspot.com/_ah/api/");
//            // end options for devappserver
//
//            myApiService = builder.build();
//        }
//        String uname = params[0].first;
//        String upass = params[0].second;
//        try {
//            String s =  myApiService.authenticate(uname, upass).execute().getData();
//            Integer d = myApiService.authenticate(uname, upass).execute().getUserId();
//            return new Pair<String, Integer>(s,d );
//            //return  myApiService.connect().execute().getData();
//
//            //return myApiService.sayNO(name).execute().getData();
//            //return myApiService.sayHi(name).execute().getData();
//        } catch (IOException e) {
//            return new Pair<String, Integer>(e.getMessage(), -1);
//        }
//    }
//}
//class AddingCategory extends AsyncTask<String, Void, String> {
//    private static MyApi myApiService = null;
//    private Context context;
//
//    @Override
//    protected String doInBackground(String... params) {
//        if (myApiService == null) {
//            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
//                    .setRootUrl("https://headsup-1260.appspot.com/_ah/api/");
//            // end options for devappserver
//
//            myApiService = builder.build();
//        }
//        String cat_name = params[0];
//        try {
//            String s =  myApiService.addCategory(cat_name).execute().getData();
//            return s;
//            //return  myApiService.connect().execute().getData();
//
//            //return myApiService.sayNO(name).execute().getData();
//            //return myApiService.sayHi(name).execute().getData();
//        } catch (IOException e) {
//            return e.getMessage();
//        }
//    }
//
//}
//class EndpointsAsyncTask extends AsyncTask<Pair<Context, String>, Void, String> {
//    private static MyApi myApiService = null;
//    private Context context;
//
//    @Override
//    protected String doInBackground(Pair<Context, String>... params) {
//        if(myApiService == null) {  // Only do this once
////            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
////                    new AndroidJsonFactory(), null)
////                    // options for running against local devappserver
////                    // - 10.0.2.2 is localhost's IP address in Android emulator
////                    // - turn off compression when running against local devappserver
////                    .setRootUrl("http://10.0.2.2:8080/_ah/api/")
////                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
////                        @Override
////                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
////                            abstractGoogleClientRequest.setDisableGZipContent(true);
////                        }
////                    });
//            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
//                    .setRootUrl("https://headsup-1260.appspot.com/_ah/api/");
//            // end options for devappserver
//
//            myApiService = builder.build();
//        }
//
//        context = params[0].first;
//        String name = params[0].second;
//
//
//        try {
//
//            return  myApiService.connect().execute().getData();
//
//            //return myApiService.sayNO(name).execute().getData();
//            //return myApiService.sayHi(name).execute().getData();
//        } catch (IOException e) {
//            return e.getMessage();
//        }
//    }
//    @Override
//    protected void onPostExecute(String result) {
//        Toast.makeText(context, result, Toast.LENGTH_LONG).show();
//    }
//}

public class MainActivity  extends AppCompatActivity{

    static final int START_APP = 1;  // The request code

    //static EndpointsAsyncTask serverApi = new EndpointsAsyncTask();

    //static ServerAPI Servera;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        //new EndpointsAsyncTask().execute(new Pair<Context, String>(this, "Manfred"));
        //Servera = ServerAPI.getInstance(this);
        //Servera.execute(new Pair<Context, String>(this, "Manfred"));
        Intent i = new Intent(this, LoginActivity.class);
        startActivityForResult(i, START_APP);
    }
//    public boolean CheckAuthenticate(String uname,String upass ){
//        Authenticate vali = new Authenticate();
//        try {
//            Pair<String, Integer> s = vali.execute(new Pair<String, String>(uname, upass)).get();
//            userID = s.second;
//            //String s = vali.Authentication(uname,upass);
//            //works everywhere except debug mode
//            return s.first.equals("true");
//        }
//        catch (InterruptedException e )
//        {
//            return false;
//        }catch (ExecutionException b ) {
//            return false;
//        }
//
//
//    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // close the activity when user return out of app
        if (requestCode == START_APP) {
            finish();
        }
    }
}
