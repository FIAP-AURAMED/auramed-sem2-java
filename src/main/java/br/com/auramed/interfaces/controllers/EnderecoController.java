package br.com.auramed.interfaces.controllers;

import br.com.auramed.domain.exception.EntidadeNaoLocalizadaException;
import br.com.auramed.interfaces.dto.request.EnderecoRequestDTO;
import br.com.auramed.interfaces.dto.response.EnderecoResponseDTO;
import java.util.List;

public interface EnderecoController {
    EnderecoResponseDTO criarEndereco(EnderecoRequestDTO enderecoRequest) throws EntidadeNaoLocalizadaException;
    EnderecoResponseDTO editarEndereco(Integer id, EnderecoRequestDTO enderecoRequest) throws EntidadeNaoLocalizadaException;
    EnderecoResponseDTO getEnderecoById(Integer id) throws EntidadeNaoLocalizadaException;
    void deleteEndereco(Integer id) throws EntidadeNaoLocalizadaException;
    List<EnderecoResponseDTO> getAllEnderecos();
    List<EnderecoResponseDTO> getEnderecosPorPessoa(Integer pessoaId);
}