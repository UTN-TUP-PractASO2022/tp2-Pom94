package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    /** Called when the user taps the Send button */
    public void sendMessage(View view) {
        EditText inputTextNombrePersona = (EditText) findViewById(R.id.editTextNombrePersona);
        String nombrePersona = inputTextNombrePersona.getText().toString();


        //LLAMO A LA API QUE CALCULA EL GENERO DADO EL NOMBRE INGRESADO
        llamarAPIGenero("https://api.genderize.io?name="  + nombrePersona, Request.Method.GET);

        //ACA DE DEBERÍA AGREGAR UNA LLAMADA A UN NUEVO METODO QUE LLAMA A LA API QUE CALCULA LA EDAD
        //llamarAPIEdad(..........) <--- ESCRIBIR LA LLAMADA CORRECTAMENTE E IMPLEMENTAR EL MÉTODO

    }

    private void llamarAPIGenero(String url, int httpVerb) {
        //obtengo la referencia al objeto que es el texto donde se mostrará el género
        EditText campoTextoGenero = (EditText) findViewById(R.id.textGenero);

        //creo una cola de requests
        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(httpVerb, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                       //aca deberíamos asignar el string al elemento de la UI, por ejemplo
                        try {
                            //El json de la respuesta tiene muchos campos, solo extraigo "gender"
                            JSONObject reader = new JSONObject(response);
                            String genero = reader.getString("gender");

                            //Traduzco al español
                            if (genero.equals("male")) {
                                genero = "hombre";
                            } else {
                                genero = "mujer";
                            }

                            //Asigno el valor de género al campo de visualización de texto
                            campoTextoGenero.setText("hoasdfasdafsdasdfasfdasdfasdfasdf");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                campoTextoGenero.setText("ocurrió un error");
            }
        });

        //Hacemos la llamada a la API
        queue.add(stringRequest);
    }
}
