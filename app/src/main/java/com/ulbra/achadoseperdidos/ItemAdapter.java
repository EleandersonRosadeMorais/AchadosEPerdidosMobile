package com.ulbra.achadoseperdidos;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ulbra.achadoseperdidos.models.Item;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {
    private List<Item> listaItens;

    public ItemAdapter(List<Item> listaItens) {
        this.listaItens = listaItens;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_perdido, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Item item = listaItens.get(position);

        holder.txtNomeItem.setText(item.getNomeItem());
        holder.txtEncontrado.setText("Encontrado: " + item.getEncontrado());
        holder.txtDataEncontrada.setText("Data: " + item.getDataEncontrada());
        holder.txtLocalizacao.setText("Local: " + item.getLocalizacao());
        holder.txtTipo.setText("Tipo: " + item.getTipo());

        Glide.with(holder.itemView.getContext())
                .load(item.getImagemUrl())
                .into(holder.imgItem);

        // 🔹 Clique na imagem para abrir em tela cheia
        holder.imgItem.setOnClickListener(v -> {
            Intent intent = new Intent(holder.itemView.getContext(), ImageFullActivity.class);
            intent.putExtra("imageUrl", item.getImagemUrl());
            holder.itemView.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return listaItens.size();
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView txtNomeItem, txtEncontrado, txtDataEncontrada, txtLocalizacao, txtTipo;
        ImageView imgItem;

        public ItemViewHolder(@NonNull View itemView) {
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
