package fiap.com.auramed.domain.repository;

import fiap.com.auramed.domain.exception.EntityNotFoundException;
import fiap.com.auramed.domain.model.Paciente;

public interface PacienteRepository {
    Paciente save(Paciente paciente);
    Paciente findById(Integer id) throws EntityNotFoundException;
    Paciente update(Paciente paciente) throws EntityNotFoundException;
    Paciente deactivate(Integer id) throws EntityNotFoundException;
    Paciente activate(Integer id) throws EntityNotFoundException;
}
