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
import pt.ipleiria.estg.dei.rentallcar.listeners.ExtrasListener;
import pt.ipleiria.estg.dei.rentallcar.listeners.PerfilListener;
import pt.ipleiria.estg.dei.rentallcar.listeners.VeiculosListener;
import pt.ipleiria.estg.dei.rentallcar.utils.PerfilJsonParser;
import pt.ipleiria.estg.dei.rentallcar.utils.RotasJsonParser;
import pt.ipleiria.estg.dei.rentallcar.utils.VeiculosJsonParser;


public class SingletonGestorVeiculos {
    private ArrayList<Veiculo> veiculos;
    private static SingletonGestorVeiculos instance = null;
    private VeiculoBDHelper veiculosBD;
    private static RequestQueue volleyQueue = null;
    private static final String mUrlAPI = "http://192.168.1.70/plsi_rentallcar/backend/web/api/";
    private static final String TOKEN = "AMSI-TOKEN";
    private VeiculosListener veiculosListener;
    private DetalhesListener detalhesListener;

    private ExtrasListener extrasListener;
    private ArrayList<Extras> extras;

    private PerfilListener perfilListener;
    private Perfil perfil;

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
    public void setDadosPessoaisListener(PerfilListener perfilListener) {
        this.perfilListener = perfilListener;
    }


    //region LIVRO-BD

    public ArrayList<Veiculo> getVeiculosBD() {
        veiculos = veiculosBD.getAllLivroBD();
        return new ArrayList(veiculos);
    }

    public Veiculo getVeiculo(int id) {
        for (Veiculo l : veiculos)
            if (l.getId() == id)
                return l;
        return null;
    }

    public void adicionarVeiculoBD(Veiculo veiculo) {
        veiculosBD.adicionarLivroBD(veiculo);
    }

    public void adicionarVeiculosBD(ArrayList<Veiculo> veiculos) {
        veiculosBD.removerAllLivrosBD();
        for (Veiculo l : veiculos)
            adicionarVeiculoBD(l);
    }

    public void removerVeiculoBD(int id) {
        Veiculo veiculoAux = getVeiculo(id);
        if (veiculoAux != null) {
            if (veiculosBD.removerLivroBD(id)) ;
        }
    }

    public void editarVeiculoBD(Veiculo veiculo) {
        Veiculo veiculoAux = getVeiculo(veiculo.getId());
        if (veiculoAux != null) {
            veiculosBD.editarLivroBD(veiculo);
        }
    }
    //endregion


    //region LIVRO-API
    public void adicionarVeiculoAPI(final Veiculo veiculo, final Context context) {
        if (!VeiculosJsonParser.isConnectionInternet(context))
            Toast.makeText(context, "Sem ligaçao a internet", Toast.LENGTH_LONG).show();
        else {
            StringRequest req = new StringRequest(Request.Method.POST, mUrlAPI + "veiculo", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    adicionarVeiculoBD(VeiculosJsonParser.parserJsonVeiculo(response));
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

    public void getAllVeiculosAPI(final Context context) {
        if (!VeiculosJsonParser.isConnectionInternet(context)) {
            Toast.makeText(context, "Sem ligaçao a internet", Toast.LENGTH_LONG).show();

            if (veiculosListener != null)
                veiculosListener.onRefreshListaVeiculos(veiculosBD.getAllLivroBD());
        } else {
            JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, mUrlAPI + "veiculo", null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    veiculos = VeiculosJsonParser.parserJsonVeiculos(response);
                    adicionarVeiculosBD(veiculos);

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

    public void removerVeiculoAPI(final Veiculo veiculo, final Context context) {
        if (!VeiculosJsonParser.isConnectionInternet(context))
            Toast.makeText(context, "Sem ligaçao a internet", Toast.LENGTH_LONG).show();
        else {
            StringRequest req = new StringRequest(Request.Method.DELETE, mUrlAPI + "veiculo" + "/" + veiculo.getId(), new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    removerVeiculoBD(veiculo.getId());
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

    public void editarVeiculoAPI(final Veiculo veiculo, final Context context) {
        if (!VeiculosJsonParser.isConnectionInternet(context))
            Toast.makeText(context, "Sem ligaçao a internet", Toast.LENGTH_LONG).show();
        else {
            StringRequest req = new StringRequest(Request.Method.PUT, mUrlAPI + "veiculo" + "/" + veiculo.getId(), new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    editarVeiculoBD(veiculo);
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

    //region métodos Rotas
    public void getAllExtrasAPI(final Context context) {
        if (!RotasJsonParser.isConnectionInternet(context)) {
            Toast.makeText(context, "Sem internet", Toast.LENGTH_SHORT).show();

        } else {
            JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, mUrlAPI + "extra", null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    extras = RotasJsonParser.parseJsonExtras(response);

                    if (extrasListener != null)
                        extrasListener.onRefreshListaExtras(extras);

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, "erro", Toast.LENGTH_SHORT).show();
                }
            });
            volleyQueue.add(req);
        }
    }

    public Extras getExtra(int id) {
        for (Extras b : extras)
            if (b.getId() == id)
                return b;
        return null;
    }
//endregion

    //region métodos getDadosPessoais
    public Perfil getDadosPessoaisAPI (final Context context, int id){
        if (!PerfilJsonParser.isConnectionInternet(context)) {
            Toast.makeText(context, "Sem internet", Toast.LENGTH_SHORT).show();

        } else {
            StringRequest req = new StringRequest(Request.Method.GET, mUrlAPI + "profile/view?id=" + id, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    perfil = PerfilJsonParser.parseJsonDadosPessoal(response);

                    if(perfilListener!=null)
                        perfilListener.onRefreshPerfil(perfil);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
            volleyQueue.add(req);
            return perfil;
        }
        return null;
    }
//endregion

}
