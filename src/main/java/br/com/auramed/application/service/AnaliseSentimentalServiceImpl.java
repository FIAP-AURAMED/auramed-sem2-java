package br.com.auramed.application.service;

import br.com.auramed.domain.model.Sentimento;
import br.com.auramed.domain.service.AnaliseSentimentalService;
import dev.langchain4j.model.chat.ChatLanguageModel;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

@ApplicationScoped
public class AnaliseSentimentalServiceImpl implements AnaliseSentimentalService {

    @Inject
    ChatLanguageModel modeloGemini;

    @Inject
    Logger logger;

    @Override
    public Sentimento analisar(String texto) {
        try {
            logger.info("Analisando sentimento do texto: " + (texto.length() > 50 ? texto.substring(0, 50) + "..." : texto));

            String prompt = """
                Analise o sentimento do seguinte texto em português e responda APENAS com uma destas palavras: 
                POSITIVO, NEUTRO ou NEGATIVO.
                
                Texto: "%s"
                """.formatted(texto);

            String resultado = modeloGemini.generate(prompt);
            Sentimento sentimento = Sentimento.valueOf(resultado.trim().toUpperCase());

            logger.info("Sentimento analisado: " + sentimento);
            return sentimento;

        } catch (Exception e) {
            logger.error("Erro ao analisar sentimento: " + e.getMessage());
            throw new RuntimeException("Falha ao analisar sentimento: " + e.getMessage());
        }
    }
}