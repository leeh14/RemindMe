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
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import teamspoiler.renameme.DataElements.Category;
import teamspoiler.renameme.DataElements.Friend;
import teamspoiler.renameme.DataElements.Item;

/**
 * Created by Hayden on 4/24/2016.
 */
//Asynchronous task to authenticate a user
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
            //grab the response from backend and get the current user's id
            String s =  myApiService.authenticate(uname, upass).execute().getData();
            Integer d = myApiService.authenticate(uname, upass).execute().getUserId();
            return new Pair<String, Integer>(s,d );
        } catch (IOException e) {
            return new Pair<String, Integer>(e.getMessage(), -1);
        }
    }
}
//Asynchronous task to add an User
class AddingUser extends AsyncTask< Pair<String , String>, Void, String> {
    private static MyApi myApiService = null;
    private Context context;
    @Override
    protected String doInBackground(Pair<String, String>... params) {
        if (myApiService == null) {
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                    .setRootUrl("https://headsup-1260.appspot.com/_ah/api/");
            // end options for devappserver

            myApiService = builder.build();
        }
        //set the stored user id
        String username = params[0].first;
        String password = params[0].second;
        try {
            return   myApiService.addUser(username, password).execute().getData();

        } catch (IOException e) {
            return e.getMessage();
        }
    }
}
//Asynchronous task to add a Friend
class AddingFriend extends AsyncTask<Integer, Void, String> {
    private static MyApi myApiService = null;
    private Context context;
    private String friend_username;
    private String friend_name;
    public AddingFriend(Friend f){
        friend_name = f.getName().trim();
        friend_username = f.getUsername().trim();
    }

    @Override
    protected String doInBackground(Integer... params) {
        if (myApiService == null) {
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                    .setRootUrl("https://headsup-1260.appspot.com/_ah/api/");
            // end options for devappserver

            myApiService = builder.build();
        }
        //set the stored user id
        Integer user_id = params[0];
        try {
            return   myApiService.addFriend(friend_name, friend_username, user_id).execute().getData();

        } catch (IOException e) {
            return e.getMessage();
        }
    }
}
//Asynchronous task to add an item
class AddingItem extends AsyncTask< Integer, Void, String> {
    private static MyApi myApiService = null;
    private Context context;
    private String item_name;
    private String[] expirationarr;
    private String expiration;
    private Integer cat_id;
    private String note;
    private Integer item_id;
    //constructor to instantite the class
    public AddingItem(Item item){
        item_name = item.getName();
        //Parse the LocalDateTime in joda that is stored
        expirationarr = item.getDate().toString().split("T");
        String [] expirationtemp = expirationarr[1].split("\\.");
        //pass in the newly parsed date and make it into MYSQL format for dates
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
        //set the stored user id
        Integer userId = params[0];
        try {
            return myApiService.addItem(item_id, item_name, expiration, cat_id, userId, note).execute().getData();

        } catch (IOException e) {
            return e.getMessage();
        }
    }
}
//Asynchronous task to update an item
class UpdatingItem extends AsyncTask< Integer, Void, String> {
    private static MyApi myApiService = null;
    private Context context;
    private String item_name;
    private String[] expirationarr;
    private String expiration;
    private Integer cat_id;
    private String note;
    private Integer item_id;
    //constructor to pass in arguments that can't normally be passed in
    public UpdatingItem(Item item){
        item_name = item.getName();
        //Parse the jota LocalDateTime
        expirationarr = item.getDate().toString().split("T");
        String [] expirationtemp = expirationarr[1].split("\\.");
        //Reformat the string to fit MYSQL format for dates
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
            return myApiService.updateItem(item_name, expiration, cat_id, userId, note, item_id).execute().getData();
        } catch (IOException e) {
            return e.getMessage();
        }
    }

}
//Asynchronous task to delete an item
class DeletingItem extends AsyncTask< Integer, Void, String> {
    private static MyApi myApiService = null;
    private Context context;
    @Override
    protected String doInBackground(Integer... params) {
        if (myApiService == null) {
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                    .setRootUrl("https://headsup-1260.appspot.com/_ah/api/");
            // end options for devappserver

            myApiService = builder.build();
        }
        //set the stored user id
        Integer item_id = params[0];
        try {
            return   myApiService.deleteItem(item_id).execute().getData();

        } catch (IOException e) {
            return e.getMessage();
        }
    }
}
//Asynchronous task to adding a category
class AddingCategory extends AsyncTask<Integer, Void, String> {
    private static MyApi myApiService = null;
    private Context context;
    private Integer cat_id;
    private String cat_name;
    //constructor to store the additional values of the category that can't be passed in
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
            return myApiService.addCategory(cat_id, cat_name, user).execute().getData();
        } catch (IOException e) {
            return e.getMessage();
        }
    }

}
//Asynchronous task to update a category
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
            return myApiService.updateCategory(cat_name, user).execute().getData();
        } catch (IOException e) {
            return e.getMessage();
        }
    }

}
//Asynchronous task to delete a category
class DeleteingCategory extends AsyncTask< Integer, Void, String> {
    private static MyApi myApiService = null;
    private Context context;
    @Override
    protected String doInBackground(Integer... params) {
        if (myApiService == null) {
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                    .setRootUrl("https://headsup-1260.appspot.com/_ah/api/");
            // end options for devappserver

            myApiService = builder.build();
        }
        //set the stored user id
        Integer cat_id = params[0];
        try {
            return   myApiService.deleteCategory(cat_id).execute().getData();

        } catch (IOException e) {
            return e.getMessage();
        }
    }
}
//Asynchronous task to share a category
class SharingCategory extends AsyncTask< Integer, Void, String> {
    private static MyApi myApiService = null;
    private Context context;
    private String friend_name;
    private Integer cat_id;
    private String cat_name;
    public SharingCategory(String friend, Integer cat,String catname){
        friend_name = friend;

        cat_id = cat;
        cat_name = catname;
    }

    @Override
    protected String doInBackground(Integer... params) {
        if (myApiService == null) {
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                    .setRootUrl("https://headsup-1260.appspot.com/_ah/api/");
            // end options for devappserver

            myApiService = builder.build();
        }
        //set the stored user id
        Integer userid = params[0];
        try {
            return myApiService.shareCategory(friend_name, userid, cat_id, cat_name).execute().getData();

        } catch (IOException e) {
            return e.getMessage();
        }
    }
}
//Asynchronous task to check if share category is already in
class CheckShare extends AsyncTask<Pair<Integer,Integer>, Void, Pair<Pair<String, String>,Integer>> {
    private static MyApi myApiService = null;
    private Context context;

    @Override
    protected Pair<Pair<String, String>,Integer> doInBackground(Pair<Integer, Integer>... params) {
        if (myApiService == null) {
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                    .setRootUrl("https://headsup-1260.appspot.com/_ah/api/");
            // end options for devappserver

            myApiService = builder.build();
        }
        Integer user_id = params[0].first;
        Integer cat_id = params[0].second;
        try {
            //grab the response from backend and get the current user's id
            String s = myApiService.checkShareCategory(user_id).execute().getData();
            String name = myApiService.checkShareCategory(user_id).execute().getShareName();
            Pair<String , String> data = new Pair<String, String>(s,name);
            Integer d = myApiService.checkShareCategory(user_id).execute().getShareCat();
            return new Pair<Pair<String , String>, Integer>(data, d);
        } catch (IOException e) {
            return new Pair<Pair<String , String>, Integer>(new Pair<String ,String>(e.getMessage(), "sdf"), -1);
        }
    }
}
//Asynchronous task to check if share category is already in
class AddShare extends AsyncTask<Integer, Void, List<String>> {
    private static MyApi myApiService = null;
    private Context context;

    @Override
    protected List<String> doInBackground(Integer... params) {
        if (myApiService == null) {
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                    .setRootUrl("https://headsup-1260.appspot.com/_ah/api/");
            // end options for devappserver

            myApiService = builder.build();
        }
        Integer cat_id = params[0];
        try {
            //grab the response from backend and get the current user's id
            return myApiService.addShareItems(cat_id).execute().getItems();

        } catch (IOException e) {
            return new ArrayList<String>();
        }
    }

}
//This class is more of a debugging class as of right now
//used for rapid responses/ modifying the framework of the database
class EndpointsAsyncTask extends AsyncTask<Pair<Context, String>, Void, String> {
    private static MyApi myApiService = null;
    private Context context;

    @Override
    protected String doInBackground(Pair<Context, String>... params) {
        if(myApiService == null) {  // Only do this once
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                    .setRootUrl("https://headsup-1260.appspot.com/_ah/api/");
            // end options for devappserver
            myApiService = builder.build();
        }

        context = params[0].first;
        String name = params[0].second;

        try {
            //call the connect function in backend, .getData ensures the operation to be completed before moving on
            return  myApiService.connect().execute().getData();
        } catch (IOException e) {
            return e.getMessage();
        }
    }
    //output the message
    @Override
    protected void onPostExecute(String result) {
        Toast.makeText(context, result, Toast.LENGTH_LONG).show();
    }
}
public class ServerAPI {
    private static ServerAPI sInstance;
    private Context context;                //used as the context for outputting if needed
    public Integer UserID;                  //the userid that the user holds

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


    //Starts the asynchronus task of adding a category
    public void AddingUser(String username , String password){
        AddingUser adduser = new AddingUser();
        adduser.execute(new Pair<String, String>(username,password));
    }
    //Starts the asynchronus task of adding a category
    public void AddingCat(Category cat){
        AddingCategory addingc = new AddingCategory(cat);
        addingc.execute(UserID);
    }
    //Starts the asynchronus task of updating a category
    public void UpdatingCat(String c_name){
        UpdatingCategory updatingc = new UpdatingCategory();
        updatingc.execute(new Pair<String, Integer>(c_name, UserID));
        //used for debbugging purposes to grab a string output
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
    //Starts the asynchronus task of deleting a category
    public void DeleteingCat(Integer cat_id){
        DeleteingCategory deletingc = new DeleteingCategory();
        deletingc.execute(cat_id);
    }
    //Starts the asynchronus task of adding a category
    public void ShareCategory(Friend friend, Integer cat_id, String cat_name){

        SharingCategory sharingc = new SharingCategory(friend.getUsername(), cat_id, cat_name);
        sharingc.execute(UserID);
    }
    //Starts the asynchronus task of adding a category
    public Pair<Boolean, Pair<Integer,String>> CheckShareCategory(){
        CheckShare checkshare = new CheckShare();
        try {
            Pair<Pair<String, String> , Integer> s = checkshare.execute(new Pair<Integer, Integer>(UserID, 123)).get();
            return new Pair<Boolean, Pair<Integer,String>>(s.first.first.equals("true"), new Pair<Integer, String>(s.second, s.first.second));
            //return new Pair<Boolean, Integer>(false, 234);
        }
        catch (InterruptedException e )
        {
            return new Pair<Boolean, Pair<Integer,String>>(false,new Pair<Integer, String>( -1, ""));
        }catch (ExecutionException b ) {
            return new Pair<Boolean, Pair<Integer,String>>(false,new Pair<Integer, String>( -1, ""));
        }
    }
    //starts the asynchronus task of adding share category items
    public List<String> AddShare(Integer cat_id){
        AddShare addingshare = new AddShare();
        try {
            return addingshare.execute(cat_id).get();
        }
        catch (InterruptedException e )
        {
            return new ArrayList<String>();
        }catch (ExecutionException b ) {
            return new ArrayList<String>();
        }
    }
    //Starts the asynchronus task of updating an item
    public void AddItem(Item item){
        AddingItem addingi = new AddingItem(item);
        addingi.execute( UserID);
    }
    //Starts the asynchronus task of updating an item
    public void UpdateItem(Item item)
    {
        UpdatingItem updatingi = new UpdatingItem(item);
        updatingi.execute( UserID);
    }
    //Starts the asynchronus task of deleting an item
    public void DeleteItem(Integer item_id)
    {
        DeletingItem deletingi = new DeletingItem();
        deletingi.execute(item_id);
    }
    //Starts the asynchronus task of adding a category
    public void AddingFriend(Friend friend){
        AddingFriend addingf = new AddingFriend(friend);
        addingf.execute(UserID);
    }

    //Boolean function that returns true id the username and password exist
    //Then it stores the user id for the singleton
    public boolean CheckAuthenticate(String uname,String upass ){
        Authenticate auth = new Authenticate();
        try {
            //starts asychronus task to make sure user is valid
            Pair<String, Integer> s = auth.execute(new Pair<String, String>(uname, upass)).get();
            //stores the user id obtained
            UserID = s.second;
            //return whether or not the username and password is valid
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
