package teamspoiler.renameme;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.joda.time.LocalDateTime;
import org.w3c.dom.Text;

import java.util.Calendar;

import teamspoiler.renameme.DataElements.Category;
import teamspoiler.renameme.DataElements.Item;
import teamspoiler.renameme.DataElements.IterableMap;

public class EditItemActivity extends AppCompatActivity {

    private DatabaseHelperClass db;                             // reference to the database class
    private int iid;                                           // item id and category id
    private Item item;                                         // the item object
    private int sMinute, sHour, sDay, sMonth, sYear;      // the time

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        initialize();
    }

    private void initialize(){
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            iid = extras.getInt("Item_ID");
        }
        db = DatabaseHelperClass.getInstance(this);
        item = db.getItem(iid);

        final TextView Name = (TextView) findViewById(R.id.EditItem_Name);
        final TextView ExpDate = (TextView) findViewById(R.id.EditItem_ExpDate);
        final TextView ExpTime = (TextView) findViewById(R.id.EditItem_ExpTime);
        final TextView Note = (TextView) findViewById(R.id.EditItem_Note);
        final Button Save = (Button) findViewById(R.id.EditItem_SaveButton);
        final Button Cancel = (Button) findViewById(R.id.EditItem_CancelButton);
        final Button ChangeDate = (Button) findViewById(R.id.EditItem_ChangeDateButton);
        final Button ChangeTime = (Button) findViewById(R.id.EditItem_ChangeTimeButton);

        Name.setText(item.getName());
        if(item.getDate() != null) {
            LocalDateTime date = item.getDate();
            sMinute = date.getMinuteOfHour();
            sHour = date.getHourOfDay();
            sDay = date.getDayOfMonth();
            sMonth = date.getMonthOfYear();
            sYear = date.getYear();
            ExpDate.setText((sMonth+1) + "/"
                    + sDay + "/" + sYear);
            ExpTime.setText(sHour + ":"
                    + sMinute);
        }
        if (item.getNote() != null) {
            Note.setText(item.getNote());
        }

        // set change date button
        ChangeDate.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentDate = Calendar.getInstance();
                DatePickerDialog mDatePicker;
                mDatePicker = new DatePickerDialog(EditItemActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int selectedYear,
                                                  int selectedMonth, int selectedDay) {
                                sDay = selectedDay;
                                sMonth = selectedMonth;
                                sYear = selectedYear;
                                ExpDate.setText((sMonth+1) + "/" + sDay + "/" + sYear);
                                ExpDate.invalidate();
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
                mTimePicker = new TimePickerDialog(EditItemActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker,
                                                  int selectedHour, int selectedMinute) {
                                sHour = selectedHour;
                                sMinute = selectedMinute;
                                ExpTime.setText(sHour + ":" + sMinute);
                                ExpTime.invalidate();
                            }
                        }, sHour, sMinute, true); //Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        }));

        // set action for save item changes button at bottom
        Save.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item.setName(Name.getText().toString());
                item.setDate(new LocalDateTime(sYear, sMonth, sDay, sHour, sMinute));
                item.setNote(Note.getText().toString());
                db.updateItem(item);
                finish();
            }
        }));

        // set action for cancel button at bottom
        Cancel.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        }));
    }
}
