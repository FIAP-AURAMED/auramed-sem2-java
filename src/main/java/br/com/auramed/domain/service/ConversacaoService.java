package br.com.auramed.domain.service;

import br.com.auramed.domain.model.Conversacao;
import java.util.List;

public interface ConversacaoService {
    Conversacao salvar(Conversacao conversacao);
    List<Conversacao> listarPorUsuario(String usuarioId);
    List<Conversacao> listarTodas();
}