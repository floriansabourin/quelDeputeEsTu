package depute;


import java.util.List;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(identityType=IdentityType.APPLICATION)
public class Loi {
	
	@PrimaryKey
	@Persistent(valueStrategy=IdGeneratorStrategy.IDENTITY)
	Long id;

	@Persistent
	String titre;
	@Persistent
	String date;
	@Persistent
	int nb_votes;
	@Persistent
	int nb_nspp;
	@Persistent
	List<String> votes_p;
	@Persistent
	List<String> votes_c;
	@Persistent
	List<String> votes_a;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitre() {
		return titre;
	}
	public void setTitre(String titre) {
		this.titre = titre;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public int getNb_votes() {
		return nb_votes;
	}
	public void setNb_votes(int nb) {
		this.nb_votes = nb;
	}
	public int getNb_nspp() {
		return nb_nspp;
	}
	public void setNb_nspp(int nb) {
		this.nb_nspp = nb;
	}
	public List<String> getVotes_p() {
		return votes_p;
	}
	public void setVotes_p(List<String> votes_p) {
		this.votes_p = votes_p;
	}
	public List<String> getVotes_c() {
		return votes_c;
	}
	public void setVotes_c(List<String> votes_c) {
		this.votes_c = votes_c;
	}
	public List<String> getVotes_a() {
		return votes_a;
	}
	public void setVotes_a(List<String> votes_a) {
		this.votes_a = votes_a;
	}

}
