package br.com.auramed.infrastructure.api.rest;

import br.com.auramed.domain.exception.EntidadeNaoLocalizadaException;
import br.com.auramed.interfaces.controllers.PacienteController;
import br.com.auramed.interfaces.dto.request.PacienteRequestDTO;
import br.com.auramed.interfaces.dto.response.PacienteResponseDTO;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/pacientes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PacienteRestController {
    private final PacienteController pacienteController;

    @Inject
    public PacienteRestController(PacienteController pacienteController) {
        this.pacienteController = pacienteController;
    }

    @GET
    @Path("/{idPessoa}")
    public Response buscarPorId(@PathParam("idPessoa") Integer idPessoa) {
        try {
            PacienteResponseDTO paciente = pacienteController.getPacienteById(idPessoa);
            return Response.ok(paciente).build();
        } catch (EntidadeNaoLocalizadaException e){
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @GET
    public Response buscarTodosPacientes() {
        try {
            List<PacienteResponseDTO> pacientes = pacienteController.getAllPacientes();
            return Response.ok(pacientes).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/medico/{idMedico}")
    public Response buscarPacientesPorMedico(@PathParam("idMedico") Integer idMedico) {
        try {
            List<PacienteResponseDTO> pacientes = pacienteController.getPacientesPorMedico(idMedico);
            return Response.ok(pacientes).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @POST
    public Response criarPaciente(PacienteRequestDTO pacienteRequest) {
        try {
            PacienteResponseDTO paciente = pacienteController.criarPaciente(pacienteRequest);
            return Response.status(Response.Status.CREATED).entity(paciente).build();
        } catch (Exception e){
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("/{idPessoa}")
    public Response atualizarPaciente(@PathParam("idPessoa") Integer idPessoa, PacienteRequestDTO pacienteRequest) {
        try {
            PacienteResponseDTO paciente = pacienteController.editarPaciente(idPessoa, pacienteRequest);
            return Response.status(Response.Status.OK).entity(paciente).build();
        } catch (EntidadeNaoLocalizadaException e){
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/{idPessoa}")
    public Response deletarPaciente(@PathParam("idPessoa") Integer idPessoa) {
        try {
            pacienteController.deletePaciente(idPessoa);
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (EntidadeNaoLocalizadaException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @PATCH
    @Path("/{idPessoa}/ativar")
    public Response ativarPaciente(@PathParam("idPessoa") Integer idPessoa) {
        try {
            PacienteResponseDTO paciente = pacienteController.ativarPaciente(idPessoa);
            return Response.ok(paciente).build();
        } catch (EntidadeNaoLocalizadaException e){
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @PATCH
    @Path("/{idPessoa}/inativar")
    public Response inativarPaciente(@PathParam("idPessoa") Integer idPessoa) {
        try {
            PacienteResponseDTO paciente = pacienteController.inativarPaciente(idPessoa);
            return Response.ok(paciente).build();
        } catch (EntidadeNaoLocalizadaException e){
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
}