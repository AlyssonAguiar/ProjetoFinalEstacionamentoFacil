package com.example.estacionamentofacil;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.estacionamentofacil.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class MainActivity extends AppCompatActivity implements  CarrosAdapInterface{
    ActivityMainBinding binding;

    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    CarrosAdap adapter;
    int estacionados=0,pagos=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        fAuth=FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();
        adapter=new CarrosAdap(this,this);

        binding.carsRecycler.setAdapter(adapter);
        binding.carsRecycler.setLayoutManager(new LinearLayoutManager(this));

        loaddata();

        binding.adicionarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), AdicionarActivity.class));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Aguarde");
        progressDialog.setMessage("processando");
        if(fAuth.getCurrentUser()==null){
            fAuth.signInAnonymously()
                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            progressDialog.cancel();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.cancel();
                            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }else
            progressDialog.cancel();
    }

    private void loaddata(){
        fStore.collection("estacionados")
                .whereEqualTo("userId",fAuth.getCurrentUser().getUid())
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> dsList=queryDocumentSnapshots.getDocuments();

                        for (DocumentSnapshot ds:dsList){
                            AdicionarCarroModel model = ds.toObject(AdicionarCarroModel.class);
                            if(model.getStatus().equals("Estacionado")){
                                String taxa=model.getTaxa();
                                estacionados=estacionados+Integer.parseInt(taxa);
                            }
                            if(model.getStatus().equals("Pago")){
                                String taxa=model.getTaxa();
                                pagos=pagos+Integer.parseInt(taxa);
                            }
                            adapter.add(model);
                        }
                        binding.pagos.setText(pagos+"");
                        binding.pendentes.setText(estacionados+"");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this,e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void OnLongClick(int pos, String id) {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Stus")
                .setPositiveButton("Pago", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        ChangeStatus("Pago",id);
                    }
                })
                .setNegativeButton("Cancelado", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        ChangeStatus("Cancelado",id);
                    }
                });
        builder.show();
    }

    private void ChangeStatus(String status,String id){
        fStore.collection("estacionados")
                .document(id)
                .update("status",status)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(MainActivity.this, "Status", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        }
}
