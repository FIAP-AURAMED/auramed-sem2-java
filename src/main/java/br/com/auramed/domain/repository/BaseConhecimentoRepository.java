package br.com.auramed.domain.repository;

import br.com.auramed.domain.model.BaseConhecimento;
import br.com.auramed.domain.exception.EntidadeNaoLocalizadaException;
import java.util.List;

public interface BaseConhecimentoRepository {
    BaseConhecimento buscarPorPalavrasChave(String pergunta);
    BaseConhecimento buscarPorSimilaridade(String pergunta);
    BaseConhecimento buscarPorId(String id) throws EntidadeNaoLocalizadaException;
    List<BaseConhecimento> buscarTodos();
    List<BaseConhecimento> buscarPorCategoria(String categoria);
    BaseConhecimento salvar(BaseConhecimento conhecimento);
    BaseConhecimento editar(BaseConhecimento conhecimento);
    void remover(String id);
}