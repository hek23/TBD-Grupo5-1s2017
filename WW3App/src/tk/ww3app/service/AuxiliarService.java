package tk.ww3app.service;

import java.util.ArrayList;
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
import tk.ww3app.model.CircularGraphInfo;
import tk.ww3app.model.CountryResume;
import tk.ww3app.model.CountryStat;
import tk.ww3app.model.GraphPoint;
import tk.ww3app.model.InfoGenerationGraph;
import tk.ww3app.model.Keyword;
import tk.ww3app.model.LinearGraphFrame;
import tk.ww3app.model.LinearGraphInfo;
import tk.ww3app.model.KeywordJSONResume;

@Path("/")
@ApplicationPath("/")

public class AuxiliarService extends Application{

	//Dado que se generarán objetos y JSON, se necesitará acceso a todo el código
	//Por ello se inyectan todos los facade
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
	public GraphPoint getCircularData(){
		//Se calcula cuantos paises hay en el gráfico y cuantos tweets hay en total
		//Para esto se usa la vista CountryResume
		List<CountryResume> cr = CRFFacadeInjection.findAll();
		//Se calcula la suma de los tweets
		//int sumaTweets = 0;
		int cantidadPaises = 0;
		//Se define una lista de objetos, que representan la informacion del gráfico.
		//La lista en su conjunto es toda la info. Cada objeto es una parte del gráfico.
		List<GraphPoint> lgp = new ArrayList<GraphPoint>();
		for (int i=0; i<cr.size(); i++){
			//Solo se agregarán los puntos, tal que solo se tomen los que tienen tweets en el resumen
			if (cr.get(i).getTweetsCount().intValue()>0){
				//sumaTweets = sumaTweets + cr.get(i).getTweetsCount().intValue();
				//Se controla la cantidad de Paises en el gráfico
				cantidadPaises = cantidadPaises + 1;
				//Ahora se crea el objeto que lleva la información en si
				GraphPoint gp = new GraphPoint(cr.get(i).getName(), (cr.get(i).getTweetsCount()));
				//Se agrega el punto a la lista
				lgp.add(gp);
			}
		}
		//Ahora que se tienen todos los datos se crea el objeto que representa al gráfico en si.
		CircularGraphInfo circData = new CircularGraphInfo(cantidadPaises, lgp); 
		//Se retorna el objeto del gráfico como un JSON por la anotacion al inicio.
		return lgp.get(0);	
	}
	
	@GET
	@Path("/auxiliarJsonFullLinear")
	@Produces("application/json")
	public LinearGraphInfo getLinearData(){
		List<LinearGraphFrame> listaDeCapas = new ArrayList<LinearGraphFrame>();
		List<Keyword> lkw= KWFacadeInjection.findAll();
		for (Keyword kw : lkw){
			List<KeywordJSONResume> fullkeyword = KRFJson.findByWord(kw.getWord());
			LinearGraphFrame info = new LinearGraphFrame(kw.getWord(), null);
			List<GraphPoint> listaPuntos = new ArrayList<GraphPoint>();
			for (KeywordJSONResume kwr : fullkeyword){
				Double tweets = kwr.getRtc() + kwr.getTc();
				GraphPoint punto = new GraphPoint(null, tweets);
				punto.insertarFecha(kwr.getDate());
				listaPuntos.add(punto);
			}
			info.setPuntos(listaPuntos);
			listaDeCapas.add(info);
		}
		LinearGraphInfo lpgi = new LinearGraphInfo(listaDeCapas.get(0).getPuntos().size(), listaDeCapas);	
		return lpgi;
		}
	
	/*@GET
	@Path("/auxiliarJsonFullLinear")
	@Produces("application/json")
	public List<InfoGenerationGraph> getGenerationData(){
		List<InfoGenerationGraph> lig = new ArrayList<InfoGenerationGraph>();
		List<CountryStat> lcs = CSFFacadeInjection.findByCountry();
		
	}*/
	}