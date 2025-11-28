package com.ulbra.achadoseperdidos;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import java.util.HashMap;
import java.util.Map;

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

        database = FirebaseDatabase.getInstance().getReference("achados");
        storageRef = FirebaseStorage.getInstance().getReference("imagens");

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

        if (imagemSelecionada != null) {
            StorageReference fileRef = storageRef.child(System.currentTimeMillis() + ".jpg");
            UploadTask uploadTask = fileRef.putFile(imagemSelecionada);

            uploadTask.addOnSuccessListener(taskSnapshot ->
                    fileRef.getDownloadUrl().addOnSuccessListener(uri -> {
                        String imagemUrl = uri.toString();

                        Map<String, Object> item = new HashMap<>();
                        item.put("nomeItem", nomeItem);
                        item.put("localizacao", localizacao);
                        item.put("dataEncontrada", dataEncontrada);
                        item.put("tipo", tipo);
                        item.put("encontrado", encontrado);
                        item.put("imagemUrl", imagemUrl);

                        database.push().setValue(item);
                        finish();
                    })
            );
        }
    }
}
