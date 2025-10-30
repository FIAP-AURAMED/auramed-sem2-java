package br.com.auramed.domain.service;

import br.com.auramed.domain.exception.EntidadeNaoLocalizadaException;
import br.com.auramed.domain.model.Cuidador;
import java.util.List;

public interface CuidadorService {
    Cuidador criar(Cuidador cuidador);
    Cuidador editar(Integer idPessoa, Cuidador cuidador) throws EntidadeNaoLocalizadaException;
    Cuidador remover(Integer idPessoa) throws EntidadeNaoLocalizadaException;
    Cuidador localizar(Integer idPessoa) throws EntidadeNaoLocalizadaException;
    List<Cuidador> listarTodos();
    Cuidador ativar(Integer idPessoa) throws EntidadeNaoLocalizadaException;
    Cuidador inativar(Integer idPessoa) throws EntidadeNaoLocalizadaException;
}