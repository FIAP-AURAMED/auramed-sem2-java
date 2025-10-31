package br.com.auramed.interfaces.controllers;

import br.com.auramed.domain.exception.EntidadeNaoLocalizadaException;
import br.com.auramed.domain.model.BaseConhecimento;
import br.com.auramed.domain.service.BaseConhecimentoService;
import br.com.auramed.interfaces.dto.request.BaseConhecimentoRequestDTO;
import br.com.auramed.interfaces.dto.response.BaseConhecimentoResponseDTO;
import br.com.auramed.interfaces.mappers.BaseConhecimentoMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

import java.util.List;

@ApplicationScoped
public class BaseConhecimentoControllerImpl implements BaseConhecimentoController {
    private final BaseConhecimentoService baseConhecimentoService;
    private final BaseConhecimentoMapper baseConhecimentoMapper;

    @Inject
    Logger logger;

    @Inject
    public BaseConhecimentoControllerImpl(BaseConhecimentoService baseConhecimentoService,
                                          BaseConhecimentoMapper baseConhecimentoMapper) {
        this.baseConhecimentoService = baseConhecimentoService;
        this.baseConhecimentoMapper = baseConhecimentoMapper;
    }

    @Override
    public BaseConhecimentoResponseDTO criarBaseConhecimento(BaseConhecimentoRequestDTO conhecimentoRequest) {
        try {
            logger.info("Criando nova entrada na base de conhecimento");

            BaseConhecimento conhecimento = baseConhecimentoMapper.toDomain(conhecimentoRequest);
            BaseConhecimento conhecimentoCriado = this.baseConhecimentoService.criar(conhecimento);

            logger.info("BaseConhecimento criada com sucesso. ID: " + conhecimentoCriado.getId());
            return baseConhecimentoMapper.toResponseDTO(conhecimentoCriado);

        } catch (Exception e) {
            logger.error("Erro ao criar base de conhecimento: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public BaseConhecimentoResponseDTO editarBaseConhecimento(String id, BaseConhecimentoRequestDTO conhecimentoRequest) throws EntidadeNaoLocalizadaException {
        try {
            logger.info("Editando base de conhecimento. ID: " + id);

            BaseConhecimento conhecimento = baseConhecimentoMapper.toDomain(conhecimentoRequest);
            BaseConhecimento conhecimentoAtualizado = this.baseConhecimentoService.editar(id, conhecimento);

            logger.info("BaseConhecimento atualizada com sucesso. ID: " + id);
            return baseConhecimentoMapper.toResponseDTO(conhecimentoAtualizado);

        } catch (EntidadeNaoLocalizadaException e) {
            logger.error("BaseConhecimento não encontrada para edição. ID: " + id);
            throw e;
        } catch (Exception e) {
            logger.error("Erro ao editar base de conhecimento. ID: " + id + ": " + e.getMessage());
            throw new RuntimeException("Falha ao editar base de conhecimento: " + e.getMessage());
        }
    }

    @Override
    public BaseConhecimentoResponseDTO getBaseConhecimentoById(String id) throws EntidadeNaoLocalizadaException {
        try {
            BaseConhecimento conhecimento = this.baseConhecimentoService.localizar(id);
            logger.info("BaseConhecimento localizada. ID: " + id);
            return baseConhecimentoMapper.toResponseDTO(conhecimento);

        } catch (EntidadeNaoLocalizadaException e) {
            logger.error("BaseConhecimento não localizada. ID: " + id);
            throw e;
        } catch (Exception e) {
            logger.error("Erro ao localizar base de conhecimento. ID: " + id + ": " + e.getMessage());
            throw new RuntimeException("Falha ao localizar base de conhecimento: " + e.getMessage());
        }
    }

    @Override
    public void deleteBaseConhecimento(String id) throws EntidadeNaoLocalizadaException {
        try {
            this.baseConhecimentoService.remover(id);
            logger.info("BaseConhecimento removida com sucesso. ID: " + id);

        } catch (EntidadeNaoLocalizadaException e) {
            logger.error("BaseConhecimento não encontrada para remoção. ID: " + id);
            throw e;
        } catch (Exception e) {
            logger.error("Erro ao remover base de conhecimento. ID: " + id + ": " + e.getMessage());
            throw new RuntimeException("Falha ao remover base de conhecimento: " + e.getMessage());
        }
    }

    @Override
    public List<BaseConhecimentoResponseDTO> getAllBaseConhecimento() {
        try {
            List<BaseConhecimento> conhecimentos = baseConhecimentoService.listarTodos();
            logger.info("Listadas " + conhecimentos.size() + " entradas na base de conhecimento");
            return baseConhecimentoMapper.toResponseDTOList(conhecimentos);

        } catch (Exception e) {
            logger.error("Erro ao listar base de conhecimento: " + e.getMessage());
            throw new RuntimeException("Falha ao listar base de conhecimento: " + e.getMessage());
        }
    }

    @Override
    public List<BaseConhecimentoResponseDTO> getBaseConhecimentoPorCategoria(String categoria) {
        try {
            List<BaseConhecimento> conhecimentos = baseConhecimentoService.listarPorCategoria(categoria);
            logger.info("Listadas " + conhecimentos.size() + " entradas na categoria: " + categoria);
            return baseConhecimentoMapper.toResponseDTOList(conhecimentos);

        } catch (Exception e) {
            logger.error("Erro ao listar base de conhecimento por categoria " + categoria + ": " + e.getMessage());
            throw new RuntimeException("Falha ao listar base de conhecimento por categoria: " + e.getMessage());
        }
    }
}