package br.com.auramed.infrastructure.api.rest;

import br.com.auramed.domain.exception.EntidadeNaoLocalizadaException;
import br.com.auramed.interfaces.controllers.BaseConhecimentoController;
import br.com.auramed.interfaces.dto.request.BaseConhecimentoRequestDTO;
import br.com.auramed.interfaces.dto.response.BaseConhecimentoResponseDTO;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.logging.Logger;

import java.util.List;

@Path("/base-conhecimento")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BaseConhecimentoRestController {
    private final BaseConhecimentoController baseConhecimentoController;

    @Inject
    Logger logger;

    @Inject
    public BaseConhecimentoRestController(BaseConhecimentoController baseConhecimentoController) {
        this.baseConhecimentoController = baseConhecimentoController;
    }

    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") String id) {
        try {
            BaseConhecimentoResponseDTO conhecimento = baseConhecimentoController.getBaseConhecimentoById(id);
            return Response.ok(conhecimento).build();
        } catch (EntidadeNaoLocalizadaException e){
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @GET
    public Response buscarTodos() {
        try {
            List<BaseConhecimentoResponseDTO> conhecimentos = baseConhecimentoController.getAllBaseConhecimento();
            return Response.ok(conhecimentos).build();
        } catch (Exception e) {
            logger.error("Erro ao buscar base de conhecimento: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/categoria/{categoria}")
    public Response buscarPorCategoria(@PathParam("categoria") String categoria) {
        try {
            List<BaseConhecimentoResponseDTO> conhecimentos = baseConhecimentoController.getBaseConhecimentoPorCategoria(categoria);
            return Response.ok(conhecimentos).build();
        } catch (Exception e) {
            logger.error("Erro ao buscar por categoria: " + e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @POST
    public Response criarBaseConhecimento(BaseConhecimentoRequestDTO conhecimentoRequest) {
        try {
            BaseConhecimentoResponseDTO conhecimento = baseConhecimentoController.criarBaseConhecimento(conhecimentoRequest);
            return Response.status(Response.Status.CREATED).entity(conhecimento).build();
        } catch (Exception e){
            logger.error("Erro ao criar base de conhecimento: " + e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response atualizarBaseConhecimento(@PathParam("id") String id,
                                              BaseConhecimentoRequestDTO conhecimentoRequest) {
        try {
            BaseConhecimentoResponseDTO conhecimento = baseConhecimentoController.editarBaseConhecimento(id, conhecimentoRequest);
            return Response.status(Response.Status.OK).entity(conhecimento).build();
        } catch (EntidadeNaoLocalizadaException e){
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (Exception e) {
            logger.error("Erro ao atualizar base de conhecimento: " + e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deletarBaseConhecimento(@PathParam("id") String id) {
        try {
            baseConhecimentoController.deleteBaseConhecimento(id);
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (EntidadeNaoLocalizadaException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (Exception e) {
            logger.error("Erro ao deletar base de conhecimento: " + e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/health")
    public Response healthCheck() {
        return Response.ok("Base de Conhecimento service is running").build();
    }
}