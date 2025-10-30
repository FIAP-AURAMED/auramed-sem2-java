package br.com.auramed.infrastructure.api.rest;

import br.com.auramed.domain.exception.EntidadeNaoLocalizadaException;
import br.com.auramed.interfaces.controllers.PerfilCognitivoController;
import br.com.auramed.interfaces.dto.request.PerfilCognitivoRequestDTO;
import br.com.auramed.interfaces.dto.response.PerfilCognitivoResponseDTO;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/perfil-cognitivo")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PerfilCognitivoRestController {
    private final PerfilCognitivoController perfilCognitivoController;

    @Inject
    public PerfilCognitivoRestController(PerfilCognitivoController perfilCognitivoController) {
        this.perfilCognitivoController = perfilCognitivoController;
    }

    @GET
    @Path("/{idPerfilCognitivo}")
    public Response buscarPorId(@PathParam("idPerfilCognitivo") Integer idPerfilCognitivo) {
        try {
            PerfilCognitivoResponseDTO perfilCognitivo = perfilCognitivoController.getPerfilCognitivoById(idPerfilCognitivo);
            return Response.ok(perfilCognitivo).build();
        } catch (EntidadeNaoLocalizadaException e){
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/paciente/{idPaciente}")
    public Response buscarPorPaciente(@PathParam("idPaciente") Integer idPaciente) {
        try {
            PerfilCognitivoResponseDTO perfilCognitivo = perfilCognitivoController.getPerfilCognitivoPorPaciente(idPaciente);
            return Response.ok(perfilCognitivo).build();
        } catch (EntidadeNaoLocalizadaException e){
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @GET
    public Response buscarTodosPerfisCognitivos() {
        try {
            List<PerfilCognitivoResponseDTO> perfisCognitivos = perfilCognitivoController.getAllPerfisCognitivos();
            return Response.ok(perfisCognitivos).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @POST
    public Response criarPerfilCognitivo(PerfilCognitivoRequestDTO perfilCognitivoRequest) {
        try {
            PerfilCognitivoResponseDTO perfilCognitivo = perfilCognitivoController.criarPerfilCognitivo(perfilCognitivoRequest);
            return Response.status(Response.Status.CREATED).entity(perfilCognitivo).build();
        } catch (Exception e){
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("/{idPerfilCognitivo}")
    public Response atualizarPerfilCognitivo(
            @PathParam("idPerfilCognitivo") Integer idPerfilCognitivo,
            PerfilCognitivoRequestDTO perfilCognitivoRequest) {
        try {
            PerfilCognitivoResponseDTO perfilCognitivo = perfilCognitivoController.editarPerfilCognitivo(idPerfilCognitivo, perfilCognitivoRequest);
            return Response.status(Response.Status.OK).entity(perfilCognitivo).build();
        } catch (EntidadeNaoLocalizadaException e){
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/{idPerfilCognitivo}")
    public Response deletarPerfilCognitivo(@PathParam("idPerfilCognitivo") Integer idPerfilCognitivo) {
        try {
            perfilCognitivoController.deletePerfilCognitivo(idPerfilCognitivo);
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (EntidadeNaoLocalizadaException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
}