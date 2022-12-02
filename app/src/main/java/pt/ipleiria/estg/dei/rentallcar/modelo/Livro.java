package pt.ipleiria.estg.dei.rentallcar.modelo;

public class Livro {

    private int id,ano,capa;
    private String titulo,autor,serie;
    private static int autoIncrementId=1;

    public Livro(int ano, int capa, String titulo, String serie, String autor) {
        this.id = autoIncrementId++;
        this.ano = ano;
        this.capa = capa;
        this.titulo = titulo;
        this.autor = autor;
        this.serie = serie;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public int getCapa() {
        return capa;
    }

    public void setCapa(int capa) {
        this.capa = capa;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }
}
