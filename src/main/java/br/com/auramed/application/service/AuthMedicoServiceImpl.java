package br.com.auramed.application.service;

import br.com.auramed.domain.model.AuthMedico;
import br.com.auramed.domain.repository.AuthMedicoRepository;
import br.com.auramed.domain.service.AuthMedicoService;
import br.com.auramed.domain.exception.EntidadeNaoLocalizadaException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

@ApplicationScoped
public class AuthMedicoServiceImpl implements AuthMedicoService {

    @Inject
    AuthMedicoRepository authMedicoRepository;

    @Inject
    Logger logger;

    @Override
    public AuthMedico criar(AuthMedico authMedico) {
        try {
            authMedico.validarMedico();
            authMedico.validarEmail();
            authMedico.validarSenhaHash();
            authMedico.validarTentativasLogin();
            authMedico.validarContaAtiva();

            if (existeAuthComEmail(authMedico.getEmail())) {
                throw new RuntimeException("Já existe autenticação cadastrada com este email: " + authMedico.getEmail());
            }

            if (existeAuthComMedicoId(authMedico.getMedico().getId())) {
                throw new RuntimeException("Já existe autenticação cadastrada para este médico ID: " + authMedico.getMedico().getId());
            }

            AuthMedico authSalvo = authMedicoRepository.salvar(authMedico);
            logger.info("Autenticação de médico criada com sucesso. ID: " + authSalvo.getId());

            return authSalvo;

        } catch (Exception e) {
            logger.error("Erro ao criar autenticação de médico: " + e.getMessage());
            throw new RuntimeException("Falha ao criar autenticação de médico: " + e.getMessage());
        }
    }

    @Override
    public AuthMedico editar(Integer id, AuthMedico authMedico) throws EntidadeNaoLocalizadaException {
        try {
            AuthMedico authExistente = authMedicoRepository.buscarPorId(id);

            authMedico.validarMedico();
            authMedico.validarEmail();
            authMedico.validarSenhaHash();
            authMedico.validarTentativasLogin();
            authMedico.validarContaAtiva();

            if (!authExistente.getEmail().equals(authMedico.getEmail()) &&
                    existeAuthComEmail(authMedico.getEmail())) {
                throw new RuntimeException("Já existe autenticação cadastrada com este email: " + authMedico.getEmail());
            }

            authMedico.setId(id);
            AuthMedico authAtualizado = authMedicoRepository.editar(authMedico);
            logger.info("Autenticação de médico atualizada com sucesso. ID: " + id);

            return authAtualizado;
        } catch (EntidadeNaoLocalizadaException e) {
            logger.error("Autenticação de médico não encontrada para edição. ID: " + id);
            throw e;
        } catch (Exception e) {
            logger.error("Erro ao editar autenticação de médico. ID: " + id + ": " + e.getMessage());
            throw new RuntimeException("Falha ao editar autenticação de médico: " + e.getMessage());
        }
    }

    @Override
    public AuthMedico remover(Integer id) throws EntidadeNaoLocalizadaException {
        try {
            AuthMedico authMedico = authMedicoRepository.buscarPorId(id);

            authMedicoRepository.remover(id);
            logger.info("Autenticação de médico removida com sucesso. ID: " + id);

            return authMedico;
        } catch (EntidadeNaoLocalizadaException e) {
            logger.error("Autenticação de médico não encontrada para remoção. ID: " + id);
            throw e;
        } catch (Exception e) {
            logger.error("Erro ao remover autenticação de médico. ID: " + id + ": " + e.getMessage());
            throw new RuntimeException("Falha ao remover autenticação de médico: " + e.getMessage());
        }
    }

    @Override
    public AuthMedico localizar(Integer id) throws EntidadeNaoLocalizadaException {
        try {
            AuthMedico authMedico = authMedicoRepository.buscarPorId(id);
            logger.info("Autenticação de médico localizada. ID: " + id);
            return authMedico;

        } catch (EntidadeNaoLocalizadaException e) {
            logger.error("Autenticação de médico não localizada. ID: " + id);
            throw e;
        } catch (Exception e) {
            logger.error("Erro ao localizar autenticação de médico. ID: " + id + ": " + e.getMessage());
            throw new RuntimeException("Falha ao localizar autenticação de médico: " + e.getMessage());
        }
    }

    @Override
    public AuthMedico localizarPorEmail(String email) throws EntidadeNaoLocalizadaException {
        try {
            AuthMedico authMedico = authMedicoRepository.buscarPorEmail(email);
            logger.info("Autenticação de médico localizada por email: " + email);
            return authMedico;

        } catch (EntidadeNaoLocalizadaException e) {
            logger.error("Autenticação de médico não localizada por email: " + email);
            throw e;
        } catch (Exception e) {
            logger.error("Erro ao localizar autenticação de médico por email " + email + ": " + e.getMessage());
            throw new RuntimeException("Falha ao localizar autenticação de médico por email: " + e.getMessage());
        }
    }

    @Override
    public AuthMedico localizarPorMedicoId(Integer medicoId) throws EntidadeNaoLocalizadaException {
        try {
            AuthMedico authMedico = authMedicoRepository.buscarPorMedicoId(medicoId);
            logger.info("Autenticação de médico localizada por médico ID: " + medicoId);
            return authMedico;

        } catch (EntidadeNaoLocalizadaException e) {
            logger.error("Autenticação de médico não localizada por médico ID: " + medicoId);
            throw e;
        } catch (Exception e) {
            logger.error("Erro ao localizar autenticação de médico por médico ID " + medicoId + ": " + e.getMessage());
            throw new RuntimeException("Falha ao localizar autenticação de médico por médico ID: " + e.getMessage());
        }
    }

    @Override
    public void validarCredenciais(String email, String senha) throws EntidadeNaoLocalizadaException {
        try {
            AuthMedico authMedico = authMedicoRepository.buscarPorEmail(email);

            if (!"S".equals(authMedico.getContaAtiva())) {
                throw new RuntimeException("Conta inativa. Entre em contato com o administrador.");
            }

            if (authMedico.getTentativasLogin() >= 5) {
                throw new RuntimeException("Conta bloqueada devido a tentativas excessivas. Entre em contato com o administrador.");
            }

            // Aqui você implementaria a validação da senha com bcrypt
            // Por enquanto é um placeholder
            if (!authMedico.getSenhaHash().equals(senha)) { // Isso será substituído por bcrypt
                authMedico.incrementarTentativaLogin();
                authMedicoRepository.editar(authMedico);
                throw new RuntimeException("Credenciais inválidas.");
            }

            // Login bem-sucedido
            authMedico.resetarTentativasLogin();
            authMedicoRepository.editar(authMedico);
            logger.info("Credenciais validadas com sucesso para email: " + email);

        } catch (EntidadeNaoLocalizadaException e) {
            logger.error("Tentativa de login com email não cadastrado: " + email);
            throw new RuntimeException("Credenciais inválidas.");
        } catch (Exception e) {
            logger.error("Erro ao validar credenciais para email: " + email + ": " + e.getMessage());
            throw new RuntimeException("Falha ao validar credenciais: " + e.getMessage());
        }
    }

    @Override
    public AuthMedico atualizarSenha(Integer id, String novaSenhaHash) throws EntidadeNaoLocalizadaException {
        try {
            AuthMedico authMedico = authMedicoRepository.buscarPorId(id);
            authMedico.setSenhaHash(novaSenhaHash);

            AuthMedico authAtualizado = authMedicoRepository.editar(authMedico);
            logger.info("Senha atualizada com sucesso. Auth ID: " + id);

            return authAtualizado;
        } catch (EntidadeNaoLocalizadaException e) {
            logger.error("Autenticação não encontrada para atualizar senha. ID: " + id);
            throw e;
        } catch (Exception e) {
            logger.error("Erro ao atualizar senha. ID: " + id + ": " + e.getMessage());
            throw new RuntimeException("Falha ao atualizar senha: " + e.getMessage());
        }
    }

    @Override
    public AuthMedico registrarLoginSucesso(Integer id) {
        try {
            AuthMedico authMedico = authMedicoRepository.buscarPorId(id);
            authMedico.resetarTentativasLogin();

            AuthMedico authAtualizado = authMedicoRepository.editar(authMedico);
            logger.info("Login sucesso registrado. Auth ID: " + id);

            return authAtualizado;
        } catch (Exception e) {
            logger.error("Erro ao registrar login sucesso. ID: " + id + ": " + e.getMessage());
            throw new RuntimeException("Falha ao registrar login sucesso: " + e.getMessage());
        }
    }

    @Override
    public AuthMedico registrarTentativaLoginFalha(Integer id) {
        try {
            AuthMedico authMedico = authMedicoRepository.buscarPorId(id);
            authMedico.incrementarTentativaLogin();

            AuthMedico authAtualizado = authMedicoRepository.editar(authMedico);
            logger.info("Tentativa de login falha registrada. Auth ID: " + id);

            return authAtualizado;
        } catch (Exception e) {
            logger.error("Erro ao registrar tentativa de login falha. ID: " + id + ": " + e.getMessage());
            throw new RuntimeException("Falha ao registrar tentativa de login falha: " + e.getMessage());
        }
    }

    @Override
    public AuthMedico bloquearConta(Integer id) throws EntidadeNaoLocalizadaException {
        try {
            AuthMedico authMedico = authMedicoRepository.buscarPorId(id);
            authMedico.bloquearConta();

            AuthMedico authAtualizado = authMedicoRepository.editar(authMedico);
            logger.info("Conta bloqueada. Auth ID: " + id);

            return authAtualizado;
        } catch (EntidadeNaoLocalizadaException e) {
            logger.error("Autenticação não encontrada para bloquear conta. ID: " + id);
            throw e;
        } catch (Exception e) {
            logger.error("Erro ao bloquear conta. ID: " + id + ": " + e.getMessage());
            throw new RuntimeException("Falha ao bloquear conta: " + e.getMessage());
        }
    }

    @Override
    public AuthMedico ativarConta(Integer id) throws EntidadeNaoLocalizadaException {
        try {
            AuthMedico authMedico = authMedicoRepository.buscarPorId(id);
            authMedico.ativarConta();

            AuthMedico authAtualizado = authMedicoRepository.editar(authMedico);
            logger.info("Conta ativada. Auth ID: " + id);

            return authAtualizado;
        } catch (EntidadeNaoLocalizadaException e) {
            logger.error("Autenticação não encontrada para ativar conta. ID: " + id);
            throw e;
        } catch (Exception e) {
            logger.error("Erro ao ativar conta. ID: " + id + ": " + e.getMessage());
            throw new RuntimeException("Falha ao ativar conta: " + e.getMessage());
        }
    }

    private boolean existeAuthComEmail(String email) {
        try {
            authMedicoRepository.buscarPorEmail(email);
            return true;
        } catch (EntidadeNaoLocalizadaException e) {
            return false;
        }
    }

    private boolean existeAuthComMedicoId(Integer medicoId) {
        try {
            authMedicoRepository.buscarPorMedicoId(medicoId);
            return true;
        } catch (EntidadeNaoLocalizadaException e) {
            return false;
        }
    }
}