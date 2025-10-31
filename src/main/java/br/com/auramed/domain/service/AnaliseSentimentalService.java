package br.com.auramed.domain.service;

import br.com.auramed.domain.model.Sentimento;

public interface AnaliseSentimentalService {
    Sentimento analisar(String texto);
}