package pt.ipleiria.estg.dei.rentallcar.vistas;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Button;
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
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import pt.ipleiria.estg.dei.rentallcar.MenuMainActivity;
import pt.ipleiria.estg.dei.rentallcar.R;
import pt.ipleiria.estg.dei.rentallcar.modelo.Reserva;
import pt.ipleiria.estg.dei.rentallcar.modelo.SingletonGestorVeiculos;

public class DetalhesReservaActivity extends AppCompatActivity {

    private Reserva reserva;
    private int idprofile, idreserva;
    private TextView etMarca, etModelo, tvLocalL, etseguro, tvDataL, tvLocalD, tvDataD;
    private ImageView imgCapa;
    private FloatingActionButton fabGuardar;
    private Button btnReservar;
    public static final String IDVEICULO = "IDVEICULO";
    public static final int MIN_CHAR = 3;
    public static final int MIN_NUMERO = 4;
    public static final String IDRESERVA = "IDRESERVA";

    private Spinner dpwnseguro, dpwdn_localizacaol, dpwn_localizacaod;
    private LinearLayout ctnrextras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_reserva);
        etMarca = findViewById(R.id.etMarca);
        etModelo = findViewById(R.id.etModelo);
        etseguro = findViewById(R.id.etSeguro);
        tvLocalL = findViewById(R.id.tvLocalL);
        tvDataL = findViewById(R.id.tvDataL);
        tvLocalD = findViewById(R.id.tvLocalD);
        tvDataD = findViewById(R.id.tvDataD);
        idreserva = getIntent().getIntExtra(IDRESERVA, 0);

            /*//listaExtras = findViewById(R.id.listaExtras);
        //getDropdownSeguro();
        //listaExtras = findViewById(R.id.listaExtras);
       // btnReservar = findViewById(R.id.btnReservar);
        btnReservar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetalhesReservaActivity.this, ReservaVeiculoActivity.class);
                intent.putExtra(ReservaVeiculoActivity.IDVEICULO, (int) idveiculo);
                startActivity(intent);
            }
        });

       // fabGuardar = findViewById(R.id.fabGuardar);*/

        reserva = SingletonGestorVeiculos.getInstance(getApplicationContext()).getReserva(idreserva);
        if (reserva != null) {
            carregarVeiculo();
        } else {
            setTitle(getString(R.string.act_detalhes));
        }
        /*fabGuardar.setOnClickListener(new View.OnClickListener() {
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

                    } else
                    {
                        //adicionar veiculo favorito
                        Veiculo livroAux = new Veiculo(idveiculo, preco, "http://amsi.dei.estg.ipleiria.pt/img/ipl_semfundo.png", marca, modelo, combustivel, matricula);
                        SingletonGestorVeiculos.getInstance(getApplicationContext()).adicionarVeiculoAPI(livroAux, getApplicationContext());
                    }
                    setResult(RESULT_OK, intent);
                    finish();
                }

            }
        });*/
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
                            Toast.makeText(DetalhesReservaActivity.this, message, Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(DetalhesReservaActivity.this, MenuMainActivity.class);
                            startActivity(intent);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Handle the error
                Toast.makeText(DetalhesReservaActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
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
        RequestQueue requestQueue = Volley.newRequestQueue(DetalhesReservaActivity.this);
        requestQueue.add(jsonObjectRequest);
    }

    private void carregarVeiculo() {
        Resources res = getResources();
        String nome = String.format(res.getString(R.string.act_livro), reserva.getMarca() + " " + reserva.getModelo());
        setTitle(nome);
        etMarca.setText(reserva.getMarca());
        etModelo.setText(reserva.getModelo());
        etseguro.setText(reserva.getSeguro());
        tvLocalL.setText(reserva.getLocalizacao_levantamento());
        tvDataL.setText(reserva.getData_inicio() + "");
        tvLocalD.setText(reserva.getLocalizacao_devolucao());
        tvDataD.setText(reserva.getData_fim() + "");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (reserva != null) {
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
                        SingletonGestorVeiculos.getInstance(getApplicationContext()).removerVeiculoBD(reserva.getId());
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