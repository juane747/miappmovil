package com.example.myapp0812201;
import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ConsultaActivity extends AppCompatActivity {
    TextView textViewNombrePais;
    Button buttonrefrescar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta);

        textViewNombrePais = findViewById(R.id.textViewNombrePais);
        buttonrefrescar = findViewById(R.id.buttonrefrescar);

        buttonrefrescar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Leerws();
            }
        });
    }

    private void Leerws() {
        String url = "https://restcountries.com/v3.1/all";
        StringRequest postResquest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d(TAG, "Respuesta del API: " + response);

                    JSONArray jsonArray = new JSONArray(response);

                    if (jsonArray.length() > 0) {
                        JSONObject country = jsonArray.getJSONObject(0);

                        JSONObject nameObject = country.getJSONObject("name");

                        String officialName = nameObject.getString("official");

                        textViewNombrePais.setText(officialName);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        Volley.newRequestQueue(this).add(postResquest);
    }
}