package teamspoiler.renameme;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.joda.time.LocalDateTime;
import java.util.Calendar;

import teamspoiler.renameme.DataElements.*;

public class AddItemActivity extends AppCompatActivity {
    private DatabaseHelperClass db;
    private int cid;
    private int sMinute, sHour, sDay, sMonth, sYear;
    private ServerAPI serAPI;                       //reference to server api class

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        initialize();
    }

    private void initialize() {
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            cid = extras.getInt("Category_ID");
        }
        db = DatabaseHelperClass.getInstance(this);
        serAPI = ServerAPI.getInstance(this);
        final Button ChangeDate = (Button) findViewById(R.id.AddItem_ChangeDateButton);
        final Button ChangeTime = (Button) findViewById(R.id.AddItem_ChangeTimeButton);
        final Button Save = (Button) findViewById(R.id.AddItem_SaveButton);
        final Button Cancel = (Button) findViewById(R.id.AddItem_CancelButton);
        final TextView Name = (TextView) findViewById(R.id.AddItem_NameInput);
        final TextView Note = (TextView) findViewById(R.id.AddItem_NoteInput);
        final TextView DateText = (TextView) findViewById(R.id.AddItem_DateText);
        final TextView TimeText = (TextView) findViewById(R.id.AddItem_TimeText);

        final Calendar Cal = Calendar.getInstance();
        sMinute = Cal.get(Calendar.MINUTE);
        sHour = Cal.get(Calendar.HOUR_OF_DAY);
        sDay = Cal.get(Calendar.DAY_OF_MONTH);
        sMonth = Cal.get(Calendar.MONTH);
        sYear = Cal.get(Calendar.YEAR);

        // set change date button
        ChangeDate.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentDate = Calendar.getInstance();
                DatePickerDialog mDatePicker;
                mDatePicker = new DatePickerDialog(AddItemActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int selectedYear,
                                                  int selectedMonth, int selectedDay) {
                                sDay = selectedDay;
                                sMonth = selectedMonth;
                                sYear = selectedYear;
                                DateText.setText((sMonth+1) + "/" + sDay + "/" + sYear);
                                DateText.invalidate();
                            }
                        }, sYear, sMonth, sDay);
                mDatePicker.setTitle("Select Day");
                mDatePicker.show();
            }
        }));

        // set change time button
        ChangeTime.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(AddItemActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker,
                                                  int selectedHour, int selectedMinute) {
                                sHour = selectedHour;
                                sMinute = selectedMinute;
                                TimeText.setText(sHour + ":" + sMinute);
                                TimeText.invalidate();
                            }
                        }, sHour, sMinute, true); //Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        }));

        // save
        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Item item = new Item(Name.getText().toString(), cid);
                item.setDate(new LocalDateTime(sYear, sMonth + 1, sDay, sHour, sMinute));
                item.setNote(Note.getText().toString());
                if (item.getName() == null || item.getName().isEmpty()) {
                    Toast.makeText(AddItemActivity.this, "You must specify an item name", Toast.LENGTH_LONG).show();
                }
                else if (item.getDate().isBefore(LocalDateTime.now())) {
                    Toast.makeText(AddItemActivity.this, "Expiration date must be after the current time", Toast.LENGTH_LONG).show();
                }
                else {
                    Boolean k = db.addItem(item);
                    serAPI.AddItem(item);
                    ItemNotification.notify(AddItemActivity.this, item);
                    finish();
                }

            }
        });

        // cancel
       Cancel.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               finish();
           }
       });

        DateText.setText((sMonth + 1) + "/" + sDay + "/" + sYear);
        TimeText.setText(sHour + ":" + sMinute);
    }
}
