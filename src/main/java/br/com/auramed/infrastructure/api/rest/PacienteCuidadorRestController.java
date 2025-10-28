package br.com.auramed.infrastructure.api.rest;

import br.com.auramed.domain.exception.EntidadeNaoLocalizadaException;
import br.com.auramed.interfaces.controllers.PacienteCuidadorController;
import br.com.auramed.interfaces.dto.request.PacienteCuidadorRequestDTO;
import br.com.auramed.interfaces.dto.response.PacienteCuidadorResponseDTO;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/pacientes-cuidadores")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PacienteCuidadorRestController {
    private final PacienteCuidadorController pacienteCuidadorController;

    @Inject
    public PacienteCuidadorRestController(PacienteCuidadorController pacienteCuidadorController) {
        this.pacienteCuidadorController = pacienteCuidadorController;
    }

    @GET
    @Path("/paciente/{idPaciente}/cuidador/{idCuidador}")
    public Response buscarAssociacaoPorIds(
            @PathParam("idPaciente") Integer idPaciente,
            @PathParam("idCuidador") Integer idCuidador) {
        try {
            PacienteCuidadorResponseDTO associacao = pacienteCuidadorController.getAssociacaoByIds(idPaciente, idCuidador);
            return Response.ok(associacao).build();
        } catch (EntidadeNaoLocalizadaException e){
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/paciente/{idPaciente}")
    public Response buscarCuidadoresDoPaciente(@PathParam("idPaciente") Integer idPaciente) {
        try {
            List<PacienteCuidadorResponseDTO> associacoes = pacienteCuidadorController.getCuidadoresDoPaciente(idPaciente);
            return Response.ok(associacoes).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/cuidador/{idCuidador}")
    public Response buscarPacientesDoCuidador(@PathParam("idCuidador") Integer idCuidador) {
        try {
            List<PacienteCuidadorResponseDTO> associacoes = pacienteCuidadorController.getPacientesDoCuidador(idCuidador);
            return Response.ok(associacoes).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @GET
    public Response buscarTodasAssociacoes() {
        try {
            List<PacienteCuidadorResponseDTO> associacoes = pacienteCuidadorController.getAllAssociacoes();
            return Response.ok(associacoes).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @POST
    public Response associarPacienteCuidador(PacienteCuidadorRequestDTO pacienteCuidadorRequest) {
        try {
            PacienteCuidadorResponseDTO associacao = pacienteCuidadorController.associarPacienteCuidador(pacienteCuidadorRequest);
            return Response.status(Response.Status.CREATED).entity(associacao).build();
        } catch (Exception e){
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/paciente/{idPaciente}/cuidador/{idCuidador}")
    public Response desassociarPacienteCuidador(
            @PathParam("idPaciente") Integer idPaciente,
            @PathParam("idCuidador") Integer idCuidador) {
        try {
            pacienteCuidadorController.desassociarPacienteCuidador(idPaciente, idCuidador);
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (EntidadeNaoLocalizadaException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
}