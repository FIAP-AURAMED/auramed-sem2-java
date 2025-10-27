package br.com.auramed.interfaces.controllers;

import br.com.auramed.domain.exception.EntidadeNaoLocalizadaException;
import br.com.auramed.domain.model.Endereco;
import br.com.auramed.domain.service.EnderecoService;
import br.com.auramed.interfaces.dto.request.EnderecoRequestDTO;
import br.com.auramed.interfaces.dto.response.EnderecoResponseDTO;
import br.com.auramed.interfaces.mappers.EnderecoMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;

@ApplicationScoped
public class EnderecoControllerImpl implements EnderecoController {
    private final EnderecoService enderecoService;
    private final EnderecoMapper enderecoMapper;

    @Inject
    public EnderecoControllerImpl(EnderecoService enderecoService, EnderecoMapper enderecoMapper) {
        this.enderecoService = enderecoService;
        this.enderecoMapper = enderecoMapper;
    }

    @Override
    public EnderecoResponseDTO criarEndereco(EnderecoRequestDTO enderecoRequest) throws EntidadeNaoLocalizadaException {
        try {
            Endereco endereco = enderecoMapper.toDomain(enderecoRequest);
            Endereco enderecoCriado = this.enderecoService.criar(endereco);
            return enderecoMapper.toResponseDTO(enderecoCriado);

        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public EnderecoResponseDTO editarEndereco(Integer id, EnderecoRequestDTO enderecoRequest) throws EntidadeNaoLocalizadaException {
        Endereco enderecoExistente = this.enderecoService.localizar(id);

        enderecoExistente.setTipoEndereco(enderecoRequest.getTipoEndereco());
        enderecoExistente.setLogradouro(enderecoRequest.getLogradouro());
        enderecoExistente.setNumero(enderecoRequest.getNumero());
        enderecoExistente.setComplemento(enderecoRequest.getComplemento());
        enderecoExistente.setBairro(enderecoRequest.getBairro());
        enderecoExistente.setCidade(enderecoRequest.getCidade());
        enderecoExistente.setUf(enderecoRequest.getUf());
        enderecoExistente.setCep(enderecoRequest.getCep());
        enderecoExistente.setPrincipal(enderecoRequest.getPrincipal());

        Endereco enderecoAtualizado = this.enderecoService.editar(id, enderecoExistente);
        return enderecoMapper.toResponseDTO(enderecoAtualizado);
    }

    @Override
    public EnderecoResponseDTO getEnderecoById(Integer id) throws EntidadeNaoLocalizadaException {
        Endereco endereco = this.enderecoService.localizar(id);
        return enderecoMapper.toResponseDTO(endereco);
    }

    @Override
    public void deleteEndereco(Integer id) throws EntidadeNaoLocalizadaException {
        this.enderecoService.remover(id);
    }

    @Override
    public List<EnderecoResponseDTO> getAllEnderecos() {
        List<Endereco> enderecos = enderecoService.listarTodos();
        return enderecoMapper.toResponseDTOList(enderecos);
    }

    @Override
    public List<EnderecoResponseDTO> getEnderecosPorPessoa(Integer pessoaId) {
        List<Endereco> enderecos = enderecoService.listarPorPessoaId(pessoaId);
        return enderecoMapper.toResponseDTOList(enderecos);
    }
}