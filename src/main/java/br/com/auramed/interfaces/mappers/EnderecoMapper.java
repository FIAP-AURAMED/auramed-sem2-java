package br.com.auramed.interfaces.mappers;

import br.com.auramed.domain.model.Endereco;
import br.com.auramed.interfaces.dto.request.EnderecoRequestDTO;
import br.com.auramed.interfaces.dto.response.EnderecoResponseDTO;
import java.util.List;

public interface EnderecoMapper {
    Endereco toDomain(EnderecoRequestDTO dto);
    EnderecoResponseDTO toResponseDTO(Endereco endereco);
    List<EnderecoResponseDTO> toResponseDTOList(List<Endereco> enderecos);
}