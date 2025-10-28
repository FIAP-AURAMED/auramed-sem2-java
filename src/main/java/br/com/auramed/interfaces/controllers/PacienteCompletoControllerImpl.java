package br.com.auramed.interfaces.controllers;

import br.com.auramed.domain.exception.EntidadeNaoLocalizadaException;
import br.com.auramed.domain.model.*;
import br.com.auramed.domain.service.*;
import br.com.auramed.interfaces.dto.request.PacienteCompletoRequestDTO;
import br.com.auramed.interfaces.dto.response.PacienteCompletoResponseDTO;
import br.com.auramed.interfaces.mappers.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class PacienteCompletoControllerImpl implements PacienteCompletoController {

    @Inject
    PessoaService pessoaService;

    @Inject
    PacienteService pacienteService;

    @Inject
    InfoTeleconsultaService infoTeleconsultaService;

    @Inject
    PerfilCognitivoService perfilCognitivoService;

    @Inject
    PessoaMapper pessoaMapper;

    @Inject
    PacienteMapper pacienteMapper;

    @Inject
    InfoTeleconsultaMapper infoTeleconsultaMapper;

    @Inject
    PerfilCognitivoMapper perfilCognitivoMapper;

    @Inject
    PacienteCompletoMapper pacienteCompletoMapper;

    @Override
    public PacienteCompletoResponseDTO criarPacienteCompleto(PacienteCompletoRequestDTO pacienteCompletoRequest) throws EntidadeNaoLocalizadaException {
        try {
            // 1. Criar Pessoa
            Pessoa pessoa = pessoaMapper.toDomain(pacienteCompletoRequest.getPessoa());
            Pessoa pessoaCriada = pessoaService.criar(pessoa);

            // 2. Criar Paciente
            Paciente paciente = pacienteMapper.toDomain(pacienteCompletoRequest.getPaciente());
            paciente.setIdPessoa(pessoaCriada.getId());
            Paciente pacienteCriado = pacienteService.criar(paciente);

            // 3. Criar InfoTeleconsulta
            InfoTeleconsulta infoTeleconsulta = null;
            if (pacienteCompletoRequest.getInfoTeleconsulta() != null) {
                infoTeleconsulta = infoTeleconsultaMapper.toDomain(pacienteCompletoRequest.getInfoTeleconsulta());
                infoTeleconsulta.setIdPaciente(pessoaCriada.getId());
                infoTeleconsulta = infoTeleconsultaService.criar(infoTeleconsulta);
            }

            // 4. Criar PerfilCognitivo
            PerfilCognitivo perfilCognitivo = null;
            if (pacienteCompletoRequest.getPerfilCognitivo() != null) {
                perfilCognitivo = perfilCognitivoMapper.toDomain(pacienteCompletoRequest.getPerfilCognitivo());
                perfilCognitivo.setIdPaciente(pessoaCriada.getId());
                perfilCognitivo = perfilCognitivoService.criar(perfilCognitivo);
            }

            // 5. Montar resposta
            PacienteCompletoResponseDTO response = new PacienteCompletoResponseDTO();
            response.setPessoa(pessoaMapper.toResponseDTO(pessoaCriada));
            response.setPaciente(pacienteMapper.toResponseDTO(pacienteCriado));

            if (infoTeleconsulta != null) {
                response.setInfoTeleconsulta(infoTeleconsultaMapper.toResponseDTO(infoTeleconsulta));
            }

            if (perfilCognitivo != null) {
                response.setPerfilCognitivo(perfilCognitivoMapper.toResponseDTO(perfilCognitivo));
            }

            return response;

        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public PacienteCompletoResponseDTO getPacienteCompleto(Integer idPaciente) throws EntidadeNaoLocalizadaException {
        try {
            // 1. Buscar Pessoa
            Pessoa pessoa = pessoaService.localizar(idPaciente);

            // 2. Buscar Paciente
            Paciente paciente = pacienteService.localizar(idPaciente);

            // 3. Buscar InfoTeleconsulta (pode não existir)
            InfoTeleconsulta infoTeleconsulta = null;
            try {
                infoTeleconsulta = infoTeleconsultaService.localizarPorPaciente(idPaciente);
            } catch (EntidadeNaoLocalizadaException e) {
                // InfoTeleconsulta não existe, é opcional
            }

            // 4. Buscar PerfilCognitivo (pode não existir)
            PerfilCognitivo perfilCognitivo = null;
            try {
                perfilCognitivo = perfilCognitivoService.localizarPorPaciente(idPaciente);
            } catch (EntidadeNaoLocalizadaException e) {
                // PerfilCognitivo não existe, é opcional
            }

            // 5. Montar resposta
            PacienteCompletoResponseDTO response = new PacienteCompletoResponseDTO();
            response.setPessoa(pessoaMapper.toResponseDTO(pessoa));
            response.setPaciente(pacienteMapper.toResponseDTO(paciente));

            if (infoTeleconsulta != null) {
                response.setInfoTeleconsulta(infoTeleconsultaMapper.toResponseDTO(infoTeleconsulta));
            }

            if (perfilCognitivo != null) {
                response.setPerfilCognitivo(perfilCognitivoMapper.toResponseDTO(perfilCognitivo));
            }

            return response;

        } catch (Exception e) {
            throw e;
        }
    }
}