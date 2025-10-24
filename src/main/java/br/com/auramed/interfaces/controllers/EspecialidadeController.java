package br.com.auramed.interfaces.controllers;

import br.com.auramed.domain.exception.EntidadeNaoLocalizadaException;
import br.com.auramed.interfaces.dto.request.EspecialidadeRequestDTO;
import br.com.auramed.interfaces.dto.response.EspecialidadeResponseDTO;
import java.util.List;

public interface EspecialidadeController {
    EspecialidadeResponseDTO criarEspecialidade(EspecialidadeRequestDTO especialidadeRequest) throws EntidadeNaoLocalizadaException;
    EspecialidadeResponseDTO editarEspecialidade(Integer id, EspecialidadeRequestDTO especialidadeRequest) throws EntidadeNaoLocalizadaException;
    EspecialidadeResponseDTO getEspecialidadeById(Integer id) throws EntidadeNaoLocalizadaException;
    EspecialidadeResponseDTO getEspecialidadeByNome(String nome) throws EntidadeNaoLocalizadaException;
    void deleteEspecialidade(Integer id) throws EntidadeNaoLocalizadaException;
    List<EspecialidadeResponseDTO> getAllEspecialidades();
    List<EspecialidadeResponseDTO> getEspecialidadesAtivas();
    EspecialidadeResponseDTO ativarEspecialidade(Integer id) throws EntidadeNaoLocalizadaException;
    EspecialidadeResponseDTO inativarEspecialidade(Integer id) throws EntidadeNaoLocalizadaException;
}