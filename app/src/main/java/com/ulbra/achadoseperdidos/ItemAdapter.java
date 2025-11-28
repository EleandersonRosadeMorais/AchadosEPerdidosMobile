package com.ulbra.achadoseperdidos;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;


public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.SenhaViewHolder> {
    private List<Banco> item_senha;

    public ItemAdapter(List<Banco> item_senha) {
        this.item_senha = item_senha;
    }

    @NonNull
    @Override
    public SenhaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_perdido, parent, false);
        return new SenhaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SenhaViewHolder holder, int position) {
        Banco appAchados = item_senha.get(position);
        holder.txtNomeItem.setText(appAchados.getNomeItem());
        holder.txtEncontrado.setText("Login: " + appAchados.getEncontrado());
        holder.txtDataEncontrada.setText("Senha: " + appAchados.getDataEncontrada());
        holder.txtLocalizacao.setText("Senha: " + appAchados.getLocalizacao());
        holder.txtTipo.setText("Senha: " + appAchados.getTipo());
        Glide.with(holder.itemView.getContext())
                .load(appAchados.getImagemUrl())
                .into(holder.imgItem);
    }

    @Override
    public int getItemCount() {
        return item_senha.size();
    }

    static class SenhaViewHolder extends RecyclerView.ViewHolder {
        TextView txtNomeItem, txtEncontrado, txtDataEncontrada, txtLocalizacao, txtTipo;
        ImageView imgItem;

        public SenhaViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNomeItem = itemView.findViewById(R.id.txtNomeItem);
            txtLocalizacao = itemView.findViewById(R.id.txtLocalizacao);
            txtEncontrado = itemView.findViewById(R.id.txtEncontrado);
            txtDataEncontrada = itemView.findViewById(R.id.txtDataEncontrada);
            txtTipo = itemView.findViewById(R.id.txtTipo);
            imgItem = itemView.findViewById(R.id.imgMiniatura);
        }
    }
}