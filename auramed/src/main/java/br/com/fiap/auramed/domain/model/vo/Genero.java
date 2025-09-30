package br.com.fiap.auramed.domain.model.vo;

public enum Genero {
    MASCULINO('M'),
    FEMININO('F'),
    OUTRO('O');

    private final char sigla;

    Genero(char sigla) {
        this.sigla = sigla;
    }

    public char getSigla() {
        return sigla;
    }

    public static Genero fromSigla(char sigla) {
        for (Genero genero : values()) {
            if (genero.getSigla() == sigla) {
                return genero;
            }
        }
        throw new IllegalArgumentException(STR."Sigla de gênero inválida: \{sigla}");
    }
}