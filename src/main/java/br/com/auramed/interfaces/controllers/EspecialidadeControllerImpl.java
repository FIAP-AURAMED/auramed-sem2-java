package br.com.auramed.interfaces.controllers;

import br.com.auramed.domain.exception.EntidadeNaoLocalizadaException;
import br.com.auramed.domain.model.Especialidade;
import br.com.auramed.domain.service.EspecialidadeService;
import br.com.auramed.interfaces.dto.request.EspecialidadeRequestDTO;
import br.com.auramed.interfaces.dto.response.EspecialidadeResponseDTO;
import br.com.auramed.interfaces.mappers.EspecialidadeMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;

@ApplicationScoped
public class EspecialidadeControllerImpl implements EspecialidadeController {
    private final EspecialidadeService especialidadeService;
    private final EspecialidadeMapper especialidadeMapper;

    @Inject
    public EspecialidadeControllerImpl(EspecialidadeService especialidadeService, EspecialidadeMapper especialidadeMapper) {
        this.especialidadeService = especialidadeService;
        this.especialidadeMapper = especialidadeMapper;
    }

    @Override
    public EspecialidadeResponseDTO criarEspecialidade(EspecialidadeRequestDTO especialidadeRequest) throws EntidadeNaoLocalizadaException {
        try {
            Especialidade especialidade = especialidadeMapper.toDomain(especialidadeRequest);
            Especialidade especialidadeCriada = this.especialidadeService.criar(especialidade);
            return especialidadeMapper.toResponseDTO(especialidadeCriada);

        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public EspecialidadeResponseDTO editarEspecialidade(Integer id, EspecialidadeRequestDTO especialidadeRequest) throws EntidadeNaoLocalizadaException {
        Especialidade especialidadeExistente = this.especialidadeService.localizar(id);

        especialidadeExistente.setNome(especialidadeRequest.getNome());
        especialidadeExistente.setDescricao(especialidadeRequest.getDescricao());

        Especialidade especialidadeAtualizada = this.especialidadeService.editar(id, especialidadeExistente);
        return especialidadeMapper.toResponseDTO(especialidadeAtualizada);
    }

    @Override
    public EspecialidadeResponseDTO getEspecialidadeById(Integer id) throws EntidadeNaoLocalizadaException {
        Especialidade especialidade = this.especialidadeService.localizar(id);
        return especialidadeMapper.toResponseDTO(especialidade);
    }

    @Override
    public EspecialidadeResponseDTO getEspecialidadeByNome(String nome) throws EntidadeNaoLocalizadaException {
        Especialidade especialidade = this.especialidadeService.localizarPorNome(nome);
        return especialidadeMapper.toResponseDTO(especialidade);
    }

    @Override
    public void deleteEspecialidade(Integer id) throws EntidadeNaoLocalizadaException {
        this.especialidadeService.remover(id);
    }

    @Override
    public List<EspecialidadeResponseDTO> getAllEspecialidades() {
        List<Especialidade> especialidades = especialidadeService.listarTodos();
        return especialidadeMapper.toResponseDTOList(especialidades);
    }

    @Override
    public List<EspecialidadeResponseDTO> getEspecialidadesAtivas() {
        List<Especialidade> especialidades = especialidadeService.listarAtivas();
        return especialidadeMapper.toResponseDTOList(especialidades);
    }

    @Override
    public EspecialidadeResponseDTO ativarEspecialidade(Integer id) throws EntidadeNaoLocalizadaException {
        Especialidade especialidadeAtivada = this.especialidadeService.ativar(id);
        return especialidadeMapper.toResponseDTO(especialidadeAtivada);
    }

    @Override
    public EspecialidadeResponseDTO inativarEspecialidade(Integer id) throws EntidadeNaoLocalizadaException {
        Especialidade especialidadeInativada = this.especialidadeService.inativar(id);
        return especialidadeMapper.toResponseDTO(especialidadeInativada);
    }
}