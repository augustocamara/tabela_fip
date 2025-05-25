package br.com.rootnet.tabelafip.model;

public enum TipoVeiculo {
    CARRO("carros"),
    MOTO("motos"),
    CAMINHAO("caminhoes");

    private final String path;

    TipoVeiculo(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}

