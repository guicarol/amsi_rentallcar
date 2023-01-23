package pt.ipleiria.estg.dei.rentallcar.vistas;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import pt.ipleiria.estg.dei.rentallcar.MenuMainActivity;
import pt.ipleiria.estg.dei.rentallcar.R;
import pt.ipleiria.estg.dei.rentallcar.modelo.SingletonGestorVeiculos;
import pt.ipleiria.estg.dei.rentallcar.modelo.Veiculo;

public class AssistenciaActivity extends AppCompatActivity {

    private Veiculo veiculo;
    private int idprofile, idveiculo;
    private TextView etVeiculo, etDataPedido, etEstado;
    private ImageView imgCapa;
    private EditText etMensagem, etLocalizacao;
    private Button btnEnviarPedido;
    public static final String IDVEICULO = "IDVEICULO";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assistencia);
        etVeiculo = findViewById(R.id.etVeiculo);
        etDataPedido = findViewById(R.id.etDataPedido);
        etMensagem = findViewById(R.id.etMensagem);
        etLocalizacao = findViewById(R.id.etLocalizacao);
        String etEstado = "nao_resolvido";
        SharedPreferences sharedPreferences = getSharedPreferences("user_info", MODE_PRIVATE);
        idprofile = sharedPreferences.getInt("id", -1);
        imgCapa = findViewById(R.id.imgCapa);
        idveiculo = getIntent().getIntExtra(IDVEICULO, 0);

        btnEnviarPedido = findViewById(R.id.btnEnviarPedido);
        btnEnviarPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                criarPedidoAssistencia(idprofile, idveiculo,  etMensagem.getText().toString(), etLocalizacao.getText().toString(), etEstado);

            }
        });

        veiculo = SingletonGestorVeiculos.getInstance(getApplicationContext()).getVeiculo(idveiculo);
        if (veiculo != null) {
            carregarVeiculo();
        } else {
            setTitle(getString(R.string.act_detalhes));
        }

    }

    private void criarPedidoAssistencia(int idprofile, int idveiculo, String etmensagem, String etlocalizacao, String etestado) {
        String url = SingletonGestorVeiculos.mUrlAPI + "reserva/pedido?idveiculo=" + idveiculo + "&idprofile=" + idprofile + "&id=1&etmensagem=" + etmensagem + "&etlocalizacao=" + etlocalizacao + "&etestado=" + etestado;
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
                            Toast.makeText(AssistenciaActivity.this, message, Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(AssistenciaActivity.this, MenuMainActivity.class);
                            startActivity(intent);
                            finish();
                        } catch (
                                JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Handle the error
                Toast.makeText(AssistenciaActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
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
        RequestQueue requestQueue = Volley.newRequestQueue(AssistenciaActivity.this);
        requestQueue.add(jsonObjectRequest);
    }

    private void carregarVeiculo() {
        Resources res = getResources();
        String nome = String.format(res.getString(R.string.act_livro), veiculo.getMarca() + " " + veiculo.getModelo());
        setTitle(nome);
        etVeiculo.setText(veiculo.getMarca());
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        etDataPedido.setText(format.format(calendar.getTime()));

        Glide.with(this)
                .load(veiculo.getDescricao())
                .placeholder(R.drawable.assistencia)
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
}