package zinlok.server.cliente;

public class CompruebaNumClientes extends Thread implements CompruebaNumClientesInterfaz{
	// Parámetros para comprobar
	private int numClientesSer = 0;
	private int numClientes = 0;
	SirveCliente servidor = null;
	
	public CompruebaNumClientes (SirveCliente servidor){
		this.servidor = servidor;
	}
	
	public void run (){
		while(true){	
			this.numClientesSer = this.servidor.obtieneNumClientes();
			this.numClientes = this.servidor.obtenerLista().size();
			
			if (this.numClientesSer != this.numClientes){
				servidor.decrementaLista();
			}
		}			
	}
}
