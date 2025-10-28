package br.com.auramed.interfaces.controllers;

import br.com.auramed.domain.exception.EntidadeNaoLocalizadaException;
import br.com.auramed.domain.model.PacienteCuidador;
import br.com.auramed.domain.service.PacienteCuidadorService;
import br.com.auramed.interfaces.dto.request.PacienteCuidadorRequestDTO;
import br.com.auramed.interfaces.dto.response.PacienteCuidadorResponseDTO;
import br.com.auramed.interfaces.mappers.PacienteCuidadorMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;

@ApplicationScoped
public class PacienteCuidadorControllerImpl implements PacienteCuidadorController {
    private final PacienteCuidadorService pacienteCuidadorService;
    private final PacienteCuidadorMapper pacienteCuidadorMapper;

    @Inject
    public PacienteCuidadorControllerImpl(PacienteCuidadorService pacienteCuidadorService, PacienteCuidadorMapper pacienteCuidadorMapper) {
        this.pacienteCuidadorService = pacienteCuidadorService;
        this.pacienteCuidadorMapper = pacienteCuidadorMapper;
    }

    @Override
    public PacienteCuidadorResponseDTO associarPacienteCuidador(PacienteCuidadorRequestDTO pacienteCuidadorRequest) throws EntidadeNaoLocalizadaException {
        try {
            PacienteCuidador pacienteCuidador = pacienteCuidadorMapper.toDomain(pacienteCuidadorRequest);
            PacienteCuidador associacaoCriada = this.pacienteCuidadorService.associar(pacienteCuidador);
            return pacienteCuidadorMapper.toResponseDTO(associacaoCriada);

        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public void desassociarPacienteCuidador(Integer idPaciente, Integer idCuidador) throws EntidadeNaoLocalizadaException {
        this.pacienteCuidadorService.desassociar(idPaciente, idCuidador);
    }

    @Override
    public PacienteCuidadorResponseDTO getAssociacaoByIds(Integer idPaciente, Integer idCuidador) throws EntidadeNaoLocalizadaException {
        PacienteCuidador associacao = this.pacienteCuidadorService.localizarAssociacao(idPaciente, idCuidador);
        return pacienteCuidadorMapper.toResponseDTO(associacao);
    }

    @Override
    public List<PacienteCuidadorResponseDTO> getCuidadoresDoPaciente(Integer idPaciente) {
        List<PacienteCuidador> associacoes = pacienteCuidadorService.listarCuidadoresDoPaciente(idPaciente);
        return pacienteCuidadorMapper.toResponseDTOList(associacoes);
    }

    @Override
    public List<PacienteCuidadorResponseDTO> getPacientesDoCuidador(Integer idCuidador) {
        List<PacienteCuidador> associacoes = pacienteCuidadorService.listarPacientesDoCuidador(idCuidador);
        return pacienteCuidadorMapper.toResponseDTOList(associacoes);
    }

    @Override
    public List<PacienteCuidadorResponseDTO> getAllAssociacoes() {
        List<PacienteCuidador> associacoes = pacienteCuidadorService.listarTodasAssociacoes();
        return pacienteCuidadorMapper.toResponseDTOList(associacoes);
    }
}