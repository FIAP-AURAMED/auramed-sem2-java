package br.com.auramed.application.service;

import br.com.auramed.domain.model.BaseConhecimento;
import br.com.auramed.domain.service.GeminiService;
import dev.langchain4j.model.chat.ChatLanguageModel;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

@ApplicationScoped
public class GeminiServiceImpl implements GeminiService {

    @Inject
    ChatLanguageModel modeloGemini;

    @Inject
    Logger logger;

    @Override
    public String gerarResposta(String pergunta, BaseConhecimento contexto) {
        try {
            logger.info("Gerando resposta com Gemini para: " + pergunta);

            String prompt = construirPrompt(pergunta, contexto);
            String resposta = modeloGemini.generate(prompt);

            logger.info("Resposta gerada com sucesso pelo Gemini");
            return resposta;

        } catch (Exception e) {
            logger.error("Erro ao gerar resposta com Gemini: " + e.getMessage());
            throw new RuntimeException("Falha ao gerar resposta: " + e.getMessage());
        }
    }

    private String construirPrompt(String pergunta, BaseConhecimento contexto) {
        StringBuilder prompt = new StringBuilder();
        prompt.append("Você é um assistente médico especializado chamado AuraMed. ");
        prompt.append("Responda em português de forma clara, precisa e empática.\n\n");

        if (contexto != null) {
            prompt.append("Contexto relevante: ").append(contexto.getResposta()).append("\n\n");
        }

        prompt.append("Pergunta do paciente: ").append(pergunta).append("\n\n");
        prompt.append("Instruções: \n");
        prompt.append("- Seja preciso e baseie-se em evidências médicas\n");
        prompt.append("- Use linguagem acessível para pacientes\n");
        prompt.append("- Se não souber, diga que vai consultar um especialista\n");
        prompt.append("- Mantenha o tom profissional mas acolhedor\n");

        return prompt.toString();
    }
}