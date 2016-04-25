package teamspoiler.renameme;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import teamspoiler.renameme.DataElements.*;

public class CategoryActivity extends AppCompatActivity {
    private DatabaseHelperClass db;                 // reference to database helper class
    static int cid;                                 // id of the category
    private Category category;                     // reference to category itself
    private IterableMap<Item> items;               // reference to items inside category
    final Context context = this;                  // context of this activity
    static final int ADD_ITEM_REQUEST = 1;      // The request code
    static final int UPDATE_TIME_REQUEST = 2;  // The request code 2

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        initialize();
    }

    // initialize
    private void initialize() {
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            cid = extras.getInt("Category_ID");
        }
        db = DatabaseHelperClass.getInstance(this);
        category = db.getCategory(cid);
        items = db.getItems(cid);

        TextView categoryTitle = (TextView)findViewById(R.id.Cate_Title);
        categoryTitle.setText(category.getName());

        final Button Categories = (Button) findViewById(R.id.Cate_CategoriesButton);
        final Button Friends = (Button) findViewById(R.id.Cate_FriendsButton);
        final Button Settings = (Button) findViewById(R.id.Cate_SettingsButton);
        final Button AddItem = (Button) findViewById(R.id.Cate_AddItemButton);
        final Button Share = (Button) findViewById(R.id. Cate_ShareButton);
        final Button Delete = (Button) findViewById(R.id.Cate_DeleteButton);

        // set action for categories button at top
        Categories.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent CategoriesIntent = new Intent(v.getContext(), CategoriesActivity.class);
                startActivity(CategoriesIntent);
            }
        }));

        // set action for friend button at top
        Friends.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent FriendsIntent = new Intent(v.getContext(), FriendsActivity.class);
                startActivity(FriendsIntent);
            }
        }));

        // set action for setting button at top
        Settings.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent SettingsIntent = new Intent(v.getContext(), SettingsActivity.class);
                startActivity(SettingsIntent);
            }
        }));

        // set action for add item button at bottom
        AddItem.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent AddItemIntent = new Intent(v.getContext(), AddItemActivity.class);
                AddItemIntent.putExtra("Category_ID", cid);
                startActivityForResult(AddItemIntent, ADD_ITEM_REQUEST);
            }
        }));

        // set action for share button at bottom
        Share.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ShareIntent = new Intent(v.getContext(), ShareCategoryActivity.class);
                startActivity(ShareIntent);
            }
        }));

        Delete.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        context);

                // set title
                String cName = category.getName();
                alertDialogBuilder.setTitle("Delete Category");

                // set dialog message
                alertDialogBuilder
                        .setMessage("Do you want to delete " + cName + "?")
                        .setCancelable(false)
                        .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                db.deleteCategory(category.getID());
                                finish();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // close the dialog
                                dialog.cancel();
                            }
                        });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();
            }
        }));

        populateItemsList();
        registerClickCallBack();
    }

    // populate the category list with category button
    private void populateItemsList() {
        ArrayAdapter<Item> adapter = new ArrayAdapter<Item>(
                this,
                R.layout.data_itemslist,
                items.toList());
        ListView list = (ListView) findViewById(R.id.Cate_ItemsList);
        list.setAdapter(adapter);
    }

    // set action for category list button
    private void registerClickCallBack() {
        // category list
        ListView list = (ListView) findViewById(R.id.Cate_ItemsList);
        list.setOnItemClickListener((new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {
                Item it = (Item) parent.getAdapter().getItem(position);
                Intent i = new Intent(CategoryActivity.this, ItemActivity.class);
                i.putExtra("Category_ID", cid);
                i.putExtra("Item_ID", it.getID());
                startActivityForResult(i, UPDATE_TIME_REQUEST);
            }
        }));

        // sorting spinner
        final Spinner spinner = (Spinner) findViewById(R.id.Cate_SortOptions);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.sort_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        // set on sorting method selected action
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                String method = spinner.getSelectedItem().toString();
                sortList(method);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });
    }

    // sort the list
    private void sortList(String method){

        List<Item> sortList = items.toList();

        switch(method){
            case "None":
                break;
            case "Alphabetical":
                insertionSort(sortList, 0);
                break;
            case "Closest Expire":
                insertionSort(sortList, 1);
                break;
        }

        if(method != "None") {
            ArrayAdapter<Item> adapter = new ArrayAdapter<Item>(
                    this,
                    R.layout.data_itemslist,
                    sortList);
            ListView list = (ListView) findViewById(R.id.Cate_ItemsList);
            list.setAdapter(adapter);
        }
    }

    private List<Item> insertionSort(List<Item> input, int type){
        Item temp;
        for (int i = 1; i < input.size(); i++) {
            for (int j = i; j > 0; j--) {
                if (compare(input.get(j), input.get(j - 1), type)){
                    temp = input.get(j);
                    input.set(j, input.get(j - 1));
                    input.set(j - 1, temp);
                }
            }
        }
        return input;
    }

    private boolean compare(Item i, Item j, int type){
        switch (type){
            // Alphabetical
            case 0:
                if(i.getName().compareToIgnoreCase(j.getName()) > 0) {
                    return false;
                }
                break;
            // Closest Expire
            case 1:
                if(i.getDate().isAfter(j.getDate())) {
                    return false;
                }
                break;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // refresh the activity
        if (requestCode == ADD_ITEM_REQUEST || requestCode == UPDATE_TIME_REQUEST) {
            finish();
            startActivity(getIntent());
        }
    }
}
