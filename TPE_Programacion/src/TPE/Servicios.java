package TPE;


import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Servicios {

	private static final int URGENCIA_MINIMA = 1;
    
	private static final int URGENCIA_MAXIMA = 100;
    
	
	
	
	private List<Camion> camiones;
	
	private Map<String, Paquete> paquetesPorCodigo;
	
	private List<Paquete> paquetesConAlimentos;
	
    private List<Paquete> paquetesSinAlimentos;
	
    
    private List<Paquete>[] paquetesPorUrgencia;
	
    
    /*Complegidad temporal del constructor
     * 
     * 1° Inicializacion de estructuras (new ArrayList,new Hashmap) O(1)
     * 
     * 2° Llenar las posiciones del array de baldes con un ArrayList
     *    En este caso hace  n veces new ArrayList donde n = URGENCIA_MAXIMA
     *    como el valor es una constante nos da como resultado O(1) 
     *    
     * 3° En las dos funciones se va a ejecutar n veces O(1),
     *	  donde n en este caso de cargarCamiones y cargarPaquetes
     *	  son las cantidades de camiones y de paquetes que en el peor de
     *	  los casos pueden ser infinitos,es decir no hay un limite fijo
     *	  por eso nos da como resultado que estas dos 
     *	  funciones O(p) + O(c) = O(p + c)
     */
    
    
    
	public Servicios(String pathCamiones, String pathPaquetes){
		
		this.camiones = new ArrayList<>();
		this.paquetesPorCodigo = new HashMap<>();
		
		//Servicio 2
		
		this.paquetesConAlimentos = new ArrayList<>();
		
		this.paquetesSinAlimentos = new ArrayList<>();
		
		//Servicio 3
		this.paquetesPorUrgencia = new List[URGENCIA_MAXIMA + 1];
		
		
		 for (int u = URGENCIA_MINIMA; u <= URGENCIA_MAXIMA; u++) {
	            paquetesPorUrgencia[u] = new ArrayList<>();
	        }
		 
		
		cargarCamiones(pathCamiones);
		cargarPaquetes(pathPaquetes);
		
	}
	
	
	

	private void cargarCamiones(String pathCamiones) {
		
		try (BufferedReader br = new BufferedReader(new FileReader(pathCamiones))) {
            String linea = br.readLine(); 
            while ((linea = br.readLine()) != null) {
                if (linea.isBlank()) continue;
                String[] campos = linea.split(";");
                int id = Integer.parseInt(campos[0].trim());
                String patente = campos[1].trim();
                boolean refrigerado = campos[2].trim().equals("1");
                double capacidadMaxima = Double.parseDouble(campos[3].trim());
                camiones.add(new Camion(id, patente, refrigerado, capacidadMaxima));
            }
        } catch (IOException e) {
            throw new RuntimeException("Error al leer el archivo de camiones: " + pathCamiones, e);
        }
	
	}
	
	
	  private void cargarPaquetes(String path) {
	        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
	            String linea = br.readLine(); 
	            while ((linea = br.readLine()) != null) {
	                if (linea.isBlank()) continue;
	                String[] campos = linea.split(";");
	                int id = Integer.parseInt(campos[0].trim());
	                String codigo = campos[1].trim();
	                double peso = Double.parseDouble(campos[2].trim());
	                boolean contieneAlimentos = campos[3].trim().equals("1");
	                int urgencia = Integer.parseInt(campos[4].trim());
	 
	                Paquete paquete = new Paquete(id, codigo, peso, contieneAlimentos, urgencia);
	 
	                paquetesPorCodigo.put(codigo, paquete);
	 
	                if (contieneAlimentos) {
	                    paquetesConAlimentos.add(paquete);
	                } else {
	                    paquetesSinAlimentos.add(paquete);
	                }
	                
	                
	                
	                paquetesPorUrgencia[urgencia].add(paquete);
	            }
	        } catch (IOException e) {
	            throw new RuntimeException("Error al leer el archivo de paquetes: " + path, e);
	        }
	    }
	




	public List<Camion> getCamiones() {
		return new ArrayList<>(camiones);
	}




	  public void setCamiones(List<Camion> camiones) {
		  this.camiones = camiones;
	  }




	  public Map<String, Paquete> getPaquetesPorCodigo() {
		  return new HashMap<String,Paquete>(paquetesPorCodigo);
	  }




	  public void setPaquetesPorCodigo(Map<String, Paquete> paquetesPorCodigo) {
		  this.paquetesPorCodigo = paquetesPorCodigo;
	  }

	/*
	 * En este caso la complegidad computacional es O(1) ya que al utilizar la 
	 * tabla de HASHING con tener la clave del paquete obtenemos el paquete
	 * sin necesidad de recorrer toda la estructura
	 */
	  public Paquete servicio1(String codigoPaquete) { 

		  if(paquetesPorCodigo.containsKey(codigoPaquete)) {
			  
			  Paquete p = this.paquetesPorCodigo.get(codigoPaquete);
			  return p;
			  
		  }else {
			  return null;
		  }

	  }
	
	
	/*
	 * En este caso la complegidad cumpletacional es O(n)
	 * donde n es la cantidad de paquetes que tiene
	 * la lista que va a copiar uno por uno cada elemento y lo va
	 * a devolver,a la hora de seleccionar segun el 
	 * boleano pasado por parametro, si tiene o no Alimentos
	 * 
	 */
	
	
	public List<Paquete> servicio2(boolean contieneAlimentos) {
		
		if(contieneAlimentos) {
			
			return new ArrayList<>(paquetesConAlimentos);
		}else {
			
			return new ArrayList<>(paquetesSinAlimentos);
		}
			
	
	}
	/*
	 * En esta caso la complegidad computacional es O(100)
	 * donde este valor son la cantidad de veces que tengo que 
	 * acceder a memoria en el peor de los casos, ademas de O(n)
	 * donde n es la cantidad de paquetes a cargar en la solucion
	 * en el rango de urgencia pasado por parametro.
	 * Entonces seria O(100)+O(n) = O(n)
	 */
	
	public List<Paquete> servicio3(int urgenciaMinima, int urgenciaMaxima) {
		
		List<Paquete> solucion = new ArrayList<>();
		
		//Controlo que los valores pasados por parametros esten en rango para que no me rompan la solucion
		int minimo = Math.max(urgenciaMinima, URGENCIA_MINIMA);
        int maximo = Math.min(urgenciaMaxima, URGENCIA_MAXIMA);
        
        for (int u = minimo; u <= maximo; u++) {
            solucion.addAll(paquetesPorUrgencia[u]);
        }
        
        return solucion;
        
	}
	
	
	public List<Paquete> getPaquetes() {
	    List<Paquete> todos = new ArrayList<>(paquetesConAlimentos);
	    todos.addAll(paquetesSinAlimentos);
	    return todos;
	}
	
	}
