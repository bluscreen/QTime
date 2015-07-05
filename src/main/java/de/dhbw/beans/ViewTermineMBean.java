package de.dhbw.beans;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import de.dhbw.domain.AlleStati;
import de.dhbw.domain.Patient;
import de.dhbw.domain.Status;
import de.dhbw.domain.Termin;

@ManagedBean
@SessionScoped
public class ViewTermineMBean {
	private List<Termin> termine = new ArrayList<Termin>();
	private Date today = new Date(115, 5, 15, 8, 15);
	private List<Status> stati = new ArrayList<Status>();

	public ViewTermineMBean() {
	}

	@PostConstruct
	public void populateData() {
		popuplateStatiList();
		populateTermineList();
		calcTerminIst();
	}

	public void popuplateStatiList() {
		for (AlleStati stat : AlleStati.values()) {
			Status pojoStat = new Status();
			pojoStat.setId(stat.statId());
			pojoStat.setColor(stat.color());
			pojoStat.setLabel(stat.label());
			stati.add(pojoStat);
		}
	}

	public void calcTerminIst() {
		for (Termin t : termine) {
			int idx = termine.indexOf(t) - 1;
			Date terminist;
			long after = 0;
			if (idx < 0) {
				terminist = new Date(t.getTerminPlan().getTime());
				after = terminist.getTime() + t.getDelay() * 60000;

			} else {
				Termin tvorgaenger = termine.get(idx);
				terminist = new Date(tvorgaenger.getTerminIst().getTime());
				if (tvorgaenger.getStatus().getId() == AlleStati.CANCELLED
						.statId()) {
					after = terminist.getTime();
				} else {
					if (t.getStatus().getId() == AlleStati.EMERGENCY.statId()) {
						after = t.getTerminPlan().getTime();
						long newvorg = (after - tvorgaenger.getTerminIst()
								.getTime()) / 60000;
						if (tvorgaenger.getDauer() > newvorg) {
							tvorgaenger
									.setDelay(t.getDauer()
											+ ((int) ((tvorgaenger
													.getTerminIst().getTime() - tvorgaenger
													.getTerminPlan().getTime()) / 60000)));
						}
					} else {
						if (tvorgaenger.getStatus().getId() == AlleStati.EMERGENCY
								.statId()) {
							if (idx - 1 > 0) {
								Termin tzweivorher = termine.get(idx - 1);
								long vorterminVergangeneMinuten = (tvorgaenger
										.getTerminIst().getTime() - tzweivorher
										.getTerminIst().getTime());
								long nochZuWarten = (tzweivorher.getDauer() * 60000)
										- vorterminVergangeneMinuten;

								if (nochZuWarten > 0) {
									after = terminist.getTime()
											+ tvorgaenger.getDauer() * 60000
											+ t.getDelay() * 60000
											+ nochZuWarten;
									t.setDelay((int) ((after - t
											.getTerminPlan().getTime()) / 60000));
								}
							}
						} else {
							after = terminist.getTime()
									+ tvorgaenger.getDauer() * 60000
									+ t.getDelay() * 60000;
							t.setDelay((int) ((after - t.getTerminPlan()
									.getTime()) / 60000));
						}
					}
				}
			}
			System.out.println(t.getTerminId() + " " + terminist + t.getDauer()
					+ t.getDelay());
			terminist.setTime(after);
			System.out.println(t.getTerminId() + " " + terminist);
			// if (t.getStatus().getId() != AlleStati.CANCELLED.statId()) {
			t.setTerminIst(terminist);
			// }
		}
	}

	public void populateTermineList() {
		Termin ta = new Termin();
		ta.setTerminId("0");
		Patient pa = new Patient();
		pa.setEmail("a@1.1");
		pa.setRating(4.5);
		pa.setTelNr("3423");
		pa.setVorname("Ferdi");
		pa.setNachname("Fertig");
		ta.setPatient(pa);
		ta.setPrio(1);
		ta.setTerminPlan(new Date(115, 5, 15, 8, 0));
		ta.setStatus(stati.get(AlleStati.FINISHED.ordinal()));

		/**
		 * doppelte verkettung einführen...: terminIst = vorgänger termin ist +
		 * dauer + delay
		 */
		ta.setDauer(10);

		Termin t0 = new Termin();
		t0.setTerminId("1");
		Patient p0 = new Patient();
		p0.setEmail("0@1.1");
		p0.setRating(5.0);
		p0.setTelNr("4234");
		p0.setVorname("Finn");
		p0.setNachname("First");
		t0.setPatient(p0);
		t0.setTerminPlan(new Date(115, 5, 15, 8, 10));
		t0.setPrio(2);
		t0.setStatus(stati.get(AlleStati.IN_THERAPY_NO_DELAY.ordinal()));
		t0.setDauer(20);

		Termin t1 = new Termin();
		t1.setTerminId("2");
		Patient p1 = new Patient();
		p1.setEmail("1@1.1");
		p1.setRating(5.0);
		p1.setTelNr("1234");
		p1.setVorname("Günter");
		p1.setNachname("Grass");
		t1.setPatient(p1);
		t1.setPrio(5);
		t1.setTerminPlan(new Date(115, 5, 15, 8, 30));
		t1.setStatus(stati.get(AlleStati.ONTIME_NOAPP.ordinal()));
		t1.setDauer(30);

		Termin t2 = new Termin();
		t2.setTerminId("3");
		Patient p2 = new Patient();
		p2.setEmail("2@1.1");
		p2.setRating(4.0);
		p2.setTelNr("2345");
		p2.setVorname("Peter");
		p2.setNachname("Pünktlich");
		t2.setPatient(p2);
		t2.setPrio(5);
		t2.setTerminPlan(new Date(115, 5, 15, 9, 0));
		t2.setStatus(stati.get(AlleStati.ONTIME_CONFIRMED.ordinal()));
		t2.setDauer(30);

		Termin t3 = new Termin();
		t3.setTerminId("4");
		Patient p3 = new Patient();
		p3.setEmail("3@1.1");
		p3.setRating(2.0);
		p3.setTelNr("4345");
		p3.setVorname("Dörte");
		p3.setNachname("Dösel");
		t3.setPatient(p3);
		t3.setPrio(3);
		t3.setTerminPlan(new Date(115, 5, 15, 9, 30));
		t3.setStatus(stati.get(AlleStati.DELAYED.ordinal()));
		t3.setDelay(30);
		t3.setDauer(30);

		Termin t4 = new Termin();
		t4.setTerminId("5");
		Patient p4 = new Patient();
		p4.setEmail("4@1.1");
		p4.setRating(1.0);
		p4.setTelNr("41145");
		p4.setVorname("Abel");
		p4.setNachname("Absager");
		t4.setPrio(4);
		t4.setPatient(p4);
		t4.setTerminPlan(new Date(115, 5, 15, 10, 0));
		t4.setStatus(stati.get(AlleStati.CANCELLED.ordinal()));
		t4.setDauer(15);

		Termin t5 = new Termin();
		t5.setTerminId("6");
		Patient p5 = new Patient();
		p5.setEmail("5@1.1");
		p5.setRating(3.0);
		p5.setTelNr("3423");
		p5.setVorname("Ulla");
		p5.setNachname("Unklar");
		t5.setPatient(p5);
		t5.setPrio(4);
		t5.setTerminPlan(new Date(115, 5, 15, 10, 15));
		t5.setStatus(stati.get(AlleStati.ONTIME_UNCONFIRMED.ordinal()));
		t5.setDauer(30);

		Termin t6 = new Termin();
		t6.setTerminId("N");
		Patient p6 = new Patient();
		p6.setEmail("6@1.1");
		p6.setRating(3.5);
		p6.setTelNr("23423");
		p6.setVorname("Nora");
		p6.setNachname("Notfall");
		t6.setPatient(p6);
		t6.setPrio(3);
		t6.setTerminPlan(new Date(115, 5, 15, 10, 45));
		t6.setStatus(stati.get(AlleStati.EMERGENCY.ordinal()));
		t6.setDauer(60);

		Termin t7 = new Termin();
		t7.setTerminId("7");
		Patient p7 = new Patient();
		p7.setEmail("pansen@beef.traeger");
		p7.setRating(3.5);
		p7.setTelNr("23423");
		p7.setVorname("Axel");
		p7.setNachname("Ebner");
		t7.setPatient(p7);
		t7.setPrio(3);
		t7.setTerminPlan(new Date(115, 5, 15, 11, 30));
		t7.setStatus(stati.get(AlleStati.IDLE.ordinal()));
		t7.setDauer(10);

		Termin t8 = new Termin();
		t8.setTerminId("8");
		Patient p8 = new Patient();
		p8.setEmail("Ma@ma.ya");
		p8.setRating(3.5);
		p8.setTelNr("23423");
		p8.setVorname("Maya");
		p8.setNachname("Mayaaaa");
		t8.setPatient(p8);
		t8.setPrio(3);
		t8.setTerminPlan(new Date(115, 5, 15, 12, 00));
		t8.setStatus(stati.get(AlleStati.ONTIME_UNCONFIRMED.ordinal()));
		t8.setDauer(10);

		Termin t9 = new Termin();
		t9.setTerminId("9");
		Patient p9 = new Patient();
		p9.setEmail("asdasdasd");
		p9.setRating(3.5);
		p9.setTelNr("23423");
		p9.setVorname("Sandy");
		p9.setNachname("Mandy");
		t9.setPatient(p9);
		t9.setPrio(3);
		t9.setTerminPlan(new Date(115, 5, 15, 12, 10));
		t9.setStatus(stati.get(AlleStati.DELAYED.ordinal()));
		t9.setDelay(10);
		t9.setDauer(20);

		Termin tx = new Termin();
		tx.setTerminId("10");
		Patient px = new Patient();
		px.setEmail("asdasdasd");
		px.setRating(3.5);
		px.setTelNr("23423");
		px.setVorname("Rüdiger");
		px.setNachname("Rückblick");
		tx.setPatient(px);
		tx.setPrio(3);
		tx.setTerminPlan(new Date(115, 5, 15, 12, 30));
		tx.setStatus(stati.get(AlleStati.ONTIME_NOAPP.ordinal()));
		tx.setDauer(40);

		termine.add(ta);
		termine.add(t0);
		termine.add(t1);
		termine.add(t2);
		termine.add(t3);
		termine.add(t4);
		termine.add(t5);
		termine.add(t6);
		termine.add(t7);
		termine.add(t8);
		termine.add(t9);
		termine.add(tx);
	}

	public List<Termin> getTermine() {
		return termine;
	}

	public void setTermine(List<Termin> termine) {
		this.termine = termine;
	}

	public Date getToday() {
		return today;
	}

	public List<Status> getStati() {
		return stati;
	}

	public void setStati(List<Status> stati) {
		this.stati = stati;
	}

	public void setToday(Date today) {
		this.today = today;
	}

}
