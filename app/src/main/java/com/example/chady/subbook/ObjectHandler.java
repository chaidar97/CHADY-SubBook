package com.example.chady.subbook;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.example.chady.subbook.Subscriptions;

import org.apache.commons.lang3.SerializationUtils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
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
import java.util.ArrayList;

/**
 * Created by Chady on 2018-01-18.
 */

public class ObjectHandler extends Activity {
    public ArrayList<Subscriptions> subList = new ArrayList<Subscriptions>();
    public Context context;

    public ObjectHandler(Context context){
        this.context = context;
    }

    public ArrayList<Subscriptions> loadSubArray() {
        try {
            InputStream input = context.openFileInput("data.txt");

            if (input != null) {
                InputStreamReader reader = new InputStreamReader(input);
                BufferedReader bufferedReader = new BufferedReader(reader);
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    String values[] = line.split(",");
                    Subscriptions s = new Subscriptions(values[1]);
                    s.setID(Integer.parseInt(values[0]));
                    s.setDate(values[2]);
                    s.setPrice(Double.parseDouble(values[3]));
                    s.setComment(values[4]);
                    subList.add(s);
                }
                input.close();
            }
        }
        catch (FileNotFoundException e) {
            Log.i("FNFERROR", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.i("IOERROR", "Can not read file: " + e.toString());
        }
        return subList;
    }

    public void saveSubArray(ArrayList<Subscriptions> subs) {
        try {
            OutputStreamWriter output = new OutputStreamWriter(context.openFileOutput("data.txt", Context.MODE_PRIVATE));
            for(Subscriptions s : subs){
                String line = Integer.toString(s.getID()) + "," + s.getName() + "," + s.getDate() + "," + Double.toString(s.getPrice()) + "," +  s.getComment() + ",\n";
                output.write(line);
            }
            output.close();
        }
        catch (IOException e) {
            Log.e("ExceptionWRITE", "File write failed: " + e.toString());
        }
    }
}



