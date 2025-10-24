package br.com.auramed.interfaces.controllers;

import br.com.auramed.domain.exception.EntidadeNaoLocalizadaException;
import br.com.auramed.domain.model.Pessoa;
import br.com.auramed.domain.service.PessoaService;
import br.com.auramed.interfaces.dto.request.PessoaRequestDTO;
import br.com.auramed.interfaces.dto.response.PessoaResponseDTO;
import br.com.auramed.interfaces.mappers.PessoaMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;

@ApplicationScoped
public class PessoaControllerImpl implements PessoaController {
    private final PessoaService pessoaService;
    private final PessoaMapper pessoaMapper;

    @Inject
    public PessoaControllerImpl(PessoaService pessoaService, PessoaMapper pessoaMapper) {
        this.pessoaService = pessoaService;
        this.pessoaMapper = pessoaMapper;
    }

    @Override
    public PessoaResponseDTO criarPessoa(PessoaRequestDTO pessoaRequest) throws EntidadeNaoLocalizadaException {
        try {
            Pessoa pessoa = pessoaMapper.toDomain(pessoaRequest);
            Pessoa pessoaCriada = this.pessoaService.criar(pessoa);
            return pessoaMapper.toResponseDTO(pessoaCriada);

        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public PessoaResponseDTO editarPessoa(Integer id, PessoaRequestDTO pessoaRequest) throws EntidadeNaoLocalizadaException {
        Pessoa pessoaExistente = this.pessoaService.localizar(id);

        pessoaExistente.setNome(pessoaRequest.getNome());
        pessoaExistente.setEmail(pessoaRequest.getEmail());
        pessoaExistente.setCpf(pessoaRequest.getCpf());
        pessoaExistente.setDataNascimento(pessoaRequest.getDataNascimento());
        pessoaExistente.setGenero(pessoaRequest.getGenero());
        pessoaExistente.setTelefone(pessoaRequest.getTelefone());
        pessoaExistente.setTipoPessoa(pessoaRequest.getTipoPessoa());

        Pessoa pessoaAtualizada = this.pessoaService.editar(id, pessoaExistente);
        return pessoaMapper.toResponseDTO(pessoaAtualizada);
    }

    @Override
    public PessoaResponseDTO getPessoaById(Integer id) throws EntidadeNaoLocalizadaException {
        Pessoa pessoa = this.pessoaService.localizar(id);
        return pessoaMapper.toResponseDTO(pessoa);
    }

    @Override
    public void deletePessoa(Integer id) throws EntidadeNaoLocalizadaException {
        this.pessoaService.remover(id);
    }

    @Override
    public List<PessoaResponseDTO> getAllPessoas() {
        List<Pessoa> pessoas = pessoaService.listarTodos();
        return pessoaMapper.toResponseDTOList(pessoas);
    }

    @Override
    public List<PessoaResponseDTO> getPessoasPorTipo(String tipoPessoa) {
        List<Pessoa> pessoas = pessoaService.listarPorTipo(tipoPessoa);
        return pessoaMapper.toResponseDTOList(pessoas);
    }

    @Override
    public PessoaResponseDTO ativarPessoa(Integer id) throws EntidadeNaoLocalizadaException {
        Pessoa pessoaAtivada = this.pessoaService.ativar(id);
        return pessoaMapper.toResponseDTO(pessoaAtivada);
    }

    @Override
    public PessoaResponseDTO inativarPessoa(Integer id) throws EntidadeNaoLocalizadaException {
        Pessoa pessoaInativada = this.pessoaService.inativar(id);
        return pessoaMapper.toResponseDTO(pessoaInativada);
    }
}