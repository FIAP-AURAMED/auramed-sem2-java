package br.com.auramed.domain.repository;

import br.com.auramed.domain.exception.EntidadeNaoLocalizadaException;
import br.com.auramed.domain.model.Pessoa;
import java.util.List;

public interface PessoaRepository {
    Pessoa buscarPorId(Integer id) throws EntidadeNaoLocalizadaException;
    Pessoa buscarPorEmail(String email) throws EntidadeNaoLocalizadaException;
    Pessoa buscarPorCpf(String cpf) throws EntidadeNaoLocalizadaException;
    List<Pessoa> buscarTodos();
    List<Pessoa> buscarPorTipo(String tipoPessoa);
    List<Pessoa> buscarAtivos();
    Pessoa salvar(Pessoa pessoa);
    Pessoa editar(Pessoa pessoa);
    void remover(Integer id);
}