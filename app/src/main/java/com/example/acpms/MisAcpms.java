package com.example.acpms;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.acpms.Modelo.Acpms;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MisAcpms extends Fragment {

    FirebaseDatabase miBase;
    DatabaseReference miReferencia;
    FirebaseAuth miAuth;
    String ActualUsuarioID;

    //AdapterAcpm adapterAcpm;
    RecyclerView recyclerAcpms;
    ArrayList<Acpms> listaAcpms;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mis_acpms, container, false);

        miBase = FirebaseDatabase.getInstance();
        miReferencia = miBase.getReference().child("Acpms");
        miAuth = FirebaseAuth.getInstance();
        ActualUsuarioID = miAuth.getCurrentUser().getUid();

        recyclerAcpms = view.findViewById(R.id.rvMisapms);
        recyclerAcpms.setLayoutManager(new LinearLayoutManager(getContext()));

        listaAcpms = new ArrayList<>();


        FirebaseRecyclerOptions<Acpms> options =
                new FirebaseRecyclerOptions.Builder<Acpms>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Acpms"), Acpms.class)
                        .build();

        //adapterAcpm =new AdapterAcpm(options);
        //recyclerAcpms.setAdapter(adapterAcpm);
        //cargarLista();
        // mostrarLista();

        return view;

    }

    private void mostrarLista() {

    }

    private void cargarLista() {

        listaAcpms.add(new Acpms());
    }

    @Override
    public void onStart() {
        super.onStart();
        //adapterAcpm.startListening();

        /*FirebaseRecyclerAdapter<Acpms,AcpmsViewHolder>adaptador = new FirebaseRecyclerAdapter<Acpms, AcpmsViewHolder>() {
            @Override
            protected void onBindViewHolder(@NonNull AcpmsViewHolder holder, int position, @NonNull Acpms model) {
                String userID=
            }

            @NonNull
            @Override
            public AcpmsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.lista_misacpms,viewGroup,false);
                  AcpmsViewHolder viewHolder=new AcpmsViewHolder(view);
                  return viewHolder;
            }
        };*/

    }

    @Override
    public void onStop() {
        super.onStop();
       // adapterAcpm.stopListening();
    }

    /*public static class AcpmsViewHolder extends RecyclerView.ViewHolder {
        TextView tvestado, tvdescri;
        ImageView imgenacpm;
        public AcpmsViewHolder(@NonNull View itemView) {
            super(itemView);

            tvdescri=itemView.findViewById(R.id.descripcion);
            tvestado=itemView.findViewById(R.id.estado);
            imgenacpm=itemView.findViewById(R.id.Imagen_acpm);
        }
    }*/
}