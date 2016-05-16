package depute;


import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SAXXMLHandlerDepute extends DefaultHandler {

	Map<String,String> org;
    List<Depute> deputes;
    Depute depute;
    String texte, idOrg, libOrg;
    boolean organe, acteur, mandat, parti, uid;

    public SAXXMLHandlerDepute() {
    	texte = "";
    	idOrg = "";
    	libOrg = "";
    	organe = false;
    	acteur = false;
    	parti = false;
    	uid = false;
    	mandat = false;
        deputes = new ArrayList<Depute>();
        org = new HashMap<String, String>();
    }

    public List<Depute> getListeDeputes() {
    	/*
    	for(int i=0; i<deputes.size();i++){
			System.out.println("----------------------------------------------");
			System.out.println("ref: "+deputes.get(i).getRef());
			System.out.println("nom: "+deputes.get(i).getNom());
			System.out.println("prenom: "+deputes.get(i).getPrenom());
			System.out.println("parti: "+deputes.get(i).getParti());
			System.out.println("----------------------------------------------");
		}
		*/
        return deputes;
    }

    public void startElement(String uri, String localName, String qName,
                             Attributes att) throws SAXException {

    	if (qName.equalsIgnoreCase("organes")) {
        	organe = true;
        } else if (qName.equalsIgnoreCase("acteurs")) {
        	acteur = true;
        } else if (qName.equalsIgnoreCase("acteur")) {
        	depute = new Depute();
        } else if (qName.equalsIgnoreCase("mandat")) {
            mandat = true;
        } 
    	
    	if (acteur && qName.equalsIgnoreCase("uid")) {
            if(att.getValue("xsi:type") != null && att.getValue("xsi:type").equals("IdActeur_type")){
            	uid = true;
            }
        }	
    }

    public void characters(char[] ch, int start, int length)
            throws SAXException {
    	texte = new String(ch, start, length);
    }

    public void endElement(String uri, String localName, String qName)
            throws SAXException { 	
    	
    	if(uid){
    		depute.setRef(texte);
    		uid = false;
    	}
    	
    	if(organe){
        	if (qName.equalsIgnoreCase("codeType")){
	    		if(texte.equals("PARPOL") || texte.equals("GP")){
	    			parti = true;
	    		}
        	} else if(qName.equalsIgnoreCase("uid")){        		
    			idOrg = texte; 
        	} else if(qName.equalsIgnoreCase("libelle")){
    			libOrg = texte; 
        	}
		}
    	
    	if (qName.equalsIgnoreCase("organes")) {
        	organe = false;
        } else if (qName.equalsIgnoreCase("acteurs")) {
        	acteur = false;
        } else if (qName.equalsIgnoreCase("organe")) {
        	if(parti){
        		org.put(idOrg, libOrg);
        	}
    		parti = false;
        } else if (qName.equalsIgnoreCase("acteur")) {
            deputes.add(depute);
        }
    	
    	
    	if(acteur){ 
    		if(qName.equalsIgnoreCase("mandat")){
        		mandat = false;
        		parti = false;
        	} else if(qName.equalsIgnoreCase("nom")){
        		depute.setNom(texte);
        	} else if(qName.equalsIgnoreCase("prenom")){
        		depute.setPrenom(texte);
        	}  		
    	} 
    	
    	if(mandat){
    		if (qName.equalsIgnoreCase("typeOrgane")){
	    		if(texte.equals("PARPOL") || texte.equals("GP")){
	    			parti = true;
	    		}
    		} else if(qName.equalsIgnoreCase("organeRef")){
    			if(org.get(texte) != null){
    				depute.setParti(org.get(texte));
    			}    			
        	} 
        } 
    	
    }
}
