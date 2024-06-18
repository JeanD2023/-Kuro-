package com.example.kuro;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
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

public class pag2 extends AppCompatActivity {

    private Spinner spPais;
    private EditText et2,et3,etN1,et4,et5;
    private RadioButton rbtn1,rbtn2;
    private Button btnGuardar,btnBuscar,btnActualizar,btnBorrar;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pag2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        requestQueue = Volley.newRequestQueue(this);
        spPais = (Spinner) findViewById(R.id.spPais);
        String [] opciones = {"Ecuador","Colombia","Venezuela","Peru"};
        ArrayAdapter<String> adapter= new
                ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,opciones);
        spPais.setAdapter(adapter);
        et2 = (EditText) findViewById(R.id.et2);
        et3 = (EditText) findViewById(R.id.et3);
        etN1 = (EditText) findViewById(R.id.etN1);
        rbtn1 = (RadioButton) findViewById(R.id.rbtn1);
        rbtn2 = (RadioButton)findViewById(R.id.rbtn2);
        et4 = (EditText) findViewById(R.id.et4);
        et5 = (EditText) findViewById(R.id.et5);
        btnGuardar=(Button)findViewById(R.id.btnGuardar);
        btnBuscar=(Button)findViewById(R.id.btnBuscar);
        btnActualizar=(Button)findViewById(R.id.btnActualizar);
        btnBorrar=(Button)findViewById(R.id.btnBorrar);
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ejecutarServico();
            }
        });
        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buscar("http://192.168.56.1/formulario/buscardatos.php?cedula="+etN1.getText());
            }
        });
        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizar("http://192.168.56.1/formulario/actualizardatos.php?cedula="+etN1.getText());
            }
        });
        btnBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                borrar("http://192.168.56.1/formulario/borrardatos.php?cedula="+etN1.getText());
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
                parametros.put("nombres",et2.getText().toString());
                parametros.put("apellidos",et3.getText().toString());
                parametros.put("cedula",etN1.getText().toString());
                parametros.put("sexo",sexo());
                parametros.put("pais",String.valueOf(spPais.getSelectedItemPosition()));
                parametros.put("provincia",et4.getText().toString());
                parametros.put("direccion",et5.getText().toString());
                return parametros;
            }
        };
        requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    public String sexo() {
        String sexo="";
        if (rbtn1.isChecked()) {
            sexo = "Masculino";
        } else if (rbtn2.isChecked()) {
            sexo = "Femenino";
        }
        return sexo;

    }
    private void buscar(String URL){
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        et2.setText(jsonObject.getString("nombres"));
                        et3.setText(jsonObject.getString("apellidos"));
                        etN1.setText(jsonObject.getString("cedula"));
                        et4.setText(jsonObject.getString("provincia"));
                        et5.setText(jsonObject.getString("direccion"));
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
                parametros.put("nombres",et2.getText().toString());
                parametros.put("apellidos",et3.getText().toString());
                parametros.put("sexo",sexo());
                parametros.put("pais",String.valueOf(spPais.getSelectedItemPosition()));
                parametros.put("provincia",et4.getText().toString());
                parametros.put("direccion",et5.getText().toString());
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