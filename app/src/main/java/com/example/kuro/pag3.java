package com.example.kuro;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class pag3 extends AppCompatActivity {
private EditText etN2,et6,etN3,etD1,etD2;
private Button btnGuardar1,btnBuscar1,btnActualizar1,btnBorrar1;
    private RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pag3);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        etN2=(EditText) findViewById(R.id.etN2);
        et6=(EditText) findViewById(R.id.et6);
        etN3=(EditText) findViewById(R.id.etN3);
        etD1=(EditText) findViewById(R.id.etD1);
        etD2=(EditText) findViewById(R.id.etD2);
        btnGuardar1=(Button) findViewById(R.id.btnGuardar1);
        btnBuscar1=(Button) findViewById(R.id.btnBuscar1);
        btnActualizar1=(Button) findViewById(R.id.btnActualizar1);
        btnBorrar1=(Button) findViewById(R.id.btnBorrar1);
        requestQueue = Volley.newRequestQueue(this);
        btnGuardar1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ejecutarServico();
            }
        });
        btnBuscar1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buscar("http://192.168.56.1/formulario/buscardatos.php?codigo="+etN2.getText());
            }
        });
        btnActualizar1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizar("http://192.168.56.1/formulario/actualizardatos.php?codigo="+etN2.getText());
            }
        });
        btnBorrar1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                borrar("http://192.168.56.1/formulario/borrardatos.php?codigo="+etN2.getText());
            }
        });
    }
    private void ejecutarServico(){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, "http://192.168.56.1/formulario/insertardatos.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "Operacion exitosa", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parametros=new HashMap<String,String>();
                parametros.put("codigo",etN2.getText().toString());
                parametros.put("producto",et6.getText().toString());
                parametros.put("stock",etN3.getText().toString());
                parametros.put("costo",etD1.getText().toString());
                parametros.put("venta",etD2.getText().toString());
                return parametros;
            }
        };
        requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    private void buscar(String URL){
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        etN2.setText(jsonObject.getString("codigo"));
                        et6.setText(jsonObject.getString("producto"));
                        etN3.setText(jsonObject.getString("stock"));
                        etD1.setText(jsonObject.getString("costo"));
                        etD2.setText(jsonObject.getString("venta"));
                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Error de conexion",Toast.LENGTH_SHORT).show();
            }
        }
        );
        requestQueue=Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

    private void actualizar(String URL){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "Operacion exitosa", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parametros=new HashMap<String,String>();
                parametros.put("codigo",etN2.getText().toString());
                parametros.put("producto",et6.getText().toString());
                parametros.put("stock",etN3.getText().toString());
                parametros.put("costo",etD1.getText().toString());
                parametros.put("venta",etD2.getText().toString());
                return parametros;
            }
        };
        requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    private void borrar(String URL){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "Operacion exitosa", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return new HashMap<String,String>();
            }
        };
        requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}