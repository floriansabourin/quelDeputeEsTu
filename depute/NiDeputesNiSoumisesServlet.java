package depute;


import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.*;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Transaction;

import java.util.List;
import java.util.logging.Logger;


@SuppressWarnings("serial")
public class NiDeputesNiSoumisesServlet extends HttpServlet {
	private static final Logger log = Logger.getLogger(NiDeputesNiSoumisesServlet.class.getName());
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)	throws IOException {
		resp.setContentType("text/plain");		

		log.info("NiDeputesNiSoumisesServlet : doGet");
		
		List<Loi> lois;
		List<Depute> deputes;		
		InputStream stream = getServletContext().getResourceAsStream("/res/votes.xml");
		lois = SAXXMLParserLoi.parse(stream);		
		InputStream stream2 = getServletContext().getResourceAsStream("/res/deputes.xml");
		deputes = SAXXMLParserDepute.parse(stream2);

		 
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();		
		Transaction txn = datastore.beginTransaction();
		try {			
			for(int i=0; i<deputes.size();i++){
				Entity dep = new Entity("Depute");
				dep.setProperty("ref", deputes.get(i).getRef());
				dep.setProperty("nom", deputes.get(i).getNom());
				dep.setProperty("prenom", deputes.get(i).getPrenom());
				dep.setProperty("parti", deputes.get(i).getParti());
				datastore.put(dep);
				txn.commit();
			}		 
		} finally{
			 if (txn.isActive()){
				 txn.rollback();
			 }
		}
		
		Transaction txn2 = datastore.beginTransaction();
		try {			
			for(int i=0; i<lois.size();i++){
				Entity loi = new Entity("Loi");
				loi.setProperty("titre", lois.get(i).getTitre());
				loi.setProperty("date", lois.get(i).getDate());
				loi.setProperty("nb_votes", lois.get(i).getNb_votes());
				loi.setProperty("nb_nspp", lois.get(i).getNb_nspp());
				loi.setProperty("votes_p", lois.get(i).getVotes_p());
				loi.setProperty("votes_c", lois.get(i).getVotes_c());
				loi.setProperty("votes_a", lois.get(i).getVotes_a());
				datastore.put(loi);
				txn2.commit();
			}		 
		} finally{
			 if (txn2.isActive()){
				 txn2.rollback();
			 }
		}

		
		/*
		Filter VotePourLoi =
				  new FilterPredicate("pour",
				                      FilterOperator.NOT_EQUAL ,
				                      "4115503168");


		// Use class Query to assemble a query
		Query q = new Query("Lois").setFilter(VotePourLoi);
		
		// Use PreparedQuery interface to retrieve results
		PreparedQuery pq = datastore.prepare(q);

		for (Entity result : pq.asIterable()) {
			  String resultat = result.getProperty("pour").toString();

			  
				resp.getWriter().println("test"+resultat);
		}
		
		
		GqlQuery.Builder query = GqlQuery.newBuilder().setQueryString("SELECT * FROM Person");
		RunQueryRequest request = RunQueryRequest.newBuilder().setGqlQuery(query).build();

		RunQueryResponse response = datastore.runQuery(request);
		List<Entity> results = new ArrayList<Entity>();
		for (EntityResult entityResult : response.getBatch().getEntityResultList()) {
		  results.add(entityResult.getEntity());
		}
		 
		 
		 
		 * 
		 * 
		 * 
		 * 
		 * 
		 
		
		
		
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Transaction txn = datastore.beginTransaction();
		
		Entity lois = new Entity("Lois");
		lois.setProperty("id","1");
		lois.setProperty("nom","loi 1");
		lois.setProperty("annee","2005");
		lois.setProperty("votes_pour",new ArrayList<String>(
				Arrays.asList("Depute1", "Depute2")));
		lois.setProperty("votes_contre",new ArrayList<String>(
				Arrays.asList("Depute3", "Depute4")));
		datastore.put(lois);
		
		Entity lois2 = new Entity("Lois");
		lois2.setProperty("id","2");
		lois2.setProperty("nom","loi 1");
		lois2.setProperty("annee","2015");
		lois.setProperty("votes_pour",new ArrayList<String>(
				Arrays.asList("Depute2", "Depute4")));
		lois.setProperty("votes_contre",new ArrayList<String>(
				Arrays.asList("Depute1", "Depute3")));
		datastore.put(lois2);
		
		Entity Deputes = new Entity("Deputes");
		Deputes.setProperty("nom", "Depute1");
		Deputes.setProperty("Identifiant", "01");
		Deputes.setProperty("Parti", "Republicains");
		datastore.put(Deputes);
		
		Entity Deputes1 = new Entity("Deputes");
		Deputes1.setProperty("nom", "Depute2");
		Deputes1.setProperty("Identifiant", "02");
		Deputes1.setProperty("Parti", "PS");
		datastore.put(Deputes1);
		
		Entity Deputes2 = new Entity("Deputes");
		Deputes2.setProperty("nom", "Depute3");
		Deputes2.setProperty("Identifiant", "03");
		Deputes2.setProperty("Parti", "Ecolo");
		datastore.put(Deputes2);
		
		Entity Deputes3 = new Entity("Deputes");
		Deputes3.setProperty("nom", "Depute4");
		Deputes3.setProperty("Identifiant", "04");
		Deputes3.setProperty("Parti", "Republicains");
		datastore.put(Deputes3);		
		
		
		
		//Filter d = new FilterPredicate("id_votant",FilterOperator.EQUAL,"Identifiant");
		Filter l = new FilterPredicate("annee",FilterOperator.GREATER_THAN,"2013");
		//Filter f = CompositeFilterOperator.and(d,l);
		Query q = new Query("Lois").setFilter(l);
		
		PreparedQuery pq = datastore.prepare(q);
		for (Entity result : pq.asIterable()) {
		  String n = (String) result.getProperty("nom");
		  String p = (String) result.getProperty("annee");		  
		  ArrayList<String> vc = (ArrayList<String>) result.getProperty("votes_contre");
		  log.info(n + ", "+p+"|");
		  for (int i=0; i<vc.size();i++){
			  log.info(vc.get(i));
		  }
		}	
		*/
		
		/*
		Entity test;
		Entity test2 = new Entity("XXX");
		test2.setProperty("nom", "aaa");
		datastore.put(test2);
		try {
			test = datastore.get(test2.getKey());
			Entity me = new Entity ("TRANSACTION", "fils", test.getKey());
			datastore.put(me);
			txn.commit();
		} catch (EntityNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(txn.isActive()){
				txn.rollback();
			}
		} 
		*/
	}
}
