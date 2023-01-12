package pt.ipleiria.estg.dei.rentallcar.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import pt.ipleiria.estg.dei.rentallcar.modelo.Perfil;

public class PerfilJsonParser {

    public static ArrayList<Perfil> parseJsonDadosPessoais(JSONArray response) {
        ArrayList<Perfil> dadosPessoais = new ArrayList<>();
        try {
            for (int i = 0; i < response.length(); i++) {
                JSONObject dados = (JSONObject) response.get(i);
                int id = dados.getInt("id_profile");
                String nome = dados.getString("nome");
                String apelido = dados.getString("apelido");
                //String imgPerfil = dados.getString("imgPerfil");
                String telemovel = dados.getString("telemovel");
                String nif = dados.getString("nif");
                Perfil auxDadosPessoal = new Perfil(id, nome, apelido, /*imgPerfil,*/ telemovel, nif);
                dadosPessoais.add(auxDadosPessoal);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return dadosPessoais;
    }

    public static Perfil parseJsonDadosPessoal(String response) {
        Perfil auxDadosPessoal = null;
        try {
            JSONObject dados = new JSONObject(response);
            int id = dados.getInt("id_profile");
            String nome = dados.getString("nome");
            String apelido = dados.getString("apelido");
            //String imgPerfil = dados.getString("imgPerfil");
            String telemovel = dados.getString("telemovel");
            String nif = dados.getString("nif");
            auxDadosPessoal = new Perfil(id, nome, apelido, /*imgPerfil,*/ telemovel, nif);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return auxDadosPessoal;
    }

    public static Boolean isConnectionInternet(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }
}