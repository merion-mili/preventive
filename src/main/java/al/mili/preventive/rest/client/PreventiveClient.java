package al.mili.preventive.rest.client;

import java.io.Serializable;
import java.net.URI;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import al.mili.preventive.db.model.Preventive;

public class PreventiveClient implements Serializable {

	private Client client;

	public PreventiveClient() {

		client = ClientBuilder.newClient();
	}

	public List<Preventive> get() {
		URI uri = UriBuilder.fromUri("http://localhost:8080/soccer-0.0.1-SNAPSHOT/webapi/preventive").build();

		WebTarget target = client.target(uri);

		List<Preventive> response = target.request(MediaType.APPLICATION_JSON).get(
				new GenericType<List<Preventive>>() {
				});

		/*
		 * Response response = (Response)
		 * target.request(MediaType.APPLICATION_JSON) .get(new
		 * GenericType<List<Season>> () {});
		 * 
		 * if (response.getStatus() != 200) { throw new
		 * RuntimeException(response.getStatus() +
		 * ": there is an error in the server."); }
		 * 
		 * return response.ok().build();
		 */
		return response;
	}

	/*public League get(String leagueName) {

		URI uri = UriBuilder.fromUri("http://localhost:8080/soccer-0.0.1-SNAPSHOT/webapi").build();
		WebTarget target = client.target(uri);
		Response response = target.path("/leagues/"+leagueName).request().get(Response.class);

		if (response.getStatus() != 200) {
			throw new RuntimeException(response.getStatus()
					+ ": there is an error in the server.");
		}

		return response.readEntity(League.class);
	}*/
}
