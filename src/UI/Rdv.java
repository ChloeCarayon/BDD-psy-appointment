package UI;

import java.sql.Date;
import java.sql.Time;

public class Rdv {
	private int id; 
	private Date date;
	private String heure, payement;
	private float prix;
	private int client1, client2, client3;
	public Rdv(int id, Date date, String heure, float prix, String payement, int client1, int client2, int client3) {
		super();
		this.id = id;
		this.date = date;
		this.heure = heure;
		this.prix = prix;
		this.payement = payement;
		this.client1 = client1;
		this.client2 = client2;
		this.client3 = client3;
	}
		
	public int getId() {
		return id;
	}

	public Date getDate() {
		return date;
	}


	public void setDate(Date date) {
		this.date = date;
	}


	public String getHeure() {
		return heure;
	}


	public void setHeure(String heure) {
		this.heure = heure;
	}


	public float getPrix() {
		return prix;
	}


	public void setPrix(float prix) {
		this.prix = prix;
	}

	public String getPayement() {
		return payement;
	}

	public void setPayement(String payement) {
		this.payement = payement;
	}

	public  int getClient1() {
		return client1;
	}

	public  int getClient2() {
		return client2; 
	}

	public int getClient3() {
		return client3;
	}

	  

}
