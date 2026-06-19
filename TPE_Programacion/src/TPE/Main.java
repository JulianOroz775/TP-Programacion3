package TPE;

public class Main {


	
public static void main(String[] args) {
	
	Servicios servicios = new Servicios("Camiones.csv", "Paquetes.csv");
	 
	
	
	System.out.println("--- Listado completo de camiones ---");
	for (Camion c : servicios.getCamiones()) {
	    System.out.println(c);
	}

	System.out.println("--- Listado completo de paquetes ---");
	for (String s : servicios.getPaquetesPorCodigo().keySet()) {
	    System.out.println(s);
	}
	
	Paquete p = servicios.servicio1("P001");
	
	System.out.println(p.toString());
	
	
	System.out.println("Listado de paquetes por urgencia");
	for (Paquete pa : servicios.servicio3(0, 10)) {
	    System.out.println(pa);
	}
	
	Greedy g = new Greedy();
	
	System.out.println(g.greedy(servicios.getPaquetes(),servicios.getCamiones()).toString());
	
	Backtracking b = new Backtracking();
	
	System.out.println(b.backtracking(servicios.getPaquetes(), servicios.getCamiones()).toString());
}

}
