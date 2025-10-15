package fiap.com.auramed.domain.model;

public class InfoTeleconsulta {
    private int id;
    private String habilidadeDigital;
    private String canalLembrete;
    private boolean precisaCuidador;
    private boolean jaFezTele;
    private Paciente paciente;

    public InfoTeleconsulta(String habilidadeDigital, String canalLembrete, boolean precisaCuidador, boolean jaFezTele, Paciente paciente) {
        this.habilidadeDigital = habilidadeDigital;
        this.canalLembrete = canalLembrete;
        this.precisaCuidador = precisaCuidador;
        this.jaFezTele = jaFezTele;
        this.paciente = paciente;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHabilidadeDigital() {
        return habilidadeDigital;
    }

    public void setHabilidadeDigital(String habilidadeDigital) {
        this.habilidadeDigital = habilidadeDigital;
    }

    public String getCanalLembrete() {
        return canalLembrete;
    }

    public void setCanalLembrete(String canalLembrete) {
        this.canalLembrete = canalLembrete;
    }

    public boolean isPrecisaCuidador() {
        return precisaCuidador;
    }

    public void setPrecisaCuidador(boolean precisaCuidador) {
        this.precisaCuidador = precisaCuidador;
    }

    public boolean isJaFezTele() {
        return jaFezTele;
    }

    public void setJaFezTele(boolean jaFezTele) {
        this.jaFezTele = jaFezTele;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }
}