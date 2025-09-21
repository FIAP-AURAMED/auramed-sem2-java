package br.com.fiap.auramed.domain.model.vo;

public enum Genero {
    FEMININO('F'),
    MASCULINO('M'),
    OUTRO('O');

    private final char sigla;

    Genero(char sigla) {
        this.sigla = sigla;
    }

    public char getSigla() {
        return sigla;
    }
}