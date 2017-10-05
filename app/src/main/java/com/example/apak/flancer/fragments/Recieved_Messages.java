package com.example.apak.flancer.fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.example.apak.flancer.Entry.Messages_class;
import com.example.apak.flancer.R;
import com.example.apak.flancer.models.ChatMessage;
import com.example.apak.flancer.models.DataofRecyclerview;
import com.example.apak.flancer.models.MyAdapter;
import com.example.apak.flancer.models.UserInformation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class Recieved_Messages extends Fragment {
    private ArrayList<String> nameofusers;
    private ArrayList<String> uriofusers;
    private DatabaseReference databaseReference;
    private MyAdapter mAdapterofRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView rv;
    private Button button_browse_messages;
    private ChatMessage chatMessage;
    public Recieved_Messages() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_recieved__messages, container, false);
        rv=(RecyclerView) view.findViewById(R.id.Recieved_messages_rv);
        nameofusers=new ArrayList<String>();
        uriofusers=new ArrayList<String>();
        databaseReference= FirebaseDatabase.getInstance().getReference();
        getActivity().setTitle("Messages");

        button_browse_messages=(Button)view.findViewById(R.id.button_browse_messages);
        button_browse_messages.setOnClickListener(new View.OnClickListener() {
                @Override
            public void onClick(View view) {
                mLayoutManager = new LinearLayoutManager(getActivity());
                rv.setLayoutManager(mLayoutManager);
                rv.setAdapter(mAdapterofRecyclerView);

                rv.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), rv, new FlancerMainPage.ClickListener() {
                    @Override
                    public void onClick(View view, int position) {
                        Intent i = new Intent(getActivity(), Messages_class.class);
                        i.putExtra("worker",FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
                        i.putExtra("user",nameofusers.get(position));
                        startActivity(i);
                    }

                    @Override
                    public void onLongClick(View viev, int position) {

                    }
                }));
            }
        });
        try{
            uriofusers.clear();
            nameofusers.clear();
            mAdapterofRecyclerView=new MyAdapter(getActivity(),retrieve(FirebaseAuth.getInstance().getCurrentUser().getDisplayName()));
        }
        catch(NullPointerException e){
            System.out.println("hahaha got u:D");
        }


        return view;
    }
    public DataofRecyclerview retrieve(String gg)
    {
        databaseReference.child("messages:")
                .child(gg)
                .addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                fetchData(dataSnapshot);

            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                fetchData(dataSnapshot);

            }
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        return new DataofRecyclerview(uriofusers,nameofusers) ;
    }
    private void fetchData(DataSnapshot dataSnapshot)
    {

        boolean b=true;
        while(b) {
            String[] parts = dataSnapshot.getKey().toString().split("s_p_l_i_t_u_p");
            chatMessage = dataSnapshot.getValue(ChatMessage.class);
            System.out.println(dataSnapshot.getChildren().toString());
            System.out.println(dataSnapshot.getValue(ChatMessage.class));

            if (!nameofusers.contains(parts[0])){
                nameofusers.add(parts[0]);
                try{
                uriofusers.add(chatMessage.getMessageUri());
                System.out.println(chatMessage.getMessageUri());
                }
                catch (NullPointerException e){System.out.println("Hahaha Got u:D");}
            }
            else b=false;
        }

    }
    private class RecyclerTouchListener implements RecyclerView.OnItemTouchListener{
        private GestureDetector gestureDetector;
        private FlancerMainPage.ClickListener clickListener;
        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final FlancerMainPage.ClickListener clickListener) {
            this.clickListener=clickListener;
            gestureDetector=new GestureDetector(context,new GestureDetector.SimpleOnGestureListener(){
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    super.onLongPress(e);
                    View child=recyclerView.findChildViewUnder(e.getX(),e.getY());
                    if(child!=null &&clickListener!=null){
                        clickListener.onLongClick(child,recyclerView.getChildLayoutPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View child=rv.findChildViewUnder(e.getX(),e.getY());
            if(child!=null &&clickListener!=null &&gestureDetector.onTouchEvent(e)){
                clickListener.onClick(child,rv.getChildLayoutPosition(child));
            }

            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }
    public static interface ClickListener{
        public void onClick(View view,int position);
        public void onLongClick(View viev,int position);
    }

}
