package br.com.auramed.interfaces.controllers;

import br.com.auramed.domain.exception.EntidadeNaoLocalizadaException;
import br.com.auramed.domain.model.PerfilCognitivo;
import br.com.auramed.domain.service.PerfilCognitivoService;
import br.com.auramed.interfaces.dto.request.PerfilCognitivoRequestDTO;
import br.com.auramed.interfaces.dto.response.PerfilCognitivoResponseDTO;
import br.com.auramed.interfaces.mappers.PerfilCognitivoMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;

@ApplicationScoped
public class PerfilCognitivoControllerImpl implements PerfilCognitivoController {
    private final PerfilCognitivoService perfilCognitivoService;
    private final PerfilCognitivoMapper perfilCognitivoMapper;

    @Inject
    public PerfilCognitivoControllerImpl(PerfilCognitivoService perfilCognitivoService, PerfilCognitivoMapper perfilCognitivoMapper) {
        this.perfilCognitivoService = perfilCognitivoService;
        this.perfilCognitivoMapper = perfilCognitivoMapper;
    }

    @Override
    public PerfilCognitivoResponseDTO criarPerfilCognitivo(PerfilCognitivoRequestDTO perfilCognitivoRequest) throws EntidadeNaoLocalizadaException {
        try {
            PerfilCognitivo perfilCognitivo = perfilCognitivoMapper.toDomain(perfilCognitivoRequest);
            PerfilCognitivo perfilCriado = this.perfilCognitivoService.criar(perfilCognitivo);
            return perfilCognitivoMapper.toResponseDTO(perfilCriado);

        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public PerfilCognitivoResponseDTO editarPerfilCognitivo(Integer idPerfilCognitivo, PerfilCognitivoRequestDTO perfilCognitivoRequest) throws EntidadeNaoLocalizadaException {
        PerfilCognitivo perfilExistente = this.perfilCognitivoService.localizarPorId(idPerfilCognitivo);

        perfilExistente.setIdPaciente(perfilCognitivoRequest.getIdPaciente());
        perfilExistente.setInDificuldadeVisao(perfilCognitivoRequest.getInDificuldadeVisao());
        perfilExistente.setInUsaOculos(perfilCognitivoRequest.getInUsaOculos());
        perfilExistente.setInDificuldadeAudicao(perfilCognitivoRequest.getInDificuldadeAudicao());
        perfilExistente.setInUsaAparelhoAud(perfilCognitivoRequest.getInUsaAparelhoAud());
        perfilExistente.setInDificuldadeCogn(perfilCognitivoRequest.getInDificuldadeCogn());

        PerfilCognitivo perfilAtualizado = this.perfilCognitivoService.editar(idPerfilCognitivo, perfilExistente);
        return perfilCognitivoMapper.toResponseDTO(perfilAtualizado);
    }

    @Override
    public void deletePerfilCognitivo(Integer idPerfilCognitivo) throws EntidadeNaoLocalizadaException {
        this.perfilCognitivoService.remover(idPerfilCognitivo);
    }

    @Override
    public PerfilCognitivoResponseDTO getPerfilCognitivoById(Integer idPerfilCognitivo) throws EntidadeNaoLocalizadaException {
        PerfilCognitivo perfilCognitivo = this.perfilCognitivoService.localizarPorId(idPerfilCognitivo);
        return perfilCognitivoMapper.toResponseDTO(perfilCognitivo);
    }

    @Override
    public PerfilCognitivoResponseDTO getPerfilCognitivoPorPaciente(Integer idPaciente) throws EntidadeNaoLocalizadaException {
        PerfilCognitivo perfilCognitivo = this.perfilCognitivoService.localizarPorPaciente(idPaciente);
        return perfilCognitivoMapper.toResponseDTO(perfilCognitivo);
    }

    @Override
    public List<PerfilCognitivoResponseDTO> getAllPerfisCognitivos() {
        List<PerfilCognitivo> perfisCognitivos = perfilCognitivoService.listarTodos();
        return perfilCognitivoMapper.toResponseDTOList(perfisCognitivos);
    }
}