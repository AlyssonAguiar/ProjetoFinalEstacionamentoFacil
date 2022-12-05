package com.example.estacionamentofacil;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.estacionamentofacil.databinding.ActivityAdicionarBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.UUID;

public class AdicionarActivity extends AppCompatActivity {
    ActivityAdicionarBinding binding;

    FirebaseFirestore fSTore;
    FirebaseAuth fAuth;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdicionarBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        fAuth=FirebaseAuth.getInstance();
        fSTore=FirebaseFirestore.getInstance();
        dialog=new ProgressDialog(this);

        binding.adicionarCarroBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nomeMotorista=binding.nomeMotorista.getText().toString();
                String numeroMotorista=binding.numeroMotorista.getText().toString();
                String placa=binding.placa.getText().toString();
                String marca=binding.marca.getText().toString();
                long tempo= Calendar.getInstance().getTimeInMillis();
                String taxa = binding.taxa.getText().toString();
                String id= UUID.randomUUID().toString();

                AdicionarCarroModel adicionarCarroModel = new AdicionarCarroModel();

                adicionarCarroModel.setId(id);
                adicionarCarroModel.setNomeMotorista(nomeMotorista);
                adicionarCarroModel.setNumeroMotorista(numeroMotorista);
                adicionarCarroModel.setMarca(marca);
                adicionarCarroModel.setTaxa(taxa);
                adicionarCarroModel.setPlaca(placa);
                adicionarCarroModel.setTempo(tempo);
                adicionarCarroModel.setUserId(fAuth.getCurrentUser().getUid());
                adicionarCarroModel.setStatus("Estacionado");

                dialog.setTitle("Atualizando");
                dialog.setMessage("Adicionado com sucesso");
                dialog.show();
                fSTore.collection("estacionados")
                        .document(id)
                        .set(adicionarCarroModel)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                dialog.cancel();
                                finish();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                dialog.cancel();
                                Toast.makeText(AdicionarActivity.this,e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

    }
}