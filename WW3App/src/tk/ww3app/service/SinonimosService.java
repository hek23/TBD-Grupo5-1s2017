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

import tk.ww3app.facade.KeywordResumeFacade;
import tk.ww3app.facade.SinonimosFacade;
import tk.ww3app.model.KeywordResume;
import tk.ww3app.model.Sinonimos;

@Path("/krs")
@ApplicationPath("/")

public class SinonimosService extends Application{
	
	@EJB 
	SinonimosFacade SinonimosInjection;
	
	Logger logger = Logger.getLogger(KeywordResume.class.getName());
	
	@GET
	@Path("/listsinonimos")
	@Produces("application/jsonaa")
	public List<Sinonimos> findAll(){
		return SinonimosInjection.findAll();
	}
	
	@GET
    @Path("/getsinonimo")
    @Produces("application/json")
    public Sinonimos find(@QueryParam("id") Integer id) {
        return SinonimosInjection.find(id);
    }
	
	
	

}
