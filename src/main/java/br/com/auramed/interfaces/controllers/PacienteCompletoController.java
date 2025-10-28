package br.com.auramed.interfaces.controllers;

import br.com.auramed.domain.exception.EntidadeNaoLocalizadaException;
import br.com.auramed.interfaces.dto.request.PacienteCompletoRequestDTO;
import br.com.auramed.interfaces.dto.response.PacienteCompletoResponseDTO;

public interface PacienteCompletoController {
    PacienteCompletoResponseDTO criarPacienteCompleto(PacienteCompletoRequestDTO pacienteCompletoRequest) throws EntidadeNaoLocalizadaException;
    PacienteCompletoResponseDTO getPacienteCompleto(Integer idPaciente) throws EntidadeNaoLocalizadaException;
}