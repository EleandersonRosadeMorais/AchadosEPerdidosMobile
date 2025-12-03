package com.ulbra.achadoseperdidos.activities;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.ulbra.achadoseperdidos.R;

public class SobreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_sobre);

        TextView txtVersao = findViewById(R.id.txtVersao);
        TextView txtWhats = findViewById(R.id.txtWhats);
        TextView txtContato = findViewById(R.id.txtContato);
        TextView txtDescricao = findViewById(R.id.txtDescricao);

        // 🔹 Exemplo de preenchimento dinâmico
        String versaoApp = "1.0.0"; // você pode puxar do BuildConfig.VERSION_NAME
        txtVersao.setText("Versão: " + versaoApp);

        txtWhats.setText("Whats: (51)3451-7557");
        txtContato.setText("Contato: ulbrasaolucas@ulbra.br");
        txtDescricao.setText("Aplicativo desenvolvido para auxiliar na gestão de itens achados e perdidos.");
    }
}
