package com.example.theheroproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void buscarHeroe(View view ){
        final TextView buscar = (TextView) findViewById(R.id.heroe);
        Intent resultadoHeroes = new Intent(getBaseContext(), Resultado.class);
        resultadoHeroes.putExtra("idheroe", buscar.getText().toString());
        startActivity(resultadoHeroes);

    }
}