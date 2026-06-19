package TPE;

public class Camion {


	private int id;
	private String patente;
	private boolean refrigerado;
	private double capacidadMaxima;


	public Camion(int id, String patente, boolean refrigerado, double capacidadMaxima) {
		this.id = id;
		this.patente = patente;
		this.refrigerado = refrigerado;
		this.capacidadMaxima = capacidadMaxima;
	}


	public int getId() {
		return id;
	}


	public String getPatente() {
		return patente;
	}


	public boolean isRefrigerado() {
		return refrigerado;
	}


	public double getCapacidadMaxima() {
		return capacidadMaxima;
	}


	@Override
	public String toString() {
		return "Camion{id=" + id +
				", patente='" + patente + '\'' +
				", refrigerado=" + refrigerado +
				", capacidadMaxima=" + capacidadMaxima +
				'}';
	}
}

