package com.ulbra.achadoseperdidos;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class ImageFullActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.imagem_tela_cheia);

        ImageView imgFull = findViewById(R.id.imgFull);

        // Recebe a URL da imagem
        String imageUrl = getIntent().getStringExtra("imageUrl");

        // Carrega em tela cheia
        Glide.with(this)
                .load(imageUrl)
                .into(imgFull);
    }
}
