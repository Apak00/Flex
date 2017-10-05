package com.example.apak.flancer.Entry;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apak.flancer.fragments.About_Us;
import com.example.apak.flancer.fragments.FlancerMainPage;
import com.example.apak.flancer.fragments.JobFragment;
import com.example.apak.flancer.fragments.ProfileFragment;
import com.example.apak.flancer.R;
import com.example.apak.flancer.fragments.Recieved_Messages;
import com.example.apak.flancer.models.UserInformation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import static com.example.apak.flancer.R.id.nav_view;

public class FlancerMain extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener ,
        View.OnClickListener,
        ProfileFragment.OnFragmentInteractionListener,
        JobFragment.OnFragmentInteractionListener,
        FlancerMainPage.OnFragmentInteractionListener
{
    private FirebaseAuth firebaseAuth;
    private TextView twemailofuser;
    private String name;
    private String phone;
    private ImageView main_user_pic;
    private UserInformation userInformation;
    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flancer_main);
        firebaseAuth =FirebaseAuth.getInstance();
        databaseReference= FirebaseDatabase.getInstance().getReference();
        FragmentManager manager =getSupportFragmentManager();
        FlancerMainPage flancerMainPage=new FlancerMainPage();


        manager.beginTransaction().replace(R.id.content_flancer_main,flancerMainPage,flancerMainPage.getTag()).commit();


        if(firebaseAuth.getCurrentUser()==null){
            finish();
            startActivity(new Intent(this,LoginActivity.class));
        }else{
            try {
                Toast.makeText(this,
                "Welcome " + FirebaseAuth.getInstance()
                        .getCurrentUser()
                        .getDisplayName(),
                Toast.LENGTH_LONG)
                .show();}
        catch(java.lang.NullPointerException e){
            Toast.makeText(this,
                    "Welcome Anonymous",
                    Toast.LENGTH_LONG)
                    .show();
        }}

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View navheaderView = navigationView.getHeaderView(0);
        twemailofuser=(TextView)navheaderView.findViewById(R.id.twemailofuser);
        main_user_pic=(ImageView)navheaderView.findViewById(R.id.main_user_pic);

        FirebaseUser user=firebaseAuth.getCurrentUser();
        if (user != null) {
            twemailofuser.setText(user.getEmail());
            if(user.getPhotoUrl()!=null){
                databaseReference.child(user.getUid()).child("uri").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        Picasso.with(FlancerMain.this)
                                .load(Uri.parse(dataSnapshot.getValue().toString()))
                                .resize(80,80)
                                .transform(new CircleTransform())
                                .into(main_user_pic);
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }


        }

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else if (getFragmentManager().getBackStackEntryCount() > 0 ){
                getFragmentManager().popBackStack();
            } else {
                super.onBackPressed();
            }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.flancer_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        FragmentManager manager =getSupportFragmentManager();
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_About_Us) {
            About_Us about_us=new About_Us();
            manager.beginTransaction().replace(R.id.content_flancer_main,about_us,about_us.getTag()).addToBackStack(null).commit();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentManager manager =getSupportFragmentManager();
        if (id == R.id.nav_profile) {
            ProfileFragment profilefragment=ProfileFragment.newInstance(twemailofuser.getText().toString());
            manager.beginTransaction().replace(R.id.content_flancer_main,profilefragment,profilefragment.getTag()).addToBackStack(null).commit();
            // Handle the camera action
        } else if (id == R.id.nav_logout) {
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(this,LoginActivity.class));
        } else if (id==R.id.nav_job){
            JobFragment jobFragment=new JobFragment();
            manager.beginTransaction().replace(R.id.content_flancer_main,jobFragment,jobFragment.getTag()).addToBackStack(null).commit();
        } else if (id==R.id.nav_main_menu){
            finish();
            startActivity(new Intent(this,FlancerMain.class));
        } else if(id==R.id.nav_Recieved){
            Recieved_Messages recieved_messages=new Recieved_Messages();
            manager.beginTransaction().replace(R.id.content_flancer_main,recieved_messages,recieved_messages.getTag()).addToBackStack(null).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(String s,int a) {
        if(a==2){
            Uri myimageuri=Uri.parse(s);
            //take profil pic to firebase
        }else if(a==3){
            phone=s;
           //take phone number to firebase
        }else if(a==1){
            name=s;
            //take user name to firebase
        }else if(a==4){

        }
    }

    @Override
    public void onClick(View view) {


    }



    static public class CircleTransform implements Transformation {
        @Override
        public Bitmap transform(Bitmap source) {
            int size = Math.min(source.getWidth(), source.getHeight());

            int x = (source.getWidth() - size) / 2;
            int y = (source.getHeight() - size) / 2;

            Bitmap squaredBitmap = Bitmap.createBitmap(source, x, y, size, size);
            if (squaredBitmap != source) {
                source.recycle();
            }

            Bitmap bitmap = Bitmap.createBitmap(size, size, source.getConfig());

            Canvas canvas = new Canvas(bitmap);
            Paint paint = new Paint();
            BitmapShader shader = new BitmapShader(squaredBitmap,
                    BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
            paint.setShader(shader);
            paint.setAntiAlias(true);

            float r = size / 2f;
            canvas.drawCircle(r, r, r, paint);

            squaredBitmap.recycle();
            return bitmap;
        }

        @Override
        public String key() {
            return "circle";
        }
    }
    private class DownloadFilesTask extends AsyncTask<String, Integer, Bitmap> {
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
                System.out.println("there is an IO EXCEPTION");
            }
            return bm;
        }

        protected void onProgressUpdate(Integer... progress) {

        }

        protected void onPostExecute(Bitmap result) {



        }
    }
    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }
}
