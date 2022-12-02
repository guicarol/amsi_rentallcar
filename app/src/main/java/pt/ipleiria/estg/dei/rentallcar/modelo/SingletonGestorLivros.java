package pt.ipleiria.estg.dei.rentallcar.modelo;

import android.content.Context;

import java.util.ArrayList;

import pt.ipleiria.estg.dei.rentallcar.R;


public class SingletonGestorLivros {
    private ArrayList<Livro> livros;
    private static SingletonGestorLivros instance = null;
    private LivroBDHelper livrosBD;

    public static synchronized SingletonGestorLivros getInstance(Context context) {
        if (instance == null)
            instance = new SingletonGestorLivros(context);
        return instance;
    }

    private SingletonGestorLivros(Context context) {
        //inicializar o vetor
        gerarDadosDinamico();
        livros = new ArrayList<>();
        livrosBD = new LivroBDHelper(context);

    }

    private void gerarDadosDinamico() {
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
    }


    //region LIVRO

    public ArrayList<Livro> getLivrosBD() {
        return new ArrayList(livros);
    }

    public Livro getLivro(int id) {
        for (Livro l : livros)
            if (l.getId() == id)
                return l;
        return null;
    }

    public void adicionarLivroBD(Livro livro) {
        if (livro != null)
            livros.add(livro);
    }

    public void removerLivroBD(int id) {
        Livro livroAux = getLivro(id);
        if (livroAux != null) {
            livros.remove(livroAux);
        }
    }

    public void editarLivroBD(Livro livro) {
        Livro livroAux = getLivro(livro.getId());
        if (livroAux != null) {
            if (livrosBD.editarLivroBD(livro)) {
                livroAux.setTitulo(livro.getTitulo());
                livroAux.setAno(livro.getAno());
                livroAux.setAutor(livro.getAutor());
                livroAux.setSerie(livro.getSerie());
            }
        }
    }
    //endregion


}
