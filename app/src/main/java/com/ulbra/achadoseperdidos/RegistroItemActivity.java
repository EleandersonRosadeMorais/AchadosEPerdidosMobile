package com.ulbra.achadoseperdidos;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ulbra.achadoseperdidos.api.ApiClient;
import com.ulbra.achadoseperdidos.api.ApiService;
import com.ulbra.achadoseperdidos.models.ApiResponse;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistroItemActivity extends AppCompatActivity {

    private EditText edtNomeItem, edtLocalizacao, edtDataEncontrada, edtTipo, edtEncontrado;
    private Button btnSalvar, btnSelecionarImagem;
    private ImageView imgPreview;
    private Uri imagemSelecionada;

    private static final int PICK_IMAGE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registro_item);

        edtNomeItem = findViewById(R.id.edtNomeItem);
        edtLocalizacao = findViewById(R.id.edtLocalizacao);
        edtDataEncontrada = findViewById(R.id.edtDataEncontrada);
        edtTipo = findViewById(R.id.edtTipo);
        edtEncontrado = findViewById(R.id.edtEncontrado);
        btnSalvar = findViewById(R.id.btnSalvar);
        btnSelecionarImagem = findViewById(R.id.btnSelecionarImagem);
        imgPreview = findViewById(R.id.imgPreview);

        btnSelecionarImagem.setOnClickListener(v -> abrirGaleria());
        btnSalvar.setOnClickListener(v -> salvarItem());
    }

    private void abrirGaleria() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Selecionar Imagem"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imagemSelecionada = data.getData();
            imgPreview.setImageURI(imagemSelecionada);
        }
    }

    private void salvarItem() {
        String nomeItem = edtNomeItem.getText().toString();
        String localizacao = edtLocalizacao.getText().toString();
        String dataEncontrada = edtDataEncontrada.getText().toString();
        String tipo = edtTipo.getText().toString();
        String encontrado = edtEncontrado.getText().toString();

        if (nomeItem.isEmpty() || localizacao.isEmpty() || dataEncontrada.isEmpty() || tipo.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos obrigatórios", Toast.LENGTH_SHORT).show();
            return;
        }

        ApiService apiService = ApiClient.getClient().create(ApiService.class);

        // 🔹 Preparar partes do formulário
        RequestBody nomeBody = RequestBody.create(MediaType.parse("text/plain"), nomeItem);
        RequestBody localizacaoBody = RequestBody.create(MediaType.parse("text/plain"), localizacao);
        RequestBody dataBody = RequestBody.create(MediaType.parse("text/plain"), dataEncontrada);
        RequestBody tipoBody = RequestBody.create(MediaType.parse("text/plain"), tipo);
        RequestBody encontradoBody = RequestBody.create(MediaType.parse("text/plain"), encontrado);

        MultipartBody.Part imagemPart = null;
        if (imagemSelecionada != null) {
            try {
                // Copiar URI para arquivo temporário
                String fileName = getFileName(imagemSelecionada);
                File file = new File(getCacheDir(), fileName);
                try (InputStream inputStream = getContentResolver().openInputStream(imagemSelecionada);
                     OutputStream outputStream = new java.io.FileOutputStream(file)) {
                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = inputStream.read(buffer)) > 0) {
                        outputStream.write(buffer, 0, length);
                    }
                }

                RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);
                imagemPart = MultipartBody.Part.createFormData("imagem", file.getName(), requestFile);

            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "Erro ao processar imagem", Toast.LENGTH_SHORT).show();
            }
        }

        Call<ApiResponse> call = apiService.registrarItem(
                nomeBody, localizacaoBody, dataBody, tipoBody, encontradoBody, imagemPart
        );

        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(RegistroItemActivity.this,
                            response.body().getMessage(),
                            Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(RegistroItemActivity.this,
                            "Erro ao salvar item: " + response.code(),
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Toast.makeText(RegistroItemActivity.this,
                        "Falha na conexão: " + t.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            try (android.database.Cursor cursor = getContentResolver().query(uri, null, null, null, null)) {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndexOrThrow(OpenableColumns.DISPLAY_NAME));
                }
            }
        }
        if (result == null) {
            result = uri.getLastPathSegment();
        }
        return result;
    }
}
