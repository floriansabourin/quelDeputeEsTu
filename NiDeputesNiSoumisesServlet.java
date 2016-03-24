package depute;

import java.io.IOException;

import javax.servlet.http.*;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.*;
import com.google.apphosting.api.DatastorePb.Transaction;

import java.util.ArrayList;
import java.util.logging.Logger;


@SuppressWarnings("serial")
public class NiDeputesNiSoumisesServlet extends HttpServlet {
	private static final Logger log = Logger.getLogger(NiDeputesNiSoumisesServlet.class.getName());
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)	throws IOException {
		resp.setContentType("text/plain");
		
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		
		Entity lois = new Entity("Lois");
		lois.setProperty("id","1");
		lois.setProperty("nom","loi 1");
		lois.setProperty("annee","2005");
		ArrayList<String> tab = new ArrayList();
		tab.add("Depute1");
		tab.add("Depute2");
		lois.setProperty("votes_pour",tab);
		ArrayList<String> tab2 = new ArrayList();
		tab2.add("Depute3");
		tab2.add("Depute4");
		lois.setProperty("votes_contre",tab2);
		datastore.put(lois);
		
		Entity lois2 = new Entity("Lois");
		lois2.setProperty("id","2");
		lois2.setProperty("nom","loi 1");
		lois2.setProperty("annee","2015");
		lois2.setProperty("votes_pour",tab2);
		lois2.setProperty("votes_contre",tab);
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
		
		Entity me = new Entity ("TEST_fils", "C'est le fils", lois.getKey());
		datastore.put(me);
				
		
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
	}
}
