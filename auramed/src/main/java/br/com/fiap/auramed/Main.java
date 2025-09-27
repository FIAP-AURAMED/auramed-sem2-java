package br.com.fiap.auramed;

import br.com.fiap.auramed.domain.model.entity.Pessoa;
import br.com.fiap.auramed.domain.model.vo.Genero;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        Pessoa pessoa = new Pessoa("Diego Andrade", "contato@yahoo.com", "111.333.222-6", LocalDate.of(2002,4,10), Genero.MASCULINO, "11958152477");
    }
}
