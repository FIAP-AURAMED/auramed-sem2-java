package br.com.auramed.interfaces.controllers;

import br.com.auramed.domain.exception.EntidadeNaoLocalizadaException;
import br.com.auramed.interfaces.dto.request.PessoaRequestDTO;
import br.com.auramed.interfaces.dto.response.PessoaResponseDTO;
import java.util.List;

public interface PessoaController {
    PessoaResponseDTO criarPessoa(PessoaRequestDTO pessoaRequest) throws EntidadeNaoLocalizadaException;
    PessoaResponseDTO editarPessoa(Integer id, PessoaRequestDTO pessoaRequest) throws EntidadeNaoLocalizadaException;
    PessoaResponseDTO getPessoaById(Integer id) throws EntidadeNaoLocalizadaException;
    void deletePessoa(Integer id) throws EntidadeNaoLocalizadaException;
    List<PessoaResponseDTO> getAllPessoas();
    List<PessoaResponseDTO> getPessoasPorTipo(String tipoPessoa);
    PessoaResponseDTO ativarPessoa(Integer id) throws EntidadeNaoLocalizadaException;
    PessoaResponseDTO inativarPessoa(Integer id) throws EntidadeNaoLocalizadaException;
}