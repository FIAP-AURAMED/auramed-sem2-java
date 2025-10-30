package br.com.auramed.interfaces.mappers;

import br.com.auramed.domain.model.Pessoa;
import br.com.auramed.interfaces.dto.request.PessoaRequestDTO;
import br.com.auramed.interfaces.dto.response.PessoaResponseDTO;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class PessoaMapperImpl implements PessoaMapper {

    @Override
    public Pessoa toDomain(PessoaRequestDTO dto) {
        if (dto == null) {
            return null;
        }

        Pessoa pessoa = new Pessoa(dto.getNome(), dto.getTelefone(), dto.getTipoPessoa());

        if (dto.getId() != null) {
            pessoa.setId(dto.getId());
        } else {
            pessoa.setId(0);
        }

        pessoa.setEmail(dto.getEmail());
        pessoa.setCpf(dto.getCpf());
        pessoa.setDataNascimento(dto.getDataNascimento());
        pessoa.setGenero(dto.getGenero());

        return pessoa;
    }

    @Override
    public PessoaResponseDTO toResponseDTO(Pessoa pessoa) {
        if (pessoa == null) {
            return null;
        }

        PessoaResponseDTO response = new PessoaResponseDTO();
        response.setId(pessoa.getId());
        response.setNome(pessoa.getNome());
        response.setEmail(pessoa.getEmail());
        response.setCpf(pessoa.getCpf());
        response.setDataNascimento(pessoa.getDataNascimento());
        response.setGenero(pessoa.getGenero());
        response.setTelefone(pessoa.getTelefone());
        response.setTipoPessoa(pessoa.getTipoPessoa());
        response.setDataCadastro(pessoa.getDataCadastro());
        response.setAtivo(pessoa.getAtivo());

        return response;
    }

    @Override
    public List<PessoaResponseDTO> toResponseDTOList(List<Pessoa> pessoas) {
        if (pessoas == null) {
            return List.of();
        }
        return pessoas.stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }
}