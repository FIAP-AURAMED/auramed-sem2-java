package br.com.auramed.application.service;

import br.com.auramed.domain.model.Pessoa;
import br.com.auramed.domain.repository.PessoaRepository;
import br.com.auramed.domain.service.PessoaService;
import br.com.auramed.domain.exception.EntidadeNaoLocalizadaException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

import java.util.List;

@ApplicationScoped
public class PessoaServiceImpl implements PessoaService {

    @Inject
    PessoaRepository pessoaRepository;

    @Inject
    Logger logger;

    @Override
    public Pessoa criar(Pessoa pessoa) {
        try {
            pessoa.validarNome();
            pessoa.validarEmail();
            pessoa.validarCpf();
            pessoa.validarTelefone();
            pessoa.validarTipoPessoa();
            pessoa.validarDataNascimento();
            pessoa.validarGenero();

            if (pessoa.getEmail() != null && !pessoa.getEmail().isBlank() &&
                    existePessoaComEmail(pessoa.getEmail())) {
                throw new RuntimeException("Já existe pessoa cadastrada com este email: " + pessoa.getEmail());
            }

            if (pessoa.getCpf() != null && !pessoa.getCpf().isBlank() &&
                    existePessoaComCpf(pessoa.getCpf())) {
                throw new RuntimeException("Já existe pessoa cadastrada com este CPF: " + pessoa.getCpf());
            }

            Pessoa pessoaSalva = pessoaRepository.salvar(pessoa);
            logger.info("Pessoa criada com sucesso. ID: " + pessoaSalva.getId() + " - Tipo: " + pessoaSalva.getTipoPessoa());

            return pessoaSalva;

        } catch (Exception e) {
            logger.error("Erro ao criar pessoa: " + e.getMessage());
            throw new RuntimeException("Falha ao criar pessoa: " + e.getMessage());
        }
    }

    @Override
    public Pessoa editar(Integer id, Pessoa pessoa) throws EntidadeNaoLocalizadaException {
        try {
            Pessoa pessoaExistente = pessoaRepository.buscarPorId(id);

            pessoa.validarNome();
            pessoa.validarEmail();
            pessoa.validarCpf();
            pessoa.validarTelefone();
            pessoa.validarTipoPessoa();
            pessoa.validarDataNascimento();
            pessoa.validarGenero();

            if (pessoa.getEmail() != null && !pessoa.getEmail().isBlank() &&
                    !pessoaExistente.getEmail().equals(pessoa.getEmail()) &&
                    existePessoaComEmail(pessoa.getEmail())) {
                throw new RuntimeException("Já existe pessoa cadastrada com este email: " + pessoa.getEmail());
            }

            if (pessoa.getCpf() != null && !pessoa.getCpf().isBlank() &&
                    !pessoaExistente.getCpf().equals(pessoa.getCpf()) &&
                    existePessoaComCpf(pessoa.getCpf())) {
                throw new RuntimeException("Já existe pessoa cadastrada com este CPF: " + pessoa.getCpf());
            }

            pessoa.setId(id);
            Pessoa pessoaAtualizada = pessoaRepository.editar(pessoa);
            logger.info("Pessoa atualizada com sucesso. ID: " + id);

            return pessoaAtualizada;
        } catch (EntidadeNaoLocalizadaException e) {
            logger.error("Pessoa não encontrada para edição. ID: " + id);
            throw e;
        } catch (Exception e) {
            logger.error("Erro ao editar pessoa. ID: " + id + ": " + e.getMessage());
            throw new RuntimeException("Falha ao editar pessoa: " + e.getMessage());
        }
    }

    @Override
    public Pessoa remover(Integer id) throws EntidadeNaoLocalizadaException {
        try {
            Pessoa pessoa = pessoaRepository.buscarPorId(id);

            pessoaRepository.remover(id);
            logger.info("Pessoa removida com sucesso. ID: " + id);

            return pessoa;
        } catch (EntidadeNaoLocalizadaException e) {
            logger.error("Pessoa não encontrada para remoção. ID: " + id);
            throw e;
        } catch (Exception e) {
            logger.error("Erro ao remover pessoa. ID: " + id + ": " + e.getMessage());
            throw new RuntimeException("Falha ao remover pessoa: " + e.getMessage());
        }
    }

    @Override
    public Pessoa localizar(Integer id) throws EntidadeNaoLocalizadaException {
        try {
            Pessoa pessoa = pessoaRepository.buscarPorId(id);
            logger.info("Pessoa localizada. ID: " + id);
            return pessoa;

        } catch (EntidadeNaoLocalizadaException e) {
            logger.error("Pessoa não localizada. ID: " + id);
            throw e;
        } catch (Exception e) {
            logger.error("Erro ao localizar pessoa. ID: " + id + ": " + e.getMessage());
            throw new RuntimeException("Falha ao localizar pessoa: " + e.getMessage());
        }
    }

    @Override
    public List<Pessoa> listarTodos() {
        try {
            List<Pessoa> pessoas = pessoaRepository.buscarTodos();
            logger.info("Listadas " + pessoas.size() + " pessoas");
            return pessoas;

        } catch (Exception e) {
            logger.error("Erro ao listar pessoas: " + e.getMessage());
            throw new RuntimeException("Falha ao listar pessoas: " + e.getMessage());
        }
    }

    @Override
    public List<Pessoa> listarPorTipo(String tipoPessoa) {
        try {
            List<Pessoa> pessoas = pessoaRepository.buscarPorTipo(tipoPessoa);
            logger.info("Listadas " + pessoas.size() + " pessoas do tipo: " + tipoPessoa);
            return pessoas;

        } catch (Exception e) {
            logger.error("Erro ao listar pessoas por tipo " + tipoPessoa + ": " + e.getMessage());
            throw new RuntimeException("Falha ao listar pessoas por tipo: " + e.getMessage());
        }
    }

    @Override
    public Pessoa ativar(Integer id) throws EntidadeNaoLocalizadaException {
        try {
            Pessoa pessoa = pessoaRepository.buscarPorId(id);
            pessoa.setAtivo("S");

            Pessoa pessoaAtualizada = pessoaRepository.editar(pessoa);
            logger.info("Pessoa ativada com sucesso. ID: " + id);

            return pessoaAtualizada;
        } catch (EntidadeNaoLocalizadaException e) {
            logger.error("Pessoa não encontrada para ativação. ID: " + id);
            throw e;
        } catch (Exception e) {
            logger.error("Erro ao ativar pessoa. ID: " + id + ": " + e.getMessage());
            throw new RuntimeException("Falha ao ativar pessoa: " + e.getMessage());
        }
    }

    @Override
    public Pessoa inativar(Integer id) throws EntidadeNaoLocalizadaException {
        try {
            Pessoa pessoa = pessoaRepository.buscarPorId(id);
            pessoa.setAtivo("N");

            Pessoa pessoaAtualizada = pessoaRepository.editar(pessoa);
            logger.info("Pessoa inativada com sucesso. ID: " + id);

            return pessoaAtualizada;
        } catch (EntidadeNaoLocalizadaException e) {
            logger.error("Pessoa não encontrada para inativação. ID: " + id);
            throw e;
        } catch (Exception e) {
            logger.error("Erro ao inativar pessoa. ID: " + id + ": " + e.getMessage());
            throw new RuntimeException("Falha ao inativar pessoa: " + e.getMessage());
        }
    }

    private boolean existePessoaComEmail(String email) {
        try {
            pessoaRepository.buscarPorEmail(email);
            return true;
        } catch (EntidadeNaoLocalizadaException e) {
            return false;
        }
    }

    private boolean existePessoaComCpf(String cpf) {
        try {
            pessoaRepository.buscarPorCpf(cpf);
            return true;
        } catch (EntidadeNaoLocalizadaException e) {
            return false;
        }
    }
}