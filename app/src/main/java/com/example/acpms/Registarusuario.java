package com.example.acpms;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.acpms.Modelo.Persona;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class Registarusuario extends Fragment {



    private EditText nom, cel, contra, nuevacontra, email;
    private Spinner proce1, proce2;
    private Button BtnRegistrar;
    private TextView textotitulo;
    String[] area1 = {"Proceso 1", " Gestion de Calidad", "Gestion de Talento Humano", "Gestion de Seguridad y salud en el trabajo", "Gestion de Bienestar", "Gestion Academica", "Gestion Financiera", "Direccion Organizacional", "Area de Mercadeo", " Area de Educacion Continua"};
    String[] area2 = {"Proceso 2", " Gestion de Calidad", "Gestion de Talento Humano", "Gestion de Seguridad y salud en el trabajo", "Gestion de Bienestar", "Gestion Academica", "Gestion Financiera", "Direccion Organizacional", "Area de Mercadeo", " Area de Educacion Continua"};
    private String seleccionarea1, seleccionarea2;
    private  String no="";
    private  String ema="";
    private  String a1="";
    private  String a2="";
    private  String ce="";
    private  String ca="";
    private  String cf="";
    private  String mensaje="";

    FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference mDatabase;
    private RecyclerView mRecyclerView;
    public Registarusuario() {
        // Required empty public constructor
    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_registarusuario, container, false);
        final TextView textView = root.findViewById(R.id.txt_registrarusuario);

        mAuth = FirebaseAuth.getInstance();
        mDatabase=FirebaseDatabase.getInstance().getReference();

        //CODIGO REGISTRAR USUARIO
        nom = (EditText) root.findViewById(R.id.EdtNombreR);
        email = (EditText) root.findViewById(R.id.EdtemailR);
        proce1 = (Spinner) root.findViewById(R.id.Sarea1R);
        proce2 = (Spinner) root.findViewById(R.id.Sarea2R);
        cel = (EditText) root.findViewById(R.id.EdtPhoneR);
        contra = (EditText) root.findViewById(R.id.EdtcontrasenaR);
        nuevacontra = (EditText) root.findViewById(R.id.EdtcontrasenaConfirmacionR);

        BtnRegistrar = (Button) root.findViewById(R.id.BtnRegistrarUsuario);

        textotitulo = (TextView) root.findViewById(R.id.txt_Mensaje);

        //Spinner 1
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1, area1);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        proce1.setAdapter(arrayAdapter);
        //Spinner 2
        ArrayAdapter<String> adapterr = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, area2);
        adapterr.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        proce2.setAdapter(adapterr);

        String ssd = "";
        //Spinner 1
        proce1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                seleccionarea1 = proce1.getSelectedItem().toString();
                //              textotitulo.setText(String.valueOf(seleccionarea1));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        //Spinner 2
        proce2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                seleccionarea2 = proce2.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });


        BtnRegistrar.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                no = nom.getText().toString();
                ema = email.getText().toString();
                a1 = seleccionarea1;
                a2 = seleccionarea2;
                ce = cel.getText().toString();
                ca = contra.getText().toString();
                cf = nuevacontra.getText().toString();

                if(!no.isEmpty() && !ema.isEmpty()
                        && !ce.isEmpty() && !ca.isEmpty() && !cf.isEmpty()){
                    if(ca.length() >= 6 && cf.length() >= 6 ){
                        registerUser();
                        limpiarCajas();
                    }

                }
                else{
                    Toast.makeText(getActivity(),"Debe Completar Los Campos" ,Toast.LENGTH_SHORT).show();
                }
            }
        });

        return root;
    }



    public void registerUser(){
        mAuth.createUserWithEmailAndPassword(ema,ca).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    Persona p = new Persona();
                    p.setId(UUID.randomUUID().toString());
                    p.setNombrecom(no);
                    p.setEmail(ema);
                    p.setProceso1(a1);
                    p.setProceso2(a2);
                    p.setCelular(ce);
                    p.setContrasena(ca);
                    p.setNuevacontrasena(cf);

                    String idAuth=mAuth.getCurrentUser().getUid();

                    mDatabase.child("Persona").child(idAuth).setValue(p).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task2) {
                            if(task2.isSuccessful()){
                                Toast.makeText(getActivity(),"Usuario Registrado Exitosamente" ,Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                }
                else {
                    Toast.makeText(getActivity(),"No se pudo Registar Este Usuario ",Toast.LENGTH_SHORT).show();

                }

            }
        });

    }

    public void  limpiarCajas() {

        nom.setText("");
        email.setText("");
        cel.setText("");
        contra.setText("");
        nuevacontra.setText("");

    }
}