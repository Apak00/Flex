package com.example.apak.flancer.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.apak.flancer.R;
import com.example.apak.flancer.models.UserInformation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link JobFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link JobFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class JobFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private DatabaseReference databaseReference;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Button Register_job;
    private Spinner Firstclass;
    private Spinner Secondclass;
    private Spinner Thirdclass;
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
    private EditText AdditionalInformation;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public JobFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static JobFragment newInstance(String param1, String param2) {
        JobFragment fragment = new JobFragment();
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


        View view = inflater.inflate(R.layout.fragment_job,
                container, false);
        databaseReference= FirebaseDatabase.getInstance().getReference();

        AdditionalInformation =(EditText)view.findViewById(R.id.etAdditional_info);
        Register_job=(Button)view.findViewById(R.id.Register_job);
        getActivity().setTitle("Job Registration");

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

        Firstclass=(Spinner)view.findViewById(R.id.FirstClassSpinner);
        Secondclass=(Spinner)view.findViewById(R.id.SecondCLassSpinner);
        Thirdclass=(Spinner)view.findViewById(R.id.ThirdClassSpinner);

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


        // Inflate the layout for this fragment
Register_job.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        //databaseReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Jobs_of_user").setValue(null);
        if(Thirdclass.getItemAtPosition(Thirdclass.getSelectedItemPosition())!=null
                && Thirdclass.getItemAtPosition(Thirdclass.getSelectedItemPosition())!="Select a category"){


            if(!Jobs_of_user_list.contains(Thirdclass.getItemAtPosition(Thirdclass.getSelectedItemPosition()).toString())){

                DatabaseReference mykey=databaseReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Jobs_of_user").push();
                final DatabaseReference Jobskey=databaseReference.child("Jobs:")
                        .child(Thirdclass.getItemAtPosition(Thirdclass.getSelectedItemPosition()).toString())
                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                if(FirebaseAuth.getInstance().getCurrentUser().getDisplayName()!=null){
                //Jobskey.setValue(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());}
                   ValueEventListener user_informationlistener = new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            try{
                            // Get Post object and use the values to update the UI
                            UserInformation userInformation = dataSnapshot.getValue(UserInformation.class);
                            //set retrieved info to UI
                            Jobskey.setValue(userInformation.name+"s_p_l_i_t_u_p"+FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl().toString());

                                Toast.makeText(getActivity(), "Registered "+userInformation.name,
                                        Toast.LENGTH_SHORT).show();}
                            catch (NullPointerException e){

                                Toast.makeText(getActivity(), "Registration to "
                                                +Thirdclass.getItemAtPosition(Thirdclass.getSelectedItemPosition()).toString()
                                                +" Failed!",
                                        Toast.LENGTH_SHORT).show();
                                        }
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            // Getting Post failed, log a message
                            Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                            // ...
                        }
                    };
                    databaseReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(user_informationlistener);
                }
                    else{
                    Toast.makeText(getActivity(), "Set your profile name", Toast.LENGTH_SHORT).show();
                    // TODO : error occured if profile name is not set then email is removed while transaction...
                    /*FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(((ViewGroup)getView().getParent()).getId(), new ProfileFragment(), "NewFragmentTag");
                    ft.addToBackStack(null);
                    ft.commit();*/

                }
                mykey.setValue(Thirdclass.getItemAtPosition(Thirdclass.getSelectedItemPosition()));
                //TODO _______________Handle the Additional information_______:
               // mykey.child((Thirdclass.getItemAtPositi6on(Thirdclass.getSelectedItemPosition())).toString())
                //    .setValue(AdditionalInformation.getText().toString());
            }
        }else{
            Toast.makeText(getActivity(), "You should select a job", Toast.LENGTH_SHORT).show();

        }
    }
});
        return view;
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


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(String s,int a);
    }
}
