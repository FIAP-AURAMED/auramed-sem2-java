package br.com.auramed.infrastructure.api.rest;

import br.com.auramed.domain.exception.EntidadeNaoLocalizadaException;
import br.com.auramed.interfaces.controllers.AuthMedicoController;
import br.com.auramed.interfaces.dto.request.AuthMedicoRequestDTO;
import br.com.auramed.interfaces.dto.response.AuthMedicoResponseDTO;
import br.com.auramed.interfaces.dto.request.LoginRequestDTO;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/auth/medicos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthMedicoRestController {
    private final AuthMedicoController authMedicoController;

    @Inject
    public AuthMedicoRestController(AuthMedicoController authMedicoController) {
        this.authMedicoController = authMedicoController;
    }

    @POST
    @Path("/login")
    public Response login(LoginRequestDTO loginRequest) {
        try {
            authMedicoController.validarCredenciais(loginRequest.getEmail(), loginRequest.getSenha());
            return Response.ok().entity("Login realizado com sucesso").build();
        } catch (EntidadeNaoLocalizadaException e){
            return Response.status(Response.Status.UNAUTHORIZED).entity("Credenciais inválidas").build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") Integer id) {
        try {
            AuthMedicoResponseDTO authMedico = authMedicoController.getAuthMedicoById(id);
            return Response.ok(authMedico).build();
        } catch (EntidadeNaoLocalizadaException e){
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/email/{email}")
    public Response buscarPorEmail(@PathParam("email") String email) {
        try {
            AuthMedicoResponseDTO authMedico = authMedicoController.getAuthMedicoByEmail(email);
            return Response.ok(authMedico).build();
        } catch (EntidadeNaoLocalizadaException e){
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/medico/{medicoId}")
    public Response buscarPorMedicoId(@PathParam("medicoId") Integer medicoId) {
        try {
            AuthMedicoResponseDTO authMedico = authMedicoController.getAuthMedicoByMedicoId(medicoId);
            return Response.ok(authMedico).build();
        } catch (EntidadeNaoLocalizadaException e){
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @GET
    public Response buscarTodosAuthMedicos() {
        try {
            List<AuthMedicoResponseDTO> authMedicos = authMedicoController.getAllAuthMedicos();
            return Response.ok(authMedicos).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @POST
    public Response criarAuthMedico(AuthMedicoRequestDTO authMedicoRequest) {
        try {
            AuthMedicoResponseDTO authMedico = authMedicoController.criarAuthMedico(authMedicoRequest);
            return Response.status(Response.Status.CREATED).entity(authMedico).build();
        } catch (Exception e){
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response atualizarAuthMedico(@PathParam("id") Integer id, AuthMedicoRequestDTO authMedicoRequest) {
        try {
            AuthMedicoResponseDTO authMedico = authMedicoController.editarAuthMedico(id, authMedicoRequest);
            return Response.status(Response.Status.OK).entity(authMedico).build();
        } catch (EntidadeNaoLocalizadaException e){
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deletarAuthMedico(@PathParam("id") Integer id) {
        try {
            authMedicoController.deleteAuthMedico(id);
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (EntidadeNaoLocalizadaException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @PATCH
    @Path("/{id}/senha")
    public Response atualizarSenha(@PathParam("id") Integer id, String novaSenhaHash) {
        try {
            AuthMedicoResponseDTO authMedico = authMedicoController.atualizarSenha(id, novaSenhaHash);
            return Response.ok(authMedico).build();
        } catch (EntidadeNaoLocalizadaException e){
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @PATCH
    @Path("/{id}/bloquear")
    public Response bloquearConta(@PathParam("id") Integer id) {
        try {
            AuthMedicoResponseDTO authMedico = authMedicoController.bloquearConta(id);
            return Response.ok(authMedico).build();
        } catch (EntidadeNaoLocalizadaException e){
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @PATCH
    @Path("/{id}/ativar")
    public Response ativarConta(@PathParam("id") Integer id) {
        try {
            AuthMedicoResponseDTO authMedico = authMedicoController.ativarConta(id);
            return Response.ok(authMedico).build();
        } catch (EntidadeNaoLocalizadaException e){
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
}