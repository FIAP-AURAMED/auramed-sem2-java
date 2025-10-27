package br.com.auramed.domain.service;

import br.com.auramed.domain.exception.EntidadeNaoLocalizadaException;
import br.com.auramed.domain.model.Pessoa;
import java.util.List;

public interface PessoaService {
    Pessoa criar(Pessoa pessoa);
    Pessoa editar(Integer id, Pessoa pessoa) throws EntidadeNaoLocalizadaException;
    Pessoa remover(Integer id) throws EntidadeNaoLocalizadaException;
    Pessoa localizar(Integer id) throws EntidadeNaoLocalizadaException;
    List<Pessoa> listarTodos();
    List<Pessoa> listarPorTipo(String tipoPessoa);
    Pessoa ativar(Integer id) throws EntidadeNaoLocalizadaException;
    Pessoa inativar(Integer id) throws EntidadeNaoLocalizadaException;
}