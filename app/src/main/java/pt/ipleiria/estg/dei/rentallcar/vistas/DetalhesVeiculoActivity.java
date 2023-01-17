package pt.ipleiria.estg.dei.rentallcar.vistas;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;

import pt.ipleiria.estg.dei.rentallcar.MenuMainActivity;
import pt.ipleiria.estg.dei.rentallcar.R;
import pt.ipleiria.estg.dei.rentallcar.modelo.SingletonGestorVeiculos;
import pt.ipleiria.estg.dei.rentallcar.modelo.Veiculo;

public class DetalhesVeiculoActivity extends AppCompatActivity {

    private Veiculo veiculo;
    private TextView etMarca,etModelo, etPreco, etCombustivel, etMatricula;
    private ImageView imgCapa;
    private ListView listaExtras;
    private FloatingActionButton fabGuardar;
    public static final String IDVEICULO = "IDVEICULO";
    public static final int MIN_CHAR = 3;
    public static final int MIN_NUMERO = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_veiculo);
        etMarca = findViewById(R.id.etMarca);
        etCombustivel = findViewById(R.id.etCombustivel);
        etModelo = findViewById(R.id.etModelo);
        etPreco = findViewById(R.id.etPreco);
        etMatricula = findViewById(R.id.etMatricula);
        imgCapa = findViewById(R.id.imgCapa);
        fabGuardar = findViewById(R.id.fabGuardar);
        //listaExtras = findViewById(R.id.listaExtras);

        int id = getIntent().getIntExtra(IDVEICULO, 0);

        veiculo = SingletonGestorVeiculos.getInstance(getApplicationContext()).getVeiculo(id);
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
                    Intent intent = new Intent();
                    if (veiculo != null) {
                        //editar livro
                        //livroAux= new Livro(preco,livro.getCapa(),marca,modelo,combustivel);
                        veiculo.setMarca(marca);
                        veiculo.setCombustivel(combustivel);
                        veiculo.setModelo(modelo);
                        veiculo.setPreco(preco);
                        veiculo.setMatricula(matricula);
                        SingletonGestorVeiculos.getInstance(getApplicationContext()).editarVeiculoBD(veiculo);
                        intent.putExtra(MenuMainActivity.OPERACAO, MenuMainActivity.EDIT);

                    } else {
                        //criar Livro
                        Veiculo livroAux = new Veiculo(0, preco, "http://amsi.dei.estg.ipleiria.pt/img/ipl_semfundo.png", marca, modelo, combustivel, matricula);
                        SingletonGestorVeiculos.getInstance(getApplicationContext()).adicionarVeiculoAPI(livroAux, getApplicationContext());

                    }
                    setResult(RESULT_OK, intent);
                    finish();
                }

            }
        });
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