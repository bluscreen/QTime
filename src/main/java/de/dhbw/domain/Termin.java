package de.dhbw.domain;

import java.util.Date;

public class Termin {
	private String terminId;
	private Date terminPlan;
	private Date terminIst;

	private int dauer;
	private Patient patient;
	private Status status;
	private int delay;
	private int prio;

	// private String label;
	// private String color;

	public String getTerminId() {
		return terminId;
	}

	public void setTerminId(String terminId) {
		this.terminId = terminId;
	}

	public Date getTerminPlan() {
		return terminPlan;
	}

	public void setTerminPlan(Date terminPlan) {
		this.terminPlan = terminPlan;
	}

	public Date getTerminIst() {
		return terminIst;
	}

	public void setTerminIst(Date terminIst) {
		this.terminIst = terminIst;
	}

	public int getDauer() {
		return dauer;
	}

	public void setDauer(int dauer) {
		this.dauer = dauer;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public int getDelay() {
		return delay;
	}

	public void setDelay(int delay) {
		this.delay = delay;
	}

	public String getLabel() {
		return AlleStati.getLabelByStatId(this.status.getId());

	}

	public String getColor() {
		return AlleStati.getColorByStatId(this.status.getId());
	}

	public int getPrio() {
		return prio;
	}

	public void setPrio(int prio) {
		this.prio = prio;
	}

}
