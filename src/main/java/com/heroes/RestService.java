package com.heroes;

import java.sql.SQLException;
import java.util.Collection;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("/")
public class RestService {
	@GET
	@Produces("application/json")
	@Path("/articles")
	public Response getStartingPage() throws ClassNotFoundException, SQLException {
		Collection<Article> output = ArticleService.INSTANCE.getArticles();
		
		return Response
					.status(200)
					.header("Access-Control-Allow-Origin", "*")
					.entity(output)
					.build();
	}
}
