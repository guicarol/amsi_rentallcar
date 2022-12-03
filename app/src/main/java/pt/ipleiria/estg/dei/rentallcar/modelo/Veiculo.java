package pt.ipleiria.estg.dei.rentallcar.modelo;

public class Veiculo {

    private int id, preco,capa;
    private String marca, combustivel, modelo;
    private static int autoIncrementId=1;

    public Veiculo(int preco, int capa, String marca, String modelo, String combustivel) {
        this.id = autoIncrementId++;
        this.preco = preco;
        this.capa = capa;
        this.marca = marca;
        this.combustivel = combustivel;
        this.modelo = modelo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPreco() {
        return preco;
    }

    public void setPreco(int preco) {
        this.preco = preco;
    }

    public int getCapa() {
        return capa;
    }

    public void setCapa(int capa) {
        this.capa = capa;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getCombustivel() {
        return combustivel;
    }

    public void setCombustivel(String combustivel) {
        this.combustivel = combustivel;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }
}
