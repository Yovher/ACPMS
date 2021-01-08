package com.example.acpms;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class Resta_contrasena extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private ProgressDialog progreso;
    private String gmail;
     private EditText correo;
    private Button enviar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resta_contrasena);

        mAuth = FirebaseAuth.getInstance();
        correo= findViewById(R.id.et_correo_cont);
        enviar=findViewById(R.id.btn_restablecer);
        progreso = new ProgressDialog(this);

        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 gmail=correo.getText().toString();
                if(!gmail.isEmpty()){
                    progreso.setMessage("Espera un momento");
                    progreso.setCanceledOnTouchOutside(false);
                    progreso.show();
                    enviarCorreo();
                }else{
                    Toast.makeText(getApplicationContext(),"No se pudo enviar correo",Toast.LENGTH_SHORT).show();
                }




            }
        });

    }


    public  void enviarCorreo(){

        mAuth.sendPasswordResetEmail(gmail).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(getApplicationContext(),"Revise su Correo para restablecer su contraseña",Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(Resta_contrasena.this, MainActivity.class);
                        startActivity(i);
                        finish();
                    }else{
                        Toast.makeText(getApplicationContext(),"no se pudo enviar el correo",Toast.LENGTH_SHORT).show();
                    }
            }
        });

    }
}