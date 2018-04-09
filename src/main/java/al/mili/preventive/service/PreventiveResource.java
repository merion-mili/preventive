package al.mili.preventive.service;

import java.io.Serializable;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import al.mili.preventive.db.model.Preventive;
import al.mili.preventive.db.service.PreventiveDbService;

@Path("/preventive")
public class PreventiveResource implements Serializable {

	PreventiveDbService preventiveDbService = new PreventiveDbService();

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPreventives() {
		List<Preventive> preventives = preventiveDbService.getPreventives();
		if (preventives == null || preventives.size() <= 0) {
			return Response.status(Status.NOT_FOUND).build();
		}
		return Response.ok()
				.entity(new GenericEntity<List<Preventive>>(preventives) {
				}).build();
	}

}
