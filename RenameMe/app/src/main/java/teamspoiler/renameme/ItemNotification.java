package teamspoiler.renameme;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
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
    /*protected BroadcastReceiver stopServiceReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            stopSelf();
        }
    };*/

    // Use this method to create a new notification
    public static void notify(Context context, Item item) {
        Intent myIntent = new Intent(context, ItemNotification.class);
        myIntent.putExtra("Item_ID", item.getID());

        AlarmManager alarmManager = (AlarmManager)context.getSystemService(ALARM_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getService(context, 0, myIntent, 0);

        Calendar calendar = item.getDate().toDateTime().toCalendar(Locale.ENGLISH);
        //context.startService(myIntent);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }

    public static void denotify(Context context, Item item) {
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.cancel(item.getID());
    }

    protected Notification buildNotification(Item item) {
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.my_selector)
                        .setContentTitle("RemindMe")
                        .setContentText(item.getName() + " at " + item.getDate().toString());

        //registerReceiver(stopServiceReceiver, new IntentFilter("notification_stopper"));
        //PendingIntent stopIntent = PendingIntent.getBroadcast(this, 0, new Intent("notification_stopper"), PendingIntent.FLAG_UPDATE_CURRENT);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        // Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(CategoriesActivity.class);
        // Adds the Intent that starts the Activity to the top of the stack
        //Intent.get
        //stackBuilder.addNextIntent(new Intent(stopServiceReceiver));
        // Creates an explicit intent for an Activity in your app
        Intent categoryIntent = new Intent(this, CategoryActivity.class);
        categoryIntent.putExtra("Category_ID", item.getCategoryID());
        stackBuilder.addNextIntent(categoryIntent);

        Intent itemIntent = new Intent(this,ItemActivity.class);
        itemIntent.putExtra("Item_ID", item.getID());
        stackBuilder.addNextIntent(itemIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(1, PendingIntent.FLAG_UPDATE_CURRENT);

        mBuilder.setContentIntent(resultPendingIntent);

        // mId allows you to update the notification later on.

        //mBuilder.setContentIntent(stopIntent);
        return mBuilder.build();
    }

    @Override
    public void onCreate() {
        db = DatabaseHelperClass.getInstance(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startID) {
        int mId = intent.getExtras().getInt("Item_ID");
        Item item = db.getItem(mId);

        LocalDateTime now = LocalDateTime.now();
        Duration window = new Duration(5*60*1000);
        if (item != null && now.isAfter(item.getDate().minus(window)) && now.isBefore(item.getDate().plus(window)))  {
            NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
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
