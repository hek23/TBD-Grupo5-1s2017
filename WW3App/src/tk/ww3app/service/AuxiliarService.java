package tk.ww3app.service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Application;

import tk.ww3app.facade.CountryFacade;
import tk.ww3app.facade.CountryResumeFacade;
import tk.ww3app.facade.CountryStatFacade;
import tk.ww3app.facade.KeywordFacade;
import tk.ww3app.facade.KeywordResumeFacade;
import tk.ww3app.facade.KeywordResumeJSONFacade;
import tk.ww3app.facade.SinonimosFacade;
import tk.ww3app.facade.UserFacade;
import tk.ww3app.model.CircularGraphInfo;
import tk.ww3app.model.Country;
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
	@EJB
	SinonimosFacade SFInjection;
	
	
	@GET
	@Path("/auxiliarJsonFullCake")
	@Produces("application/json")
	public CircularGraphInfo getCircularData(){
		System.out.println("llego solicitus");
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
		return circData;	
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
	
	@GET
	@Path("/jsongenerationgraph/{nombrePais}")
	@Produces("application/json")
	public JsonArray getGenerationData(@PathParam("nombrePais")String ctryName){
		List<InfoGenerationGraph> lig = new ArrayList<InfoGenerationGraph>();
		List<Country> lc = new ArrayList<Country>();
		lc = CFFacadeInjection.findAll();
		JsonArrayBuilder arrayext = Json.createArrayBuilder();
		List<Object[]> lcs = CSFFacadeInjection.findByCountry(ctryName);
		for (Object[] obj : lcs){
			JsonObjectBuilder builder = Json.createObjectBuilder();
			Double tweets = (Double)obj[1] + (Double)obj[0];
			int tweetctd = tweets.intValue();
			JsonObject punto = builder.add("fecha",obj[2].toString()).add("cantidad",tweetctd).build();
			arrayext.add(punto);
		}
		
		return arrayext.build();
		
		
		}
	
	@POST
	@Path("/addWord")
	@Produces("application/json")
	@Consumes("application/json")
	public JsonObject addWord(JsonObject palabras){
		JsonObject elem = palabras;
		JsonObjectBuilder builder = Json.createObjectBuilder();
		//Se sabe que la clave concepto trae el concepto nuevo
		//La clave palabras trae un arreglo con los sinonimos
		//Por tanto primero creamos un arreglo de palabras
		JsonArray jsonPalabras = null;
		String concepto = null;
		int idConcepto = 0;
		try{
			jsonPalabras = elem.getJsonArray("palabras");
		}
		catch (Exception e){
			System.out.println(e);
			return builder.add("estado","ERROR INSERTE SINONIMOS").build();
		}
		//Ahora se extrae el concepto
		try{
			concepto = elem.getString("concepto");
		}
		catch (Exception e){
			System.out.println(e);
			return builder.add("estado","ERROR. INSERTE CONCEPTO").build();
		}
		//Ya que se tienen los datos, se ingresan en la base de datos
		//Se retorna el id del concepto
		try{
			idConcepto = KWFacadeInjection.insertarConcepto(concepto);
		}
		catch (Exception e){
			System.out.println(e);
			return builder.add("estado","ERROR CONCEPTO YA EXISTENTE O MUY LARGO").build();
		}//Ahora se inserta la lista de sinónimos
		try{
			for (JsonValue sinonimo : jsonPalabras) {
				SFInjection.insertarSinonimo(sinonimo.toString(), idConcepto);
			}
		}
		catch (Exception e){
			return builder.add("estado","ERROR INGRESE SINONIMOS O LIMITE SU LONGITUD").build();
		}
		return builder.add("estado","ok").build();
		
	}
	@POST
	@Path("/deleteWord")
	@Produces("application/json")
	@Consumes("application/json")
	public JsonObject borrarConcepto(JsonObject concepto){
		//Para ello se deben dar 3 pasos: Borrar estadisticas
		//Borrar Sinonimos, borrar palabra
		String palabra = concepto.getString("concepto");
		int idConcepto = KWFacadeInjection.buscarWord(palabra);
		CSFFacadeInjection.borrarStats(idConcepto);
		SFInjection.borrarSinonimos(idConcepto);
		KWFacadeInjection.deleteWord(idConcepto);
		JsonObjectBuilder builder = Json.createObjectBuilder();
		return builder.add("estado", "ok").build();
	}
	@GET
	@Path("/rankingJSON")
	@Produces("application/json")
	public JsonArray ranking(){
		JsonArrayBuilder array = Json.createArrayBuilder();
		JsonObjectBuilder builder = Json.createObjectBuilder(); 
		List<Object[]> rankInfo = CSFFacadeInjection.getRankInfo();
		for (Object[] info : rankInfo){
			//En el primer elemento viene la suma o puntaje, el segundo el pais
			Double puente = (Double)info[0];
			array.add(builder.add("Pais", info[1].toString()).add("Puntaje",puente.intValue()).build());
		}
		return array.build();
	}
}
	
	
	