package com.example.monitoreo;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private DatabaseReference humedadRef, presionRef, velocidadRef, temperaturaRef;
    private TextView tvTemperatura, tvHumedad, tvPresion, tvVelocidad;
    private EditText etSetTemperatura, etSetHumedad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Vincular vistas
        tvTemperatura = findViewById(R.id.valor_Temperatura);
        tvHumedad = findViewById(R.id.valor_Humedad);
        tvPresion = findViewById(R.id.valor_Presion);
        tvVelocidad = findViewById(R.id.valor_Velocidad);
        etSetTemperatura = findViewById(R.id.setvalor_Temperatura);
        etSetHumedad = findViewById(R.id.setvalor_Humedad);

        // Firebase - Referencias corregidas según tu captura
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        humedadRef = database.getReference("sensores/Humedad");
        presionRef = database.getReference("sensores/Presión");
        velocidadRef = database.getReference("sensores/Velocidad");
        temperaturaRef = database.getReference("sensores/temperatura");

        setupListeners();
    }

    private void setupListeners() {
        temperaturaRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) tvTemperatura.setText(snapshot.getValue().toString() + " °C");
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

        humedadRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) tvHumedad.setText(snapshot.getValue().toString() + " %");
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

        presionRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) tvPresion.setText(snapshot.getValue().toString() + " hPa");
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

        velocidadRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) tvVelocidad.setText(snapshot.getValue().toString() + " km/h");
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    public void clickBotonTemperatura(View view) {
        String valor = etSetTemperatura.getText().toString();
        if (!valor.isEmpty()) {
            temperaturaRef.setValue(valor);
            Toast.makeText(this, "Enviado: " + valor, Toast.LENGTH_SHORT).show();
            etSetTemperatura.setText("");
        }
    }

    public void clickBotonHumedad(View view) {
        String valor = etSetHumedad.getText().toString();
        if (!valor.isEmpty()) {
            humedadRef.setValue(valor);
            Toast.makeText(this, "Enviado: " + valor, Toast.LENGTH_SHORT).show();
            etSetHumedad.setText("");
        }
    }
}