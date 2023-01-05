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
    private static final String mUrLAPIVeiculos = "http://localhost:8080/";
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

    public void adicionarLivroBD(Veiculo veiculo) {
        veiculosBD.adicionarLivroBD(veiculo);
    }

    public void adicionarLivrosBD(ArrayList<Veiculo> veiculos) {
        veiculosBD.removerAllLivrosBD();
        for (Veiculo l : veiculos)
            adicionarLivroBD(l);
    }

    public void removerLivroBD(int id) {
        Veiculo veiculoAux = getLivro(id);
        if (veiculoAux != null) {
            if (veiculosBD.removerLivroBD(id)) ;
        }
    }

    public void editarLivroBD(Veiculo veiculo) {
        Veiculo veiculoAux = getLivro(veiculo.getId());
        if (veiculoAux != null) {
            veiculosBD.editarLivroBD(veiculo);
        }
    }
    //endregion


    //region LIVRO-API
    public void adicionarLivroAPI(final Veiculo veiculo, final Context context) {
        if (!VeiculosJsonParser.isConnectionInternet(context))
            Toast.makeText(context, "Sem ligaçao a internet", Toast.LENGTH_LONG).show();
        else {
            StringRequest req = new StringRequest(Request.Method.POST, mUrLAPIVeiculos, new Response.Listener<String>() {
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
                    params.put("titulo", veiculo.getMarca());
                    params.put("autor", veiculo.getModelo());
                    params.put("serie", veiculo.getCombustivel());
                    params.put("ano", veiculo.getPreco() + "");
                    params.put("capa", veiculo.getDescricao());
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
            JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, mUrLAPIVeiculos + "veiculo", null, new Response.Listener<JSONArray>() {
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

    public void removerLivroAPI(final Veiculo veiculo, final Context context) {
        if (!VeiculosJsonParser.isConnectionInternet(context))
            Toast.makeText(context, "Sem ligaçao a internet", Toast.LENGTH_LONG).show();
        else {
            StringRequest req = new StringRequest(Request.Method.DELETE, mUrLAPIVeiculos + "/" + veiculo.getId(), new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    removerLivroBD(veiculo.getId());
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

    public void editarLivroAPI(final Veiculo veiculo, final Context context) {
        if (!VeiculosJsonParser.isConnectionInternet(context))
            Toast.makeText(context, "Sem ligaçao a internet", Toast.LENGTH_LONG).show();
        else {
            StringRequest req = new StringRequest(Request.Method.PUT, mUrLAPIVeiculos + "/" + veiculo.getId(), new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    editarLivroBD(veiculo);
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
                    params.put("titulo", veiculo.getMarca());
                    params.put("autor", veiculo.getModelo());
                    params.put("serie", veiculo.getCombustivel());
                    params.put("ano", veiculo.getPreco() + "");
                    params.put("capa", veiculo.getDescricao());
                    return params;
                }
            };
            volleyQueue.add(req);

        }

    }


    //endregion


}
