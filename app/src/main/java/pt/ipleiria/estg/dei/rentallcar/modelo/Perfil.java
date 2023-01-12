package pt.ipleiria.estg.dei.rentallcar.modelo;

public class Perfil {
    private int id;
    private String nome, apelido, telemovel, codPostal, imgPerfil;

    public Perfil(int id, String nome, String apelido, /*String imgPerfil,*/ String telemovel, String codPostal) {
        this.id = id;
        this.nome = nome;
        this.apelido = apelido;
        //this.imgPerfil = imgPerfil;
        this.telemovel = telemovel;
        this.codPostal = codPostal;
    }

    public int getId() {
        return id;
    }

    public void setId(int idDados) {
        this.id = idDados;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getApelido() {
        return apelido;
    }

    public void setApelido(String apelido) {
        this.apelido = apelido;
    }

    public String getImgPerfil() {
        return imgPerfil;
    }

    public void setImgPerfil(String imgPerfil) {
        this.imgPerfil = imgPerfil;
    }

    public String getTelemovel() {
        return telemovel;
    }

    public void setTelemovel(String telemovel) {
        this.telemovel = telemovel;
    }

    public String getCodPostal() {
        return codPostal;
    }

    public void setCodPostal(String codPostal) {
        this.codPostal = codPostal;
    }
}
