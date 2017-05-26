package tk.ww3app.service;

import java.util.List;
import java.util.logging.Logger;

import javax.ejb.EJB;
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

import tk.ww3app.facade.KeywordFacade;
import tk.ww3app.model.Keyword;

@Path("/")
@ApplicationPath("/")

public class KeywordService extends Application{
	
	@EJB 
	KeywordFacade FacadeInjection;
	
	Logger logger = Logger.getLogger(CountryService.class.getName());
	
	@GET
	@Path("/listwords")
	@Produces("application/json")
	public List<Keyword> findAll(){
		return FacadeInjection.findAll();
	}
	
	@GET
    @Path("/getword")
    @Produces("application/json")
    public Keyword find(@QueryParam("id") Integer id) {
        return FacadeInjection.find(id);
    }
	
	

}
