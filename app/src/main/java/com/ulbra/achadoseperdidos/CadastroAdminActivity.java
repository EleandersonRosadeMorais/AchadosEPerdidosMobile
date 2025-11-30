package com.ulbra.achadoseperdidos;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ulbra.achadoseperdidos.api.ApiClient;
import com.ulbra.achadoseperdidos.api.ApiService;
import com.ulbra.achadoseperdidos.models.Administrador;
import com.ulbra.achadoseperdidos.models.ApiResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CadastroAdminActivity extends AppCompatActivity {

    private EditText edtNome, edtCpf, edtEmail, edtSenha;
    private Button btnRegistrarAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_registro_admin);

        edtNome = findViewById(R.id.edtNomeAdmin);
        edtCpf = findViewById(R.id.edtCpfAdmin);
        edtEmail = findViewById(R.id.edtEmailAdmin);
        edtSenha = findViewById(R.id.edtSenhaAdmin);
        btnRegistrarAdmin = findViewById(R.id.btnRegistrarAdmin);

        btnRegistrarAdmin.setOnClickListener(v -> {
            String nome = edtNome.getText().toString().trim();
            String cpf = edtCpf.getText().toString().trim();
            String email = edtEmail.getText().toString().trim();
            String senha = edtSenha.getText().toString().trim();

            if (nome.isEmpty() || cpf.isEmpty() || email.isEmpty() || senha.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(this, "Email inválido", Toast.LENGTH_SHORT).show();
                return;
            }
            if (cpf.length() != 11) {
                Toast.makeText(this, "CPF deve ter 11 dígitos", Toast.LENGTH_SHORT).show();
                return;
            }

            Administrador admin = new Administrador();
            admin.setNome(nome);
            admin.setCpf(cpf);
            admin.setEmail(email);
            admin.setSenha(senha);

            ApiService apiService = ApiClient.getClient().create(ApiService.class);
            Call<ApiResponse> call = apiService.cadastrarAdmin(admin);

            call.enqueue(new Callback<ApiResponse>() {
                @Override
                public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        ApiResponse apiResponse = response.body();
                        Toast.makeText(CadastroAdminActivity.this,
                                apiResponse.getMessage(),
                                Toast.LENGTH_SHORT).show();

                        if (apiResponse.isSuccess()) {
                            startActivity(new Intent(CadastroAdminActivity.this, MenuActivity.class));
                            finish();
                        }
                    } else {
                        Toast.makeText(CadastroAdminActivity.this,
                                "Erro ao cadastrar: " + response.code(),
                                Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ApiResponse> call, Throwable t) {
                    Toast.makeText(CadastroAdminActivity.this,
                            "Falha na conexão: " + t.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}
