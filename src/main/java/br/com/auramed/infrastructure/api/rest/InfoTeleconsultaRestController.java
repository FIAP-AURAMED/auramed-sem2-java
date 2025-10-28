package br.com.auramed.infrastructure.api.rest;

import br.com.auramed.domain.exception.EntidadeNaoLocalizadaException;
import br.com.auramed.interfaces.controllers.InfoTeleconsultaController;
import br.com.auramed.interfaces.dto.request.InfoTeleconsultaRequestDTO;
import br.com.auramed.interfaces.dto.response.InfoTeleconsultaResponseDTO;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/info-teleconsulta")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class InfoTeleconsultaRestController {
    private final InfoTeleconsultaController infoTeleconsultaController;

    @Inject
    public InfoTeleconsultaRestController(InfoTeleconsultaController infoTeleconsultaController) {
        this.infoTeleconsultaController = infoTeleconsultaController;
    }

    @GET
    @Path("/{idInfoTeleconsulta}")
    public Response buscarPorId(@PathParam("idInfoTeleconsulta") Integer idInfoTeleconsulta) {
        try {
            InfoTeleconsultaResponseDTO infoTeleconsulta = infoTeleconsultaController.getInfoTeleconsultaById(idInfoTeleconsulta);
            return Response.ok(infoTeleconsulta).build();
        } catch (EntidadeNaoLocalizadaException e){
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/paciente/{idPaciente}")
    public Response buscarPorPaciente(@PathParam("idPaciente") Integer idPaciente) {
        try {
            InfoTeleconsultaResponseDTO infoTeleconsulta = infoTeleconsultaController.getInfoTeleconsultaPorPaciente(idPaciente);
            return Response.ok(infoTeleconsulta).build();
        } catch (EntidadeNaoLocalizadaException e){
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @GET
    public Response buscarTodasInfoTeleconsultas() {
        try {
            List<InfoTeleconsultaResponseDTO> infoTeleconsultas = infoTeleconsultaController.getAllInfoTeleconsultas();
            return Response.ok(infoTeleconsultas).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @POST
    public Response criarInfoTeleconsulta(InfoTeleconsultaRequestDTO infoTeleconsultaRequest) {
        try {
            InfoTeleconsultaResponseDTO infoTeleconsulta = infoTeleconsultaController.criarInfoTeleconsulta(infoTeleconsultaRequest);
            return Response.status(Response.Status.CREATED).entity(infoTeleconsulta).build();
        } catch (Exception e){
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("/{idInfoTeleconsulta}")
    public Response atualizarInfoTeleconsulta(
            @PathParam("idInfoTeleconsulta") Integer idInfoTeleconsulta,
            InfoTeleconsultaRequestDTO infoTeleconsultaRequest) {
        try {
            InfoTeleconsultaResponseDTO infoTeleconsulta = infoTeleconsultaController.editarInfoTeleconsulta(idInfoTeleconsulta, infoTeleconsultaRequest);
            return Response.status(Response.Status.OK).entity(infoTeleconsulta).build();
        } catch (EntidadeNaoLocalizadaException e){
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/{idInfoTeleconsulta}")
    public Response deletarInfoTeleconsulta(@PathParam("idInfoTeleconsulta") Integer idInfoTeleconsulta) {
        try {
            infoTeleconsultaController.deleteInfoTeleconsulta(idInfoTeleconsulta);
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (EntidadeNaoLocalizadaException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
}