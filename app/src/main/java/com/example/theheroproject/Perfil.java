package com.example.theheroproject;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Perfil extends AppCompatActivity {
    public String  nombre_heroe = "";
    public int id_heroe;
    public BarChart grafico;
    private RequestQueue mQueue;
    private static String url_source = "https://superheroapi.com/api.php/183037120010210/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        mQueue = Volley.newRequestQueue(this);
        Intent heroes = getIntent();
        this.nombre_heroe= (String)heroes.getExtras().get("nombre_completo");
        this.id_heroe = (int)heroes.getExtras().get("id");
        this.graficar();
    }
    public void graficar() {
        grafico = findViewById(R.id.barChart);
        final List<BarEntry> entradas = new ArrayList<>();
        String url_Busqueda = url_source + id_heroe + "/powerstats";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url_Busqueda, null, new Response.Listener<JSONObject>() {

                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onResponse(JSONObject response) {
                        JSONArray jsonarray = null;
                        JSONObject objeto = null;
                        int inteligencia, fuerza, velocidad,durabilidad, poder, combate;
                        try {
                            jsonarray = new JSONArray(response);
                            objeto = jsonarray.getJSONObject(0);
                            entradas.add(new BarEntry(1,objeto.getInt("intelligence")));
                            entradas.add(new BarEntry(2,objeto.getInt("strength")));
                            entradas.add(new BarEntry(3,objeto.getInt("speed")));
                            entradas.add(new BarEntry(4,objeto.getInt("durability")));
                            entradas.add(new BarEntry(5,objeto.getInt("power")));
                            entradas.add(new BarEntry(6,objeto.getInt("combat")));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error

                    }
                });
        BarDataSet datos = new BarDataSet(entradas,"Habilidades");
        BarData data = new BarData(datos);
        datos.setColors(ColorTemplate.COLORFUL_COLORS);
        data.setBarWidth(0.9f);
        grafico.setData(data);
        grafico.setFitBars(true);
        grafico.invalidate();
        String[] months = new String[] {"inteligencia", "fuerza", "velocidad", "durabilidad", "poder", "combate"};
        XAxis xAxis = grafico.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(months));
        grafico.getAxisLeft().setAxisMinimum(0);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1);
        xAxis.setCenterAxisLabels(true);
        xAxis.setGranularityEnabled(true);

    }
}