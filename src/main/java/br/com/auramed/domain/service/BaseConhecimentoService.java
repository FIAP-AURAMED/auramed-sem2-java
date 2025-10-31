package br.com.auramed.domain.service;

import br.com.auramed.domain.exception.EntidadeNaoLocalizadaException;
import br.com.auramed.domain.model.BaseConhecimento;
import java.util.List;

public interface BaseConhecimentoService {
    BaseConhecimento buscarMelhorResposta(String pergunta);
    BaseConhecimento criar(BaseConhecimento conhecimento);
    BaseConhecimento editar(String id, BaseConhecimento conhecimento) throws EntidadeNaoLocalizadaException;
    BaseConhecimento remover(String id) throws EntidadeNaoLocalizadaException;
    BaseConhecimento localizar(String id) throws EntidadeNaoLocalizadaException;
    List<BaseConhecimento> listarTodos();
    List<BaseConhecimento> listarPorCategoria(String categoria);
}