package br.com.rootnet.tabelafip.model;

public record Dados(String codigo, String nome) {
    @Override
    public String toString() {
        return codigo + " - " + nome;
    }
}
