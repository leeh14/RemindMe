package teamspoiler.renameme;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import org.joda.time.Duration;
import org.joda.time.LocalDateTime;

import java.util.Calendar;
import java.util.Locale;

import teamspoiler.renameme.DataElements.*;

/**
 * Created by hirats on 4/18/2016.
 */
public class ItemNotification extends Service {
    DatabaseHelperClass db;

    //The maximum duration of time (in milliseconds) that the notification can
    //be offset from the item's expiration date.
    public final int DURATION_WINDOW = 5*60*1000;

    // Use this method to create an Alarm that goes off at the given time and
    //sets off the ItemNotification Service. Call it whenever an item is added or modified
    public static void notify(Context context, Item item) {
        Intent myIntent = new Intent(context, ItemNotification.class);
        myIntent.putExtra(context.getString(R.string.extra_item_id), item.getID());

        AlarmManager alarmManager = (AlarmManager)context.getSystemService(ALARM_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getService(context, 0, myIntent, 0);

        Calendar calendar = item.getDate().toDateTime().toCalendar(Locale.ENGLISH);
        //context.startService(myIntent);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }

    //Use this method to remove a notification from the Android device if one exists.
    //Call it when an item is viewed.
    public static void denotify(Context context, Item item) {
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.cancel(item.getID());
    }

    //
    protected Notification buildNotification(Item item) {
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.my_selector)
                        .setContentTitle("RemindMe")
                        .setContentText(item.getName() + " at " + item.getDate().toString());


        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        // Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(CategoriesActivity.class);
        // Adds the Intent that starts the Activity to the top of the stack

        // Creates an explicit intent for an Activity in your app
        Intent categoryIntent = new Intent(this, CategoryActivity.class);
        categoryIntent.putExtra(getString(R.string.extra_category_id), item.getCategoryID());
        stackBuilder.addNextIntent(categoryIntent);

        Intent itemIntent = new Intent(this,ItemActivity.class);
        itemIntent.putExtra(getString(R.string.extra_item_id), item.getID());
        stackBuilder.addNextIntent(itemIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(1, PendingIntent.FLAG_UPDATE_CURRENT);

        mBuilder.setContentIntent(resultPendingIntent);

        return mBuilder.build();
    }

    @Override
    public void onCreate() {
        db = DatabaseHelperClass.getInstance(this);
    }

    //Method that is called when the AlarmTimer runs out and the Service starts
    //Creates a corresponding notification if it the current time correpsonds to item's expiration date.
    @Override
    public int onStartCommand(Intent intent, int flags, int startID) {
        int mId = intent.getExtras().getInt(getString(R.string.extra_item_id));
        Item item = db.getItem(mId);

        LocalDateTime now = LocalDateTime.now();
        Duration window = new Duration(DURATION_WINDOW);
        if (item != null && now.isAfter(item.getDate().minus(window)) && now.isBefore(item.getDate().plus(window)))  {
            NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            // mId allows you to update the notification later on by creating or cancelling a notification with the same ID.
            mNotificationManager.notify(mId, buildNotification(item));
            stopSelf();
            return 1;
        }
        stopSelf();
        return 0;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return null;
    }
}
