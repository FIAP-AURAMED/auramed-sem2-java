package br.com.auramed.application.service;

import br.com.auramed.domain.model.Endereco;
import br.com.auramed.domain.repository.EnderecoRepository;
import br.com.auramed.domain.service.EnderecoService;
import br.com.auramed.domain.exception.EntidadeNaoLocalizadaException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

import java.util.List;

@ApplicationScoped
public class EnderecoServiceImpl implements EnderecoService {

    @Inject
    EnderecoRepository enderecoRepository;

    @Inject
    Logger logger;

    @Override
    public Endereco criar(Endereco endereco) {
        try {
            endereco.validarPessoa();
            endereco.validarTipoEndereco();
            endereco.validarLogradouro();
            endereco.validarNumero();
            endereco.validarBairro();
            endereco.validarCidade();
            endereco.validarUf();
            endereco.validarCep();
            endereco.validarComplemento();
            endereco.validarPrincipal();

            if ("S".equals(endereco.getPrincipal())) {
                removerPrincipalDeOutrosEnderecos(endereco.getPessoa().getId());
            }

            Endereco enderecoSalvo = enderecoRepository.salvar(endereco);
            logger.info("Endereço criado com sucesso. ID: " + enderecoSalvo.getId() + " - Pessoa ID: " + enderecoSalvo.getPessoa().getId());

            return enderecoSalvo;

        } catch (Exception e) {
            logger.error("Erro ao criar endereço: " + e.getMessage());
            throw new RuntimeException("Falha ao criar endereço: " + e.getMessage());
        }
    }

    @Override
    public Endereco editar(Integer id, Endereco endereco) throws EntidadeNaoLocalizadaException {
        try {
            Endereco enderecoExistente = enderecoRepository.buscarPorId(id);

            endereco.validarPessoa();
            endereco.validarTipoEndereco();
            endereco.validarLogradouro();
            endereco.validarNumero();
            endereco.validarBairro();
            endereco.validarCidade();
            endereco.validarUf();
            endereco.validarCep();
            endereco.validarComplemento();
            endereco.validarPrincipal();

            if ("S".equals(endereco.getPrincipal()) &&
                    !"S".equals(enderecoExistente.getPrincipal())) {
                removerPrincipalDeOutrosEnderecos(endereco.getPessoa().getId());
            }

            endereco.setId(id);
            Endereco enderecoAtualizado = enderecoRepository.editar(endereco);
            logger.info("Endereço atualizado com sucesso. ID: " + id);

            return enderecoAtualizado;
        } catch (EntidadeNaoLocalizadaException e) {
            logger.error("Endereço não encontrado para edição. ID: " + id);
            throw e;
        } catch (Exception e) {
            logger.error("Erro ao editar endereço. ID: " + id + ": " + e.getMessage());
            throw new RuntimeException("Falha ao editar endereço: " + e.getMessage());
        }
    }

    @Override
    public Endereco remover(Integer id) throws EntidadeNaoLocalizadaException {
        try {
            Endereco endereco = enderecoRepository.buscarPorId(id);

            enderecoRepository.remover(id);
            logger.info("Endereço removido com sucesso. ID: " + id);

            return endereco;
        } catch (EntidadeNaoLocalizadaException e) {
            logger.error("Endereço não encontrado para remoção. ID: " + id);
            throw e;
        } catch (Exception e) {
            logger.error("Erro ao remover endereço. ID: " + id + ": " + e.getMessage());
            throw new RuntimeException("Falha ao remover endereço: " + e.getMessage());
        }
    }

    @Override
    public Endereco localizar(Integer id) throws EntidadeNaoLocalizadaException {
        try {
            Endereco endereco = enderecoRepository.buscarPorId(id);
            logger.info("Endereço localizado. ID: " + id);
            return endereco;

        } catch (EntidadeNaoLocalizadaException e) {
            logger.error("Endereço não localizado. ID: " + id);
            throw e;
        } catch (Exception e) {
            logger.error("Erro ao localizar endereço. ID: " + id + ": " + e.getMessage());
            throw new RuntimeException("Falha ao localizar endereço: " + e.getMessage());
        }
    }

    @Override
    public List<Endereco> listarTodos() {
        try {
            List<Endereco> enderecos = enderecoRepository.buscarTodos();
            logger.info("Listados " + enderecos.size() + " endereços");
            return enderecos;

        } catch (Exception e) {
            logger.error("Erro ao listar endereços: " + e.getMessage());
            throw new RuntimeException("Falha ao listar endereços: " + e.getMessage());
        }
    }

    @Override
    public List<Endereco> listarPorPessoaId(Integer pessoaId) {
        try {
            List<Endereco> enderecos = enderecoRepository.buscarPorPessoaId(pessoaId);
            logger.info("Listados " + enderecos.size() + " endereços para pessoa ID: " + pessoaId);
            return enderecos;

        } catch (Exception e) {
            logger.error("Erro ao listar endereços por pessoa ID " + pessoaId + ": " + e.getMessage());
            throw new RuntimeException("Falha ao listar endereços por pessoa ID: " + e.getMessage());
        }
    }

    @Override
    public Endereco definirComoPrincipal(Integer id) throws EntidadeNaoLocalizadaException {
        try {
            Endereco endereco = enderecoRepository.buscarPorId(id);

            // Remover status principal de outros endereços da mesma pessoa
            removerPrincipalDeOutrosEnderecos(endereco.getPessoa().getId());

            // Definir este endereço como principal
            endereco.definirComoPrincipal();

            Endereco enderecoAtualizado = enderecoRepository.editar(endereco);
            logger.info("Endereço definido como principal. ID: " + id);

            return enderecoAtualizado;
        } catch (EntidadeNaoLocalizadaException e) {
            logger.error("Endereço não encontrado para definir como principal. ID: " + id);
            throw e;
        } catch (Exception e) {
            logger.error("Erro ao definir endereço como principal. ID: " + id + ": " + e.getMessage());
            throw new RuntimeException("Falha ao definir endereço como principal: " + e.getMessage());
        }
    }

    @Override
    public Endereco definirComoNaoPrincipal(Integer id) throws EntidadeNaoLocalizadaException {
        try {
            Endereco endereco = enderecoRepository.buscarPorId(id);
            endereco.definirComoNaoPrincipal();

            Endereco enderecoAtualizado = enderecoRepository.editar(endereco);
            logger.info("Endereço definido como não principal. ID: " + id);

            return enderecoAtualizado;
        } catch (EntidadeNaoLocalizadaException e) {
            logger.error("Endereço não encontrado para definir como não principal. ID: " + id);
            throw e;
        } catch (Exception e) {
            logger.error("Erro ao definir endereço como não principal. ID: " + id + ": " + e.getMessage());
            throw new RuntimeException("Falha ao definir endereço como não principal: " + e.getMessage());
        }
    }

    @Override
    public List<Endereco> listarEnderecosPrincipais() {
        try {
            List<Endereco> enderecos = enderecoRepository.buscarEnderecosPrincipais();
            logger.info("Listados " + enderecos.size() + " endereços principais");
            return enderecos;

        } catch (Exception e) {
            logger.error("Erro ao listar endereços principais: " + e.getMessage());
            throw new RuntimeException("Falha ao listar endereços principais: " + e.getMessage());
        }
    }

    private void removerPrincipalDeOutrosEnderecos(Integer pessoaId) {
        try {
            List<Endereco> enderecosPrincipais = enderecoRepository.buscarPorPessoaId(pessoaId)
                    .stream()
                    .filter(e -> "S".equals(e.getPrincipal()))
                    .toList();

            for (Endereco endereco : enderecosPrincipais) {
                endereco.definirComoNaoPrincipal();
                enderecoRepository.editar(endereco);
            }

            if (!enderecosPrincipais.isEmpty()) {
                logger.info("Removido status principal de " + enderecosPrincipais.size() + " endereços da pessoa ID: " + pessoaId);
            }
        } catch (Exception e) {
            logger.error("Erro ao remover status principal de outros endereços: " + e.getMessage());
            throw new RuntimeException("Falha ao remover status principal de outros endereços: " + e.getMessage());
        }
    }
}