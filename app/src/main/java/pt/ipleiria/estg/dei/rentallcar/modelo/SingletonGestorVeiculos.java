package pt.ipleiria.estg.dei.rentallcar.modelo;

import android.content.Context;

import java.util.ArrayList;

import pt.ipleiria.estg.dei.rentallcar.R;


public class SingletonGestorVeiculos {
    private ArrayList<Veiculo> veiculos;
    private static SingletonGestorVeiculos instance = null;
    private LivroBDHelper livrosBD;

    public static synchronized SingletonGestorVeiculos getInstance(Context context) {
        if (instance == null)
            instance = new SingletonGestorVeiculos(context);
        return instance;
    }

    private SingletonGestorVeiculos(Context context) {
        //inicializar o vetor
        gerarDadosDinamico();
        veiculos = new ArrayList<>();
        livrosBD = new LivroBDHelper(context);

    }

    private void gerarDadosDinamico() {
        veiculos = new ArrayList<>();
        veiculos.add(new Veiculo(2021, R.drawable.programarandroid2, "Programar em Android AMSI - 1", "2ª Temporada", "AMSI TEAM"));
        veiculos.add(new Veiculo(2021, R.drawable.programarandroid1, "Programar em Android AMSI - 2", "2ª Temporada", "AMSI TEAM"));
        veiculos.add(new Veiculo(2021, R.drawable.logoipl, "Programar em Android AMSI - 3", "2ª Temporada", "AMSI TEAM"));
        veiculos.add(new Veiculo(2021, R.drawable.programarandroid2, "Programar em Android AMSI - 4", "2ª Temporada", "AMSI TEAM"));
        veiculos.add(new Veiculo(2021, R.drawable.programarandroid1, "Programar em Android AMSI - 5", "2ª Temporada", "AMSI TEAM"));
        veiculos.add(new Veiculo(2021, R.drawable.logoipl, "Programar em Android AMSI - 6", "2ª Temporada", "AMSI TEAM"));
        veiculos.add(new Veiculo(2021, R.drawable.programarandroid2, "Programar em Android AMSI - 7", "2ª Temporada", "AMSI TEAM"));
        veiculos.add(new Veiculo(2021, R.drawable.programarandroid1, "Programar em Android AMSI - 8", "2ª Temporada", "AMSI TEAM"));
        veiculos.add(new Veiculo(2021, R.drawable.logoipl, "Programar em Android AMSI - 9", "2ª Temporada", "AMSI TEAM"));
        veiculos.add(new Veiculo(2021, R.drawable.programarandroid2, "Programar em Android AMSI - 10", "2ª Temporada", "AMSI TEAM"));
    }


    //region LIVRO

    public ArrayList<Veiculo> getLivrosBD() {
        return new ArrayList(veiculos);
    }

    public Veiculo getLivro(int id) {
        for (Veiculo l : veiculos)
            if (l.getId() == id)
                return l;
        return null;
    }

    public void adicionarLivroBD(Veiculo veiculo) {
        if (veiculo != null)
            veiculos.add(veiculo);
    }

    public void removerLivroBD(int id) {
        Veiculo veiculoAux = getLivro(id);
        if (veiculoAux != null) {
            veiculos.remove(veiculoAux);
        }
    }

    public void editarLivroBD(Veiculo veiculo) {
        Veiculo veiculoAux = getLivro(veiculo.getId());
        if (veiculoAux != null) {
            if (livrosBD.editarLivroBD(veiculo)) {
                veiculoAux.setMarca(veiculo.getMarca());
                veiculoAux.setPreco(veiculo.getPreco());
                veiculoAux.setCombustivel(veiculo.getCombustivel());
                veiculoAux.setModelo(veiculo.getModelo());
            }
        }
    }
    //endregion


}
