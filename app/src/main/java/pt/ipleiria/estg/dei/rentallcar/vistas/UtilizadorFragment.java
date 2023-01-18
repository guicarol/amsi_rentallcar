package pt.ipleiria.estg.dei.rentallcar.vistas;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import pt.ipleiria.estg.dei.rentallcar.R;
import pt.ipleiria.estg.dei.rentallcar.listeners.PerfilListener;
import pt.ipleiria.estg.dei.rentallcar.modelo.Perfil;
import pt.ipleiria.estg.dei.rentallcar.modelo.SingletonGestorVeiculos;

public class UtilizadorFragment extends Fragment implements PerfilListener {

    private String email;
    private String username;
    private int id;
    private ImageView imgCapa;

    private Button btnalterar;


    private TextView logout, etNome, etApelido, etTelefone, etNif, etEmail, etUsername;

    private Perfil perfil;

    public UtilizadorFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_utilizador, container, false);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("user_info", MODE_PRIVATE);
        username = sharedPreferences.getString("username", "");
        email = sharedPreferences.getString("email", "");
        id = sharedPreferences.getInt("id", -1);

        SingletonGestorVeiculos.getInstance(getContext()).setDadosPessoaisListener(this);
        SingletonGestorVeiculos.getInstance(getContext()).getPerfilAPI(getContext(), id);

        logout = (TextView) view.findViewById(R.id.LogOut);

        imgCapa = view.findViewById(R.id.imageView);

        etNome = view.findViewById(R.id.etNome);
        etApelido = view.findViewById(R.id.etApelido);
        etTelefone = view.findViewById(R.id.etTelefone);
        etNif = view.findViewById(R.id.etNif);

        etEmail = view.findViewById(R.id.etEmail);
        etUsername = view.findViewById(R.id.etUsername);

        btnalterar = view.findViewById(R.id.btnAlterar);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view.getContext(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
        btnalterar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString();
                String email = etEmail.getText().toString();
                //String password = passwordEditText.getText().toString();
                UpdateUser(username, email);
                String nome=etNome.getText().toString();
                String apelido=etApelido.getText().toString();
                String telemovel=etTelefone.getText().toString();
                String nif=etNif.getText().toString();
                UpdatePerfil(nome,apelido,telemovel,nif);
            }
        });
        return view;
    }

    private void UpdatePerfil(String nome, String apelido, String telemovel, String nif) {
        String url = SingletonGestorVeiculos.mUrlAPI + "user/updateprofile?id=" + id;
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("nome", nome);
            jsonBody.put("apelido", apelido);
            jsonBody.put("telemovel", telemovel+"");
            jsonBody.put("nif", nif+"");
            //jsonBody.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, url, jsonBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Handle the response
                        try {
                            String message = response.getString("message");
                            Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Handle the error
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                // you can add more headers if needed
                return headers;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(jsonObjectRequest);
    }

    private void UpdateUser(String username, String email) {
        // Encrypt the password and add the salt value
        //String hashedPassword = hashPassword(password + salt);
        String url = SingletonGestorVeiculos.mUrlAPI + "user/updateuser?id=" + id;

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("username", username);
            jsonBody.put("email", email);
            //jsonBody.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, url, jsonBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Handle the response
                        try {
                            String message = response.getString("message");
                            Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Handle the error
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                // you can add more headers if needed
                return headers;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(jsonObjectRequest);
    }

    @Override
    public void onRefreshPerfil(Perfil perfil) {
        if (perfil != null) {
            etNome.setText(perfil.getNome());
            etApelido.setText(perfil.getApelido());
            etTelefone.setText(perfil.getTelemovel() + "");
            etNif.setText(perfil.getNif() + "");
            etEmail.setText(email);
            etUsername.setText(username);
            Glide.with(this)
                    .load(getActivity())
                    .placeholder(R.drawable.logo)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imgCapa);
            //etNome.setText(" " + perfil.getNome() + " " + perfil.getApelido());
        }
    }
}