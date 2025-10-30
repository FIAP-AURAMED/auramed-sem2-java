package br.com.auramed.infrastructure.api.rest;

import br.com.auramed.domain.exception.EntidadeNaoLocalizadaException;
import br.com.auramed.interfaces.controllers.PessoaController;
import br.com.auramed.interfaces.dto.request.PessoaRequestDTO;
import br.com.auramed.interfaces.dto.response.PessoaResponseDTO;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/pessoas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PessoaRestController {
    private final PessoaController pessoaController;

    @Inject
    public PessoaRestController(PessoaController pessoaController) {
        this.pessoaController = pessoaController;
    }

    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") Integer id) {
        try {
            PessoaResponseDTO pessoa = pessoaController.getPessoaById(id);
            return Response.ok(pessoa).build();
        } catch (EntidadeNaoLocalizadaException e){
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @GET
    public Response buscarTodasPessoas() {
        try {
            List<PessoaResponseDTO> pessoas = pessoaController.getAllPessoas();
            return Response.ok(pessoas).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/tipo/{tipo}")
    public Response buscarPessoasPorTipo(@PathParam("tipo") String tipoPessoa) {
        try {
            List<PessoaResponseDTO> pessoas = pessoaController.getPessoasPorTipo(tipoPessoa);
            return Response.ok(pessoas).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @POST
    public Response criarPessoa(PessoaRequestDTO pessoaRequest) {
        try {
            PessoaResponseDTO pessoa = pessoaController.criarPessoa(pessoaRequest);
            return Response.status(Response.Status.CREATED).entity(pessoa).build();
        } catch (Exception e){
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response atualizarPessoa(@PathParam("id") Integer id, PessoaRequestDTO pessoaRequest) {
        try {
            PessoaResponseDTO pessoa = pessoaController.editarPessoa(id, pessoaRequest);
            return Response.status(Response.Status.OK).entity(pessoa).build();
        } catch (EntidadeNaoLocalizadaException e){
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deletarPessoa(@PathParam("id") Integer id) {
        try {
            pessoaController.deletePessoa(id);
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (EntidadeNaoLocalizadaException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @PATCH
    @Path("/{id}/ativar")
    public Response ativarPessoa(@PathParam("id") Integer id) {
        try {
            PessoaResponseDTO pessoa = pessoaController.ativarPessoa(id);
            return Response.ok(pessoa).build();
        } catch (EntidadeNaoLocalizadaException e){
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @PATCH
    @Path("/{id}/inativar")
    public Response inativarPessoa(@PathParam("id") Integer id) {
        try {
            PessoaResponseDTO pessoa = pessoaController.inativarPessoa(id);
            return Response.ok(pessoa).build();
        } catch (EntidadeNaoLocalizadaException e){
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
}