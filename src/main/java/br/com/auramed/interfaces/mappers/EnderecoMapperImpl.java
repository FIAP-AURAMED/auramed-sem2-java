package br.com.auramed.interfaces.mappers;

import br.com.auramed.domain.model.Endereco;
import br.com.auramed.domain.model.Pessoa;
import br.com.auramed.interfaces.dto.request.EnderecoRequestDTO;
import br.com.auramed.interfaces.dto.response.EnderecoResponseDTO;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class EnderecoMapperImpl implements EnderecoMapper {

    @Override
    public Endereco toDomain(EnderecoRequestDTO dto) {
        if (dto == null) {
            return null;
        }

        Pessoa pessoa = new Pessoa(null, null, null);
        pessoa.setId(dto.getPessoaId());

        Endereco endereco = new Endereco(pessoa, dto.getLogradouro(), dto.getNumero(),
                dto.getBairro(), dto.getCidade(), dto.getUf(), dto.getCep());

        if (dto.getId() != null) {
            endereco.setId(dto.getId());
        } else {
            endereco.setId(0);
        }

        endereco.setTipoEndereco(dto.getTipoEndereco());
        endereco.setComplemento(dto.getComplemento());
        endereco.setPrincipal(dto.getPrincipal());

        return endereco;
    }

    @Override
    public EnderecoResponseDTO toResponseDTO(Endereco endereco) {
        if (endereco == null) {
            return null;
        }

        EnderecoResponseDTO response = new EnderecoResponseDTO();
        response.setId(endereco.getId());
        response.setPessoaId(endereco.getPessoa().getId());
        response.setTipoEndereco(endereco.getTipoEndereco());
        response.setLogradouro(endereco.getLogradouro());
        response.setNumero(endereco.getNumero());
        response.setComplemento(endereco.getComplemento());
        response.setBairro(endereco.getBairro());
        response.setCidade(endereco.getCidade());
        response.setUf(endereco.getUf());
        response.setCep(endereco.getCep());
        response.setPrincipal(endereco.getPrincipal());

        return response;
    }

    @Override
    public List<EnderecoResponseDTO> toResponseDTOList(List<Endereco> enderecos) {
        if (enderecos == null) {
            return List.of();
        }
        return enderecos.stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }
}