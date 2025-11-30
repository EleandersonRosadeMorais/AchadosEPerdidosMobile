package com.ulbra.achadoseperdidos;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.ulbra.achadoseperdidos.api.ApiClient;
import com.ulbra.achadoseperdidos.api.ApiService;
import com.ulbra.achadoseperdidos.models.Item;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenuActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<Item> listaItens = new ArrayList<>();
    private ItemAdapter adapter;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ImageView btnMenu;

    private void abrirRegistroItem() {
        startActivity(new Intent(MenuActivity.this, RegistroItemActivity.class));
    }

    private void abrirLogin() {
        startActivity(new Intent(MenuActivity.this, LoginActivity.class));
    }

    private void abrirSobre() {
        startActivity(new Intent(MenuActivity.this, SobreActivity.class));
    }

    // 🔹 Agora busca itens via API (Retrofit + MySQL)
    private void carregarItensApi() {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<List<Item>> call = apiService .listarItens   ();

        call.enqueue(new Callback<List<Item>>() {
            @Override
            public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    listaItens.clear();
                    listaItens.addAll(response.body());
                    adapter.notifyDataSetChanged();
                } else {
                    Log.e("MenuActivity", "Erro ao carregar itens: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Item>> call, Throwable t) {
                Log.e("MenuActivity", "Falha na conexão: " + t.getMessage());
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_menu);

        recyclerView = findViewById(R.id.recyclerView);
        adapter = new ItemAdapter(listaItens);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);
        btnMenu = findViewById(R.id.btnMenu);

        // 🔹 Escolhe o menu conforme login (usando SharedPreferences ou UsuarioSession)
        if (UsuarioSession.isLoggedIn(this)) {
            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.menu_logado);
        } else {
            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.menu_nao_logado);
        }

        btnMenu.setOnClickListener(v -> drawerLayout.openDrawer(GravityCompat.END));

        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.nav_funcionario) {
                startActivity(new Intent(this, CadastroAdminActivity.class));
            } else if (id == R.id.nav_registro) {
                abrirRegistroItem();
            } else if (id == R.id.nav_sair) {
                UsuarioSession.logout(this);
                recreate(); // recarrega a activity para atualizar o menu
            } else if (id == R.id.nav_conectar) {
                abrirLogin();
            } else if (id == R.id.nav_sobre) {
                abrirSobre();
            }

            drawerLayout.closeDrawer(GravityCompat.END);
            return true;
        });

        carregarItensApi(); // 🔹 agora busca itens via API
    }
}
