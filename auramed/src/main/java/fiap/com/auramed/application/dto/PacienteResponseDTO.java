package fiap.com.auramed.application.dto;

import java.time.LocalDate;
import fiap.com.auramed.domain.model.Genero;
import fiap.com.auramed.domain.model.Paciente;
import fiap.com.auramed.domain.model.StatusUsuario;

public record PacienteResponseDTO(
        Integer id,
        String nome,
        String email,
        String telefone,
        String cpf,
        LocalDate dataNascimento,
        Genero genero,
        StatusUsuario status,
        String cartaoSus,
        Integer medicoId,
        String medicoNome
) {
    public PacienteResponseDTO(Paciente paciente) {
        this(
                paciente.getId(),
                paciente.getNome(),
                paciente.getEmail(),
                paciente.getTelefone(),
                paciente.getCpf(),
                paciente.getDataNascimento(),
                paciente.getGenero(),
                paciente.getStatus(),
                paciente.getCartaoSus(),
                paciente.getMedico().getId(),
                paciente.getMedico().getNome()
        );
    }
}