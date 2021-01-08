package com.example.acpms;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.acpms.Modelo.Acpms;
import com.example.acpms.Modelo.Persona;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

import id.zelory.compressor.Compressor;

public class RegistraracpmsActivity extends AppCompatActivity {

    ImageView foto;
    Button subir;
    ImageButton seleccionar;
    ProgressDialog cargando;
    EditText descripcion;
    public String[] tipoacom = {"Seleccione", "correctiva", "Preventiva", "Mejora"};
    Bitmap thumb_bitmap = null;
    Spinner tipo;
    String selecciontipo;
    private  String tipoacpm="";

   // DatabaseReference imgref;
    StorageReference storageReference;
    FirebaseAuth mAuth;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registraracpms);

        storageReference = FirebaseStorage.getInstance().getReference().child("Fotos_Acpms");
        //imgref = FirebaseDatabase.getInstance().getReference().child("img_comprimido");
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        foto = findViewById(R.id.img_foto);
        seleccionar = findViewById(R.id.btn_selefoto);
        subir = findViewById(R.id.btn_subirfoto);

        cargando = new ProgressDialog(this);
        descripcion = findViewById(R.id.editTextDescripcion);
        tipo = findViewById(R.id.StipoAcpm);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this.getApplicationContext(), android.R.layout.simple_list_item_1, tipoacom);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tipo.setAdapter(arrayAdapter);

        tipo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                selecciontipo = tipo.getSelectedItem().toString();


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        seleccionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.startPickImageActivity(RegistraracpmsActivity.this);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

       if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Uri imagenuri = CropImage.getPickImageResultUri(this, data);
            //recortar Imagen
            CropImage.activity(imagenuri).setGuidelines(CropImageView.Guidelines.ON).start(RegistraracpmsActivity.this);
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                File url = new File(resultUri.getPath());

                Picasso.with(this).load(url).into(foto);
                //comprimiendo Imagen------

                try {
                    thumb_bitmap = new Compressor(this).
                            setMaxWidth(700).
                            setMaxHeight(480).
                            setQuality(90).
                            compressToBitmap(url);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                thumb_bitmap.compress(Bitmap.CompressFormat.JPEG, 90, byteArrayOutputStream);
                final byte[] thumb_byte = byteArrayOutputStream.toByteArray();
                //// fin del compresor
                int p = (int) (Math.random() * 25 + 1);
                int s = (int) (Math.random() * 25 + 1);
                int t = (int) (Math.random() * 25 + 1);
                int c = (int) (Math.random() * 25 + 1);
                int numero1 = (int) (Math.random() * 1012 + 2111);
                int numero2 = (int) (Math.random() * 1012 + 2111);

                String[] elemetos = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};

                final String aleatorio = elemetos[p] + elemetos[s] +
                        numero1 + elemetos[t] + elemetos[c] + numero2 + "comprimido.jpg";

                if (tipo.getSelectedItem()!=""){

                    subir.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            cargando.setTitle("Subiendo Foto...");
                            cargando.setMessage("Espere por favor...");
                            cargando.show();
                             tipoacpm = selecciontipo;
                            final String descripacpm = descripcion.getText().toString();

                            final StorageReference ref = storageReference.child(aleatorio);
                            UploadTask uploadTask = ref.putBytes(thumb_byte);
                            //subir imagen en storage---...
                            Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                                @Override
                                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                                    if (!task.isSuccessful()) {
                                        throw Objects.requireNonNull(task.getException());
                                    }
                                    return ref.getDownloadUrl();
                                }
                            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {
                                    Uri downloaduri = task.getResult();
                                   // imgref.push().child("urlfoto").setValue(downloaduri.toString());
                                    cargando.dismiss();
                                    String imagenobtenida = downloaduri.toString();
                                    String idPer = mAuth.getCurrentUser().getUid();
                                    //mDatabase.child("Persona").child("mgGflE3rLuXgobaEpqdZuILXuGs1").child("Acpms").removeValue();

                                    String idper=mAuth.getCurrentUser().getUid();
                                    mDatabase.child("Persona").child(idPer).addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            if (snapshot.exists()){

                                                Acpms ac = new Acpms();
                                                ac.setId(UUID.randomUUID().toString());
                                                ac.setDescripcionacpm(descripacpm);
                                                ac.setTipo1acpm(tipoacpm);
                                                ac.setUrlAcpm(imagenobtenida);
                                                ac.setIdPersona(idPer);

                                                mDatabase.child("Acpms").child(ac.getId()).setValue(ac);

                                                Toast.makeText(RegistraracpmsActivity.this, "Registro realizado con Exito.", Toast.LENGTH_SHORT).show();


                                            }

                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });



                                }
                            });
                        }
                    });

                }else {
                    Toast.makeText(RegistraracpmsActivity.this, "No has seleccionado el tipo de ACPM", Toast.LENGTH_SHORT).show();
                }


            }
        }
    }
}