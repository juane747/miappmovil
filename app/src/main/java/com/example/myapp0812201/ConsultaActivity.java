package com.example.myapp0812201;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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

    @SuppressLint("WrongViewCast")
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
                    // La respuesta es un array JSON
                    JSONArray jsonArray = new JSONArray(response);

                    // Verifica si hay al menos un país en la respuesta
                    if (jsonArray.length() > 0) {
                        // Tomamos el primer país del array
                        JSONObject country = jsonArray.getJSONObject(0);

                        // "name" está dentro de "name" y es un objeto JSON
                        JSONObject nameObject = country.getJSONObject("name");

                        // Obtenemos el nombre oficial del país
                        String officialName = nameObject.getString("official");

                        // Actualizamos la interfaz de usuario con el nombre del país
                        textViewNombrePais.setText(officialName);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Manejo de errores
            }
        });
        Volley.newRequestQueue(this).add(postResquest);
    }
}
