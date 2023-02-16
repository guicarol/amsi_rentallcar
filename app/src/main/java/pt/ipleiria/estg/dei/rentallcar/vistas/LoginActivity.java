package pt.ipleiria.estg.dei.rentallcar.vistas;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import pt.ipleiria.estg.dei.rentallcar.MenuMainActivity;
import pt.ipleiria.estg.dei.rentallcar.MenuMainGestorActivity;
import pt.ipleiria.estg.dei.rentallcar.R;
import pt.ipleiria.estg.dei.rentallcar.modelo.SingletonGestorVeiculos;

public class LoginActivity extends AppCompatActivity {

    private static final int MIN_PASS = 4;
    private EditText etUsername, etPassword;
    private Button loginButton;
    private ImageView imgCapa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        imgCapa = findViewById(R.id.imageView);
        loginButton = (Button) findViewById(R.id.btnLogin);
        Glide.with(this)
                .load(LoginActivity.this)
                .placeholder(R.drawable.logo)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgCapa);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                login(email, password);
            }
        });
    }

    private void login(final String username, final String password) {
        String loginUrl = SingletonGestorVeiculos.mUrlAPI + "user/login";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, loginUrl,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getBoolean("success")) {
                                Toast.makeText(LoginActivity.this, "Login efetuado com sucesso", Toast.LENGTH_LONG).show();
                                SharedPreferences sharedPreferences = getSharedPreferences("user_info", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("username", username);
                                editor.putString("email", jsonObject.getString("email"));
                                editor.putInt("id", jsonObject.getInt("id"));
                                editor.apply();

                                if (jsonObject.getString("role").equals("cliente")) {
                                    Intent intent = new Intent(LoginActivity.this, MenuMainActivity.class);
                                    startActivity(intent);
                                    intent.putExtra("EMAIL", username);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Intent intent = new Intent(LoginActivity.this, MenuMainGestorActivity.class);
                                    startActivity(intent);
                                    intent.putExtra("EMAIL", username);
                                    startActivity(intent);
                                    finish();
                                }

                                // Intent dados = new Intent(this, Utilizador.class);
                                //dados.putExtra("EMAIL", email);
                            } else {

                                if (!isUsernameValido(username)) {
                                    etUsername.setError("Erro no username");
                                    return;
                                }
                                if (!isPasswordValida(password)) {
                                    etPassword.setError(getString(R.string.txt_password_invalida));
                                    return;
                                }
                                Toast.makeText(LoginActivity.this, "Username ou password incorretas", Toast.LENGTH_LONG).show();

                                // Handle unsuccessful login
                                // Show an error message to the user
                            }
                        } catch (JSONException e) {
                            // Handle JSON parsing error
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Handle Volley error
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("password", password);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json;charset=UTF-8");
                headers.put("Accept", "application/json");
                String credentials = username + ":" + password; // Your username and password
                String auth = "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
                headers.put("Authorization", auth);
                return headers;
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                Map<String, String> params = getParams();
                if (params != null && params.size() > 0) {
                    String requestBody = new JSONObject(params).toString();
                    Log.d("REQUEST BODY", requestBody);
                    return requestBody.getBytes();
                }
                return null;
            }
        };
        // Add the request to the RequestQueue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

  /*  private boolean isEmailValido(String email) {
        if (email == null)
            return false;
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }*/

    private boolean isPasswordValida(String pass) {
        if (pass == null)
            return false;
        return pass.length() >= MIN_PASS;
    }

    private boolean isUsernameValido(String username) {
        if (username == null)
            return false;
        return username.length() >= MIN_PASS;
    }

    public void onClickRegistar(View view) {
        Intent intent = new Intent(this, RegistoActivity.class);
        startActivity(intent);
    }
}