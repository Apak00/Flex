package com.example.apak.flancer.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apak.flancer.Manifest;
import com.example.apak.flancer.R;
import com.example.apak.flancer.models.UserInformation;
import com.example.apak.flancer.Entry.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Downloader;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import static android.app.Activity.RESULT_OK;

public class ProfileFragment extends Fragment implements View.OnClickListener{
    private DatabaseReference databaseReference;
    private StorageReference picStorage;

    private final String TAG=this.getClass().getSimpleName();
    // TODO: Rename parameter arguments, choose names that match
    private static final String ARG_PARAM1 = "param1";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private EditText profil_user_email;
    private EditText profil_user_name;
    private ImageView profil_user_pic;
    private static final int RESULT_LOAD_IMAGE=1;
    private EditText profil_user_phone;
    private Button saveButton;
    private UserInformation userInformation;
    private Uri downloadUri;
    private ProgressDialog pDialog;

    private OnFragmentInteractionListener mListener;

    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance(String param1) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        picStorage= FirebaseStorage.getInstance().getReference();

        databaseReference= FirebaseDatabase.getInstance().getReference();
        try{getActivity().setTitle("Profile "+FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
        }
        catch(NullPointerException e){
            getActivity().setTitle("Profile");
        }
        View view = inflater.inflate(R.layout.fragment_profile,
                container, false);
        saveButton=(Button)view.findViewById(R.id.button_save_profile);
        saveButton.setOnClickListener(this);
        profil_user_pic=(ImageView)view.findViewById(R.id.imageView5);
        profil_user_pic.setOnClickListener(this);
        profil_user_email=(EditText) view.findViewById(R.id.profil_frag_user_email);
        profil_user_email.setText(mParam1);
        profil_user_name=(EditText) view.findViewById(R.id.profil_frag_user_name);
        profil_user_phone=(EditText)view.findViewById(R.id.profil_frag_user_phone);

        //retrieve user profile informations:

        ValueEventListener user_informationlistener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                userInformation = dataSnapshot.getValue(UserInformation.class);
                //set retrieved info to UI
                try {
                    profil_user_phone.setText(userInformation.phone);
                    profil_user_name.setText(userInformation.name);
                    profil_user_email.setText(userInformation.email);
                    new DownloadFilesTask().execute(userInformation.uri);

                }catch(NullPointerException e){
System.out.println("ha ha I got u:D");
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




        onButtonPressed(profil_user_phone.toString(),3);
        onButtonPressed(profil_user_name.toString(),1);
        return view;
    }

    private void saveUserInformation(){
        try{
        String name =profil_user_name.getText().toString().trim();
        String email=profil_user_email.getText().toString().trim();
        String phone =profil_user_phone.getText().toString().trim();
        String myuri=downloadUri.toString().trim();
        userInformation=new UserInformation(name,phone,email,myuri);
        databaseReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(userInformation);
            Toast.makeText(getActivity(), "User information is saved",
                    Toast.LENGTH_SHORT).show();
        }
        catch (NullPointerException e){
            Toast.makeText(getActivity(), "Try Again",
                    Toast.LENGTH_SHORT).show();
        }

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

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imageView5:
                saveButton.setEnabled(false);
                Intent galleryintent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryintent,RESULT_LOAD_IMAGE);

                break;
            case R.id.button_save_profile:
                if(FirebaseAuth.getInstance().getCurrentUser()!=null){
                saveUserInformation();
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                            .setDisplayName(profil_user_name.getText().toString().trim())
                            .build();

                    user.updateProfile(profileUpdates)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {

                                    }
                                }
                            });
                }
                else{
                    startActivity(new Intent(getActivity(),LoginActivity.class));
                    //handle if there is anonymous user or no user
                }
                break;
        }

    }
    

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==RESULT_LOAD_IMAGE && resultCode==RESULT_OK && data != null){
            Uri selectedImage=data.getData();
             profil_user_pic.setImageURI(selectedImage);
            StorageReference filepath=picStorage.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Photos").child(selectedImage.getLastPathSegment());
            filepath.putFile(selectedImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    downloadUri = taskSnapshot.getDownloadUrl();
                    try{onButtonPressed(downloadUri.toString(),4);
                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                .setPhotoUri(downloadUri)
                                .build();
                        FirebaseAuth.getInstance().getCurrentUser().updateProfile(profileUpdates);


                        Toast.makeText(getActivity(),"Upload is done",Toast.LENGTH_SHORT).show();
                        saveButton.setEnabled(true);}
                    catch (NullPointerException e){System.out.println("haha got u:D");}
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    // handle if image upload failed
                }
            });
            onButtonPressed(selectedImage.toString(),2);
        }

    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(String s,int a);
    }

   public class DownloadFilesTask extends AsyncTask<String, Integer, Bitmap> {
       protected Bitmap doInBackground(String... urls) {
           Bitmap bm = null;
           try {
               URL aURL = new URL(urls[0]);
               URLConnection conn = aURL.openConnection();
               conn.connect();
               InputStream is = conn.getInputStream();
               BufferedInputStream bis = new BufferedInputStream(is);
               bm = BitmapFactory.decodeStream(bis);
               bis.close();
               is.close();
           } catch (IOException e) {
               Log.e(TAG, "Error getting bitmap", e);
           }
           return bm;
       }

       protected void onProgressUpdate(Integer... progress) {

       }

       protected void onPostExecute(Bitmap result) {
           profil_user_pic.setImageBitmap(result);
           System.out.println(result);
       }
   }


}
