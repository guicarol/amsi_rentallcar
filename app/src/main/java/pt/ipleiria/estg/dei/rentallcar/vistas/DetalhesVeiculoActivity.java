package pt.ipleiria.estg.dei.rentallcar.vistas;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import pt.ipleiria.estg.dei.rentallcar.MenuMainActivity;
import pt.ipleiria.estg.dei.rentallcar.R;
import pt.ipleiria.estg.dei.rentallcar.modelo.DatabaseHelper;
import pt.ipleiria.estg.dei.rentallcar.modelo.Favorito;
import pt.ipleiria.estg.dei.rentallcar.modelo.SingletonGestorVeiculos;
import pt.ipleiria.estg.dei.rentallcar.modelo.Veiculo;

public class DetalhesVeiculoActivity extends AppCompatActivity {

    private Veiculo veiculo;
    private Favorito favorito;
    private int idprofile, idveiculo, idseguro, idLocalizacaol, idLocalizacaod;
    private TextView etMarca, etModelo, etPreco, etCombustivel, etMatricula, etDescricao, etTipoVeiculo, etFranquia;
    private ImageView imgCapa;
    private FloatingActionButton fabGuardar;
    private Button btnReservar;
    public static final String IDVEICULO = "IDVEICULO";
    private DatabaseHelper db;

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
        etFranquia = findViewById(R.id.etFranquia);

        SharedPreferences sharedPreferences = getSharedPreferences("user_info", MODE_PRIVATE);
        idprofile = sharedPreferences.getInt("id", -1);
        imgCapa = findViewById(R.id.imgCapa);
        idveiculo = getIntent().getIntExtra(IDVEICULO, 0);
        db = new DatabaseHelper(this);

        fabGuardar = findViewById(R.id.fabGuardar);

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

                DatabaseHelper myDb;

// Initialize the database helper in onCreate() method
                myDb = new DatabaseHelper(getApplicationContext());

// To insert data
                myDb.insertData(idprofile + "", veiculo.getMarca() + " " + veiculo.getModelo());
                Toast.makeText(DetalhesVeiculoActivity.this, "Veiculo guardado com sucesso", Toast.LENGTH_LONG).show();

            }
        });
    }

    private void carregarVeiculo() {
        Resources res = getResources();
        String nome = String.format(res.getString(R.string.act_livro), veiculo.getMarca() + " " + veiculo.getModelo());
        setTitle(nome);
        etMarca.setText(veiculo.getMarca());
        etModelo.setText(veiculo.getModelo());
        etCombustivel.setText(veiculo.getCombustivel());
        etPreco.setText(veiculo.getPreco() + "€");
        etMatricula.setText(veiculo.getMatricula());
        etDescricao.setText(veiculo.getDescricao());
        etFranquia.setText(veiculo.getFranquia() + "");
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