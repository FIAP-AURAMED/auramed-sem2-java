package br.com.auramed.interfaces.dto.request;

public class PacienteCompletoRequestDTO {
    private PessoaRequestDTO pessoa;
    private PacienteRequestDTO paciente;
    private InfoTeleconsultaRequestDTO infoTeleconsulta;
    private PerfilCognitivoRequestDTO perfilCognitivo;

    // Construtores
    public PacienteCompletoRequestDTO() {}

    public PacienteCompletoRequestDTO(PessoaRequestDTO pessoa, PacienteRequestDTO paciente,
                                      InfoTeleconsultaRequestDTO infoTeleconsulta, PerfilCognitivoRequestDTO perfilCognitivo) {
        this.pessoa = pessoa;
        this.paciente = paciente;
        this.infoTeleconsulta = infoTeleconsulta;
        this.perfilCognitivo = perfilCognitivo;
    }

    // Getters e Setters
    public PessoaRequestDTO getPessoa() {
        return pessoa;
    }

    public void setPessoa(PessoaRequestDTO pessoa) {
        this.pessoa = pessoa;
    }

    public PacienteRequestDTO getPaciente() {
        return paciente;
    }

    public void setPaciente(PacienteRequestDTO paciente) {
        this.paciente = paciente;
    }

    public InfoTeleconsultaRequestDTO getInfoTeleconsulta() {
        return infoTeleconsulta;
    }

    public void setInfoTeleconsulta(InfoTeleconsultaRequestDTO infoTeleconsulta) {
        this.infoTeleconsulta = infoTeleconsulta;
    }

    public PerfilCognitivoRequestDTO getPerfilCognitivo() {
        return perfilCognitivo;
    }

    public void setPerfilCognitivo(PerfilCognitivoRequestDTO perfilCognitivo) {
        this.perfilCognitivo = perfilCognitivo;
    }
}