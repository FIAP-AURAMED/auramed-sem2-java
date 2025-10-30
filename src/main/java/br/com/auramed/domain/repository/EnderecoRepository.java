package br.com.auramed.domain.repository;

import br.com.auramed.domain.exception.EntidadeNaoLocalizadaException;
import br.com.auramed.domain.model.Endereco;
import java.util.List;

public interface EnderecoRepository {
    Endereco buscarPorId(Integer id) throws EntidadeNaoLocalizadaException;
    List<Endereco> buscarPorPessoaId(Integer pessoaId);
    List<Endereco> buscarEnderecosPrincipais();
    List<Endereco> buscarTodos();
    Endereco salvar(Endereco endereco);
    Endereco editar(Endereco endereco);
    void remover(Integer id);
    void removerTodosPorPessoaId(Integer pessoaId);
}