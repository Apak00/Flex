package com.example.apak.flancer.models;

import android.net.Uri;

import java.net.URI;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Apak on 06/05/2017.
 */

public class DataofRecyclerview {

    ArrayList<String> workericon;
    ArrayList<String> workername;

    public DataofRecyclerview() {

    }
    public DataofRecyclerview(ArrayList<String> workericon,ArrayList<String> workername){
        this.workericon=workericon;
        this.workername=workername;
    }


    public ArrayList<String> getWorkericon() {
        return workericon;
    }

    public ArrayList<String> getWorkername() {
        return workername;
    }

    public void setWorkericon(ArrayList<String> workericon) {this.workericon = workericon;
    }

    public void setWorkername(ArrayList<String> workername) {
        this.workername = workername;
    }
}
