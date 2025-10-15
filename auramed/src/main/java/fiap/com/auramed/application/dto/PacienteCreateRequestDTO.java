package fiap.com.auramed.application.dto;

import java.time.LocalDate;
import fiap.com.auramed.domain.model.Genero;

public record PacienteCreateRequestDTO(
        String nome,
        String email,
        String telefone,
        String cpf,
        LocalDate dataNascimento,
        Genero genero,
        String cartaoSus,
        Integer medicoId
) {}