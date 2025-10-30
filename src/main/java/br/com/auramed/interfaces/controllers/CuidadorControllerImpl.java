package br.com.auramed.interfaces.controllers;

import br.com.auramed.domain.exception.EntidadeNaoLocalizadaException;
import br.com.auramed.domain.model.Cuidador;
import br.com.auramed.domain.service.CuidadorService;
import br.com.auramed.interfaces.dto.request.CuidadorRequestDTO;
import br.com.auramed.interfaces.dto.response.CuidadorResponseDTO;
import br.com.auramed.interfaces.mappers.CuidadorMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;

@ApplicationScoped
public class CuidadorControllerImpl implements CuidadorController {
    private final CuidadorService cuidadorService;
    private final CuidadorMapper cuidadorMapper;

    @Inject
    public CuidadorControllerImpl(CuidadorService cuidadorService, CuidadorMapper cuidadorMapper) {
        this.cuidadorService = cuidadorService;
        this.cuidadorMapper = cuidadorMapper;
    }

    @Override
    public CuidadorResponseDTO criarCuidador(CuidadorRequestDTO cuidadorRequest) throws EntidadeNaoLocalizadaException {
        try {
            Cuidador cuidador = cuidadorMapper.toDomain(cuidadorRequest);
            Cuidador cuidadorCriado = this.cuidadorService.criar(cuidador);
            return cuidadorMapper.toResponseDTO(cuidadorCriado);

        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public CuidadorResponseDTO editarCuidador(Integer idPessoa, CuidadorRequestDTO cuidadorRequest) throws EntidadeNaoLocalizadaException {
        Cuidador cuidadorExistente = this.cuidadorService.localizar(idPessoa);

        cuidadorExistente.setParentesco(cuidadorRequest.getParentesco());
        cuidadorExistente.setTempoCuidado(cuidadorRequest.getTempoCuidado());

        Cuidador cuidadorAtualizado = this.cuidadorService.editar(idPessoa, cuidadorExistente);
        return cuidadorMapper.toResponseDTO(cuidadorAtualizado);
    }

    @Override
    public CuidadorResponseDTO getCuidadorById(Integer idPessoa) throws EntidadeNaoLocalizadaException {
        Cuidador cuidador = this.cuidadorService.localizar(idPessoa);
        return cuidadorMapper.toResponseDTO(cuidador);
    }

    @Override
    public void deleteCuidador(Integer idPessoa) throws EntidadeNaoLocalizadaException {
        this.cuidadorService.remover(idPessoa);
    }

    @Override
    public List<CuidadorResponseDTO> getAllCuidadores() {
        List<Cuidador> cuidadores = cuidadorService.listarTodos();
        return cuidadorMapper.toResponseDTOList(cuidadores);
    }

    @Override
    public CuidadorResponseDTO ativarCuidador(Integer idPessoa) throws EntidadeNaoLocalizadaException {
        Cuidador cuidadorAtivado = this.cuidadorService.ativar(idPessoa);
        return cuidadorMapper.toResponseDTO(cuidadorAtivado);
    }

    @Override
    public CuidadorResponseDTO inativarCuidador(Integer idPessoa) throws EntidadeNaoLocalizadaException {
        Cuidador cuidadorInativado = this.cuidadorService.inativar(idPessoa);
        return cuidadorMapper.toResponseDTO(cuidadorInativado);
    }
}