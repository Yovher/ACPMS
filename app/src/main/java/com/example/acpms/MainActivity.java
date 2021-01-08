package com.example.acpms;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    FirebaseAuth mAuth;
    FirebaseUser currentUser ;
    DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        // ini

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        mDatabase= FirebaseDatabase.getInstance().getReference();
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.nuevaacpms);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent i=new Intent(getApplicationContext(),RegistraracpmsActivity.class);
                startActivity(i);
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_perfil, R.id.nav_nuevaAcpm, R.id.nav_misAcpms, R.id.nav_listaAcpms, R.id.nav_registarusuario)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        final NavigationView mynavigationView= findViewById(R.id.nav_view);

/*
        mynavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                item.setCheckable(true);
                int menu_id = item.getItemId();
                switch (menu_id){

                    case R.id.nav_perfil:
                        Toast.makeText(getApplicationContext(),"Perfil",Toast.LENGTH_SHORT).show();
                        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,new Perfil()).commit();
                        break;
                    case R.id.nav_nuevacpms:
                        Toast.makeText(getApplicationContext(),"Nueva ACPM",Toast.LENGTH_SHORT).show();
                        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,new NuevaAcpm()).commit();
                        break;

                    case R.id.nav_misAcpms:
                        Toast.makeText(getApplicationContext(),"Mis ACPMS",Toast.LENGTH_SHORT).show();
                        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,new MisAcpms()).commit();
                        break;

                    case R.id.nav_listaAcpms:
                        Toast.makeText(getApplicationContext(),"Lista de ACPMS",Toast.LENGTH_SHORT).show();
                        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,new ListaAcpms()).commit();
                        break;

                    case R.id.nav_registarusuario:
                        Toast.makeText(getApplicationContext(),"Registrar Usuario",Toast.LENGTH_SHORT).show();
                        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,new Registarusuario()).commit();
                        break;

                    case R.id.nav_signout:
                        mAuth.signOut();
                        startActivity(new Intent(MainActivity.this, Inicio_sesion.class ));
                        finish();
                        Toast.makeText(getApplicationContext(),"Sesión Cerrada",Toast.LENGTH_SHORT).show();
                        break;


                }
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawers();
                return true;
            }
        });*/


        updateNavHeader();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void updateNavHeader() {

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = headerView.findViewById(R.id.tvUserName);
        TextView navUserMail = headerView.findViewById(R.id.tvEmail);
        ImageView navUserPhot = headerView.findViewById(R.id.tvImageView);

        // navUsername.setText("Bienvenido "+currentUser.getDisplayName());
        // navUserMail.setText(currentUser.getEmail());
        String idAuth= mAuth.getCurrentUser().getUid();
        mDatabase.child("Persona").child(idAuth).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               // if(dataSnapshot.exists()){
                    String name=dataSnapshot.child("nombrecom").getValue().toString();
                    String email=dataSnapshot.child("email").getValue().toString();


                    navUsername.setText(name);
                    navUserMail.setText(email);


                //}

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        // now we will use Glide to load user image
        // first we need to import the library

        Glide.with(this).load(currentUser.getPhotoUrl()).into(navUserPhot);
    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.cerra_sesion) {
            mAuth.signOut();
            startActivity(new Intent(MainActivity.this, Inicio_sesion.class ));
            finish();
            Toast.makeText(getApplicationContext(),"Sesión Cerrada",Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }







}