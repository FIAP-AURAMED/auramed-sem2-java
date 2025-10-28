package br.com.auramed.domain.repository;

import br.com.auramed.domain.exception.EntidadeNaoLocalizadaException;
import br.com.auramed.domain.model.Cuidador;
import java.util.List;

public interface CuidadorRepository {
    Cuidador buscarPorId(Integer idPessoa) throws EntidadeNaoLocalizadaException;
    List<Cuidador> buscarTodos();
    List<Cuidador> buscarAtivos();
    Cuidador salvar(Cuidador cuidador);
    Cuidador editar(Cuidador cuidador);
    void remover(Integer idPessoa);
}