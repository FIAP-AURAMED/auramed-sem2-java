package br.com.auramed.interfaces.controllers;

import br.com.auramed.domain.exception.EntidadeNaoLocalizadaException;
import br.com.auramed.interfaces.dto.request.InfoTeleconsultaRequestDTO;
import br.com.auramed.interfaces.dto.response.InfoTeleconsultaResponseDTO;
import java.util.List;

public interface InfoTeleconsultaController {
    InfoTeleconsultaResponseDTO criarInfoTeleconsulta(InfoTeleconsultaRequestDTO infoTeleconsultaRequest) throws EntidadeNaoLocalizadaException;
    InfoTeleconsultaResponseDTO editarInfoTeleconsulta(Integer idInfoTeleconsulta, InfoTeleconsultaRequestDTO infoTeleconsultaRequest) throws EntidadeNaoLocalizadaException;
    void deleteInfoTeleconsulta(Integer idInfoTeleconsulta) throws EntidadeNaoLocalizadaException;
    InfoTeleconsultaResponseDTO getInfoTeleconsultaById(Integer idInfoTeleconsulta) throws EntidadeNaoLocalizadaException;
    InfoTeleconsultaResponseDTO getInfoTeleconsultaPorPaciente(Integer idPaciente) throws EntidadeNaoLocalizadaException;
    List<InfoTeleconsultaResponseDTO> getAllInfoTeleconsultas();
}