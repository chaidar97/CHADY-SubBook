package com.example.chady.subbook;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;


//This class will handle loading and user interaction with a specific subscription selected by the user
public class NewSubActivity extends AppCompatActivity {
    public String name;
    public double price=0;
    public String date=" ";
    public String comment=" ";
    Subscriptions currentSub;
    ObjectHandler subHandler = new ObjectHandler(this);
    public int ID;
    private ArrayList<Subscriptions> subscriptions = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_sub);
        getWindow().getDecorView().setBackgroundColor(Color.DKGRAY); //set background color
        //retrieves the extra intent name variable
        ID = getIntent().getIntExtra("ID", 0);
        name = getIntent().getStringExtra("NAME");
        Log.i("TESTER", Integer.toString(ID));
        subscriptions = subHandler.loadSubArray();
        findSub();
        initialize();
    }


    //this method will insert all the info into the boxes if they have been specified
    public void initialize(){
        //Next lines don't insert the values if they're the initial ones
        EditText subName = (EditText)findViewById(R.id.name);
        subName.setText(name, TextView.BufferType.EDITABLE);
        if(!(currentSub.getName().equals(" "))) subName.setText(currentSub.getName());
        EditText date = (EditText) findViewById(R.id.editText2);
        if(!(currentSub.getDate().equals("INITIAL"))) date.setText(currentSub.getDate());
        EditText price = (EditText) findViewById(R.id.editText5);
        if(currentSub.getPrice() != 0.0) price.setText(Double.toString(currentSub.getPrice()));
        EditText comment = (EditText) findViewById(R.id.editText4);
        if(!(currentSub.getComment().equals(" "))) comment.setText(currentSub.getComment());
        //add all the editTexts to the listener to detect changes
        date.addTextChangedListener(new GenericTextWatcher(date));
        subName.addTextChangedListener(new GenericTextWatcher(subName));
        price.addTextChangedListener(new GenericTextWatcher(price));
        comment.addTextChangedListener(new GenericTextWatcher(comment));
    }

    //Saves the changes when the activity is stopped
    @Override
    public void onStop() {
        super.onStop();
        subHandler.saveSubArray(subscriptions);
    }

    //method to find which sub we're currently viewing
    public void findSub(){
        for(Subscriptions s : subscriptions){
            if(s.getID() == ID){
                currentSub = s;
                break;
            }
        }
    }

    //When the user presses the Done button
    public void done(View view){
        //save the subscriptions
        subHandler.saveSubArray(subscriptions);
        //go to the previous activity
        Intent newSub = new Intent(NewSubActivity.this, MainActivity.class);
        startActivity(newSub);
    }

    //display the error message if the date is entered incorrectly
    public void errorMessage(View view){
        EditText date = (EditText) findViewById(R.id.editText2);
        //fill the subs array with the subscription names
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Invalid Date");
        //input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(view);
        // Set up the buttons
        builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
        //resets the date
        date.setText("");
    }


    // Textwatcher code taken from https://stackoverflow.com/questions/8543449/how-to-use-the-textwatcher-class-in-android
    //Textwatcher inner class to watch over text boxes
    private class GenericTextWatcher implements TextWatcher{

        private View view;
        private GenericTextWatcher(View view) {
            this.view = view;
        }

        public void afterTextChanged(Editable s) {
        }
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (view.getId() == R.id.editText2) {
                //returns if length is 0 to avoid issues
                if (s.length() == 0){
                    currentSub.setDate("INITIAL");
                    return;
                }
                String r = s.toString();
                //checks if any parts of the string are alphanumeric(forbidden)
                if (0 < r.length()) {
                    if (r.matches(".*[a-z].*")) {
                        errorMessage(null);
                        return;
                    }
                }
                if (r.length() == 5 && !(r.charAt(4) == '-')) { //checks to make sure the format is correct
                    errorMessage(null);
                    return;
                } else if (r.length() == 8 && !(r.charAt(7) == '-')) { //checks to make sure the format is correct
                        errorMessage(null);
                        return;
                }
                date = s.toString();
                //update date
                Log.i("DATETEST", date);
                currentSub.setDate(date);
            } else if (view.getId() == R.id.name) { //checks for name changes
                name = s.toString();
                //update name
                name = (name.isEmpty()) ? " " : name;
                currentSub.setName(name);
            } else if (view.getId() == R.id.editText5){ //checks for changes to price
                if(!s.toString().isEmpty()) {
                    price = Double.parseDouble(s.toString());
                } else {
                    price = 0;
                }
                //update price
                currentSub.setPrice(price);
            } else if (view.getId() == R.id.editText4){ //checks for changes to comments
                comment=s.toString();
                //update comment
                comment = (comment.isEmpty()) ? " " : comment;
                currentSub.setComment(comment);
            }
        }
    };
}
