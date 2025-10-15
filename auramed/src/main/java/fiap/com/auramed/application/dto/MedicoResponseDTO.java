package fiap.com.auramed.application.dto;

import fiap.com.auramed.domain.model.Medico;
import fiap.com.auramed.domain.model.StatusUsuario;

public record MedicoResponseDTO(
        Integer id,
        String nome,
        String email,
        String crm,
        StatusUsuario status
) {
    public MedicoResponseDTO(Medico medico) {
        this(
                medico.getId(),
                medico.getNome(),
                medico.getEmail(),
                medico.getCrm(),
                medico.getStatus()
        );
    }
}