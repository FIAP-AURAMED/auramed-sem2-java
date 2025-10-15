package fiap.com.auramed.domain.model;

public class PerfilCognitivo {
    private int id;
    private boolean dificuldadeVisao;
    private boolean usaOculos;
    private boolean dificuldadeAudicao;
    private boolean usaAparelhoAuditivo;
    private boolean dificuldadeCognitiva;
    private Paciente paciente;

    public PerfilCognitivo(boolean dificuldadeVisao, boolean usaOculos, boolean dificuldadeAudicao, boolean usaAparelhoAuditivo, boolean dificuldadeCognitiva, Paciente paciente) {
        this.dificuldadeVisao = dificuldadeVisao;
        this.usaOculos = usaOculos;
        this.dificuldadeAudicao = dificuldadeAudicao;
        this.usaAparelhoAuditivo = usaAparelhoAuditivo;
        this.dificuldadeCognitiva = dificuldadeCognitiva;
        this.paciente = paciente;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isDificuldadeVisao() {
        return dificuldadeVisao;
    }

    public void setDificuldadeVisao(boolean dificuldadeVisao) {
        this.dificuldadeVisao = dificuldadeVisao;
    }

    public boolean isUsaOculos() {
        return usaOculos;
    }

    public void setUsaOculos(boolean usaOculos) {
        this.usaOculos = usaOculos;
    }

    public boolean isDificuldadeAudicao() {
        return dificuldadeAudicao;
    }

    public void setDificuldadeAudicao(boolean dificuldadeAudicao) {
        this.dificuldadeAudicao = dificuldadeAudicao;
    }

    public boolean isUsaAparelhoAuditivo() {
        return usaAparelhoAuditivo;
    }

    public void setUsaAparelhoAuditivo(boolean usaAparelhoAuditivo) {
        this.usaAparelhoAuditivo = usaAparelhoAuditivo;
    }

    public boolean isDificuldadeCognitiva() {
        return dificuldadeCognitiva;
    }

    public void setDificuldadeCognitiva(boolean dificuldadeCognitiva) {
        this.dificuldadeCognitiva = dificuldadeCognitiva;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }
}