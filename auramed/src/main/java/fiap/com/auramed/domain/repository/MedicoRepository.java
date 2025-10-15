package fiap.com.auramed.domain.repository;
import fiap.com.auramed.domain.model.Medico;
import java.util.Optional;

public interface MedicoRepository {
    Optional<Medico> findById(Integer id);
    Optional<Medico> findByEmail(String email);
}
