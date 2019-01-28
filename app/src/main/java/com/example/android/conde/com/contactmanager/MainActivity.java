package com.example.android.conde.com.contactmanager;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.List;

import Data.DatabaseHandler;
import Model.Contact;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DatabaseHandler db = new DatabaseHandler(this);
        //Insert contacts
        db.addContact(new Contact("Derp Derpity", "3475555555"));
        db.addContact(new Contact("John Johnson", "7183948678"));
        db.addContact(new Contact("Michael Michelson",  "3479909990"));
        db.addContact(new Contact("Jack Jackson", "3476733903"));
        db.addContact(new Contact("Derpity Derp", "3475555555"));
        Log.d("COUNT", "Count: "+db.getContactsCount());

        //Get 1 contact
        //Contact oneContact = db.getContact(2);
//        oneContact.setName("jon jonson");
//        oneContact.setPhoneNumber("7189993456");
//        //Log.d("ONE","Name: "+ oneContact.getName());
//        //updated contact (in this case the first)
//        int newContact = db.updateContact(oneContact);
//        Log.d("UPDATE", "ID: "+newContact+" Name: "+oneContact.getName());

       // db.deleteContact(oneContact);
        List<Contact> contactList = db.getAllContacts();
        for(Contact c : contactList){
            Log.d("LOOP", "ID: "+c.getId()+" Name: "+c.getName()+" Number: "+c.getPhoneNumber());
        }
    }
}
