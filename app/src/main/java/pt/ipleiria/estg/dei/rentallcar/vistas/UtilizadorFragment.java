package pt.ipleiria.estg.dei.rentallcar.vistas;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

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
        SingletonGestorVeiculos.getInstance(getContext()).getDadosPessoaisAPI(getContext(), id);

        logout = (TextView) view.findViewById(R.id.LogOut);
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
                teste();
            }
        });

        perfil = SingletonGestorVeiculos.getInstance(getContext()).getDadosPessoaisAPI(getContext(), 12);

        return view;
    }

  /*  private void updateUser() {
        btnalterar.setEnabled(false);

        String url = "http://192.168.1.70/plsi_rentallcar/backend/web/api/user/update";

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name", etUsername.getText().toString());
            jsonObject.put("email", etEmail.getText().toString());
            //jsonObject.put("password", passwordEditText.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.PUT, url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        btnalterar.setEnabled(true);

                        try {
                            if (response.has("status") && response.getString("status").equals("success")) {
                                // user data has been updated successfully
                                Toast.makeText(Utilizador.super.getContext(), "User data has been updated successfully", Toast.LENGTH_SHORT).show();
                            } else {
                                // there's an error
                                Toast.makeText(Utilizador.super.getContext(), "Error updating user data", Toast.LENGTH_SHORT).show();
                            }
                        } catch (
                                JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        btnalterar.setEnabled(true);
                        Log.e("VolleyError", error.toString());
                        Toast.makeText(Utilizador.super.getContext(), "Error updating user data", Toast.LENGTH_SHORT).show();
                    }
                }
        ) {

        };

        RequestQueue requestQueue = Volley.newRequestQueue(Utilizador.super.getContext());
        requestQueue.add(jsonObjectRequest);
    }*/

    private void updateUser() {
        String name = etUsername.getText().toString();
        String email = etEmail.getText().toString();
        // String password = passwordEditText.getText().toString();

        HashMap<String, String> params = new HashMap<>();
        params.put("name", name);
        params.put("email", email);
        // params.put("password", password);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, "http://192.168.1.70/plsi_rentallcar/backend/web/api/user/update", new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getString("status").equals("success")) {
                                // update the user data in shared preferences
                                SharedPreferences sharedPreferences = getContext().getSharedPreferences("user_data", MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("name", name);
                                editor.putString("email", email);
                                editor.apply();
                                // display success message
                                Toast.makeText(UtilizadorFragment.super.getContext(), "User data updated successfully", Toast.LENGTH_SHORT).show();
                            } else {
                                // display error message
                                Toast.makeText(UtilizadorFragment.super.getContext(), "Error updating user data", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // display error message
                        Toast.makeText(UtilizadorFragment.super.getContext(), "Error updating user data", Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                headers.put("Authorization", "Bearer " + getContext().getSharedPreferences("user_data", MODE_PRIVATE).getString("auth_token", ""));
                return headers;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(UtilizadorFragment.super.getContext());
        requestQueue.add(jsonObjectRequest);
    }

    private void teste() {
        String name = etUsername.getText().toString();
        String email = etEmail.getText().toString();
        String url = "https://example.com/api/update-user?id=" + id;

        String loginUrl = "http://192.168.1.70/plsi_rentallcar/backend/web/api/user/update?id="+ id;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, loginUrl,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getBoolean("success")) {
                                // Handle successful login
                                // For example, you can start a new activity or save the user's information in SharedPreferences


                                // Intent dados = new Intent(this, Utilizador.class);
                                //dados.putExtra("EMAIL", email);
                            } else {

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
                params.put("username", name);
                params.put("email", email);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json;charset=UTF-8");
                headers.put("Accept", "application/json");
                String credentials = "admin:admin12345"; // Your username and password
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
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    @Override
    public void onRefreshPerfil(Perfil perfil) {
        if (perfil != null) {
            etNome.setText(perfil.getNome());
            etApelido.setText(perfil.getApelido());
            etTelefone.setText(perfil.getTelemovel());
            etNif.setText(perfil.getNif());
            etEmail.setText(email);
            etUsername.setText(username);
            //etNome.setText(" " + perfil.getNome() + " " + perfil.getApelido());
        }
    }
}