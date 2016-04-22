package zinlok.server.cliente;

public class leerapp {
	private static String cadena1="open(Bombilla)";
	private static String cadena2="open(Puerta)";
	private static String cadena3="open(Motor)";
	String cad;
	int accion;
	int elemento;

	public leerapp (String cadenaleida, int accion, int elemento){
		this.cad= cadenaleida;
		this.accion=accion;
		this.elemento=elemento;
	}
	
		public static void main (String[] args){
			leerapp orden = new leerapp(args[0], Integer.parseInt(args[1]), Integer.parseInt(args[2]));

			
			if (cadena1.equals(orden.cad)){
				System.out.println("orden1");
			}
			if (cadena2.equals(orden.cad)){
				System.out.println("orden2");
			}
			if (cadena3.equals(orden.cad)){
				System.out.println("orden3");
			}
		}


}
