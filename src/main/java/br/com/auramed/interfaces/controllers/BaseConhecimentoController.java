package br.com.auramed.interfaces.controllers;

import br.com.auramed.domain.exception.EntidadeNaoLocalizadaException;
import br.com.auramed.interfaces.dto.request.BaseConhecimentoRequestDTO;
import br.com.auramed.interfaces.dto.response.BaseConhecimentoResponseDTO;

import java.util.List;

public interface BaseConhecimentoController {
    BaseConhecimentoResponseDTO criarBaseConhecimento(BaseConhecimentoRequestDTO conhecimentoRequest);
    BaseConhecimentoResponseDTO editarBaseConhecimento(String id, BaseConhecimentoRequestDTO conhecimentoRequest) throws EntidadeNaoLocalizadaException;
    BaseConhecimentoResponseDTO getBaseConhecimentoById(String id) throws EntidadeNaoLocalizadaException;
    void deleteBaseConhecimento(String id) throws EntidadeNaoLocalizadaException;
    List<BaseConhecimentoResponseDTO> getAllBaseConhecimento();
    List<BaseConhecimentoResponseDTO> getBaseConhecimentoPorCategoria(String categoria);
}