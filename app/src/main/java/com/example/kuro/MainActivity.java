package com.example.kuro;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private EditText et1, etP1;
    private Button btn1;
    private Spinner sp1;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        et1 = (EditText) findViewById(R.id.et1);
        etP1 = (EditText) findViewById(R.id.etP1);
        btn1 = (Button) findViewById(R.id.btn1);
        sp1 = (Spinner) findViewById(R.id.sp1);

        sp1 = (Spinner) findViewById(R.id.sp1);
        String []opciones={"Persona","Producto","Inventario"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, opciones);
        sp1.setAdapter(adapter);

    }
    public void Ingresar (View v) {
        String clave=etP1.getText().toString();
        String usuario=et1.getText().toString();
        if (usuario.equals("admin") && clave.equals("12345")) {
            // Login successful, proceed to next activity
            Toast.makeText(MainActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
        } else {
            // Login failed, show error message
            Toast.makeText(MainActivity.this, "Error: Invalid username or password", Toast.LENGTH_SHORT).show();
        }
        String selec=sp1.getSelectedItem().toString();
        switch (selec) {
            case "Persona": {
                Intent i = new Intent(this, pag2.class);
                startActivity(i);
                break;
            }
            case "Producto": {
                Intent i = new Intent(this, pag3.class);
                startActivity(i);
                break;
            }
            case "Inventario": {
                Intent i = new Intent(this, pag4.class);
                startActivity(i);
                break;
            }
        }
    }
}
