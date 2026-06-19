package TPE;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Solucion {
	
    private Map<Camion, List<Paquete>> asignacion;
    private double pesoNoAsignado;
    private int estadosGenerados; 
    private int candidatosConsiderados;

    
    public Solucion() {
        this.asignacion = new HashMap<>();
        this.pesoNoAsignado = 0;
        this.estadosGenerados = 0;
        this.candidatosConsiderados = 0;
    }


    public void incrementarEstados() {
    	
    	this.estadosGenerados++;
    }
    
    public void incrementarCandidatos() {
    	
    	this.candidatosConsiderados++;
    }
    
    
	public double getPesoNoAsignado() {
		return pesoNoAsignado;
	}


	public void setPesoNoAsignado(double pesoNoAsignado) {
		this.pesoNoAsignado = pesoNoAsignado;
	}


	public int getEstadosGenerados() {
		return estadosGenerados;
	}


	public void setEstadosGenerados(int estadosGenerados) {
		this.estadosGenerados = estadosGenerados;
	}
	
	public void setAsignacion(Map<Camion, List<Paquete>>asignacion) {
		this.asignacion = asignacion;
	}


	public int getCandidatosConsiderados() {
		return candidatosConsiderados;
	}


	public void setCandidatosConsiderados(int candidatosConsiderados) {
		this.candidatosConsiderados = candidatosConsiderados;
	}

	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();

	    sb.append("Solucion obtenida:\n");
	    for (Map.Entry<Camion, List<Paquete>> entry : asignacion.entrySet()) {
	        sb.append("  Camion ").append(entry.getKey().getPatente())
	          .append(" (cap: ").append(entry.getKey().getCapacidadMaxima()).append("kg): ");
	        if (entry.getValue().isEmpty()) {
	            sb.append("sin paquetes asignados");
	        } else {
	            for (Paquete p : entry.getValue()) {
	                sb.append(p.getCodigo())
	                  .append("(").append(p.getPeso()).append("kg) ");
	            }
	        }
	        sb.append("\n");
	    }

	    sb.append("Peso no asignado: ").append(pesoNoAsignado).append(" kg\n");

	    if (estadosGenerados > 0) {
	        sb.append("Estados generados: ").append(estadosGenerados);
	    }
	    if (candidatosConsiderados > 0) {
	        sb.append("Candidatos considerados: ").append(candidatosConsiderados);
	    }

	    return sb.toString();
	}

}
