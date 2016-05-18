package depute;


import java.io.IOException;
import java.io.InputStream;

import javax.jdo.PersistenceManager;
import javax.servlet.http.*;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Transaction;

import java.util.ConcurrentModificationException;
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
		InputStream stream = getServletContext().getResourceAsStream("/res/Scrutins_XIV.xml");
		lois = SAXXMLParserLoi.parse(stream);	
		
		log.info("Scrutins_XIV.xml ok : creation des lois");
		
		InputStream stream2 = getServletContext().getResourceAsStream("/res/AMO10_deputes_actifs_mandats_actifs_organes_XIV.xml");
		deputes = SAXXMLParserDepute.parse(stream2);
		
		log.info("AMO10_deputes_actifs_mandats_actifs_organes_XIV.xml ok : creation des deputes");
		 
		
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();	
		for(int i=0; i<deputes.size();i++){
			Transaction txn = datastore.beginTransaction();	
			try {	
				Entity dep = new Entity("Depute");
				dep.setProperty("ref", deputes.get(i).getRef());
				dep.setProperty("nom", deputes.get(i).getNom());
				dep.setProperty("prenom", deputes.get(i).getPrenom());
				dep.setProperty("parti", deputes.get(i).getParti());
				datastore.put(txn,dep);
				txn.commit();					 
			} catch (ConcurrentModificationException e) {
				throw e;			
			}
			finally {
				 if (txn.isActive()){
					 txn.rollback();
				 }
			}
		}
		for(int i=0; i<lois.size();i++){
			Transaction txn2 = datastore.beginTransaction();	
			try {	
				Entity loi = new Entity("Loi");
				loi.setProperty("titre", lois.get(i).getTitre());
				loi.setProperty("date", lois.get(i).getDate());
				loi.setProperty("nb_votes", lois.get(i).getNb_votes());
				loi.setProperty("nb_nspp", lois.get(i).getNb_nspp());
				loi.setProperty("votes_p", lois.get(i).getVotes_p());
				loi.setProperty("votes_c", lois.get(i).getVotes_c());
				loi.setProperty("votes_a", lois.get(i).getVotes_a());
				datastore.put(txn2,loi);
				txn2.commit();					 
			} catch (ConcurrentModificationException e) {
				throw e;			
			}
			finally {
				 if (txn2.isActive()){
					 txn2.rollback();
				 }
			}
		}
		
		log.info("Donnees inserees dans le datastore");
	}
}
