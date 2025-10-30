package br.com.auramed.interfaces.controllers;

import br.com.auramed.domain.exception.EntidadeNaoLocalizadaException;
import br.com.auramed.interfaces.dto.request.PacienteCuidadorRequestDTO;
import br.com.auramed.interfaces.dto.response.PacienteCuidadorResponseDTO;
import java.util.List;

public interface PacienteCuidadorController {
    PacienteCuidadorResponseDTO associarPacienteCuidador(PacienteCuidadorRequestDTO pacienteCuidadorRequest) throws EntidadeNaoLocalizadaException;
    void desassociarPacienteCuidador(Integer idPaciente, Integer idCuidador) throws EntidadeNaoLocalizadaException;
    PacienteCuidadorResponseDTO getAssociacaoByIds(Integer idPaciente, Integer idCuidador) throws EntidadeNaoLocalizadaException;
    List<PacienteCuidadorResponseDTO> getCuidadoresDoPaciente(Integer idPaciente);
    List<PacienteCuidadorResponseDTO> getPacientesDoCuidador(Integer idCuidador);
    List<PacienteCuidadorResponseDTO> getAllAssociacoes();
}