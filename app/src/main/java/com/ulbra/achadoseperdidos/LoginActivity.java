package com.ulbra.achadoseperdidos;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private EditText edtEmail, edtSenha, edtNome;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_login);

        edtEmail = findViewById(R.id.edtEmail);
        edtSenha = findViewById(R.id.edtSenha);
        edtNome  = findViewById(R.id.edtNome);
        btnLogin = findViewById(R.id.btnLogin);

        // 🔹 Botão de Login
        btnLogin.setOnClickListener(v -> {
            String email = edtEmail.getText().toString().trim();
            String senha = edtSenha.getText().toString().trim();

            if (email.isEmpty() || senha.isEmpty()) {
                Toast.makeText(this, "Preencha email e senha", Toast.LENGTH_SHORT).show();
                return;
            }

            FirebaseHelper.getAuth().signInWithEmailAndPassword(email, senha)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(this, "Login realizado com sucesso!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LoginActivity.this, MenuActivity.class));
                            finish();
                        } else {
                            String erro = task.getException() != null ? task.getException().getMessage() : "Erro desconhecido";
                            Toast.makeText(this, "Erro: " + erro, Toast.LENGTH_LONG).show();
                            Log.e("LoginActivity", "Erro login: " + erro);
                        }
                    });
        });
    }
}
