package br.com.auramed.interfaces.controllers;

import br.com.auramed.domain.exception.EntidadeNaoLocalizadaException;
import br.com.auramed.domain.model.InfoTeleconsulta;
import br.com.auramed.domain.service.InfoTeleconsultaService;
import br.com.auramed.interfaces.dto.request.InfoTeleconsultaRequestDTO;
import br.com.auramed.interfaces.dto.response.InfoTeleconsultaResponseDTO;
import br.com.auramed.interfaces.mappers.InfoTeleconsultaMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;

@ApplicationScoped
public class InfoTeleconsultaControllerImpl implements InfoTeleconsultaController {
    private final InfoTeleconsultaService infoTeleconsultaService;
    private final InfoTeleconsultaMapper infoTeleconsultaMapper;

    @Inject
    public InfoTeleconsultaControllerImpl(InfoTeleconsultaService infoTeleconsultaService, InfoTeleconsultaMapper infoTeleconsultaMapper) {
        this.infoTeleconsultaService = infoTeleconsultaService;
        this.infoTeleconsultaMapper = infoTeleconsultaMapper;
    }

    @Override
    public InfoTeleconsultaResponseDTO criarInfoTeleconsulta(InfoTeleconsultaRequestDTO infoTeleconsultaRequest) throws EntidadeNaoLocalizadaException {
        try {
            InfoTeleconsulta infoTeleconsulta = infoTeleconsultaMapper.toDomain(infoTeleconsultaRequest);
            InfoTeleconsulta infoCriada = this.infoTeleconsultaService.criar(infoTeleconsulta);
            return infoTeleconsultaMapper.toResponseDTO(infoCriada);

        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public InfoTeleconsultaResponseDTO editarInfoTeleconsulta(Integer idInfoTeleconsulta, InfoTeleconsultaRequestDTO infoTeleconsultaRequest) throws EntidadeNaoLocalizadaException {
        InfoTeleconsulta infoExistente = this.infoTeleconsultaService.localizarPorId(idInfoTeleconsulta);

        infoExistente.setIdPaciente(infoTeleconsultaRequest.getIdPaciente());
        infoExistente.setCdHabilidadeDigital(infoTeleconsultaRequest.getCdHabilidadeDigital());
        infoExistente.setCdCanalLembrete(infoTeleconsultaRequest.getCdCanalLembrete());
        infoExistente.setInPrecisaCuidador(infoTeleconsultaRequest.getInPrecisaCuidador());
        infoExistente.setInJaFezTele(infoTeleconsultaRequest.getInJaFezTele());

        InfoTeleconsulta infoAtualizada = this.infoTeleconsultaService.editar(idInfoTeleconsulta, infoExistente);
        return infoTeleconsultaMapper.toResponseDTO(infoAtualizada);
    }

    @Override
    public void deleteInfoTeleconsulta(Integer idInfoTeleconsulta) throws EntidadeNaoLocalizadaException {
        this.infoTeleconsultaService.remover(idInfoTeleconsulta);
    }

    @Override
    public InfoTeleconsultaResponseDTO getInfoTeleconsultaById(Integer idInfoTeleconsulta) throws EntidadeNaoLocalizadaException {
        InfoTeleconsulta infoTeleconsulta = this.infoTeleconsultaService.localizarPorId(idInfoTeleconsulta);
        return infoTeleconsultaMapper.toResponseDTO(infoTeleconsulta);
    }

    @Override
    public InfoTeleconsultaResponseDTO getInfoTeleconsultaPorPaciente(Integer idPaciente) throws EntidadeNaoLocalizadaException {
        InfoTeleconsulta infoTeleconsulta = this.infoTeleconsultaService.localizarPorPaciente(idPaciente);
        return infoTeleconsultaMapper.toResponseDTO(infoTeleconsulta);
    }

    @Override
    public List<InfoTeleconsultaResponseDTO> getAllInfoTeleconsultas() {
        List<InfoTeleconsulta> infoTeleconsultas = infoTeleconsultaService.listarTodos();
        return infoTeleconsultaMapper.toResponseDTOList(infoTeleconsultas);
    }
}