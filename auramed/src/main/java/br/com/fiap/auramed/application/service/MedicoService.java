package br.com.fiap.auramed.application.service;

import br.com.fiap.auramed.application.dto.MedicoCreateDTO;
import br.com.fiap.auramed.application.dto.MedicoResponseDTO;
import br.com.fiap.auramed.application.repository.MedicoRepository;
import br.com.fiap.auramed.domain.model.entity.Medico;
import br.com.fiap.auramed.domain.model.vo.*;
import br.com.fiap.auramed.infrastructure.exceptions.InfraestruturaException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class MedicoService {

    @Inject
    MedicoRepository medicoRepository;

    // Use Case: Cadastrar novo médico com regras de negócio (USANDO DTO)
    public MedicoResponseDTO cadastrarNovoMedico(MedicoCreateDTO dto) {
        try {
            // REGRA DE NEGÓCIO: CRM único
            String crmFormatado = dto.crmNumero() + "/" + dto.crmUf();
            if (medicoRepository.buscarPorCrm(crmFormatado).isPresent()) {
                throw new IllegalStateException("CRM já cadastrado: " + crmFormatado);
            }

            // REGRA DE NEGÓCIO: Maior de 18 anos
            if (dto.dataNascimento().isAfter(LocalDate.now().minusYears(18))) {
                throw new IllegalStateException("Médico deve ser maior de 18 anos");
            }

            // Converter DTO para Entidade de Domínio
            Endereco endereco = new Endereco(
                    dto.logradouro(), dto.numero(), dto.complemento(),
                    dto.bairro(), dto.cidade(), UF.valueOf(dto.uf()), dto.cep()
            );

            Genero genero = Genero.valueOf(dto.genero());
            UF crmUf = UF.valueOf(dto.crmUf());

            Medico medico = new Medico(
                    dto.nomeCompleto(), dto.email(), dto.cpf(),
                    dto.dataNascimento(), genero, dto.telefone(),
                    endereco, dto.crmNumero(), crmUf, dto.senha()
            );

            Medico medicoSalvo = medicoRepository.salvar(medico);

            // Converter Entidade de Domínio para DTO de Resposta
            return toResponseDTO(medicoSalvo);

        } catch (IllegalStateException e) {
            throw e; // Propaga exceções de negócio
        } catch (Exception e) {
            throw new InfraestruturaException("Erro ao cadastrar médico", e);
        }
    }

    // MÉTODO ADICIONADO: Cadastro simples sem validações complexas (para testes)
    public Medico cadastrarMedicoSimples(Medico medico) {
        try {
            // Aplica apenas a regra de idade (mais crítica)
            if (medico.getDataNascimento().data().isAfter(LocalDate.now().minusYears(18))) {
                throw new IllegalStateException("Médico deve ser maior de 18 anos");
            }

            return medicoRepository.salvar(medico);
        } catch (IllegalStateException e) {
            throw e; // Propaga exceções de negócio
        } catch (Exception e) {
            throw new InfraestruturaException("Erro ao cadastrar médico", e);
        }
    }

    // Use Case: Listar médicos ativos
    public List<MedicoResponseDTO> listarMedicosAtivos() {
        try {
            return medicoRepository.buscarTodos().stream()
                    .map(this::toResponseDTO)
                    .toList();
        } catch (Exception e) {
            throw new InfraestruturaException("Erro ao listar médicos", e);
        }
    }

    // Use Case: Buscar médico por CRM
    public MedicoResponseDTO buscarPorCrm(String crm) {
        try {
            return medicoRepository.buscarPorCrm(crm)
                    .map(this::toResponseDTO)
                    .orElseThrow(() -> new IllegalStateException("Médico não encontrado"));
        } catch (IllegalStateException e) {
            throw e;
        } catch (Exception e) {
            throw new InfraestruturaException("Erro ao buscar médico", e);
        }
    }

    // Use Case: Atualizar médico
    public MedicoResponseDTO atualizarMedico(Long id, MedicoCreateDTO dto) {
        try {
            Optional<Medico> medicoExistente = medicoRepository.buscarPorId(id);
            if (medicoExistente.isEmpty()) {
                throw new IllegalStateException("Médico não encontrado com ID: " + id);
            }

            // Converter DTO para Entidade de Domínio
            Endereco endereco = new Endereco(
                    dto.logradouro(), dto.numero(), dto.complemento(),
                    dto.bairro(), dto.cidade(), UF.valueOf(dto.uf()), dto.cep()
            );

            Genero genero = Genero.valueOf(dto.genero());
            UF crmUf = UF.valueOf(dto.crmUf());

            Medico medicoAtualizado = new Medico(
                    dto.nomeCompleto(), dto.email(), dto.cpf(),
                    dto.dataNascimento(), genero, dto.telefone(),
                    endereco, dto.crmNumero(), crmUf, dto.senha()
            );
            medicoAtualizado.setId(id);

            Medico medicoSalvo = medicoRepository.atualizar(medicoAtualizado);
            return toResponseDTO(medicoSalvo);

        } catch (IllegalStateException e) {
            throw e;
        } catch (Exception e) {
            throw new InfraestruturaException("Erro ao atualizar médico", e);
        }
    }

    // Use Case: Desativar médico
    public void desativarMedico(String crm) {
        try {
            Optional<Medico> medicoOptional = medicoRepository.buscarPorCrm(crm);
            if (medicoOptional.isEmpty()) {
                throw new IllegalStateException("Médico não encontrado com CRM: " + crm);
            }

            Medico medico = medicoOptional.get();
            medicoRepository.desativar(medico.getId());

        } catch (IllegalStateException e) {
            throw e;
        } catch (Exception e) {
            throw new InfraestruturaException("Erro ao desativar médico", e);
        }
    }

    // Método auxiliar para converter Entity para DTO
    private MedicoResponseDTO toResponseDTO(Medico medico) {
        MedicoResponseDTO.EnderecoDTO enderecoDTO = null;
        if (medico.getEndereco() != null) {
            Endereco endereco = medico.getEndereco();
            enderecoDTO = new MedicoResponseDTO.EnderecoDTO(
                    endereco.logradouro(), endereco.numero(), endereco.complemento(),
                    endereco.bairro(), endereco.cidade(), endereco.uf().name(), endereco.cep()
            );
        }

        return new MedicoResponseDTO(
                medico.getId(),
                medico.getNomeCompleto().valor(),
                medico.getEmail().enderecoEmail(),
                medico.getCrm().getRegistroFormatado(),
                medico.getTelefone().numero(),
                medico.getDataNascimento().data(),
                medico.getGenero().name(),
                enderecoDTO
        );
    }
}