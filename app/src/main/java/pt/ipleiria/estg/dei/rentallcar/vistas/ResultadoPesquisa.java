package pt.ipleiria.estg.dei.rentallcar.vistas;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import pt.ipleiria.estg.dei.rentallcar.R;

public class ResultadoPesquisa extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado_pesquisa);
        setTitle("Resultado");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
}