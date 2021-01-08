package com.example.acpms;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.acpms.Modelo.Persona;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class Perfil extends Fragment {

    private EditText nombrecom, celular, contrasena, contrasenaconf;
    private Spinner proceso1, proceso2;
    private Button BtnActualizar;
    private TextView textotitulo;
    String[] area1 = {"Proceso 1", " Gestion de Calidad", "Gestion de Talento Humano", "Gestion de Seguridad y salud en el trabajo", "Gestion de Bienestar", "Gestion Academica", "Gestion Financiera", "Direccion Organizacional", "Area de Mercadeo", " Area de Educacion Continua"};
    String[] area2 = {"Proceso 2", " Gestion de Calidad", "Gestion de Talento Humano", "Gestion de Seguridad y salud en el trabajo", "Gestion de Bienestar", "Gestion Academica", "Gestion Financiera", "Direccion Organizacional", "Area de Mercadeo", " Area de Educacion Continua"};
    public String seleccionarea1,seleccionarea2;
    private  String a1="";
    private  String a2="";

    FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference mDatabase,mDatabase2;



    ListView listV_personas;
    Persona personaSelected;
    public Perfil() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_perfil, container, false);

        final TextView textView = view.findViewById(R.id.text_perfil);
        nombrecom = (EditText) view.findViewById(R.id.editTextTextNombre);
        celular = (EditText) view.findViewById(R.id.editTextPhone);

        proceso1 = (Spinner) view.findViewById(R.id.area1);
        proceso2 = (Spinner) view.findViewById(R.id.area2);
        textotitulo = (TextView) view.findViewById(R.id.text_perfil);
        BtnActualizar = (Button) view.findViewById(R.id.buttonActualizar);

        mAuth = FirebaseAuth.getInstance();
        mDatabase=FirebaseDatabase.getInstance().getReference();
        mDatabase2=FirebaseDatabase.getInstance().getReference();

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1, area1);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        proceso1.setAdapter(arrayAdapter);

        ArrayAdapter<String> adapterr = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, area2);
        adapterr.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        proceso2.setAdapter(adapterr);

       /* listV_personas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                personaSelected = (Persona) parent.getItemAtPosition(position);
                nombrecom.setText(personaSelected.getNombrecom());
                celular.setText(personaSelected.getCelular());
               a1 = seleccionarea1;
                a2 = seleccionarea2;

            }
        });*/


        proceso1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                seleccionarea1 =proceso1.getSelectedItem().toString();
                //              textotitulo.setText(String.valueOf(seleccionarea1));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        proceso2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                seleccionarea2 =proceso2.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        getUserInfo();

        BtnActualizar.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {


                String id=mAuth.getCurrentUser().getUid();
                mDatabase.child("Persona").child(id).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){

                            a1 = seleccionarea1;
                            a2 = seleccionarea2;
                            Persona p = new Persona();
                            String idAuth=mAuth.getCurrentUser().getUid();
                            p.setId(snapshot.child("id").getValue().toString());
                            p.setNombrecom(nombrecom.getText().toString().trim());
                            p.setEmail(snapshot.child("email").getValue().toString());
                            p.setCelular(celular.getText().toString().trim());
                            p.setContrasena(snapshot.child("contrasena").getValue().toString());
                            p.setNuevacontrasena(snapshot.child("nuevacontrasena").getValue().toString());
                            p.setProceso1(a1);
                            p.setProceso2(a2);

                            mDatabase.child("Persona").child(idAuth).setValue(p);
                            Toast.makeText(getContext(),"Actualizado", Toast.LENGTH_LONG).show();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }

        });


        return view;
    }


    private void getUserInfo(){

        String id=mAuth.getCurrentUser().getUid();
        final List<Persona> personas= new ArrayList<>();
        mDatabase2.child("Persona").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){

                    String name=snapshot.child("nombrecom").getValue().toString();
                    String phone=snapshot.child("celular").getValue().toString();
                    String proce1 =snapshot.child("proceso1").getValue().toString();
                    String proce2=snapshot.child("proceso2").getValue().toString();

                    nombrecom.setText(name);
                    celular.setText(phone);
                    proceso1.setSelection(obtenerPosicionItem(proceso1,proce1));
                    proceso2.setSelection(obtenerPosicionItem(proceso2,proce2));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    //Método para obtener la posición de un ítem del spinner
    public static int obtenerPosicionItem(Spinner spinner, String Proceso) {
        //Creamos la variable posicion y lo inicializamos en 0
        int posicion = 0;
        //Recorre el spinner en busca del ítem que coincida con el parametro `String fruta`
        //que lo pasaremos posteriormente
        for (int i = 0; i < spinner.getCount(); i++) {
            //Almacena la posición del ítem que coincida con la búsqueda
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(Proceso)) {
                posicion = i;
            }
        }
        //Devuelve un valor entero (si encontro una coincidencia devuelve la
        // posición 0 o N, de lo contrario devuelve 0 = posición inicial)
        return posicion;
    }



}