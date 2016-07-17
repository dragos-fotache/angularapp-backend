package com.heroes;

import java.sql.SQLException;
import java.util.Collection;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("/")
public class RestService {
	@GET
	@Produces("application/json")
	@Path("/articles")
	public Response getAllArticles() throws ClassNotFoundException, SQLException {
		Collection<Article> output = ArticleService.INSTANCE.getArticles();
		
		return Response
					.status(200)
					.header("Access-Control-Allow-Origin", "*")
					.entity(output)
					.build();
	}
	
	@GET
	@Produces("application/json")
	@Path("/articles/{first}/{rows}/{sortField}/{sortOrder}/{searchString}")
	public Response getArticlesSliceAndCount(@PathParam("first") int first, 
											  @PathParam("rows") int rows,
											  @PathParam("sortField") String sortFieldParam,
											  @PathParam("sortOrder") String sortOrder,
											  @PathParam("searchString") String searchStringParam
											  ) throws ClassNotFoundException, SQLException {
		
		String sortField;
		if ("undefined".equals(sortFieldParam)) {
			sortField = "id";
		} else {
			sortField = sortFieldParam;
		}
		String searchString;
		if ("*".equals(searchStringParam)) {
			searchString = "%";
		} else {
			searchString = searchStringParam;
		}
		ArticleSliceAndCount result = 
				ArticleService.INSTANCE.getArticlesSliceAndCount(first, 
																 rows, 
																 sortField, 
																 "desc".equals(sortOrder) ? true : false,
																 searchString);
		
		return Response
				.status(200)
				.header("Access-Control-Allow-Origin", "*")
				.entity(result)
				.build();
	}
	
}
