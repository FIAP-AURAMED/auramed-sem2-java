package fiap.com.auramed.application.dto;

public record MedicoLoginRequestDTO(
        String email,
        String senha
) {}