package com.example.apak.flancer.Entry;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.apak.flancer.R;
import com.example.apak.flancer.models.ChatMessage;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.google.firebase.database.FirebaseDatabase.getInstance;

import com.firebase.ui.auth.AuthUI;

public class Messages_class extends AppCompatActivity {
    private DatabaseReference databaseReference;
    private FirebaseListAdapter<ChatMessage> adapter;
    private static int SIGN_IN_REQUEST_CODE = 1;
    private String worker,user;


    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == SIGN_IN_REQUEST_CODE)
        {
            if(resultCode == RESULT_OK)
            {
                displayChatMessage();
            }
            else{
                finish();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages_class);
        Intent i=getIntent();
        worker=i.getStringExtra("worker");
        user=i.getStringExtra("user");
        databaseReference=FirebaseDatabase.getInstance().getReference();

        FloatingActionButton fab =
                (FloatingActionButton)findViewById(R.id.floatingActionButton_tomessage);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText input = (EditText)findViewById(R.id.editText_tomessage);

                // Read the input field and push a new instance
                // of ChatMessage to the Firebase database

                    if(input.getText().toString().compareTo("")!=0){
                        System.out.println(input.getText().toString());
                getInstance()
                        .getReference()
                        .child("messages:")
                        .child(worker)
                        .child(user)
                        .push()
                        .setValue(new ChatMessage(input.getText().toString(),
                                FirebaseAuth.getInstance()
                                        .getCurrentUser()
                                        .getDisplayName(),FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl().toString())
                        );}


                // Clear the input
                input.setText("");

            }
        });
        if(FirebaseAuth.getInstance().getCurrentUser() == null)
        {
            startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder().build(),SIGN_IN_REQUEST_CODE);
        }
        else
        {

            displayChatMessage();
        }

    }

    private void displayChatMessage() {

        ListView listOfMessage = (ListView)findViewById(R.id.listview_tomessage);
        adapter = new FirebaseListAdapter<ChatMessage>(this,ChatMessage.class,R.layout.list_item,
                databaseReference.child("messages:").child(worker).child(user))
        {
            @Override
            protected void populateView(View v, ChatMessage model, int position) {

                //Get references to the views of list_item.xml
                TextView messageText, messageUser, messageTime;
                messageText = (TextView) v.findViewById(R.id.message_text);
                messageUser = (TextView) v.findViewById(R.id.message_user);
                messageTime = (TextView) v.findViewById(R.id.message_time);

                messageText.setText(model.getMessageText());
                messageUser.setText(model.getMessageUser());
                messageTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)", model.getMessageTime()));

            }
        };
        try{
        listOfMessage.setAdapter(adapter);}
        catch (NullPointerException e){
            System.out.println("burdayim");
        }
    }




}
