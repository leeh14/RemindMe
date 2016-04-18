package teamspoiler.renameme;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

import java.util.Calendar;
import java.util.Locale;

import org.joda.time.LocalDateTime;

import teamspoiler.renameme.DataElements.*;

/**
 * Created by hirats on 4/18/2016.
 */
public class Notification extends Service {
    DatabaseHelperClass db;

    // Use this method to create a new notification
    public static void notify(Context context, Item item) {
        Intent myIntent = new Intent(context, Notification.class);
        myIntent.putExtra("Item_ID", item.getID());

        AlarmManager alarmManager = (AlarmManager)context.getSystemService(ALARM_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getService(context, 0, myIntent, 0);

        Calendar calendar = item.getDate().toDateTime().toCalendar(Locale.ENGLISH);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }

    @Override
    public void onCreate() {
        db = DatabaseHelperClass.getInstance(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startID) {
        int mId = intent.getExtras().getInt("Item_ID");
        Item item = db.getItem(mId);
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.my_selector)
                        .setContentTitle("My notification")
                        .setContentText("Hello World!");

// Creates an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(this,CategoriesActivity.class);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(this, 0, resultIntent, 0);
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
// mId allows you to update the notification later on.
        mNotificationManager.notify(mId, mBuilder.build());
        return super.onStartCommand(intent,flags,startID);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return null;
    }
}
