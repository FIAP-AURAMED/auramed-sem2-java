package br.com.auramed.application.service;

import br.com.auramed.domain.model.Especialidade;
import br.com.auramed.domain.repository.EspecialidadeRepository;
import br.com.auramed.domain.service.EspecialidadeService;
import br.com.auramed.domain.exception.EntidadeNaoLocalizadaException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

import java.util.List;

@ApplicationScoped
public class EspecialidadeServiceImpl implements EspecialidadeService {

    @Inject
    EspecialidadeRepository especialidadeRepository;

    @Inject
    Logger logger;

    @Override
    public Especialidade criar(Especialidade especialidade) {
        try {
            especialidade.validarNome();
            especialidade.validarDescricao();
            especialidade.validarAtivo();

            if (existeEspecialidadeComNome(especialidade.getNome())) {
                throw new RuntimeException("Já existe especialidade cadastrada com este nome: " + especialidade.getNome());
            }

            Especialidade especialidadeSalva = especialidadeRepository.salvar(especialidade);
            logger.info("Especialidade criada com sucesso. ID: " + especialidadeSalva.getId() + " - Nome: " + especialidadeSalva.getNome());

            return especialidadeSalva;

        } catch (Exception e) {
            logger.error("Erro ao criar especialidade: " + e.getMessage());
            throw new RuntimeException("Falha ao criar especialidade: " + e.getMessage());
        }
    }

    @Override
    public Especialidade editar(Integer id, Especialidade especialidade) throws EntidadeNaoLocalizadaException {
        try {
            Especialidade especialidadeExistente = especialidadeRepository.buscarPorId(id);

            especialidade.validarNome();
            especialidade.validarDescricao();
            especialidade.validarAtivo();

            if (!especialidadeExistente.getNome().equals(especialidade.getNome()) &&
                    existeEspecialidadeComNome(especialidade.getNome())) {
                throw new RuntimeException("Já existe especialidade cadastrada com este nome: " + especialidade.getNome());
            }

            especialidade.setId(id);
            Especialidade especialidadeAtualizada = especialidadeRepository.editar(especialidade);
            logger.info("Especialidade atualizada com sucesso. ID: " + id);

            return especialidadeAtualizada;
        } catch (EntidadeNaoLocalizadaException e) {
            logger.error("Especialidade não encontrada para edição. ID: " + id);
            throw e;
        } catch (Exception e) {
            logger.error("Erro ao editar especialidade. ID: " + id + ": " + e.getMessage());
            throw new RuntimeException("Falha ao editar especialidade: " + e.getMessage());
        }
    }

    @Override
    public Especialidade remover(Integer id) throws EntidadeNaoLocalizadaException {
        try {
            Especialidade especialidade = especialidadeRepository.buscarPorId(id);

            especialidadeRepository.remover(id);
            logger.info("Especialidade removida com sucesso. ID: " + id);

            return especialidade;
        } catch (EntidadeNaoLocalizadaException e) {
            logger.error("Especialidade não encontrada para remoção. ID: " + id);
            throw e;
        } catch (Exception e) {
            logger.error("Erro ao remover especialidade. ID: " + id + ": " + e.getMessage());
            throw new RuntimeException("Falha ao remover especialidade: " + e.getMessage());
        }
    }

    @Override
    public Especialidade localizar(Integer id) throws EntidadeNaoLocalizadaException {
        try {
            Especialidade especialidade = especialidadeRepository.buscarPorId(id);
            logger.info("Especialidade localizada. ID: " + id);
            return especialidade;

        } catch (EntidadeNaoLocalizadaException e) {
            logger.error("Especialidade não localizada. ID: " + id);
            throw e;
        } catch (Exception e) {
            logger.error("Erro ao localizar especialidade. ID: " + id + ": " + e.getMessage());
            throw new RuntimeException("Falha ao localizar especialidade: " + e.getMessage());
        }
    }

    @Override
    public Especialidade localizarPorNome(String nome) throws EntidadeNaoLocalizadaException {
        try {
            Especialidade especialidade = especialidadeRepository.buscarPorNome(nome);
            logger.info("Especialidade localizada por nome: " + nome);
            return especialidade;

        } catch (EntidadeNaoLocalizadaException e) {
            logger.error("Especialidade não localizada por nome: " + nome);
            throw e;
        } catch (Exception e) {
            logger.error("Erro ao localizar especialidade por nome " + nome + ": " + e.getMessage());
            throw new RuntimeException("Falha ao localizar especialidade por nome: " + e.getMessage());
        }
    }

    @Override
    public List<Especialidade> listarTodos() {
        try {
            List<Especialidade> especialidades = especialidadeRepository.buscarTodos();
            logger.info("Listadas " + especialidades.size() + " especialidades");
            return especialidades;

        } catch (Exception e) {
            logger.error("Erro ao listar especialidades: " + e.getMessage());
            throw new RuntimeException("Falha ao listar especialidades: " + e.getMessage());
        }
    }

    @Override
    public List<Especialidade> listarAtivas() {
        try {
            List<Especialidade> especialidades = especialidadeRepository.buscarAtivas();
            logger.info("Listadas " + especialidades.size() + " especialidades ativas");
            return especialidades;

        } catch (Exception e) {
            logger.error("Erro ao listar especialidades ativas: " + e.getMessage());
            throw new RuntimeException("Falha ao listar especialidades ativas: " + e.getMessage());
        }
    }

    @Override
    public Especialidade ativar(Integer id) throws EntidadeNaoLocalizadaException {
        try {
            Especialidade especialidade = especialidadeRepository.buscarPorId(id);
            especialidade.setAtivo("S");

            Especialidade especialidadeAtualizada = especialidadeRepository.editar(especialidade);
            logger.info("Especialidade ativada com sucesso. ID: " + id);

            return especialidadeAtualizada;
        } catch (EntidadeNaoLocalizadaException e) {
            logger.error("Especialidade não encontrada para ativação. ID: " + id);
            throw e;
        } catch (Exception e) {
            logger.error("Erro ao ativar especialidade. ID: " + id + ": " + e.getMessage());
            throw new RuntimeException("Falha ao ativar especialidade: " + e.getMessage());
        }
    }

    @Override
    public Especialidade inativar(Integer id) throws EntidadeNaoLocalizadaException {
        try {
            Especialidade especialidade = especialidadeRepository.buscarPorId(id);
            especialidade.setAtivo("N");

            Especialidade especialidadeAtualizada = especialidadeRepository.editar(especialidade);
            logger.info("Especialidade inativada com sucesso. ID: " + id);

            return especialidadeAtualizada;
        } catch (EntidadeNaoLocalizadaException e) {
            logger.error("Especialidade não encontrada para inativação. ID: " + id);
            throw e;
        } catch (Exception e) {
            logger.error("Erro ao inativar especialidade. ID: " + id + ": " + e.getMessage());
            throw new RuntimeException("Falha ao inativar especialidade: " + e.getMessage());
        }
    }

    private boolean existeEspecialidadeComNome(String nome) {
        try {
            especialidadeRepository.buscarPorNome(nome);
            return true;
        } catch (EntidadeNaoLocalizadaException e) {
            return false;
        }
    }
}