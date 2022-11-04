package pt.ipleiria.estg.dei.rentallcar.vistas;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;

import pt.ipleiria.estg.dei.rentallcar.MenuMainActivity;
import pt.ipleiria.estg.dei.rentallcar.R;

public class LoginActivity extends AppCompatActivity {


    private static final int MIN_PASS = 4;
    private EditText etEmail, etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
    }

    public void onClickLogin(View view) {

        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();
/*
        if(!isEmailValido(email)){
            etEmail.setError(getString(R.string.txt_email_invalido));
            return;
        }
        if(!isPasswordValida(password)){
            etPassword.setError(getString(R.string.txt_password_invalida));
            return;
        }*/
        Intent intent= new Intent(this, MenuMainActivity.class);
        intent.putExtra("EMAIL",email);
        startActivity(intent);
        finish();

        /*if (isEmailValido(email) && isPasswordValida(password))
            Toast.makeText(this, "Login efetuado com sucesso", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(this, "Erro no login", Toast.LENGTH_LONG).show();*/
    }

    private boolean isEmailValido(String email) {
        if (email == null)
            return false;
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isPasswordValida(String pass) {
        if (pass == null)
            return false;
        return pass.length() >= MIN_PASS;
    }

    public void onClickRegistar(View view) {
        Intent intent= new Intent(this, RegistoActivity.class);
        startActivity(intent);
        finish();
    }
}