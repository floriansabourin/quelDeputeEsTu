package depute;


import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import java.util.ArrayList;
import java.util.List;


public class SAXXMLHandlerLoi extends DefaultHandler {

    List<Loi> lois;
    Loi loi;
    String texte;
    List<String> votes_p;
	List<String> votes_c;
	List<String> votes_a;
	boolean decompte, pour, contre, abs;

    public SAXXMLHandlerLoi() {
    	texte = "";
    	decompte = false;
    	pour = false;
    	contre = false;
    	abs = false;
        lois = new ArrayList<Loi>();
    }

    public List<Loi> getListeLois() {
    	/*
    	for(int i=0; i<lois.size();i++){
			System.out.println("----------------------------------------------");
			System.out.println("Titre: "+lois.get(i).getTitre());
			System.out.println("Date: "+lois.get(i).getDate());
			System.out.println("Votants: "+lois.get(i).getNb_votes());
			System.out.println("Non votants: "+lois.get(i).getNb_nspp());
			List<String> p = lois.get(i).getVotes_p();
			if(p != null){
				System.out.print("Pour: ");
				for(int j = 0; j<p.size();j++){
					System.out.print(p.get(j)+"|");
				}
				System.out.println();
			}
			List<String> c = lois.get(i).getVotes_c();
			if(c != null){
				System.out.print("Contre: ");
				for(int j = 0; j<p.size();j++){
					System.out.print(p.get(j)+"|");
				}
				System.out.println();
			}
			List<String> a = lois.get(i).getVotes_a();
			if(a != null){
				System.out.print("Abs: ");
				for(int j = 0; j<p.size();j++){
					System.out.print(p.get(j)+"|");
				}
				System.out.println();
			}
			System.out.println("----------------------------------------------");
		}
		*/
        return lois;
    }

    public void startElement(String uri, String localName, String qName,
                             Attributes att) throws SAXException {
        if (qName.equalsIgnoreCase("scrutin")) {
            loi = new Loi();
        } else if (qName.equalsIgnoreCase("decompte")) {
            decompte = true;
        } else if (qName.equalsIgnoreCase("groupes")) {
        	votes_p = new ArrayList<String>();
        	votes_c = new ArrayList<String>();
        	votes_a = new ArrayList<String>();
        } else if (qName.equalsIgnoreCase("pours")) {
        	pour = true;
        } else if (qName.equalsIgnoreCase("contres")) {
        	contre = true;
        } else if (qName.equalsIgnoreCase("abstentions")) {
        	abs = true;
        } 
    }

    public void characters(char[] ch, int start, int length)
            throws SAXException {
    	texte = new String(ch, start, length);
    }

    public void endElement(String uri, String localName, String qName)
            throws SAXException {
        if (qName.equalsIgnoreCase("scrutin")) {
            lois.add(loi);
        } if (qName.equalsIgnoreCase("titre")) {
        	loi.setTitre(texte);
        } else if (qName.equalsIgnoreCase("dateScrutin")) {
            loi.setDate(texte);
        } else if (qName.equalsIgnoreCase("nombreVotants")) {
            loi.setNb_votes(Integer.parseInt(texte));
        } else if (qName.equalsIgnoreCase("decompte")) {
        	decompte = false;
        } else if (qName.equalsIgnoreCase("nonVotant")) {
        	if(decompte){
                loi.setNb_nspp(Integer.parseInt(texte));
        	}
        } else if (qName.equalsIgnoreCase("pours")) {
        	pour = false;
        } else if (qName.equalsIgnoreCase("contres")) {
        	contre = false;
        } else if (qName.equalsIgnoreCase("abstentions")) {
        	abs = false;
        } else if (qName.equalsIgnoreCase("acteurRef")) {
        	if(pour){
        		votes_p.add(texte);
        	} else if(contre){
        		votes_c.add(texte);
        	} else if(abs){
        		votes_a.add(texte);
        	}
        }  else if (qName.equalsIgnoreCase("groupes")) {        
        	loi.setVotes_p(votes_p);
        	loi.setVotes_c(votes_c);
        	loi.setVotes_a(votes_a);
        }
    }
}
