package br.com.auramed.infrastructure.api.rest;

import br.com.auramed.domain.exception.EntidadeNaoLocalizadaException;
import br.com.auramed.interfaces.controllers.EspecialidadeController;
import br.com.auramed.interfaces.dto.request.EspecialidadeRequestDTO;
import br.com.auramed.interfaces.dto.response.EspecialidadeResponseDTO;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/especialidades")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EspecialidadeRestController {
    private final EspecialidadeController especialidadeController;

    @Inject
    public EspecialidadeRestController(EspecialidadeController especialidadeController) {
        this.especialidadeController = especialidadeController;
    }

    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") Integer id) {
        try {
            EspecialidadeResponseDTO especialidade = especialidadeController.getEspecialidadeById(id);
            return Response.ok(especialidade).build();
        } catch (EntidadeNaoLocalizadaException e){
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/nome/{nome}")
    public Response buscarPorNome(@PathParam("nome") String nome) {
        try {
            EspecialidadeResponseDTO especialidade = especialidadeController.getEspecialidadeByNome(nome);
            return Response.ok(especialidade).build();
        } catch (EntidadeNaoLocalizadaException e){
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @GET
    public Response buscarTodasEspecialidades() {
        try {
            List<EspecialidadeResponseDTO> especialidades = especialidadeController.getAllEspecialidades();
            return Response.ok(especialidades).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/ativas")
    public Response buscarEspecialidadesAtivas() {
        try {
            List<EspecialidadeResponseDTO> especialidades = especialidadeController.getEspecialidadesAtivas();
            return Response.ok(especialidades).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @POST
    public Response criarEspecialidade(EspecialidadeRequestDTO especialidadeRequest) {
        try {
            EspecialidadeResponseDTO especialidade = especialidadeController.criarEspecialidade(especialidadeRequest);
            return Response.status(Response.Status.CREATED).entity(especialidade).build();
        } catch (Exception e){
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response atualizarEspecialidade(@PathParam("id") Integer id, EspecialidadeRequestDTO especialidadeRequest) {
        try {
            EspecialidadeResponseDTO especialidade = especialidadeController.editarEspecialidade(id, especialidadeRequest);
            return Response.status(Response.Status.OK).entity(especialidade).build();
        } catch (EntidadeNaoLocalizadaException e){
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deletarEspecialidade(@PathParam("id") Integer id) {
        try {
            especialidadeController.deleteEspecialidade(id);
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (EntidadeNaoLocalizadaException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @PATCH
    @Path("/{id}/ativar")
    public Response ativarEspecialidade(@PathParam("id") Integer id) {
        try {
            EspecialidadeResponseDTO especialidade = especialidadeController.ativarEspecialidade(id);
            return Response.ok(especialidade).build();
        } catch (EntidadeNaoLocalizadaException e){
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @PATCH
    @Path("/{id}/inativar")
    public Response inativarEspecialidade(@PathParam("id") Integer id) {
        try {
            EspecialidadeResponseDTO especialidade = especialidadeController.inativarEspecialidade(id);
            return Response.ok(especialidade).build();
        } catch (EntidadeNaoLocalizadaException e){
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
}