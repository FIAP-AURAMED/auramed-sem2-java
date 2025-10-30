package br.com.auramed.infrastructure.api.rest;

import br.com.auramed.domain.exception.EntidadeNaoLocalizadaException;
import br.com.auramed.interfaces.controllers.PacienteCompletoController;
import br.com.auramed.interfaces.dto.request.PacienteCompletoRequestDTO;
import br.com.auramed.interfaces.dto.response.PacienteCompletoResponseDTO;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/pacientes-completo")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PacienteCompletoRestController {
    private final PacienteCompletoController pacienteCompletoController;

    @Inject
    public PacienteCompletoRestController(PacienteCompletoController pacienteCompletoController) {
        this.pacienteCompletoController = pacienteCompletoController;
    }

    @GET
    @Path("/{idPaciente}")
    public Response buscarPacienteCompleto(@PathParam("idPaciente") Integer idPaciente) {
        try {
            PacienteCompletoResponseDTO pacienteCompleto = pacienteCompletoController.getPacienteCompleto(idPaciente);
            return Response.ok(pacienteCompleto).build();
        } catch (EntidadeNaoLocalizadaException e){
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @POST
    public Response criarPacienteCompleto(PacienteCompletoRequestDTO pacienteCompletoRequest) {
        try {
            PacienteCompletoResponseDTO pacienteCompleto = pacienteCompletoController.criarPacienteCompleto(pacienteCompletoRequest);
            return Response.status(Response.Status.CREATED).entity(pacienteCompleto).build();
        } catch (Exception e){
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
}