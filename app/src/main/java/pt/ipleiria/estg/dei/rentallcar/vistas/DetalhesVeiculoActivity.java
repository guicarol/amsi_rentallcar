package pt.ipleiria.estg.dei.rentallcar.vistas;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pt.ipleiria.estg.dei.rentallcar.MenuMainActivity;
import pt.ipleiria.estg.dei.rentallcar.R;
import pt.ipleiria.estg.dei.rentallcar.modelo.SingletonGestorVeiculos;
import pt.ipleiria.estg.dei.rentallcar.modelo.Veiculo;

public class DetalhesVeiculoActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Veiculo veiculo;
    private int idprofile, idveiculo, idseguro, idLocalizacaol, idLocalizacaod;
    private TextView etMarca, etModelo, etPreco, etCombustivel, etMatricula, etDescricao, etTipoVeiculo, etFranquia;
    private ImageView imgCapa;
    private FloatingActionButton fabGuardar;
    private Button btnReservar;
    public static final String IDVEICULO = "IDVEICULO";
    public static final int MIN_CHAR = 3;
    public static final int MIN_NUMERO = 4;
    private Spinner dpwnseguro, dpwdn_localizacaol, dpwn_localizacaod;
    private LinearLayout ctnrextras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_veiculo);
        etMarca = findViewById(R.id.etMarca);
        etCombustivel = findViewById(R.id.etCombustivel);
        etModelo = findViewById(R.id.etModelo);
        etPreco = findViewById(R.id.etPreco);
        etMatricula = findViewById(R.id.etMatricula);
        etDescricao = findViewById(R.id.etDescricao);
        etTipoVeiculo = findViewById(R.id.etTipoVeiculo);
        etFranquia = findViewById(R.id.etFranquia);

        SharedPreferences sharedPreferences = getSharedPreferences("user_info", MODE_PRIVATE);
        idprofile = sharedPreferences.getInt("id", -1);
        imgCapa = findViewById(R.id.imgCapa);
        idveiculo = getIntent().getIntExtra(IDVEICULO, 0);

        //listaExtras = findViewById(R.id.listaExtras);
        //getDropdownSeguro();

        //listaExtras = findViewById(R.id.listaExtras);


        btnReservar = findViewById(R.id.btnReservar);

        btnReservar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetalhesVeiculoActivity.this, ReservaVeiculoActivity.class);
                intent.putExtra(ReservaVeiculoActivity.IDVEICULO, (int) idveiculo);
                startActivity(intent);
                finish();
            }
        });

        fabGuardar = findViewById(R.id.fabGuardar);
        fabGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToFavorites();
            }
        });

        veiculo = SingletonGestorVeiculos.getInstance(getApplicationContext()).getVeiculo(idveiculo);
        if (veiculo != null) {
            carregarVeiculo();
            fabGuardar.setImageResource(R.drawable.ic_action_favorito);
        } else {
            setTitle(getString(R.string.act_detalhes));
            fabGuardar.setImageResource(R.drawable.ic_action_adicionar);
        }
        fabGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isVeiculoValido()) {
                    String marca = etMarca.getText().toString();
                    String combustivel = etCombustivel.getText().toString();
                    String modelo = etModelo.getText().toString();
                    int preco = Integer.parseInt(etPreco.getText().toString());
                    String matricula = etMatricula.getText().toString();
                    String extra = ctnrextras.toString();
                    String seguro = dpwnseguro.toString();
                    Intent intent = new Intent();
                    /*if (veiculo != null) {
                        //editar livro
                        //livroAux= new Livro(preco,livro.getCapa(),marca,modelo,combustivel);
                        veiculo.setMarca(marca);
                        veiculo.setCombustivel(combustivel);
                        veiculo.setModelo(modelo);
                        veiculo.setPreco(preco);
                        veiculo.setMatricula(matricula);
                        SingletonGestorVeiculos.getInstance(getApplicationContext()).editarVeiculoBD(veiculo);
                        intent.putExtra(MenuMainActivity.OPERACAO, MenuMainActivity.EDIT);

                    } else */
                    {
                        //adicionar veiculo favorito
                      //  Veiculo livroAux = new Veiculo(idveiculo, preco, "http://amsi.dei.estg.ipleiria.pt/img/ipl_semfundo.png", marca, modelo, combustivel, matricula);
                        //SingletonGestorVeiculos.getInstance(getApplicationContext()).adicionarVeiculoAPI(livroAux, getApplicationContext());
                    }
                    setResult(RESULT_OK, intent);
                    finish();
                }

            }
        });
    }

    private void criarReserva(int idprofile, int idveiculo, int idseguro, String dataL, String dataD, int idLocalizacaol, int idLocalizacaod) {
        String url = SingletonGestorVeiculos.mUrlAPI + "veiculo/create?data_inicio=2023-12-23%2000:00:00&data_fim=2023-12-23%2000:00:00&veiculo_id=" + idveiculo + "&profile_id=" + idprofile + "&id=1&seguro_id=" + idseguro + "&localizacaol=" + idLocalizacaol + "&localizacaod=" + idLocalizacaod;
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("nome", "teste");
            //jsonBody.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, jsonBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Handle the response
                        try {
                            String message = response.getString("message");
                            Toast.makeText(DetalhesVeiculoActivity.this, message, Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(DetalhesVeiculoActivity.this, MenuMainActivity.class);
                            startActivity(intent);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Handle the error
                Toast.makeText(DetalhesVeiculoActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
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
        RequestQueue requestQueue = Volley.newRequestQueue(DetalhesVeiculoActivity.this);
        requestQueue.add(jsonObjectRequest);
    }

    private void getDropdownSeguro() {
        String url = SingletonGestorVeiculos.mUrlAPI + "seguro";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    List<String> list = new ArrayList<>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String value = jsonObject.getString("cobertura");
                        list.add(value);
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(DetalhesVeiculoActivity.this, android.R.layout.simple_spinner_item, list);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    dpwnseguro.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DetalhesVeiculoActivity.this, "Error loading dropdown data", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + getSharedPreferences("user_data", MODE_PRIVATE).getString("auth_token", ""));
                return headers;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void getDropdownLocalizacaol() {
        String url = SingletonGestorVeiculos.mUrlAPI + "localizacao";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    List<String> list = new ArrayList<>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String value = jsonObject.getString("morada");
                        list.add(value);
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(DetalhesVeiculoActivity.this, android.R.layout.simple_spinner_item, list);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    dpwdn_localizacaol.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DetalhesVeiculoActivity.this, "Error loading dropdown data", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + getSharedPreferences("user_data", MODE_PRIVATE).getString("auth_token", ""));
                return headers;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void getDropdownLocalizacaod() {
        String url = SingletonGestorVeiculos.mUrlAPI + "localizacao";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    List<String> list = new ArrayList<>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String value = jsonObject.getString("morada");
                        list.add(value);
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(DetalhesVeiculoActivity.this, android.R.layout.simple_spinner_item, list);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    dpwn_localizacaod.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DetalhesVeiculoActivity.this, "Error loading dropdown data", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + getSharedPreferences("user_data", MODE_PRIVATE).getString("auth_token", ""));
                return headers;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Spinner spinner = (Spinner) parent;
        if (spinner.getId() == R.id.dpwn_seguro) {
            // Here, you can get the selected item's id
            long selectedId = id + 1;

            idseguro = Integer.parseInt(String.valueOf(selectedId));

            // Do something with the id, like sending it to the API
        } else if (spinner.getId() == R.id.dpwn_localizacaol) {
            // Here, you can get the selected item's id
            long selectedId = id + 1;

            idLocalizacaol = Integer.parseInt(String.valueOf(selectedId));

            // Do something with the id, like sending it to the API
        } else if (spinner.getId() == R.id.dpwn_localizacaod) {
            // Here, you can get the selected item's id
            long selectedId = id + 1;

            idLocalizacaod = Integer.parseInt(String.valueOf(selectedId));

            // Do something with the id, like sending it to the API
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void getCheckboxData() {
        String url = SingletonGestorVeiculos.mUrlAPI + "extra";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String value = jsonObject.getString("descricao");
                        CheckBox checkBox = new CheckBox(DetalhesVeiculoActivity.this);
                        checkBox.setText(value);
                        ctnrextras.addView(checkBox);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DetalhesVeiculoActivity.this, "Error loading checkbox data", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + getSharedPreferences("user_data", MODE_PRIVATE).getString("auth_token", ""));
                return headers;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void addToFavorites() {
        ArrayList<Veiculo> itemsList = new ArrayList<>();
    //    itemsList.add(new Veiculo(idveiculo, veiculo.getPreco(), veiculo.getDescricao(), veiculo.getMarca(), veiculo.getModelo(), veiculo.getCombustivel(), veiculo.getMatricula()));
    }

    private boolean isVeiculoValido() {
        String titulo = etMarca.getText().toString();
        String autor = etCombustivel.getText().toString();
        String serie = etModelo.getText().toString();
        String ano = etPreco.getText().toString();
        if (titulo.length() < MIN_CHAR) {
            etMarca.setError("Titulo invalido");
            return false;
        }

        if (titulo.length() < MIN_CHAR) {
            etCombustivel.setError("Serie invalida");
            return false;
        } else if (autor.length() < MIN_CHAR) {
            etCombustivel.setError("autor invalido");
            return false;
        } else if (serie.length() < MIN_CHAR) {
            etModelo.setError("serie invalido");
            return false;
        } else {
            int anoNumero = Integer.parseInt(ano);
            if (anoNumero < 1900 || anoNumero > Calendar.getInstance().get(Calendar.YEAR)) {
                etPreco.setError("Ano invalido");
                return false;
            }
        }
        return true;
    }

    private void carregarVeiculo() {
        Resources res = getResources();
        String nome = String.format(res.getString(R.string.act_livro), veiculo.getMarca() + " " + veiculo.getModelo());
        setTitle(nome);
        etMarca.setText(veiculo.getMarca());
        etModelo.setText(veiculo.getModelo());
        etCombustivel.setText(veiculo.getCombustivel());
        etPreco.setText(veiculo.getPreco() + "");
        etMatricula.setText(veiculo.getMatricula());
        etDescricao.setText(veiculo.getDescricao());
       // etTipoVeiculo.setText(veiculo.getTipoveiculo()+"");
        //etFranquia.setText(veiculo.getFranquia()+"");
        Glide.with(this)
                .load(veiculo.getDescricao())
                .placeholder(R.drawable.logo)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgCapa);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (veiculo != null) {
            getMenuInflater().inflate(R.menu.menu_detalhes_livro, menu);
            return super.onCreateOptionsMenu(menu);
        }
        return false;
    }

    private void dialogRemove() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Remover Livro")
                .setMessage("Pretende mesmo remover o livro")
                .setIcon(android.R.drawable.ic_delete)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SingletonGestorVeiculos.getInstance(getApplicationContext()).removerVeiculoBD(veiculo.getId());
                        Intent intent = new Intent();
                        intent.putExtra(MenuMainActivity.OPERACAO, MenuMainActivity.DELETE);
                        setResult(RESULT_OK, intent);

                        finish();

                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .show();
    }

}