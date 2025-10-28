package br.com.auramed.interfaces.controllers;

import br.com.auramed.domain.exception.EntidadeNaoLocalizadaException;
import br.com.auramed.interfaces.dto.request.CuidadorRequestDTO;
import br.com.auramed.interfaces.dto.response.CuidadorResponseDTO;
import java.util.List;

public interface CuidadorController {
    CuidadorResponseDTO criarCuidador(CuidadorRequestDTO cuidadorRequest) throws EntidadeNaoLocalizadaException;
    CuidadorResponseDTO editarCuidador(Integer idPessoa, CuidadorRequestDTO cuidadorRequest) throws EntidadeNaoLocalizadaException;
    CuidadorResponseDTO getCuidadorById(Integer idPessoa) throws EntidadeNaoLocalizadaException;
    void deleteCuidador(Integer idPessoa) throws EntidadeNaoLocalizadaException;
    List<CuidadorResponseDTO> getAllCuidadores();
    CuidadorResponseDTO ativarCuidador(Integer idPessoa) throws EntidadeNaoLocalizadaException;
    CuidadorResponseDTO inativarCuidador(Integer idPessoa) throws EntidadeNaoLocalizadaException;
}