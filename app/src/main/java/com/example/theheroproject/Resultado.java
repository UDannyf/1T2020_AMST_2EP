package com.example.theheroproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Resultado extends AppCompatActivity {
    private RequestQueue mQueue;
    private String busqueda = "";
    private static String url_source = "https://superheroapi.com/api.php/183037120010210/search/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado);
        mQueue = Volley.newRequestQueue(this);
        Intent main = getIntent();
        this.busqueda = (String)main.getExtras().get("idheroe");
        buscarHeroe(busqueda);
    }
    private void buscarHeroe(final String busqueda) {
        String url_Busqueda = url_source + busqueda;
        final TextView txtResultadosLen = (TextView) findViewById(R.id.txtResultados);
        Intent listado_heroes = new Intent(getBaseContext(), Resultado.class);
        final ScrollView scrollContenedorHeroes = (ScrollView) findViewById(R.id.ContenedorHeroes);
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET, url_Busqueda, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            txtResultadosLen.setText( String.valueOf(response.getJSONArray("results").length()));
                            LinearLayout linearHeroes = (LinearLayout) findViewById(R.id.listaHero3);
                            for(int i = 0 ; i<response.getJSONArray("results").length();i++){
                                TextView nombreHeroe = new TextView(Resultado.this);
                                final JSONObject j = (JSONObject)response.getJSONArray("results").get(i);
                                nombreHeroe.setTextSize(24);
                                nombreHeroe.setText(j.getString("name").toString());
                                linearHeroes.addView(nombreHeroe);
                                nombreHeroe.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        try {
                                            Intent diagrama = new Intent(getBaseContext(), Perfil.class);
                                            diagrama.putExtra("id",j.getInt("id"));
                                            diagrama.putExtra("nombre_completo", j.getString("name"));
                                            startActivity(diagrama);
                                            System.out.println(j.getString("name"));
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                            }
                            scrollContenedorHeroes.addView(linearHeroes);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                AlertDialog alertDialog = new
                        AlertDialog.Builder(Resultado.this).create();
                alertDialog.setTitle("Alerta!!!");
                alertDialog.setMessage("Heroe no enconrado");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Buscar",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int
                                    which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }
        });
        mQueue.add(request);
    }
}