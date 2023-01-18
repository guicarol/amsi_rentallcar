package pt.ipleiria.estg.dei.rentallcar.vistas;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import pt.ipleiria.estg.dei.rentallcar.MenuMainActivity;
import pt.ipleiria.estg.dei.rentallcar.R;
import pt.ipleiria.estg.dei.rentallcar.modelo.SingletonGestorVeiculos;

public class RegistoActivity extends AppCompatActivity {

    private EditText usernameEditText, passwordEditText, emailEditText, etNome, etApelido, etTelefone, etNif;
    private Button signupButton;
    private static final String salt = "your_salt_value";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registo);

        usernameEditText = findViewById(R.id.etUsername);
        passwordEditText = findViewById(R.id.etPassword);
        emailEditText = findViewById(R.id.etEmail);

        etNome = findViewById(R.id.etNome);
        etApelido = findViewById(R.id.etApelido);
        etTelefone = findViewById(R.id.etTelefone);
        etNif = findViewById(R.id.etNif);


        signupButton = findViewById(R.id.btnRegistar);

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = usernameEditText.getText().toString();
                final String password = passwordEditText.getText().toString();
                final String email = emailEditText.getText().toString();
                registo(username, password, email);

                String nome = etNome.getText().toString();
                String apelido = etApelido.getText().toString();
                String telemovel = etTelefone.getText().toString();
                String nif = etNif.getText().toString();
               // UpdatePerfil(nome, apelido, telemovel, nif);
            }
        });
    }

    private void registo(String username, String password, String email) {
        // Encrypt the password and add the salt value
        //String hashedPassword = hashPassword(password + salt);

        String url = SingletonGestorVeiculos.mUrlAPI + "user/signup";
        StringRequest stringRequest = new StringRequest(Request.Method.PUT, url, new com.android.volley.Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean("success")) {
                        // Handle successful signup
                        Toast.makeText(RegistoActivity.this, "Login efetuado com sucesso", Toast.LENGTH_LONG).show();

                    } else {
                        // Handle signup failure
                        Toast.makeText(RegistoActivity.this, "Erro no login", Toast.LENGTH_LONG).show();

                        if (username == null) {
                            usernameEditText.setError("Erro no username");
                            return;
                        }
                        if (!isEmailValido(email)) {
                            emailEditText.setError("Email inválido");
                            return;
                        }
                        if (!isPasswordValida(password)) {
                            passwordEditText.setError(getString(R.string.txt_password_invalida));
                            return;
                        }
                    }
                } catch (JSONException e) {
                    // Handle JSON exception
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Handle error
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("password", password);
                params.put("email", email);
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(RegistoActivity.this);
        requestQueue.add(stringRequest);
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashedPassword = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hashedPassword);
        } catch (NoSuchAlgorithmException e) {
            // Handle exception
        }
        return null;
    }


    public void onClickLogin(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public void onClickRegistar(View view) {
        Intent intent = new Intent(this, MenuMainActivity.class);
        startActivity(intent);
        finish();
    }

    private boolean isEmailValido(String email) {
        if (email == null)
            return false;
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isPasswordValida(String pass) {
        if (pass == null)
            return false;
        return pass.length() >= 6;
    }
}