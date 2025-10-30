package br.com.auramed.infrastructure.api.rest;

import br.com.auramed.domain.exception.EntidadeNaoLocalizadaException;
import br.com.auramed.interfaces.controllers.EnderecoController;
import br.com.auramed.interfaces.dto.request.EnderecoRequestDTO;
import br.com.auramed.interfaces.dto.response.EnderecoResponseDTO;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/enderecos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EnderecoRestController {
    private final EnderecoController enderecoController;

    @Inject
    public EnderecoRestController(EnderecoController enderecoController) {
        this.enderecoController = enderecoController;
    }

    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") Integer id) {
        try {
            EnderecoResponseDTO endereco = enderecoController.getEnderecoById(id);
            return Response.ok(endereco).build();
        } catch (EntidadeNaoLocalizadaException e){
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @GET
    public Response buscarTodosEnderecos() {
        try {
            List<EnderecoResponseDTO> enderecos = enderecoController.getAllEnderecos();
            return Response.ok(enderecos).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/pessoa/{pessoaId}")
    public Response buscarEnderecosPorPessoa(@PathParam("pessoaId") Integer pessoaId) {
        try {
            List<EnderecoResponseDTO> enderecos = enderecoController.getEnderecosPorPessoa(pessoaId);
            return Response.ok(enderecos).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @POST
    public Response criarEndereco(EnderecoRequestDTO enderecoRequest) {
        try {
            EnderecoResponseDTO endereco = enderecoController.criarEndereco(enderecoRequest);
            return Response.status(Response.Status.CREATED).entity(endereco).build();
        } catch (Exception e){
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response atualizarEndereco(@PathParam("id") Integer id, EnderecoRequestDTO enderecoRequest) {
        try {
            EnderecoResponseDTO endereco = enderecoController.editarEndereco(id, enderecoRequest);
            return Response.status(Response.Status.OK).entity(endereco).build();
        } catch (EntidadeNaoLocalizadaException e){
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deletarEndereco(@PathParam("id") Integer id) {
        try {
            enderecoController.deleteEndereco(id);
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (EntidadeNaoLocalizadaException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
}