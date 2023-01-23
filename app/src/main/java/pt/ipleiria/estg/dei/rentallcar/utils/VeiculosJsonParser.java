package pt.ipleiria.estg.dei.rentallcar.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import pt.ipleiria.estg.dei.rentallcar.modelo.Veiculo;

public class VeiculosJsonParser {
    public static ArrayList<Veiculo> parserJsonVeiculos(JSONArray response) {
        ArrayList<Veiculo> livros = new ArrayList<>();
        try {
            for (int i = 0; i < response.length(); i++) {
                JSONObject livro = (JSONObject) response.get(i);
                int id_veiculo = livro.getInt("id_veiculo");
                String marca = livro.getString("marca");
                String modelo = livro.getString("modelo");
                String combustivel = livro.getString("combustivel");
                int preco = livro.getInt("preco");
                String descricao = livro.getString("descricao");
                String matricula = livro.getString("matricula");
                int franquia = livro.getInt("franquia");
                Veiculo livroAux = new Veiculo(id_veiculo, preco,descricao, marca, modelo, combustivel,matricula,franquia);
                livros.add(livroAux);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return livros;
    }

    public static Veiculo parserJsonVeiculo(String response) {
        Veiculo livroAux = null;
        try {
            JSONObject livro = new JSONObject(response);
            int id = livro.getInt("id_veiculo");
            String marca = livro.getString("marca");
            String modelo = livro.getString("modelo");
            String combustivel = livro.getString("combustivel");
            int preco = livro.getInt("preco");
            String descricao = livro.getString("descricao");
            String matricula = livro.getString("matricula");
            int franquia = livro.getInt("franquia");
            livroAux = new Veiculo(id, preco, descricao, marca, modelo, combustivel,matricula,franquia);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return livroAux;
    }

    public static String parserJsonLogin(String response) {
        String token = null;
        try {
            JSONObject login = new JSONObject(response);
            if (login.getBoolean("success"))
                token = login.getString("token");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return token;
    }

    public static boolean isConnectionInternet(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        return ni != null && ni.isConnected();
    }

}
