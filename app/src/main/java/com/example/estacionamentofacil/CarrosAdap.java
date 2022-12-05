package com.example.estacionamentofacil;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class CarrosAdap extends RecyclerView.Adapter<CarrosAdap.MyViewHolder> {
    private Context context;
    private List<AdicionarCarroModel> adicionarCarroModelList;
    private CarrosAdapInterface listagem;

    public CarrosAdap(Context context, CarrosAdapInterface listagem) {
        this.context = context;
        this.listagem = listagem;
        adicionarCarroModelList=new ArrayList<>();
    }

    public CarrosAdap(Context context) {
        this.context = context;
        adicionarCarroModelList= new ArrayList<>();
    }

    public void add(AdicionarCarroModel adicionarCarroModel){
        adicionarCarroModelList.add(adicionarCarroModel);
        notifyDataSetChanged();
    }

    public void remove(int pos){
        adicionarCarroModelList.remove(pos);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.estacionamento_file,parent,false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        AdicionarCarroModel model = adicionarCarroModelList.get(position);
        holder.numeroMotorista.setText(model.getNumeroMotorista());
        String[] dateAndTime=longIntoString(model.getTempo());
        holder.data.setText(dateAndTime[0]+"\n"+dateAndTime[1]);
        holder.marca.setText(model.getMarca());
        holder.nomeMotorista.setText(model.getNomeMotorista());
        holder.placa.setText(model.getPlaca());
        holder.taxa.setText(model.getTaxa());
        holder.status.setText(model.getStatus());
    }

    @Override
    public int getItemCount() {
        return adicionarCarroModelList.size();

    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView marca,nomeMotorista,numeroMotorista,placa,taxa,data,status;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            marca=itemView.findViewById(R.id.marca);
            nomeMotorista=itemView.findViewById(R.id.nomeMotorista);
            numeroMotorista=itemView.findViewById(R.id.numeroMotorista);
            placa=itemView.findViewById(R.id.placa);
            taxa=itemView.findViewById(R.id.taxa);
            data=itemView.findViewById(R.id.data);
            status=itemView.findViewById(R.id.status);


            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    listagem.OnLongClick(getAdapterPosition(), adicionarCarroModelList.get(getAdapterPosition()).getId());
                    return true;
                }
            });
        }
    }
    private  String[] longIntoString(long milliseconds) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a");
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM dd, yyyy");
        return new String[]{dateFormat.format(milliseconds), timeFormat.format(milliseconds)};
    }
}
