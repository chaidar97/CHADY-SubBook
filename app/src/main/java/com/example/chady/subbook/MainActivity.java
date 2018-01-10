package com.example.chady.subbook;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
//This class handles adding/removing new subscriptions,
//as well as directing the user to the specific subscription interface

public class MainActivity extends AppCompatActivity {
    //initialise variables used
    int i=0;
    int deleteMode=0;
    private String name = "";
    ArrayList<String> subs = new ArrayList<>();
    private ListView listView;
    private ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.subs);
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, subs);
        listView.setAdapter(adapter);
        //Handles when a listview button is clicked
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long Id) {
                //if the user wants to delete the button, it will call the remove method
                if(deleteMode==1){
                    //****DELETE ROW FOR THIS SUB*****
                    adapter.remove(adapter.getItem(position));
                    adapter.notifyDataSetChanged();
                }else{
                    //passes the name variable as an extra intent
                    name = subs.get(position);
                    Intent newSub = new Intent(MainActivity.this, NewSubActivity.class); //goes to the new activity if not
                    newSub.putExtra("NAME", name);
                    startActivity(newSub);
                }
            }
        });

    }

    //Function that handles interface to type in new sub name, then adds it
    public void addSub(View view){
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
                    //****ADD A NEW ROW TO THE TABLE WITH THIS NAME****
                    subs.add(name);
                    i++;
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

    //method to put the app into removal mode to remove the subscription
    public void removeSub(View view){
        if(deleteMode==0) {
            listView.setBackgroundColor(Color.parseColor("#FF0000"));
            deleteMode=1;
        } else{
            listView.setBackgroundColor(Color.parseColor("#FFFFFF"));
            deleteMode=0;
        }
        adapter.notifyDataSetChanged();
    }
}
