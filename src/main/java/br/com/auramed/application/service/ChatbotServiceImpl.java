package br.com.auramed.application.service;

import br.com.auramed.domain.model.Conversacao;
import br.com.auramed.domain.model.BaseConhecimento;
import br.com.auramed.domain.model.CategoriaPergunta;
import br.com.auramed.domain.model.Sentimento;
import br.com.auramed.domain.model.RespostaChat; // Import adicionado
import br.com.auramed.domain.service.*;
import br.com.auramed.domain.repository.ConversacaoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

@ApplicationScoped
public class ChatbotServiceImpl implements ChatbotService {

    @Inject
    BaseConhecimentoService baseConhecimentoService;

    @Inject
    GeminiService geminiService;

    @Inject
    AnaliseSentimentalService analiseSentimentalService;

    @Inject
    CategorizacaoService categorizacaoService;

    @Inject
    ConversacaoRepository conversacaoRepository;

    @Inject
    Logger logger;

    @Override
    public RespostaChat processarPergunta(String usuarioId, String perguntaUsuario) {
        try {
            logger.info("Processando pergunta do usuário: " + usuarioId + " - " + perguntaUsuario);

            // 1. Buscar na base de conhecimento primeiro
            BaseConhecimento melhorResposta = baseConhecimentoService
                    .buscarMelhorResposta(perguntaUsuario);

            String respostaFinal;
            String fonteResposta;

            if (melhorResposta != null && melhorResposta.getConfianca() > 0.85) {
                respostaFinal = melhorResposta.getResposta();
                fonteResposta = "base_conhecimento";
                logger.info("Resposta encontrada na base de conhecimento");
            } else {
                respostaFinal = geminiService.gerarResposta(perguntaUsuario, melhorResposta);
                fonteResposta = "gemini";
                logger.info("Resposta gerada pelo Gemini");
            }

            // 3. Analisar sentimento e categoria
            Sentimento sentimento = analiseSentimentalService.analisar(perguntaUsuario);
            CategoriaPergunta categoria = categorizacaoService.categorizar(perguntaUsuario);

            // 4. Salvar conversa
            Conversacao conversa = new Conversacao();
            conversa.setUsuarioId(usuarioId);
            conversa.setPerguntaUsuario(perguntaUsuario);
            conversa.setRespostaBot(respostaFinal);
            conversa.setSentimento(sentimento.name());
            conversa.setCategoria(categoria.name());
            conversa.setFonteResposta(fonteResposta);

            conversacaoRepository.salvar(conversa);
            logger.info("Conversa salva com sucesso para usuário: " + usuarioId);

            return new RespostaChat(respostaFinal, categoria, sentimento, fonteResposta);

        } catch (Exception e) {
            logger.error("Erro ao processar pergunta do usuário " + usuarioId + ": " + e.getMessage());
            throw new RuntimeException("Falha ao processar pergunta: " + e.getMessage());
        }
    }
}