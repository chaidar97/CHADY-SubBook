package com.example.chady.subbook;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.example.chady.subbook.Subscriptions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.apache.commons.lang3.SerializationUtils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by Chady on 2018-01-18.
 */

public class ObjectHandler extends Activity {
    private static final String FILENAME = "file.sav";
    public ArrayList<Subscriptions> subList = new ArrayList<Subscriptions>();
    public Context context;


    public ObjectHandler(Context context) {
        this.context = context;
    }

    //Saves the sub array
    public void saveSubArray(ArrayList<Subscriptions> subs) {
        // Taken from Lab 3 Cmput 301
        try {
            FileOutputStream fos = context.openFileOutput(FILENAME,
                    Context.MODE_PRIVATE);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));
            Gson gson = new Gson();
            gson.toJson(subs, out);
            out.flush();

        } catch (FileNotFoundException e) {
            throw new RuntimeException();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }


    // Loads the sub array and returns it
    public ArrayList<Subscriptions> loadSubArray() {
        // Taken from Lab 3 Cmput 301
        try {
            FileInputStream fis = context.openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<Subscriptions>>() {
            }.getType();
            subList = gson.fromJson(in, listType);
        } catch (FileNotFoundException e) {
            subList = new ArrayList<Subscriptions>();
        }
        return subList;
    }

}