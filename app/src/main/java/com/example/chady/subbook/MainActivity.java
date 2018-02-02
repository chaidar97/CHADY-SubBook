package com.example.chady.subbook;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * This class handles adding/removing new subscriptions,
 * as well as directing the user to the specific subscription interface
 * @see AppCompatActivity
 * @see Subscriptions
 * @see ObjectHandler
 */
public class MainActivity extends AppCompatActivity {
    //initialise variables used
    int position;
    int ID=0;
    int deleteMode=0;
    int boxChecked=0;
    ObjectHandler subHandler = new ObjectHandler(this);
    boolean confirm=false;
    private String name = "";
    ArrayList<String> subs = new ArrayList<>();
    private ListView listView;
    private ArrayAdapter adapter;
    private ArrayList<Subscriptions> subscriptions = new ArrayList<>();

    /**
     * Initializes the activity by loading the subscriptions and listview
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().getDecorView().setBackgroundColor(Color.DKGRAY); //set background color
        //loads the subscriptions
        subscriptions = subHandler.loadSubArray();
        //subHandler.saveSubArray(subscriptions);
        fillArray();
        updatePrice();
        getMaxID();
        listView = (ListView) findViewById(R.id.subs);
        adapter = new ArrayAdapter<String>(this,
                R.layout.layout_white_text, R.id.list_content, subs);
        listView.setAdapter(adapter);
        //Handles when a listview button is clicked
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            /**
             * If a subscription is clicked, it will either delete it or
             *  go to a new activity to see details
             * @param arg0
             * @param arg1
             * @param pos
             * @param Id
             */
            public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long Id) {
                //if the user wants to delete the button, it will call the remove method
                if(deleteMode==1){
                    position = pos;
                    removeConfirm(null);
                }else{
                    //passes the name variable as an extra intent
                    subHandler.saveSubArray(subscriptions);
                    Subscriptions subTemp = subscriptions.get(pos);
                    Log.i("TESTER", "ID:" + Integer.toString(subTemp.getID()) + "POS:" + Integer.toString(pos));
                    Intent newSub = new Intent(MainActivity.this, NewSubActivity.class); //goes to the new activity if not
                    newSub.putExtra("NAME", subTemp.getName());
                    newSub.putExtra("ID", subTemp.getID());
                    startActivity(newSub);
                }
            }
        });
    }

    /**
     * Stops the activity
     */
    @Override
    public void onStop() {
        super.onStop();
        subHandler.saveSubArray(subscriptions);
    }

    /**
     * finds the highest ID upon creation
     */
    public void getMaxID(){
        for(Subscriptions s : subscriptions){
            if(s.getID() > ID){
                ID = s.getID();
            }
        }
    }

    /**
     * load each subscription name into the name array
     */
    public void fillArray(){
        subs.clear(); //empty array
        for(Subscriptions s : subscriptions){
            subs.add(s.getName());
        }
    }

    /**
     * updates the price when a new sub price changed
     */
    public void updatePrice(){
        double totalPrice=0.0;
        final TextView total = (TextView) findViewById(R.id.total);
        for(Subscriptions s : subscriptions){
            totalPrice+=s.getPrice();
        }
        DecimalFormat df = new DecimalFormat("#.##");
        total.setText("$" + df.format(totalPrice));
    }

    /**
     * prompts the user for sub removal
     * @param view
     */
    public void removalPrompt(View view){
        if(boxChecked == 0) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Tap a subscription to remove, then tap remove when finished.");
            //setup a checkbox to never show prompt again)
            View checkBoxView = View.inflate(this, R.layout.check_box, null);
            CheckBox checkBox = (CheckBox) checkBoxView.findViewById(R.id.checkbox);
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                //if the checkbox is ticked, never show it again
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    boxChecked = 1;
                }
            });
            checkBox.setText("Never show this again");
            builder.setView(checkBoxView);
            // Set up the buttons
            builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            builder.show();
        }
    }

    /**
     * Function that handles interface to type in new sub name, then adds it
     * @param view
     */
    public void addSub(View view){
        //turn off delete mode if it's on when you add a new sub
        if(deleteMode==1){
            removeSub(null);
        }
        //fill the subs array with the subscription names
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Subscription Name:");
        // Set up the input
        final EditText input = new EditText(this);
        // Specify the type of input expected
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);
        // Set up the buttons
        builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            @Override
            //Once user enters the name, create button
            public void onClick(DialogInterface dialog, int which) {
                name = input.getText().toString();
                //if the name is less than 20 chars, add it to the sub list
                if(name.length()<20) {
                    subs.add(name);
                    ID += 1;
                    Subscriptions sub = new Subscriptions(name);
                    sub.setName(name);
                    sub.setID(ID);
                    subscriptions.add(sub);
                    adapter.notifyDataSetChanged();
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    /**
     * Function that asks user to confirm a sub deletion
     * @param view
     */
    public void removeConfirm(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Are you sure you wish to delete this subscription?");
        builder.setView(view);
        // Set up the buttons
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            //Delete sub if user clicks yes
            public void onClick(DialogInterface dialog, int which) {
                //****DELETE ROW FOR THIS SUB*****
                subscriptions.remove(position);
                updatePrice();
                adapter.remove(adapter.getItem(position));
                adapter.notifyDataSetChanged();
                confirm=false;
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    /**
     * Method to put the app into removal mode to remove the subscription
     * @param view
     */
    public void removeSub(View view){
        if(deleteMode==0) {
            removalPrompt(null);
            listView.setBackgroundColor(Color.parseColor("#FF0000"));
            deleteMode=1;
        } else{
            listView.setBackgroundColor(Color.parseColor("#444444"));
            deleteMode=0;
        }
        adapter.notifyDataSetChanged();
    }
}
