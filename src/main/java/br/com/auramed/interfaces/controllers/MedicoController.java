package br.com.auramed.interfaces.controllers;

import br.com.auramed.domain.exception.EntidadeNaoLocalizadaException;
import br.com.auramed.interfaces.dto.request.MedicoRequestDTO;
import br.com.auramed.interfaces.dto.response.MedicoResponseDTO;
import java.util.List;

public interface MedicoController {
    MedicoResponseDTO criarMedico(MedicoRequestDTO medicoRequest) throws EntidadeNaoLocalizadaException;
    MedicoResponseDTO editarMedico(Integer id, MedicoRequestDTO medicoRequest) throws EntidadeNaoLocalizadaException;
    MedicoResponseDTO getMedicoById(Integer id) throws EntidadeNaoLocalizadaException;
    MedicoResponseDTO getMedicoByCrm(String crm) throws EntidadeNaoLocalizadaException;
    void deleteMedico(Integer id) throws EntidadeNaoLocalizadaException;
    List<MedicoResponseDTO> getAllMedicos();
    List<MedicoResponseDTO> getMedicosPorEspecialidade(Integer idEspecialidade);
    MedicoResponseDTO alterarStatusTeleconsulta(Integer id, String aceitaTeleconsulta) throws EntidadeNaoLocalizadaException;
}