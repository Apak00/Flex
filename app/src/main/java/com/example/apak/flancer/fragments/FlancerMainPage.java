package com.example.apak.flancer.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.example.apak.flancer.Entry.Messages_class;
import com.example.apak.flancer.models.DataofRecyclerview;
import com.example.apak.flancer.models.MyAdapter;
import com.example.apak.flancer.R;
import com.example.apak.flancer.models.UserInformation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FlancerMainPage.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FlancerMainPage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FlancerMainPage extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private DatabaseReference databaseReference;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Spinner Firstclass;
    private Spinner Secondclass;
    private Spinner Thirdclass;
    private Button button_browse;
    private ArrayList<String> FirstclassList=new ArrayList<String>();
    private ArrayList<String> F_Repair=new ArrayList<String>();
    private ArrayList<String> F_Cleaning=new ArrayList<String>();
    private ArrayList<String> F_Decoration=new ArrayList<String>();
    private ArrayList<String> F_Phorograph=new ArrayList<String>();
    private ArrayList<String> F_Course=new ArrayList<String>();
    private ArrayList<String> R_Vehicle=new ArrayList<String>();
    private ArrayList<String> R_Electronics=new ArrayList<String>();
    private ArrayList<String> R_WhiteGoods=new ArrayList<String>();
    private ArrayList<String> C_As_Group=new ArrayList<String>();
    private ArrayList<String> C_1_on_1=new ArrayList<String>();
    private ArrayList<String> C_Coach=new ArrayList<String>();
    private ArrayList<String> Jobs_of_user_list =new ArrayList<String>();
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private MyAdapter mAdapterofRecyclerView;
    private ArrayList<String> nameofworkers;
    private ArrayList<String> uriofworkers;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public FlancerMainPage() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FlancerMainPage.
     */
    // TODO: Rename and change types and number of parameters
    public static FlancerMainPage newInstance(String param1, String param2) {
        FlancerMainPage fragment = new FlancerMainPage();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        nameofworkers=new ArrayList<String>();
        uriofworkers=new ArrayList<String>();
        View view = inflater.inflate(R.layout.fragment_flancer_main_page,
                container, false);
        databaseReference= FirebaseDatabase.getInstance().getReference();
        button_browse=(Button)view.findViewById(R.id.button3_browse);
        mRecyclerView=(RecyclerView) view.findViewById(R.id.my_RecyclerView);
        final RelativeLayout relativeLayout =(RelativeLayout)view.findViewById(R.id.main_page_relative_layout);

        getActivity().setTitle("Flex");
        FirstclassList.add("Select a category");
        FirstclassList.add("Repair");
        FirstclassList.add("Course");
        FirstclassList.add("Cleaning");
        FirstclassList.add("Decoration");
        FirstclassList.add("Photograph");

        F_Repair.add("Select a category");
        F_Repair.add("Vehicle");
        F_Repair.add("Electronics");
        F_Repair.add("White Goods");
        F_Cleaning.add("Select a category");
        F_Cleaning.add("Kitchen");
        F_Cleaning.add("Complete Home");
        F_Cleaning.add("Office");
        F_Cleaning.add("Hotel");
        F_Cleaning.add("Toilet");
        F_Cleaning.add("Carpet");
        F_Decoration.add("Select a category");
        F_Decoration.add("Shower Cabin");
        F_Decoration.add("Lagging");
        F_Decoration.add("Dyeing");
        F_Decoration.add("Gardening");
        F_Phorograph.add("Select a category");
        F_Phorograph.add("Wedding Photographer");
        F_Phorograph.add("Architecture Photographer");
        F_Phorograph.add("Documental Photographer");
        F_Phorograph.add("Goods Photographer");
        F_Course.add("Select a category");
        F_Course.add("As Group");
        F_Course.add("1 on 1");
        F_Course.add("Coach");

        R_Electronics.add("Select a category");
        R_Electronics.add("Laptop");
        R_Electronics.add("TV");
        R_Vehicle.add("Select a category");
        R_Vehicle.add("Car");
        R_Vehicle.add("Motorcycle");
        R_WhiteGoods.add("Select a category");
        R_WhiteGoods.add("Refrigerator");
        R_WhiteGoods.add("Washing Machine");
        R_WhiteGoods.add("Dishwasher");
        C_1_on_1.add("Select a category");
        C_1_on_1.add("Math");
        C_1_on_1.add("Geometry");
        C_1_on_1.add("Physics");
        C_1_on_1.add("Biology");
        C_1_on_1.add("Chemistry");
        C_1_on_1.add("Language");
        C_As_Group.add("Select a category");
        C_As_Group.add("Math");
        C_As_Group.add("Geometry");
        C_As_Group.add("Physics");
        C_As_Group.add("Biology");
        C_As_Group.add("Chemistry");
        C_Coach.add("Select a category");
        C_Coach.add("Body Building");
        C_Coach.add("Dietitian");
        C_Coach.add("Esport");

        databaseReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Jobs_of_user")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                            if(!Jobs_of_user_list.contains(postSnapshot.getValue().toString())){
                                Jobs_of_user_list.add(postSnapshot.getValue().toString());}
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

        Firstclass=(Spinner)view.findViewById(R.id.FirstClassSpinnerofMainPage);
        Secondclass=(Spinner)view.findViewById(R.id.SecondCLassSpinnerofMainPage);
        Thirdclass=(Spinner)view.findViewById(R.id.ThirdClassSpinnerofMainPage);

        ArrayAdapter<String> Adapter_Firstclass = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, FirstclassList);
        Adapter_Firstclass.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Firstclass.setAdapter(Adapter_Firstclass);

        final ArrayAdapter<String> Adapter_Repair = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, F_Repair);
        Adapter_Repair.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        final ArrayAdapter<String> Adapter_Cleaning = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, F_Cleaning);
        Adapter_Cleaning.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        final ArrayAdapter<String> Adapter_Decoration = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, F_Decoration);
        Adapter_Decoration.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        final ArrayAdapter<String> Adapter_Photograph = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, F_Phorograph);
        Adapter_Photograph.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        final ArrayAdapter<String> Adapter_Course = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, F_Course);
        Adapter_Course.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        final ArrayAdapter<String> Adapter_R_Electronics = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, R_Electronics);
        Adapter_R_Electronics.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        final ArrayAdapter<String> Adapter_R_WhiteGoods = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, R_WhiteGoods);
        Adapter_R_WhiteGoods.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        final ArrayAdapter<String> Adapter_R_Vehicle = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, R_Vehicle);
        Adapter_R_Vehicle.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        final ArrayAdapter<String> Adapter_C_As_Group = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, C_As_Group);
        Adapter_C_As_Group.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        final ArrayAdapter<String> Adapter_C_1_on_1 = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, C_1_on_1);
        Adapter_C_1_on_1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        final ArrayAdapter<String> Adapter_C_Coach = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, C_Coach);
        Adapter_C_Coach.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);



        Secondclass.setVisibility(View.GONE);
        Thirdclass.setVisibility(View.GONE);

        Firstclass.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                Secondclass.setVisibility(View.GONE);
                Thirdclass.setVisibility(View.GONE);
                Secondclass.setSelection(0);
                Thirdclass.setSelection(0);
                Secondclass.setEnabled(false);
                if(Firstclass.getItemAtPosition(position)=="Repair"){
                    Secondclass.setEnabled(true);
                    Secondclass.setVisibility(View.VISIBLE);
                    Secondclass.setAdapter(Adapter_Repair);
                    relativeLayout.setBackgroundResource(R.drawable.repair_back);

                }
                else if(Firstclass.getItemAtPosition(position)=="Cleaning"){
                    Secondclass.setEnabled(false);
                    Thirdclass.setVisibility(View.VISIBLE);
                    Thirdclass.setAdapter(Adapter_Cleaning);
                }
                else if(Firstclass.getItemAtPosition(position)=="Decoration"){
                    Secondclass.setEnabled(false);
                    Thirdclass.setVisibility(View.VISIBLE);
                    Thirdclass.setAdapter(Adapter_Decoration);
                }
                else if(Firstclass.getItemAtPosition(position)=="Photograph"){
                    Secondclass.setEnabled(false);
                    Thirdclass.setVisibility(View.VISIBLE);
                    Thirdclass.setAdapter(Adapter_Photograph);
                }
                else if(Firstclass.getItemAtPosition(position)=="Course"){
                    Secondclass.setEnabled(true);
                    Secondclass.setVisibility(View.VISIBLE);
                    Secondclass.setAdapter(Adapter_Course);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                Secondclass.setVisibility(View.GONE);
                Thirdclass.setVisibility(View.GONE);
            }

        });
        Secondclass.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                Thirdclass.setEnabled(false);
                Thirdclass.setVisibility(View.GONE);
                Thirdclass.setSelection(0);
                if (Secondclass.getItemAtPosition(position)=="Vehicle") {
                    Thirdclass.setEnabled(true);
                    Thirdclass.setVisibility(View.VISIBLE);
                    Thirdclass.setAdapter(Adapter_R_Vehicle);
                } else if (Secondclass.getItemAtPosition(position)=="Electronics") {
                    Thirdclass.setVisibility(View.VISIBLE);
                    Thirdclass.setEnabled(true);
                    Thirdclass.setAdapter(Adapter_R_Electronics);
                } else if (Secondclass.getItemAtPosition(position) == "White Goods") {
                    Thirdclass.setVisibility(View.VISIBLE);
                    Thirdclass.setEnabled(true);
                    Thirdclass.setAdapter(Adapter_R_WhiteGoods);
                }else if(Secondclass.getItemAtPosition(position) == "As Group"){
                    Thirdclass.setVisibility(View.VISIBLE);
                    Thirdclass.setEnabled(true);
                    Thirdclass.setAdapter(Adapter_C_As_Group);
                }
                else if(Secondclass.getItemAtPosition(position) == "1 on 1"){
                    Thirdclass.setVisibility(View.VISIBLE);
                    Thirdclass.setEnabled(true);
                    Thirdclass.setAdapter(Adapter_C_1_on_1);
                }
                else if(Secondclass.getItemAtPosition(position) == "Coach"){
                    Thirdclass.setVisibility(View.VISIBLE);
                    Thirdclass.setEnabled(true);
                    Thirdclass.setAdapter(Adapter_C_Coach);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                Thirdclass.setVisibility(View.GONE);

            }

        });


        Thirdclass.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                try{
                    nameofworkers.clear();
                    uriofworkers.clear();
                    mAdapterofRecyclerView=new MyAdapter(getActivity(),retrieve(Thirdclass.getSelectedItem().toString()));
                }
            catch(NullPointerException e){
                System.out.println("hahaha got u:D");
            }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        button_browse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                mLayoutManager = new LinearLayoutManager(getActivity());
                mRecyclerView.setLayoutManager(mLayoutManager);
                mRecyclerView.setAdapter(mAdapterofRecyclerView);
                //handle onclick of recyclerview:
                mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), mRecyclerView, new ClickListener() {
                    @Override
                    public void onClick(View view, int position) {
                        if(FirebaseAuth.getInstance().getCurrentUser().getDisplayName()!=null){

                            System.out.println("here is the short position:"+position);
                            Intent i = new Intent(getActivity(), Messages_class.class);
//You have sent extras to the message_class acticity now: TODO:Handle extras in that activity and make chat custom between user and worker
                            i.putExtra("worker",nameofworkers.get(position));
                            i.putExtra("user", FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
                            System.out.println("here is the short position:"+position+FirebaseAuth.getInstance().getCurrentUser().getDisplayName()+nameofworkers.get(position));

                            startActivity(i);

                        }else{System.out.println("User has no DisplayName");}
                    }

                    @Override
                    public void onLongClick(View viev, int position) {
                        System.out.println("here is the long:"+position);

                    }
                }));
                // specify an adapter (see also next example)
                // use this setting to improve performance if you know that changes
                // in content do not change the layout size of the RecyclerView
                mRecyclerView.setHasFixedSize(true);

            }
        });
        // use a linear layout manager






        return view;
    }
    private void fetchData(DataSnapshot dataSnapshot)
    {boolean b=true;
        //nameofworkers.clear();

        while(b) {
            String[] parts = dataSnapshot.getValue().toString().split("s_p_l_i_t_u_p");
            if (!nameofworkers.contains(parts[0])){
            nameofworkers.add(parts[0]);
                uriofworkers.add(parts[1]);


        }
        else b=false;
        }
    }
    public DataofRecyclerview retrieve(String gg)
    {
        databaseReference.child("Jobs:").child(gg).addChildEventListener(new ChildEventListener() {
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
        return new DataofRecyclerview(uriofworkers,nameofworkers) ;
    }



    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(String s,int a) {
        if (mListener != null) {
            mListener.onFragmentInteraction(s,a);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(String s,int a);
    }
    class RecyclerTouchListener implements RecyclerView.OnItemTouchListener{
private GestureDetector gestureDetector;
        private ClickListener clickListener;
        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener) {
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
