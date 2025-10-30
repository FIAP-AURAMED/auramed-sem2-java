package br.com.auramed.interfaces.mappers;

import br.com.auramed.domain.model.Pessoa;
import br.com.auramed.interfaces.dto.request.PessoaRequestDTO;
import br.com.auramed.interfaces.dto.response.PessoaResponseDTO;
import java.util.List;

public interface PessoaMapper {
    Pessoa toDomain(PessoaRequestDTO dto);
    PessoaResponseDTO toResponseDTO(Pessoa pessoa);
    List<PessoaResponseDTO> toResponseDTOList(List<Pessoa> pessoas);
}