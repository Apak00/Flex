package com.example.apak.flancer.models;

import android.net.Uri;

import java.util.ArrayList;

/**
 * Created by Apak on 15/03/2017.
 */

public class UserInformation {
    public String name ;
    public String phone;
    public String email;
    public String uri;

    public UserInformation() {
    }

    public UserInformation(String name, String phone, String email, String uri){
        this.uri=uri;
        this.email=email;
        this.name=name;
        this.phone=phone;
    }

}
