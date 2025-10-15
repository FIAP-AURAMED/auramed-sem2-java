package fiap.com.auramed.domain.model;

import fiap.com.auramed.domain.exception.DomainValidationException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Paciente extends Pessoa {
    private String cartaoSus;
    private Medico medico;
    private InfoTeleconsulta infoTeleconsulta;
    private PerfilCognitivo perfilCognitivo;
    private List<Cuidador> cuidadores;

    public Paciente(
            String nome, String email, String telefone, String cpf, LocalDate dataNascimento, Genero genero,
            String cartaoSus, Medico medico
    ) {
        super(nome, email, telefone, cpf, dataNascimento, genero);
        this.setCartaoSus(cartaoSus);
        this.setMedico(medico);
        this.cuidadores = new ArrayList<>();
    }

    public void isCartaoSusValid() {
        if (cartaoSus == null || cartaoSus.trim().isEmpty()) {
            throw new DomainValidationException("Cartão do SUS não pode ser vazio ou nulo.");
        }

        String digitsOnly = cartaoSus.replaceAll("\\D", "");
        if (digitsOnly.length() != 15) {
            throw new DomainValidationException("Cartão do SUS deve conter 15 dígitos.");
        }
    }

    public void isMedicoValid() {
        if (medico == null) {
            throw new DomainValidationException("Paciente deve estar associado a um médico responsável.");
        }
    }

    public String getCartaoSus() {
        return cartaoSus;
    }

    public void setCartaoSus(String cartaoSus) {
        this.cartaoSus = cartaoSus;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    public InfoTeleconsulta getInfoTeleconsulta() {
        return infoTeleconsulta;
    }

    public void setInfoTeleconsulta(InfoTeleconsulta infoTeleconsulta) {
        this.infoTeleconsulta = infoTeleconsulta;
    }

    public PerfilCognitivo getPerfilCognitivo() {
        return perfilCognitivo;
    }

    public void setPerfilCognitivo(PerfilCognitivo perfilCognitivo) {
        this.perfilCognitivo = perfilCognitivo;
    }

    public List<Cuidador> getCuidadores() {
        return cuidadores;
    }

    public void setCuidadores(List<Cuidador> cuidadores) {
        this.cuidadores = cuidadores;
    }
}
