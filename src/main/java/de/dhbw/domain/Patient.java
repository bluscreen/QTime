package de.dhbw.domain;

public class Patient {

	private String vorname;
	private String nachname;
	private String telNr;
	private String email;
	private double rating;

	public String getVorname() {
		return vorname;
	}

	public void setVorname(String vorname) {
		this.vorname = vorname;
	}

	public String getNachname() {
		return nachname;
	}

	public void setNachname(String nachname) {
		this.nachname = nachname;
	}

	public String getTelNr() {
		return telNr;
	}

	public void setTelNr(String telNr) {
		this.telNr = telNr;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public String toString() {
		return this.vorname + " " + this.nachname;
	}

}
