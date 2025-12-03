package com.ulbra.achadoseperdidos.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ulbra.achadoseperdidos.R;
import com.ulbra.achadoseperdidos.activities.ImageFullActivity;
import com.ulbra.achadoseperdidos.activities.RegistroItemActivity;
import com.ulbra.achadoseperdidos.api.ApiClient;
import com.ulbra.achadoseperdidos.api.ApiService;
import com.ulbra.achadoseperdidos.models.Item;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

    public void updateList(List<Item> novaLista) {
        this.listaItens = novaLista;
        notifyDataSetChanged();
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

        // 🔹 Botão Editar → abre RegistroItemActivity com dados do item
        holder.btnEditar.setOnClickListener(v -> {
            Intent intent = new Intent(holder.itemView.getContext(), RegistroItemActivity.class);
            intent.putExtra("itemId", item.getId()); // supondo que Item tenha getId()
            holder.itemView.getContext().startActivity(intent);
        });

        // 🔹 Botão Excluir → chama API e remove da lista
        holder.btnExcluir.setOnClickListener(v -> {
            ApiService apiService = ApiClient.getClient().create(ApiService.class);
            Call<com.ulbra.achadoseperdidos.models.ApiResponse> call = apiService.excluirItem(item.getId());

            call.enqueue(new Callback<com.ulbra.achadoseperdidos.models.ApiResponse>() {
                @Override
                public void onResponse(Call<com.ulbra.achadoseperdidos.models.ApiResponse> call,
                                       Response<com.ulbra.achadoseperdidos.models.ApiResponse> response) {
                    if (response.isSuccessful()) {
                        listaItens.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, listaItens.size());
                    }
                }

                @Override
                public void onFailure(Call<com.ulbra.achadoseperdidos.models.ApiResponse> call, Throwable t) {
                    // Aqui você pode mostrar um Toast ou logar o erro
                }
            });
        });
    }

    @Override
    public int getItemCount() {
        return listaItens.size();
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView txtNomeItem, txtEncontrado, txtDataEncontrada, txtLocalizacao, txtTipo;
        ImageView imgItem;
        Button btnEditar, btnExcluir;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNomeItem = itemView.findViewById(R.id.txtNomeItem);
            txtLocalizacao = itemView.findViewById(R.id.txtLocalizacao);
            txtEncontrado = itemView.findViewById(R.id.txtEncontrado);
            txtDataEncontrada = itemView.findViewById(R.id.txtDataEncontrada);
            txtTipo = itemView.findViewById(R.id.txtTipo);
            imgItem = itemView.findViewById(R.id.imgMiniatura);
            btnEditar = itemView.findViewById(R.id.btnEditar);
            btnExcluir = itemView.findViewById(R.id.btnExcluir);
        }
    }
}
