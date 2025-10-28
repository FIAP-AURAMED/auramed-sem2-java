package br.com.auramed.infrastructure.api.rest;

import br.com.auramed.domain.exception.EntidadeNaoLocalizadaException;
import br.com.auramed.interfaces.controllers.CuidadorController;
import br.com.auramed.interfaces.dto.request.CuidadorRequestDTO;
import br.com.auramed.interfaces.dto.response.CuidadorResponseDTO;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/cuidadores")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CuidadorRestController {
    private final CuidadorController cuidadorController;

    @Inject
    public CuidadorRestController(CuidadorController cuidadorController) {
        this.cuidadorController = cuidadorController;
    }

    @GET
    @Path("/{idPessoa}")
    public Response buscarPorId(@PathParam("idPessoa") Integer idPessoa) {
        try {
            CuidadorResponseDTO cuidador = cuidadorController.getCuidadorById(idPessoa);
            return Response.ok(cuidador).build();
        } catch (EntidadeNaoLocalizadaException e){
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @GET
    public Response buscarTodosCuidadores() {
        try {
            List<CuidadorResponseDTO> cuidadores = cuidadorController.getAllCuidadores();
            return Response.ok(cuidadores).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @POST
    public Response criarCuidador(CuidadorRequestDTO cuidadorRequest) {
        try {
            CuidadorResponseDTO cuidador = cuidadorController.criarCuidador(cuidadorRequest);
            return Response.status(Response.Status.CREATED).entity(cuidador).build();
        } catch (Exception e){
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("/{idPessoa}")
    public Response atualizarCuidador(@PathParam("idPessoa") Integer idPessoa, CuidadorRequestDTO cuidadorRequest) {
        try {
            CuidadorResponseDTO cuidador = cuidadorController.editarCuidador(idPessoa, cuidadorRequest);
            return Response.status(Response.Status.OK).entity(cuidador).build();
        } catch (EntidadeNaoLocalizadaException e){
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/{idPessoa}")
    public Response deletarCuidador(@PathParam("idPessoa") Integer idPessoa) {
        try {
            cuidadorController.deleteCuidador(idPessoa);
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (EntidadeNaoLocalizadaException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @PATCH
    @Path("/{idPessoa}/ativar")
    public Response ativarCuidador(@PathParam("idPessoa") Integer idPessoa) {
        try {
            CuidadorResponseDTO cuidador = cuidadorController.ativarCuidador(idPessoa);
            return Response.ok(cuidador).build();
        } catch (EntidadeNaoLocalizadaException e){
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @PATCH
    @Path("/{idPessoa}/inativar")
    public Response inativarCuidador(@PathParam("idPessoa") Integer idPessoa) {
        try {
            CuidadorResponseDTO cuidador = cuidadorController.inativarCuidador(idPessoa);
            return Response.ok(cuidador).build();
        } catch (EntidadeNaoLocalizadaException e){
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
}