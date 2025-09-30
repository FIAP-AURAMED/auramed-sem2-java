package br.com.fiap.auramed.infrastructure.main;

import br.com.fiap.auramed.application.repository.MedicoRepository;
import br.com.fiap.auramed.domain.model.entity.Medico;
import br.com.fiap.auramed.domain.model.vo.*;
import io.quarkus.runtime.QuarkusApplication;
import jakarta.inject.Inject;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class AuramedApplication implements QuarkusApplication {
    @Inject
    MedicoRepository medicoRepository;

    @Override
    public int run(String... args) throws Exception {
        System.out.println(">>> Aplicação Quarkus iniciada. Executando teste de inserção...");

        //Testar os métodos dentro do bloco abaixo, tire os comentários e teste um de cada vez!
        try {
            //testCreateMedico();
            //testBuscarTodos();
            //testBuscarPorCrm();
            //testDesativarMedico();
            //testCriarVariosMedicos();

        } catch (Exception e) {
            System.err.println(">>> Ocorreu um erro fatal durante a execução do teste:");
            e.printStackTrace();
        }

        io.quarkus.runtime.Quarkus.waitForExit();
        return 0;
    }

    private void testCreateMedico() {
        System.out.println("\n=== TESTE DE CRIAÇÃO DE MÉDICO ===");

        Medico medicoParaSalvar = getMedico();
        System.out.println(">>> Objeto Medico criado. Tentando salvar no banco de dados...");

        try {
            Medico medicoSalvo = this.medicoRepository.salvar(medicoParaSalvar);

            System.out.println("-------------------------------------");
            System.out.println(">>> Médico salvo com SUCESSO!");
            System.out.println(STR."ID gerado pelo banco de dados: \{medicoSalvo.getId()}");
            System.out.printf("Nome: %s%n", medicoSalvo.getNomeCompleto().valor());
            System.out.printf("CRM Formatado: %s%n", medicoSalvo.getCrm().getRegistroFormatado());
            System.out.println("-------------------------------------");

        } catch (Exception e) {
            System.err.println(">>> FALHA ao salvar o médico no banco de dados:");
            e.printStackTrace();
        }
    }

    private static Medico getMedico() {
        Endereco endereco = new Endereco("Avenida Lins de Vasconcelos", "1222", null, "Aclimação", "São Paulo", UF.SP, "01538001");
        RegistroProfissional crm = new RegistroProfissional("987654", UF.SP);

        return new Medico(
                "Dra Ana Claudia Montenegro",
                "ana.montenegro@clinicasaojoao.com",
                "33344455566",
                LocalDate.of(1978, 7, 12),
                Genero.FEMININO,
                "11982345678",
                new Endereco("Rua Dr. César", "1500", "Conjunto 45", "Santana", "São Paulo", UF.SP, "02035000"),
                "112233",
                UF.SP,
                "$2a$10$bcdefghijklmnopqrstuvwx"
        );
    }

    private void testBuscarTodos() {
        System.out.println("\n=== TESTE DE BUSCAR TODOS OS MÉDICOS ===");

        try {
            List<Medico> medicos = this.medicoRepository.buscarTodos();

            System.out.println(STR.">>> Médicos encontrados: \{medicos.size()}");

            if (medicos.isEmpty()) {
                System.out.println("Nenhum médico encontrado no banco de dados.");
            } else {
                for (int i = 0; i < medicos.size(); i++) {
                    Medico medico = medicos.get(i);
                    System.out.println(STR."--- Médico #\{i + 1} ---");
                    System.out.println(STR."ID: \{medico.getId()}");
                    System.out.println(STR."Nome: \{medico.getNomeCompleto().valor()}");
                    System.out.println(STR."Email: \{medico.getEmail().enderecoEmail()}");
                    System.out.println(STR."CRM: \{medico.getCrm().getRegistroFormatado()}");
                    System.out.println(STR."Ativo: \{medico.getEndereco() != null ? "Sim" : "Não"}");

                    if (medico.getEndereco() != null) {
                        Endereco endereco = medico.getEndereco();
                        System.out.println(STR."Endereço: \{endereco.logradouro()}, \{endereco.numero()} - \{endereco.bairro()}");
                    }
                    System.out.println();
                }
            }

        } catch (Exception e) {
            System.err.println(">>> FALHA ao buscar todos os médicos:");
            e.printStackTrace();
        }
    }

    private void testBuscarPorCrm() {
        System.out.println("\n=== TESTE DE BUSCAR MÉDICO POR CRM ===");

        String crmParaBuscar = "987654";

        try {
            Optional<Medico> medicoOptional = this.medicoRepository.buscarPorCrm(crmParaBuscar);

            if (medicoOptional.isPresent()) {
                Medico medico = medicoOptional.get();
                System.out.println(STR.">>> Médico encontrado com CRM: \{crmParaBuscar}");
                System.out.println("-------------------------------------");
                System.out.println(STR."ID: \{medico.getId()}");
                System.out.println(STR."Nome: \{medico.getNomeCompleto().valor()}");
                System.out.println(STR."Email: \{medico.getEmail().enderecoEmail()}");
                System.out.println(STR."CRM: \{medico.getCrm().getRegistroFormatado()}");
                System.out.println(STR."Data Nascimento: \{medico.getDataNascimento().data()}");
                System.out.println(STR."Gênero: \{medico.getGenero()}");
                System.out.println(STR."Telefone: \{medico.getTelefone().numero()}");

                if (medico.getEndereco() != null) {
                    Endereco endereco = medico.getEndereco();
                    System.out.println("Endereço Completo:");
                    System.out.println(STR."  \{endereco.logradouro()}, \{endereco.numero()}");
                    System.out.println(endereco.complemento());
                    System.out.println(STR."  \{endereco.bairro()} - \{endereco.cidade()}/\{endereco.uf()}");
                    System.out.println(STR."  CEP: \{endereco.cep()}");
                }
                System.out.println("-------------------------------------");
            } else {
                System.out.println(STR.">>> Nenhum médico encontrado com CRM: \{crmParaBuscar}");
            }

        } catch (Exception e) {
            System.err.println(STR.">>> FALHA ao buscar médico por CRM \{crmParaBuscar}:");
            e.printStackTrace();
        }
    }

    private void testDesativarMedico() {
        System.out.println("\n=== TESTE DE DESATIVAÇÃO DE MÉDICO ===");
        String crmParaDesativar = "987654/SP";

        try {
            Optional<Medico> medicoOptional = this.medicoRepository.buscarPorCrm(crmParaDesativar);

            if (medicoOptional.isPresent()) {
                Medico medico = medicoOptional.get();
                Long idMedico = medico.getId();

                System.out.println(STR.">>> Desativando médico ID: \{idMedico} - \{medico.getNomeCompleto().valor()}");

                this.medicoRepository.desativar(idMedico);

                System.out.println(">>> Médico desativado com sucesso!");

                List<Medico> medicosAtivos = this.medicoRepository.buscarTodos();
                boolean aindaAtivo = medicosAtivos.stream()
                        .anyMatch(m -> m.getId().equals(idMedico));

                if (!aindaAtivo) {
                    System.out.println(">>> Confirmação: Médico não aparece mais na lista de ativos.");
                } else {
                    System.out.println(">>> AVISO: Médico ainda aparece na lista de ativos.");
                }

            } else {
                System.out.println(STR.">>> Nenhum médico encontrado com CRM: \{crmParaDesativar} para desativar.");
            }

        } catch (Exception e) {
            System.err.println(">>> FALHA ao desativar médico:");
            e.printStackTrace();
        }
    }

    private void testCriarVariosMedicos() {
        System.out.println("\n=== TESTE DE CRIAÇÃO DE MÚLTIPLOS MÉDICOS ===");

        Medico[] medicosParaCriar = {
                new Medico(
                        "Maria Silva Costa",
                        "maria.silva@email.com",
                        "11122233344",
                        LocalDate.of(1975, 8, 15),
                        Genero.FEMININO,
                        "11988776655",
                        new Endereco("Rua das Flores", "300", "Sala 101", "Jardim Paulista", "São Paulo", UF.SP, "01415000"),
                        "123456",
                        UF.SP,
                        "$2a$10$hash1234567890123456789"
                ),
                new Medico(
                        "João Santos Oliveira",  // REMOVIDO "Dr."
                        "joao.santos@email.com",
                        "55566677788",
                        LocalDate.of(1985, 12, 5),
                        Genero.MASCULINO,
                        "11999554433",
                        new Endereco("Av. Paulista", "1000", "Conjunto 200", "Bela Vista", "São Paulo", UF.SP, "01310000"),
                        "654321",
                        UF.SP,
                        "$2a$10$hash9876543210987654321"
                )
        };

        for (Medico medico : medicosParaCriar) {
            try {
                Medico medicoSalvo = this.medicoRepository.salvar(medico);
                System.out.println(STR.">>> Médico criado: \{medicoSalvo.getNomeCompleto().valor()} - ID: \{medicoSalvo.getId()}");
            } catch (Exception e) {
                System.err.println(STR.">>> FALHA ao criar médico \{medico.getNomeCompleto().valor()}:");
                e.printStackTrace();
            }
        }
    }
}