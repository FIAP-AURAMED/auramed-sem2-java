package br.com.auramed.domain.service;

import br.com.auramed.domain.model.CategoriaPergunta;

public interface CategorizacaoService {
    CategoriaPergunta categorizar(String pergunta);
}