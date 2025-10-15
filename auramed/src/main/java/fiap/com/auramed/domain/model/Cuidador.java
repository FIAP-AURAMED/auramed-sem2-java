package fiap.com.auramed.domain.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Cuidador extends Pessoa {
    private List<Paciente> pacientes;

    public Cuidador(String nome, String email, String telefone, String cpf, LocalDate dataNascimento, Genero genero) {
        super(nome, email, telefone, cpf, dataNascimento, genero);
        this.pacientes = new ArrayList<>();
    }

    public List<Paciente> getPacientes() {
        return pacientes;
    }
}