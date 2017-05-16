package tk.ww3app.service;

import java.text.ParseException;
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

import tk.ww3app.facade.CountryStatFacade;
import tk.ww3app.model.CountryStat;

@Path("/")
@ApplicationPath("/JsonService")

public class CountryStatService extends Application{
	
	@EJB 
	CountryStatFacade FacadeInjection;
	
	Logger logger = Logger.getLogger(CountryService.class.getName());
	
	@GET
	@Path("/listcountrystats")
	@Produces("application/json")
	public List<CountryStat> findAll(){
		List<CountryStat> lcs = FacadeInjection.findAll();
		for (int i=0; i<lcs.size(); i++){
			try {
				lcs.get(i).cambiarformatoFecha();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return lcs;
	}
	
	@GET
    @Path("/getcountrystat")
    @Produces("application/json")
    public CountryStat find(@QueryParam("id") Integer id) {
        CountryStat cs = FacadeInjection.find(id);
        try {
			cs.cambiarformatoFecha();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return cs;
    }
	
	
	

}
