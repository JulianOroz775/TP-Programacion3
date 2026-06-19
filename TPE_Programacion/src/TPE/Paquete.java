package TPE;

public class Paquete {

	private int id;
	private String codigo;
	private double peso;
	private boolean contieneAlimentos;
	private int urgencia;

	public Paquete(int id, String codigo, double peso, boolean contieneAlimentos, int urgencia) {
		this.id = id;
		this.codigo = codigo;
		this.peso = peso;
		this.contieneAlimentos = contieneAlimentos;
		this.urgencia = urgencia;
	}


	public int getId() {
		return id;
	}


	public String getCodigo() {
		return codigo;
	}


	public double getPeso() {
		return peso;
	}


	public boolean isContieneAlimentos() {
		return contieneAlimentos;
	}


	public int getUrgencia() {
		return urgencia;
	}


	@Override
	public String toString() {
		return "Paquete{id=" + id +
				", codigo='" + codigo + '\'' +
				", peso=" + peso +
				", contieneAlimentos=" + contieneAlimentos +
				", urgencia=" + urgencia +
				'}';
	}

}
