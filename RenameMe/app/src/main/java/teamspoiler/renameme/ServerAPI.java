package teamspoiler.renameme;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Pair;
import android.widget.Toast;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.teamspoiler.game.remindme.backend.myApi.MyApi;

import org.joda.time.LocalDateTime;

import java.io.IOException;
import java.sql.Date;
import java.util.concurrent.ExecutionException;

import teamspoiler.renameme.DataElements.Category;
import teamspoiler.renameme.DataElements.Item;

/**
 * Created by Elric on 4/24/2016.
 */
class Authenticate extends AsyncTask<Pair<String,String>, Void, Pair<String, Integer>> {
    private static MyApi myApiService = null;
    private Context context;

    @Override
    protected Pair<String, Integer> doInBackground(Pair<String, String>... params) {
        if (myApiService == null) {
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                    .setRootUrl("https://headsup-1260.appspot.com/_ah/api/");
            // end options for devappserver

            myApiService = builder.build();
        }
        String uname = params[0].first;
        String upass = params[0].second;
        try {
            String s =  myApiService.authenticate(uname, upass).execute().getData();
            Integer d = myApiService.authenticate(uname, upass).execute().getUserId();
            return new Pair<String, Integer>(s,d );
            //return  myApiService.connect().execute().getData();

            //return myApiService.sayNO(name).execute().getData();
            //return myApiService.sayHi(name).execute().getData();
        } catch (IOException e) {
            return new Pair<String, Integer>(e.getMessage(), -1);
        }
    }
}
class AddingItem extends AsyncTask< Integer, Void, String> {
    private static MyApi myApiService = null;
    private Context context;
    private String item_name;
    private String[] expirationarr;
    private String expiration;
    private Integer cat_id;
    private String note;
    private Integer item_id;
    public AddingItem(Item item){
        item_name = item.getName();
        expirationarr = item.getDate().toString().split("T");
        String [] expirationtemp = expirationarr[1].split("\\.");
        expiration = expirationarr[0].concat(" " +expirationtemp[0]);
        cat_id = item.getCategoryID();
        note = item.getNote();
        item_id = item.getID();
    }
    @Override
    protected String doInBackground(Integer... params) {
        if (myApiService == null) {
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                    .setRootUrl("https://headsup-1260.appspot.com/_ah/api/");
            // end options for devappserver

            myApiService = builder.build();
        }
        Integer userId = params[0];
        try {
            String s =  myApiService.addItem(item_id,item_name, expiration, cat_id, userId, note).execute().getData();
            return s;
            //return  myApiService.connect().execute().getData();

            //return myApiService.sayNO(name).execute().getData();
//            //return myApiService.sayHi(name).execute().getData();
        } catch (IOException e) {
            return e.getMessage();
        }
    }
}
class UpdatingItem extends AsyncTask< Integer, Void, String> {
    private static MyApi myApiService = null;
    private Context context;
    private String item_name;
    private String[] expirationarr;
    private String expiration;
    private Integer cat_id;
    private String note;
    private Integer item_id;
    public UpdatingItem(Item item){
        item_name = item.getName();
        expirationarr = item.getDate().toString().split("T");
        String [] expirationtemp = expirationarr[1].split("\\.");
        expiration = expirationarr[0].concat(" " +expirationtemp[0]);
        cat_id = item.getCategoryID();
        note = item.getNote();
        item_id = item.getID();
    }
    @Override
    protected String doInBackground(Integer... params) {
        if (myApiService == null) {
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                    .setRootUrl("https://headsup-1260.appspot.com/_ah/api/");
            // end options for devappserver

            myApiService = builder.build();
        }
        Integer userId = params[0];
        try {
            String s =  myApiService.updateItem(item_name, expiration, cat_id, userId, note, item_id).execute().getData();
            return s;
            //return  myApiService.connect().execute().getData();

            //return myApiService.sayNO(name).execute().getData();
//            //return myApiService.sayHi(name).execute().getData();
        } catch (IOException e) {
            return e.getMessage();
        }
    }

}
class AddingCategory extends AsyncTask<Integer, Void, String> {
    private static MyApi myApiService = null;
    private Context context;
    private Integer cat_id;
    private String cat_name;
    public AddingCategory(Category cat) {
        cat_id = cat.getID();
        cat_name = cat.getName();
    }
    @Override
    protected String doInBackground(Integer... params) {
        if (myApiService == null) {
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                    .setRootUrl("https://headsup-1260.appspot.com/_ah/api/");
            // end options for devappserver

            myApiService = builder.build();
        }
        Integer user = params[0];
        try {
            String s =  myApiService.addCategory(cat_id, cat_name, user).execute().getData();
            return s;
            //return  myApiService.connect().execute().getData();

            //return myApiService.sayNO(name).execute().getData();
            //return myApiService.sayHi(name).execute().getData();
        } catch (IOException e) {
            return e.getMessage();
        }
    }

}
class UpdatingCategory extends AsyncTask<Pair<String,Integer>, Void, String> {
    private static MyApi myApiService = null;
    private Context context;

    @Override
    protected String doInBackground(Pair<String, Integer>... params) {
        if (myApiService == null) {
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                    .setRootUrl("https://headsup-1260.appspot.com/_ah/api/");
            // end options for devappserver

            myApiService = builder.build();
        }
        String cat_name = params[0].first;
        Integer user = params[0].second;
        try {
            String s =  myApiService.updateCategory(cat_name, user).execute().getData();
            return s;
            //return  myApiService.connect().execute().getData();

            //return myApiService.sayNO(name).execute().getData();
            //return myApiService.sayHi(name).execute().getData();
        } catch (IOException e) {
            return e.getMessage();
        }
    }

}
class EndpointsAsyncTask extends AsyncTask<Pair<Context, String>, Void, String> {
    private static MyApi myApiService = null;
    private Context context;

    @Override
    protected String doInBackground(Pair<Context, String>... params) {
        if(myApiService == null) {  // Only do this once
//            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
//                    new AndroidJsonFactory(), null)
//                    // options for running against local devappserver
//                    // - 10.0.2.2 is localhost's IP address in Android emulator
//                    // - turn off compression when running against local devappserver
//                    .setRootUrl("http://10.0.2.2:8080/_ah/api/")
//                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
//                        @Override
//                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
//                            abstractGoogleClientRequest.setDisableGZipContent(true);
//                        }
//                    });
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                    .setRootUrl("https://headsup-1260.appspot.com/_ah/api/");
            // end options for devappserver

            myApiService = builder.build();
        }

        context = params[0].first;
        String name = params[0].second;


        try {

            return  myApiService.connect().execute().getData();

            //return myApiService.sayNO(name).execute().getData();
            //return myApiService.sayHi(name).execute().getData();
        } catch (IOException e) {
            return e.getMessage();
        }
    }
    @Override
    protected void onPostExecute(String result) {
        Toast.makeText(context, result, Toast.LENGTH_LONG).show();
    }
}
public class ServerAPI {
    private static ServerAPI sInstance;
    private Context context;

    public Integer UserID;
    private Authenticate auth;
    private AddingCategory addingc;
    private UpdatingCategory updatingc;
    private ServerAPI(Context context) {
        this.context = context;
    }
    //Public method to access the singleton ServerAPI object
    public static synchronized ServerAPI getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new ServerAPI(context.getApplicationContext());
        }
        return sInstance;
    }
    //connect is just used for testing purpose
    public void Connect(){
        EndpointsAsyncTask t  = new EndpointsAsyncTask();
        t.execute(new Pair<Context, String>(context, "Manfred"));
        //t.execute(new Pair<Context, String>(context, "Manfred"));
    }

    public void AddingCat(Category cat){
        addingc = new AddingCategory(cat);

        //try {
            //add .get() to end while testing for quick results
            //String s = addingc.execute(new Pair<String, Integer>(c_name, UserID)).get();
            addingc.execute(UserID);
            //String s = vali.Authentication(uname,upass);
            //works everywhere except debug mode
            String b = "sdf";
            //return s;
//        }
//        catch (InterruptedException e )
//        {
//            Toast.makeText(context,e.getMessage(), Toast.LENGTH_LONG).show();
//        }catch (ExecutionException b ) {
//            Toast.makeText(context,b.getMessage(), Toast.LENGTH_LONG).show();
//        }

    }
    public void UpdatingCat(String c_name){
        updatingc = new UpdatingCategory();
        updatingc.execute(new Pair<String, Integer>(c_name, UserID));
//        try {
            //String s = updatingc.execute(new Pair<String, Integer>(c_name, UserID)).get();
            //String s = vali.Authentication(uname,upass);
            //works everywhere except debug mode
//            String b = "sdf";
//            return s;
//        }
//        catch (InterruptedException e )
//        {
//            return e.getMessage();
//        }catch (ExecutionException b ) {
//            return b.getMessage();
//        }

    }
    public void AddItem(Item item){
        AddingItem addingi = new AddingItem(item);
        //addingi.execute( UserID);

//        try {
//            String s = addingi.execute( UserID).get();
//            String b = "asdfsad";
//            return s;
//        }
//        catch (InterruptedException e )
//        {
//            return e.getMessage();
//        }catch (ExecutionException b ) {
//            return b.getMessage();
//        }
        //return "asdf";
    }
    public void UpdateItem(Item item)
    {
        UpdatingItem updatingi = new UpdatingItem(item);
        updatingi.execute( UserID);
//        try {
//            String s = updatingi.execute( UserID).get();
//            String b = "asdfsad";
//            return s;
//        }
//        catch (InterruptedException e )
//        {
//            return e.getMessage();
//        }catch (ExecutionException b ) {
//            return b.getMessage();
//        }
    }

    public boolean CheckAuthenticate(String uname,String upass ){
        auth = new Authenticate();
        try {
            Pair<String, Integer> s = auth.execute(new Pair<String, String>(uname, upass)).get();
            UserID = s.second;
            //String s = vali.Authentication(uname,upass);
            //works everywhere except debug mode
            return s.first.equals("true");
        }
        catch (InterruptedException e )
        {
            return false;
        }catch (ExecutionException b ) {
            return false;
        }
    }
}
