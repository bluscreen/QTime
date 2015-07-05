package de.dhbw.domain;

public enum AlleStati {
	ONTIME_NOAPP(1, "Chartreuse", "Pünktlich, keine App"), ONTIME_UNCONFIRMED(
			2, "DarkSeaGreen", "Pünktlich, nicht bestätigt"), ONTIME_CONFIRMED(
			3, "ForestGreen", "Pünktlich, bestätigt"), DELAYED(4, "Gold",
			"Verspätet"), CANCELLED(5, "LightSalmon", "Abgesagt"), IN_THERAPY_NO_DELAY(
			6, "LightSkyBlue", "In Behandlung"), IN_THERAPY_DELAY(7,
			"MediumOrchid", "In Behandlung, überzogen"), FINISHED(8, "Peru",
			"Behandlung abgeschlossen"), IDLE(9, "Black", "Wartet"), EMERGENCY(
			10, "Red", "Notfall");

	private final int statId;
	private final String color;
	private final String label;

	AlleStati(int statId, String color, String label) {
		this.statId = statId;
		this.color = color;
		this.label = label;
	}

	public int statId() {
		return statId;
	}

	public String color() {
		return color;
	}

	public String label() {
		return label;
	}

	public static String getLabelByStatId(int statId) {
		for (AlleStati e : AlleStati.values()) {
			if (statId == e.statId())
				return e.label();
		}
		return null;
	}

	public static String getColorByStatId(int statId) {
		for (AlleStati e : AlleStati.values()) {
			if (statId == e.statId())
				return e.color();
		}
		return null;
	}

}
