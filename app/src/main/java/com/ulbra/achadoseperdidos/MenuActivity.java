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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MenuActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<Banco> listaItens = new ArrayList<>();
    private ItemAdapter adapter;
    private DatabaseReference database;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ImageView btnMenu;

    private void abrirRegistroItem() {
        Intent intent = new Intent(MenuActivity.this, RegistroItemActivity.class);
        startActivity(intent);
    }

    private void abrirLogin() {
        Intent intent = new Intent(MenuActivity.this, LoginActivity.class);
        startActivity(intent);
    }

//    private void abrirSobre() {
//        Intent intent = new Intent(MenuActivity.this, SobreActivity.class);
//        startActivity(intent);
//    }

    private void carregarDadosFirebase() {
        database = FirebaseDatabase.getInstance().getReference();
        DatabaseReference ref = database.child("achados").child("itens");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                listaItens.clear();
                for (DataSnapshot item : snapshot.getChildren()) {
                    String nomeItem = item.child("nomeItem").getValue(String.class);
                    String localizacao = item.child("localizacao").getValue(String.class);
                    String dataEncontrada = item.child("dataEncontrada").getValue(String.class);
                    String encontrado = item.child("encontrado").getValue(String.class);
                    String tipo = item.child("tipo").getValue(String.class);
                    String imagemUrl = item.child("imagemUrl").getValue(String.class);

                    listaItens.add(new Banco(nomeItem, localizacao, dataEncontrada, tipo, imagemUrl, encontrado));
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.e("Firebase", "Erro ao carregar dados", error.toException());
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

        // 🔹 Escolhe o menu conforme login
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.menu_logado);
        } else {
            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.menu_nao_logado);
        }

        btnMenu.setOnClickListener(v -> drawerLayout.openDrawer(GravityCompat.END));

        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.nav_registro) {
                abrirRegistroItem();
            } else if (id == R.id.nav_sair) {
                FirebaseAuth.getInstance().signOut();
                recreate(); // recarrega a activity para atualizar o menu
            } else if (id == R.id.nav_conectar) {
                abrirLogin();
            } else if (id == R.id.nav_sobre) {
                // abrirSobre();
            }

            drawerLayout.closeDrawer(GravityCompat.END);
            return true;
        });

        carregarDadosFirebase();
    }
}
