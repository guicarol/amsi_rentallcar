package pt.ipleiria.estg.dei.rentallcar.vistas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import pt.ipleiria.estg.dei.rentallcar.MenuMainActivity;
import pt.ipleiria.estg.dei.rentallcar.R;

public class RegistoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registo);
    }

    public void onClickLogin(View view) {
        Intent intent= new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public void onClickRegistar(View view) {
        Intent intent= new Intent(this, MenuMainActivity.class);
        startActivity(intent);
        finish();
    }
}