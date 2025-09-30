package br.com.fiap.auramed.infrastructure.main;

import br.com.fiap.auramed.application.service.MedicoService;
import br.com.fiap.auramed.application.repository.MedicoRepository;
import br.com.fiap.auramed.application.dto.MedicoCreateDTO;
import br.com.fiap.auramed.application.dto.MedicoResponseDTO;
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

    @Inject
    MedicoService medicoService;

    @Override
    public int run(String... args) throws Exception {
        System.out.println(">>> Aplicação Quarkus iniciada. Executando testes...");
        System.out.println(">>> INSTRUÇÕES: Descomente UM teste por vez no código para executar!");

        try {
            // =================================================
            // DESCOMENTE APENAS UM TESTE POR VEZ PARA EXECUTAR:
            // =================================================

            // TESTE 1: Criar um médico novo (Repository)
            //testCriarMedico();

            // TESTE 2: Buscar todos os médicos cadastrados
            //testBuscarTodosMedicos();

            // TESTE 3: Buscar médico específico por CRM
            //testBuscarMedicoPorCrm();

            // TESTE 4: Atualizar dados de um médico existente
            testAtualizarMedico();

            // TESTE 5: Desativar um médico (exclusão lógica)
            //testDesativarMedico();

            // TESTE 6: Criar vários médicos de uma vez
            //testCriarVariosMedicos();

            // TESTE 7: Cadastro com regras de negócio (Service)
            //testCadastroComRegrasNegocio();

            // TESTE 8: Testar regra de CRM duplicado
            //testCrmDuplicado();

            // TESTE 9: Cadastro usando DTO
            //testCadastroComDTO();

        } catch (Exception e) {
            System.err.println(">>> Ocorreu um erro fatal durante a execução do teste:");
            e.printStackTrace();
        }

        io.quarkus.runtime.Quarkus.waitForExit();
        return 0;
    }

    /**
     * TESTE 1: Cria um novo médico no banco de dados (DIRETO NO REPOSITORY)
     * Como usar: Descomente testCriarMedico() no método run()
     */
    private void testCriarMedico() {
        System.out.println("\n=== TESTE 1: CRIAÇÃO DE MÉDICO (REPOSITORY) ===");
        System.out.println(">>> Criando um novo médico diretamente no repository...");

        Medico medicoParaSalvar = criarMedicoExemplo();

        try {
            Medico medicoSalvo = this.medicoRepository.salvar(medicoParaSalvar);

            System.out.println("✅ MÉDICO CRIADO COM SUCESSO!");
            System.out.println("-------------------------------------");
            System.out.println("ID: " + medicoSalvo.getId());
            System.out.println("Nome: " + medicoSalvo.getNomeCompleto().valor());
            System.out.println("CRM: " + medicoSalvo.getCrm().getRegistroFormatado());
            System.out.println("E-mail: " + medicoSalvo.getEmail().enderecoEmail());
            System.out.println("-------------------------------------");

            // Dica: Anote o ID e CRM gerados para usar nos próximos testes!
            System.out.println("💡 ANOTE ESTES DADOS PARA PRÓXIMOS TESTES:");
            System.out.println("CRM: " + medicoSalvo.getCrm().getRegistroFormatado());
            System.out.println("ID: " + medicoSalvo.getId());

        } catch (Exception e) {
            System.err.println("❌ FALHA ao criar médico:");
            e.printStackTrace();
        }
    }

    /**
     * TESTE 2: Lista todos os médicos cadastrados e ativos
     * Como usar: Descomente testBuscarTodosMedicos() no método run()
     */
    private void testBuscarTodosMedicos() {
        System.out.println("\n=== TESTE 2: LISTAR TODOS OS MÉDICOS ===");
        System.out.println(">>> Buscando todos os médicos cadastrados...");

        try {
            List<Medico> medicos = this.medicoRepository.buscarTodos();

            System.out.println("📋 TOTAL DE MÉDICOS ENCONTRADOS: " + medicos.size());

            if (medicos.isEmpty()) {
                System.out.println("Nenhum médico encontrado no banco de dados.");
            } else {
                for (int i = 0; i < medicos.size(); i++) {
                    Medico medico = medicos.get(i);
                    System.out.println("\n--- Médico #" + (i + 1) + " ---");
                    System.out.println("ID: " + medico.getId());
                    System.out.println("Nome: " + medico.getNomeCompleto().valor());
                    System.out.println("CRM: " + medico.getCrm().getRegistroFormatado());
                    System.out.println("E-mail: " + medico.getEmail().enderecoEmail());
                    System.out.println("Telefone: " + medico.getTelefone().numero());
                }
            }

        } catch (Exception e) {
            System.err.println("❌ FALHA ao buscar médicos:");
            e.printStackTrace();
        }
    }

    /**
     * TESTE 3: Busca um médico específico pelo número do CRM
     * Como usar:
     * 1. Primeiro execute o Teste 1 para criar um médico
     * 2. Descomente testBuscarMedicoPorCrm() no método run()
     * 3. Altere o CRM abaixo para o valor real do médico criado
     */
    private void testBuscarMedicoPorCrm() {
        System.out.println("\n=== TESTE 3: BUSCAR MÉDICO POR CRM ===");

        // ⚠️ ALTERE ESTE CRM para o valor real do médico que você criou!
        String crmParaBuscar = "112233/SP";
        System.out.println(">>> Buscando médico com CRM: " + crmParaBuscar);

        try {
            Optional<Medico> medicoOptional = this.medicoRepository.buscarPorCrm(crmParaBuscar);

            if (medicoOptional.isPresent()) {
                Medico medico = medicoOptional.get();
                System.out.println("✅ MÉDICO ENCONTRADO!");
                System.out.println("-------------------------------------");
                System.out.println("ID: " + medico.getId());
                System.out.println("Nome: " + medico.getNomeCompleto().valor());
                System.out.println("CRM: " + medico.getCrm().getRegistroFormatado());
                System.out.println("E-mail: " + medico.getEmail().enderecoEmail());
                System.out.println("Telefone: " + medico.getTelefone().numero());
                System.out.println("Data Nasc.: " + medico.getDataNascimento().data());

                if (medico.getEndereco() != null) {
                    Endereco endereco = medico.getEndereco();
                    System.out.println("Endereço: " + endereco.logradouro() + ", " + endereco.numero());
                    System.out.println("Bairro: " + endereco.bairro() + " - " + endereco.cidade() + "/" + endereco.uf());
                }
                System.out.println("-------------------------------------");
            } else {
                System.out.println("❌ Nenhum médico encontrado com CRM: " + crmParaBuscar);
                System.out.println("💡 Dica: Execute primeiro o Teste 1 para criar um médico");
            }

        } catch (Exception e) {
            System.err.println("❌ FALHA na busca:");
            e.printStackTrace();
        }
    }

    /**
     * TESTE 4: Atualiza os dados de um médico existente
     * Como usar:
     * 1. Primeiro execute o Teste 1 para criar um médico
     * 2. Descomente testAtualizarMedico() no método run()
     * 3. Altere o CRM abaixo para o valor real do médico criado
     */
    private void testAtualizarMedico() {
        System.out.println("\n=== TESTE 4: ATUALIZAR DADOS DO MÉDICO ===");

        // ⚠️ ALTERE ESTE CRM para o valor real do médico que você criou!
        String crmParaBuscar = "112233/SP";
        System.out.println(">>> Buscando médico para atualizar...");

        try {
            Optional<Medico> medicoOptional = this.medicoRepository.buscarPorCrm(crmParaBuscar);

            if (medicoOptional.isPresent()) {
                Medico medico = medicoOptional.get();
                System.out.println("✅ Médico encontrado: " + medico.getNomeCompleto().valor());

                // Criando dados atualizados
                Medico medicoAtualizado = criarMedicoAtualizado(medico.getId());

                System.out.println(">>> Atualizando dados do médico...");
                Medico medicoAtualizadoSalvo = this.medicoRepository.atualizar(medicoAtualizado);

                System.out.println("✅ DADOS ATUALIZADOS COM SUCESSO!");
                System.out.println("-------------------------------------");
                System.out.println("Nome antigo: " + medico.getNomeCompleto().valor());
                System.out.println("Nome novo: " + medicoAtualizadoSalvo.getNomeCompleto().valor());
                System.out.println("Telefone antigo: " + medico.getTelefone().numero());
                System.out.println("Telefone novo: " + medicoAtualizadoSalvo.getTelefone().numero());
                System.out.println("-------------------------------------");

            } else {
                System.out.println("❌ Nenhum médico encontrado com CRM: " + crmParaBuscar);
                System.out.println("💡 Dica: Execute primeiro o Teste 1 para criar um médico");
            }

        } catch (Exception e) {
            System.err.println("❌ FALHA na atualização:");
            e.printStackTrace();
        }
    }

    /**
     * TESTE 5: Desativa um médico (exclusão lógica)
     * Como usar:
     * 1. Primeiro execute o Teste 1 para criar um médico
     * 2. Descomente testDesativarMedico() no método run()
     * 3. Altere o CRM abaixo para o valor real do médico criado
     */
    private void testDesativarMedico() {
        System.out.println("\n=== TESTE 5: DESATIVAR MÉDICO ===");

        // ⚠️ ALTERE ESTE CRM para o valor real do médico que você criou!
        String crmParaDesativar = "112233/SP";
        System.out.println(">>> Buscando médico para desativar...");

        try {
            Optional<Medico> medicoOptional = this.medicoRepository.buscarPorCrm(crmParaDesativar);

            if (medicoOptional.isPresent()) {
                Medico medico = medicoOptional.get();
                Long idMedico = medico.getId();

                System.out.println(">>> Desativando médico: " + medico.getNomeCompleto().valor());
                this.medicoRepository.desativar(idMedico);
                System.out.println("✅ MÉDICO DESATIVADO COM SUCESSO!");

                // Verifica se realmente foi desativado
                List<Medico> medicosAtivos = this.medicoRepository.buscarTodos();
                boolean aindaAtivo = medicosAtivos.stream()
                        .anyMatch(m -> m.getId().equals(idMedico));

                if (!aindaAtivo) {
                    System.out.println("✅ Confirmação: Médico não aparece mais na lista de ativos");
                } else {
                    System.out.println("⚠️  AVISO: Médico ainda aparece na lista de ativos");
                }

            } else {
                System.out.println("❌ Nenhum médico encontrado com CRM: " + crmParaDesativar);
            }

        } catch (Exception e) {
            System.err.println("❌ FALHA ao desativar médico:");
            e.printStackTrace();
        }
    }

    /**
     * TESTE 6: Cria vários médicos de uma vez
     * Como usar: Descomente testCriarVariosMedicos() no método run()
     */
    private void testCriarVariosMedicos() {
        System.out.println("\n=== TESTE 6: CRIAR VÁRIOS MÉDICOS ===");
        System.out.println(">>> Criando 2 médicos...");

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
                        "João Santos Oliveira",
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
                System.out.println("✅ Criado: " + medicoSalvo.getNomeCompleto().valor() +
                        " - CRM: " + medicoSalvo.getCrm().getRegistroFormatado());
            } catch (Exception e) {
                System.err.println("❌ Falha ao criar " + medico.getNomeCompleto().valor() + ":");
                e.printStackTrace();
            }
        }
        System.out.println(">>> Todos os médicos foram processados!");
    }

    /**
     * TESTE 7: Cadastro com regras de negócio (USANDO SERVICE)
     * Como usar: Descomente testCadastroComRegrasNegocio() no método run()
     */
    private void testCadastroComRegrasNegocio() {
        System.out.println("\n=== TESTE 7: CADASTRO COM REGRAS DE NEGÓCIO (SERVICE) ===");

        // Teste 1: Menor de idade - deve falhar
        System.out.println(">>> Testando regra: Médico deve ser maior de 18 anos...");

        try {
            Medico medicoMenor = medicoService.cadastrarMedicoSimples(
                    new Medico(
                            "Dr. Carlos Silva Menor",
                            "carlos.menor@hospital.com",
                            "12345678900",
                            LocalDate.of(2010, 5, 15), // ❌ Vai falhar - menor de 18 anos
                            Genero.MASCULINO,
                            "11999999999",
                            new Endereco("Rua Teste", "123", null, "Centro", "São Paulo", UF.SP, "01001000"),
                            "665544",
                            UF.SP,
                            "$2a$10$hash"
                    )
            );
            System.out.println("❌ ERRO: Médico menor de idade foi cadastrado!");
        } catch (Exception e) {
            System.out.println("✅ Regra de negócio aplicada: " + e.getMessage());
        }

        // Teste 2: Maior de idade - deve funcionar
        try {
            System.out.println("\n>>> Tentando cadastrar médico maior de 18 anos...");
            Medico medicoMaior = medicoService.cadastrarMedicoSimples(
                    new Medico(
                            "Dra. Maria Santos",
                            "maria.santos@hospital.com",
                            "98765432100",
                            LocalDate.of(1985, 5, 15), // ✅ Maior de 18 anos
                            Genero.FEMININO,
                            "11988887777",
                            new Endereco("Av. Paulista", "1000", "Sala 10", "Bela Vista", "São Paulo", UF.SP, "01310000"),
                            "998877",
                            UF.SP,
                            "$2a$10$hashvalido"
                    )
            );
            System.out.println("✅ Médico maior de idade cadastrado: " + medicoMaior.getNomeCompleto().valor());
        } catch (Exception e) {
            System.out.println("❌ Erro inesperado: " + e.getMessage());
        }
    }

    /**
     * TESTE 8: Testar regra de CRM duplicado
     * Como usar:
     * 1. Primeiro execute o Teste 1 para criar um médico com CRM 112233/SP
     * 2. Descomente testCrmDuplicado() no método run()
     */
    private void testCrmDuplicado() {
        System.out.println("\n=== TESTE 8: VALIDAÇÃO DE CRM DUPLICADO ===");
        System.out.println(">>> Tentando cadastrar médico com CRM existente...");

        try {
            // Tenta cadastrar médico com CRM que já existe
            Medico medicoDuplicado = medicoService.cadastrarMedicoSimples(
                    new Medico(
                            "Dr. João Duplicado",
                            "joao.duplicado@hospital.com",
                            "99988877766",
                            LocalDate.of(1980, 1, 1),
                            Genero.MASCULINO,
                            "11988887777",
                            new Endereco("Av. Duplicada", "999", null, "Centro", "São Paulo", UF.SP, "01001000"),
                            "112233", // Mesmo CRM do médico criado no Teste 1
                            UF.SP,
                            "$2a$10$hashduplicado"
                    )
            );
            System.out.println("✅ Médico cadastrado: " + medicoDuplicado.getNomeCompleto().valor());
        } catch (Exception e) {
            System.out.println("❌ Regra de negócio aplicada: " + e.getMessage());
            System.out.println("💡 CRM duplicado foi bloqueado corretamente!");
        }
    }

    /**
     * TESTE 9: Cadastro usando DTO
     * Como usar: Descomente testCadastroComDTO() no método run()
     */
    private void testCadastroComDTO() {
        System.out.println("\n=== TESTE 9: CADASTRO USANDO DTO ===");

        MedicoCreateDTO dto = new MedicoCreateDTO(
                "Dr Pedro Costa",
                "pedro.costa@hospital.com",
                "11122233345",
                LocalDate.of(1980, 5, 20),
                "MASCULINO",
                "11977776666",
                "Rua das Palmeiras",
                "500",
                "Sala 301",
                "Moema",
                "São Paulo",
                "SP",
                "04010000",
                "445566",
                "SP",
                "$2a$10$hashseguro"
        );

        try {
            MedicoResponseDTO response = medicoService.cadastrarNovoMedico(dto);
            System.out.println("✅ Médico cadastrado via DTO: " + response.nomeCompleto());
            System.out.println("CRM: " + response.crmFormatado());
            System.out.println("E-mail: " + response.email());
        } catch (Exception e) {
            System.err.println("❌ Erro: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // =================================================
    // MÉTODOS AUXILIARES
    // =================================================

    /**
     * Cria um médico de exemplo para testes
     */
    private static Medico criarMedicoExemplo() {
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

    /**
     * Cria uma versão atualizada do médico (para teste de UPDATE)
     */
    private static Medico criarMedicoAtualizado(Long id) {
        Medico medicoAtualizado = new Medico(
                "Dra Ana Claudia Montenegro Santos",
                "ana.montenegro.atualizado@clinica.com",
                "33344455566",
                LocalDate.of(1978, 7, 12),
                Genero.FEMININO,
                "11999999999",
                new Endereco("Av. Nova Paulista", "2000", "Sala 501", "Centro", "São Paulo", UF.SP, "01001000"),
                "112233",
                UF.SP,
                "$2a$10$novohashatualizado123456"
        );
        medicoAtualizado.setId(id);
        return medicoAtualizado;
    }
}