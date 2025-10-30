package br.com.auramed.application.service;

import br.com.auramed.domain.model.Paciente;
import br.com.auramed.domain.repository.PacienteRepository;
import br.com.auramed.domain.repository.PessoaRepository;
import br.com.auramed.domain.service.PacienteService;
import br.com.auramed.domain.exception.EntidadeNaoLocalizadaException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

import java.util.List;

@ApplicationScoped
public class PacienteServiceImpl implements PacienteService {

    @Inject
    PacienteRepository pacienteRepository;

    @Inject
    PessoaRepository pessoaRepository;

    @Inject
    Logger logger;

    @Override
    public Paciente criar(Paciente paciente) {
        try {
            // Validações do paciente
            paciente.validarCartaoSUS();

            // Verificar se a pessoa existe e é do tipo PACIENTE
            var pessoa = pessoaRepository.buscarPorId(paciente.getIdPessoa());
            if (!"PACIENTE".equals(pessoa.getTipoPessoa())) {
                throw new RuntimeException("A pessoa deve ser do tipo PACIENTE");
            }

            // Verificar se o médico responsável existe
            pessoaRepository.buscarPorId(paciente.getIdMedicoResponsavel());

            // Verificar unicidade do cartão SUS
            if (pacienteRepository.existeCartaoSUS(paciente.getNrCartaoSUS())) {
                throw new RuntimeException("Já existe paciente cadastrado com este Cartão SUS: " + paciente.getNrCartaoSUS());
            }

            Paciente pacienteSalvo = pacienteRepository.salvar(paciente);
            logger.info("Paciente criado com sucesso. ID Pessoa: " + pacienteSalvo.getIdPessoa() + " - Médico: " + pacienteSalvo.getIdMedicoResponsavel());

            return pacienteSalvo;

        } catch (EntidadeNaoLocalizadaException e) {
            logger.error("Pessoa ou médico não encontrado: " + e.getMessage());
            throw new RuntimeException("Pessoa ou médico responsável não encontrado: " + e.getMessage());
        } catch (Exception e) {
            logger.error("Erro ao criar paciente: " + e.getMessage());
            throw new RuntimeException("Falha ao criar paciente: " + e.getMessage());
        }
    }

    @Override
    public Paciente editar(Integer idPessoa, Paciente paciente) throws EntidadeNaoLocalizadaException {
        try {
            Paciente pacienteExistente = pacienteRepository.buscarPorId(idPessoa);

            // Validações do paciente
            paciente.validarCartaoSUS();

            // Verificar se o médico responsável existe
            pessoaRepository.buscarPorId(paciente.getIdMedicoResponsavel());

            // Verificar unicidade do cartão SUS (se alterado)
            if (!pacienteExistente.getNrCartaoSUS().equals(paciente.getNrCartaoSUS()) &&
                    pacienteRepository.existeCartaoSUS(paciente.getNrCartaoSUS())) {
                throw new RuntimeException("Já existe paciente cadastrado com este Cartão SUS: " + paciente.getNrCartaoSUS());
            }

            paciente.setIdPessoa(idPessoa);
            Paciente pacienteAtualizado = pacienteRepository.editar(paciente);
            logger.info("Paciente atualizado com sucesso. ID Pessoa: " + idPessoa);

            return pacienteAtualizado;
        } catch (EntidadeNaoLocalizadaException e) {
            logger.error("Paciente não encontrado para edição. ID Pessoa: " + idPessoa);
            throw e;
        } catch (Exception e) {
            logger.error("Erro ao editar paciente. ID Pessoa: " + idPessoa + ": " + e.getMessage());
            throw new RuntimeException("Falha ao editar paciente: " + e.getMessage());
        }
    }

    @Override
    public Paciente remover(Integer idPessoa) throws EntidadeNaoLocalizadaException {
        try {
            Paciente paciente = pacienteRepository.buscarPorId(idPessoa);

            pacienteRepository.remover(idPessoa);
            logger.info("Paciente removido com sucesso. ID Pessoa: " + idPessoa);

            return paciente;
        } catch (EntidadeNaoLocalizadaException e) {
            logger.error("Paciente não encontrado para remoção. ID Pessoa: " + idPessoa);
            throw e;
        } catch (Exception e) {
            logger.error("Erro ao remover paciente. ID Pessoa: " + idPessoa + ": " + e.getMessage());
            throw new RuntimeException("Falha ao remover paciente: " + e.getMessage());
        }
    }

    @Override
    public Paciente localizar(Integer idPessoa) throws EntidadeNaoLocalizadaException {
        try {
            Paciente paciente = pacienteRepository.buscarPorId(idPessoa);
            logger.info("Paciente localizado. ID Pessoa: " + idPessoa);
            return paciente;

        } catch (EntidadeNaoLocalizadaException e) {
            logger.error("Paciente não localizado. ID Pessoa: " + idPessoa);
            throw e;
        } catch (Exception e) {
            logger.error("Erro ao localizar paciente. ID Pessoa: " + idPessoa + ": " + e.getMessage());
            throw new RuntimeException("Falha ao localizar paciente: " + e.getMessage());
        }
    }

    @Override
    public List<Paciente> listarTodos() {
        try {
            List<Paciente> pacientes = pacienteRepository.buscarTodos();
            logger.info("Listados " + pacientes.size() + " pacientes");
            return pacientes;

        } catch (Exception e) {
            logger.error("Erro ao listar pacientes: " + e.getMessage());
            throw new RuntimeException("Falha ao listar pacientes: " + e.getMessage());
        }
    }

    @Override
    public List<Paciente> listarPorMedico(Integer idMedicoResponsavel) {
        try {
            // Verificar se o médico existe
            pessoaRepository.buscarPorId(idMedicoResponsavel);

            List<Paciente> pacientes = pacienteRepository.buscarPorMedicoResponsavel(idMedicoResponsavel);
            logger.info("Listados " + pacientes.size() + " pacientes do médico: " + idMedicoResponsavel);
            return pacientes;

        } catch (EntidadeNaoLocalizadaException e) {
            logger.error("Médico não encontrado. ID: " + idMedicoResponsavel);
            throw new RuntimeException("Médico não encontrado: " + e.getMessage());
        } catch (Exception e) {
            logger.error("Erro ao listar pacientes por médico " + idMedicoResponsavel + ": " + e.getMessage());
            throw new RuntimeException("Falha ao listar pacientes por médico: " + e.getMessage());
        }
    }

    @Override
    public Paciente ativar(Integer idPessoa) throws EntidadeNaoLocalizadaException {
        try {
            Paciente paciente = pacienteRepository.buscarPorId(idPessoa);
            paciente.setAtivo("S");

            Paciente pacienteAtualizado = pacienteRepository.editar(paciente);
            logger.info("Paciente ativado com sucesso. ID Pessoa: " + idPessoa);

            return pacienteAtualizado;
        } catch (EntidadeNaoLocalizadaException e) {
            logger.error("Paciente não encontrado para ativação. ID Pessoa: " + idPessoa);
            throw e;
        } catch (Exception e) {
            logger.error("Erro ao ativar paciente. ID Pessoa: " + idPessoa + ": " + e.getMessage());
            throw new RuntimeException("Falha ao ativar paciente: " + e.getMessage());
        }
    }

    @Override
    public Paciente inativar(Integer idPessoa) throws EntidadeNaoLocalizadaException {
        try {
            Paciente paciente = pacienteRepository.buscarPorId(idPessoa);
            paciente.setAtivo("N");

            Paciente pacienteAtualizado = pacienteRepository.editar(paciente);
            logger.info("Paciente inativado com sucesso. ID Pessoa: " + idPessoa);

            return pacienteAtualizado;
        } catch (EntidadeNaoLocalizadaException e) {
            logger.error("Paciente não encontrado para inativação. ID Pessoa: " + idPessoa);
            throw e;
        } catch (Exception e) {
            logger.error("Erro ao inativar paciente. ID Pessoa: " + idPessoa + ": " + e.getMessage());
            throw new RuntimeException("Falha ao inativar paciente: " + e.getMessage());
        }
    }
}