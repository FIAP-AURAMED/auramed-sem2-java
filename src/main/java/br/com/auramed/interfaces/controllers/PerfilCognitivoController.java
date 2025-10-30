package br.com.auramed.interfaces.controllers;

import br.com.auramed.domain.exception.EntidadeNaoLocalizadaException;
import br.com.auramed.interfaces.dto.request.PerfilCognitivoRequestDTO;
import br.com.auramed.interfaces.dto.response.PerfilCognitivoResponseDTO;
import java.util.List;

public interface PerfilCognitivoController {
    PerfilCognitivoResponseDTO criarPerfilCognitivo(PerfilCognitivoRequestDTO perfilCognitivoRequest) throws EntidadeNaoLocalizadaException;
    PerfilCognitivoResponseDTO editarPerfilCognitivo(Integer idPerfilCognitivo, PerfilCognitivoRequestDTO perfilCognitivoRequest) throws EntidadeNaoLocalizadaException;
    void deletePerfilCognitivo(Integer idPerfilCognitivo) throws EntidadeNaoLocalizadaException;
    PerfilCognitivoResponseDTO getPerfilCognitivoById(Integer idPerfilCognitivo) throws EntidadeNaoLocalizadaException;
    PerfilCognitivoResponseDTO getPerfilCognitivoPorPaciente(Integer idPaciente) throws EntidadeNaoLocalizadaException;
    List<PerfilCognitivoResponseDTO> getAllPerfisCognitivos();
}