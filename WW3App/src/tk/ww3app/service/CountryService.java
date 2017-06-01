package tk.ww3app.service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Application;

import tk.ww3app.facade.CountryFacade;
import tk.ww3app.model.Country;

@Path("/country")
@ApplicationPath("/")

public class CountryService extends Application{
	
	@EJB 
	CountryFacade CountryFacadeInjection;
	
	Logger logger = Logger.getLogger(CountryService.class.getName());
	
	@GET
	@Path("/listcountry")
	@Produces("application/json")
	public List<Country> findAll(){
		return CountryFacadeInjection.findAll();
	}
	
	@GET
	@Path("/listcountrynames")
	@Produces("application/json")
	public JsonArray findNames(){
		JsonArrayBuilder arrayext = Json.createArrayBuilder();
		JsonObjectBuilder builder = Json.createObjectBuilder();
		List<String> paises = CountryFacadeInjection.findNames();
		for (String pais : paises){
			JsonObject punto = builder.add("nombrePais", pais).build();
			arrayext.add(punto);
		}
		return arrayext.build();
				
	}
	@GET
    @Path("/getcountry")
    @Produces("application/json")
    public Country find(@QueryParam("id") Integer id) {
        return CountryFacadeInjection.find(id);
    }
	

}
