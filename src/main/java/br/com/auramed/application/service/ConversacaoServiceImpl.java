package br.com.auramed.application.service;

import br.com.auramed.domain.model.Conversacao;
import br.com.auramed.domain.service.ConversacaoService;
import br.com.auramed.domain.repository.ConversacaoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

import java.util.List;

@ApplicationScoped
public class ConversacaoServiceImpl implements ConversacaoService {

    @Inject
    ConversacaoRepository conversacaoRepository;

    @Inject
    Logger logger;

    @Override
    public Conversacao salvar(Conversacao conversacao) {
        try {
            conversacao.validarUsuarioId();
            conversacao.validarPerguntaUsuario();
            conversacao.validarRespostaBot();

            Conversacao conversacaoSalva = conversacaoRepository.salvar(conversacao);
            logger.info("Conversação salva com sucesso. ID: " + conversacaoSalva.getId() + " - Usuário: " + conversacaoSalva.getUsuarioId());

            return conversacaoSalva;

        } catch (Exception e) {
            logger.error("Erro ao salvar conversação: " + e.getMessage());
            throw new RuntimeException("Falha ao salvar conversação: " + e.getMessage());
        }
    }

    @Override
    public List<Conversacao> listarPorUsuario(String usuarioId) {
        try {
            List<Conversacao> conversacoes = conversacaoRepository.buscarPorUsuario(usuarioId);
            logger.info("Listadas " + conversacoes.size() + " conversações para usuário: " + usuarioId);
            return conversacoes;

        } catch (Exception e) {
            logger.error("Erro ao listar conversações por usuário " + usuarioId + ": " + e.getMessage());
            throw new RuntimeException("Falha ao listar conversações: " + e.getMessage());
        }
    }

    @Override
    public List<Conversacao> listarTodas() {
        try {
            List<Conversacao> conversacoes = conversacaoRepository.buscarTodos();
            logger.info("Listadas " + conversacoes.size() + " conversações no total");
            return conversacoes;

        } catch (Exception e) {
            logger.error("Erro ao listar todas as conversações: " + e.getMessage());
            throw new RuntimeException("Falha ao listar conversações: " + e.getMessage());
        }
    }
}