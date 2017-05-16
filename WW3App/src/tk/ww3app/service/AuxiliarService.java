package tk.ww3app.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;

import tk.ww3app.facade.CountryFacade;
import tk.ww3app.facade.CountryResumeFacade;
import tk.ww3app.facade.CountryStatFacade;
import tk.ww3app.facade.KeywordFacade;
import tk.ww3app.facade.KeywordResumeFacade;
import tk.ww3app.facade.KeywordResumeJSONFacade;
import tk.ww3app.facade.UserFacade;
import tk.ww3app.model.CircData;
import tk.ww3app.model.CircObject;
import tk.ww3app.model.CountryResume;
import tk.ww3app.model.CountryStat;
import tk.ww3app.model.KeywordResume;
import tk.ww3app.model.LinearData;
import tk.ww3app.model.LinearObject;
import tk.ww3app.model.KeywordJSONResume;

@Path("/")
@ApplicationPath("/JsonService")
public class AuxiliarService extends Application{

	@EJB 
	KeywordResumeFacade KWRFacadeInjection;
	@EJB 
	CountryFacade CFFacadeInjection;
	@EJB 
	CountryResumeFacade CRFFacadeInjection;
	@EJB 
	CountryStatFacade CSFFacadeInjection;
	@EJB 
	KeywordFacade KWFacadeInjection;
	@EJB 
	UserFacade UFacadeInjection;
	@EJB
	KeywordResumeJSONFacade KRFJson;
	
	@GET
	@Path("/auxiliarJsonFullCake")
	@Produces("application/json")
	public CircData getGraphData(){
		//Se calcula cuantos paises hay en el gráfico y cuantos tweets hay en total
		//Para esto se usa la vista CountryResume
		List<CountryResume> cr = CRFFacadeInjection.findAll();
		//Se calcula la suma de los tweets
		int sumaTweets = 0;
		int cantidadPaises = 0;
		List<String> paises = new ArrayList<String>();
		List<String> tweets = new ArrayList<String>();
		for (int i=0; i<cr.size(); i++){
			if (cr.get(i).getTweetsCount().intValue()>0){
				sumaTweets = sumaTweets + cr.get(i).getTweetsCount().intValue();
				cantidadPaises = cantidadPaises + 1;
				paises.add(cr.get(i).getName());
				tweets.add(String.valueOf(cr.get(i).getTweetsCount().intValue()));
			}
		}
		CircObject circobj = new CircObject(paises, tweets);
		CircData circ = new CircData(String.valueOf(cantidadPaises), String.valueOf(sumaTweets), circobj);
		return circ;
		
	}
	@GET
	@Path("/auxiliarJsonFullLinear")
	@Produces("application/json")
	public LinearData getLinearData(){
		List<KeywordJSONResume> krfj = KRFJson.findAll();
		List<String> fechas = new ArrayList<String>();
		List<Integer> tweets = new ArrayList<Integer>();
		for (int i=0; i<krfj.size(); i++){
			fechas.add(krfj.get(i).getDate().toString().split("T")[0]);
			tweets.add(krfj.get(i).getTc().intValue());
		}
		List<LinearObject> lolist = new ArrayList<LinearObject>();
		for (int i=0; i<krfj.size(); i++){
			String concepto = krfj.get(i).getWord();
			LinearObject lo =new LinearObject(concepto, fechas, tweets);
			lolist.add(lo);
		}
		LinearData ld = new LinearData(String.valueOf(fechas.size()), lolist);
		return ld;
		
		
	}
	
}
