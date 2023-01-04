package pt.ipleiria.estg.dei.rentallcar.modelo;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import pt.ipleiria.estg.dei.rentallcar.MenuMainActivity;
import pt.ipleiria.estg.dei.rentallcar.listeners.DetalhesListener;
import pt.ipleiria.estg.dei.rentallcar.listeners.VeiculosListener;
import pt.ipleiria.estg.dei.rentallcar.utils.VeiculosJsonParser;


public class SingletonGestorVeiculos {
    private ArrayList<Veiculo> veiculos;
    private static SingletonGestorVeiculos instance = null;
    private VeiculoBDHelper veiculosBD;
    private static RequestQueue volleyQueue = null;
    private static final String mUrLAPILivros = "http://192.168.1.70/rentallcar_api/web/";
    private static final String TOKEN = "AMSI-TOKEN";
    private VeiculosListener veiculosListener;
    private DetalhesListener detalhesListener;


    public static synchronized SingletonGestorVeiculos getInstance(Context context) {
        if (instance == null)
            instance = new SingletonGestorVeiculos(context);
        volleyQueue = Volley.newRequestQueue(context);
        return instance;
    }

    private SingletonGestorVeiculos(Context context) {
        //inicializar o vetor
        //gerarDadosDinamico();
        veiculos = new ArrayList<>();
        veiculosBD = new VeiculoBDHelper(context);

    }

    public void setVeiculosListener(VeiculosListener veiculosListener) {
        this.veiculosListener = veiculosListener;
    }

    public void setDetalhesListener(DetalhesListener detalhesListener) {
        this.detalhesListener = detalhesListener;
    }
    /* private void gerarDadosDinamico() {
        livros = new ArrayList<>();
        livros.add(new Livro(2021, R.drawable.programarandroid2, "Programar em Android AMSI - 1", "2ª Temporada", "AMSI TEAM"));
        livros.add(new Livro(2021, R.drawable.programarandroid1, "Programar em Android AMSI - 2", "2ª Temporada", "AMSI TEAM"));
        livros.add(new Livro(2021, R.drawable.logoipl, "Programar em Android AMSI - 3", "2ª Temporada", "AMSI TEAM"));
        livros.add(new Livro(2021, R.drawable.programarandroid2, "Programar em Android AMSI - 4", "2ª Temporada", "AMSI TEAM"));
        livros.add(new Livro(2021, R.drawable.programarandroid1, "Programar em Android AMSI - 5", "2ª Temporada", "AMSI TEAM"));
        livros.add(new Livro(2021, R.drawable.logoipl, "Programar em Android AMSI - 6", "2ª Temporada", "AMSI TEAM"));
        livros.add(new Livro(2021, R.drawable.programarandroid2, "Programar em Android AMSI - 7", "2ª Temporada", "AMSI TEAM"));
        livros.add(new Livro(2021, R.drawable.programarandroid1, "Programar em Android AMSI - 8", "2ª Temporada", "AMSI TEAM"));
        livros.add(new Livro(2021, R.drawable.logoipl, "Programar em Android AMSI - 9", "2ª Temporada", "AMSI TEAM"));
        livros.add(new Livro(2021, R.drawable.programarandroid2, "Programar em Android AMSI - 10", "2ª Temporada", "AMSI TEAM"));
    }*/


    //region LIVRO-BD

    public ArrayList<Veiculo> getVeiculosBD() {
        veiculos = veiculosBD.getAllLivroBD();
        return new ArrayList(veiculos);
    }

    public Veiculo getLivro(int id) {
        for (Veiculo l : veiculos)
            if (l.getId() == id)
                return l;
        return null;
    }

    public void adicionarLivroBD(Veiculo livro) {
        veiculosBD.adicionarLivroBD(livro);
    }

    public void adicionarLivrosBD(ArrayList<Veiculo> livros) {
        veiculosBD.removerAllLivrosBD();
        for (Veiculo l : livros)
            adicionarLivroBD(l);
    }

    public void removerLivroBD(int id) {
        Veiculo livroAux = getLivro(id);
        if (livroAux != null) {
            if (veiculosBD.removerLivroBD(id)) ;
        }
    }

    public void editarLivroBD(Veiculo livro) {
        Veiculo livroAux = getLivro(livro.getId());
        if (livroAux != null) {
            veiculosBD.editarLivroBD(livro);
        }
    }
    //endregion


    //region LIVRO-API
    public void adicionarLivroAPI(final Veiculo livro, final Context context) {
        if (!VeiculosJsonParser.isConnectionInternet(context))
            Toast.makeText(context, "Sem ligaçao a internet", Toast.LENGTH_LONG).show();
        else {
            StringRequest req = new StringRequest(Request.Method.POST, mUrLAPILivros, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    adicionarLivroBD(VeiculosJsonParser.parserJsonLivro(response));
                    if (detalhesListener != null)
                        detalhesListener.onRefreshDetalhes(MenuMainActivity.ADD);

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("token", TOKEN);
                    params.put("titulo", livro.getMarca());
                    params.put("autor", livro.getModelo());
                    params.put("serie", livro.getCombustivel());
                    params.put("ano", livro.getPreco() + "");
                    params.put("capa", livro.getDescricao());
                    return params;
                }
            };
            volleyQueue.add(req);

        }

    }

    public void getAllLivrosAPI(final Context context) {
        if (!VeiculosJsonParser.isConnectionInternet(context)) {
            Toast.makeText(context, "Sem ligaçao a internet", Toast.LENGTH_LONG).show();

            if (veiculosListener != null)
                veiculosListener.onRefreshListaVeiculos(veiculosBD.getAllLivroBD());
        } else {
            JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, mUrLAPILivros + "veiculo", null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    veiculos = VeiculosJsonParser.parserJsonLivros(response);
                    adicionarLivrosBD(veiculos);

                    if (veiculosListener != null)
                        veiculosListener.onRefreshListaVeiculos(veiculos);

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
            volleyQueue.add(req);

        }


    }

    public void removerLivroAPI(final Veiculo livro, final Context context) {
        if (!VeiculosJsonParser.isConnectionInternet(context))
            Toast.makeText(context, "Sem ligaçao a internet", Toast.LENGTH_LONG).show();
        else {
            StringRequest req = new StringRequest(Request.Method.DELETE, mUrLAPILivros + "/" + livro.getId(), new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    removerLivroBD(livro.getId());
                    if (detalhesListener != null)
                        detalhesListener.onRefreshDetalhes(MenuMainActivity.DELETE);

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
            volleyQueue.add(req);

        }

    }

    public void editarLivroAPI(final Veiculo livro, final Context context) {
        if (!VeiculosJsonParser.isConnectionInternet(context))
            Toast.makeText(context, "Sem ligaçao a internet", Toast.LENGTH_LONG).show();
        else {
            StringRequest req = new StringRequest(Request.Method.PUT, mUrLAPILivros + "/" + livro.getId(), new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    editarLivroBD(livro);
                    if (detalhesListener != null)
                        detalhesListener.onRefreshDetalhes(MenuMainActivity.EDIT);

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("token", TOKEN);
                    params.put("titulo", livro.getMarca());
                    params.put("autor", livro.getModelo());
                    params.put("serie", livro.getCombustivel());
                    params.put("ano", livro.getPreco() + "");
                    params.put("capa", livro.getDescricao());
                    return params;
                }
            };
            volleyQueue.add(req);

        }

    }


    //endregion


}
