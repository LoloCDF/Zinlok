package zinlok.server.objetos;

import java.io.IOException;
import java.net.InetAddress;

abstract public class Objeto implements ObjetoInterfaz{
	protected String nombre = "";
	protected InetAddress dirIP;
	protected Boolean estado = false;
	
	public Objeto (String nombre, InetAddress dirIP) throws IOException{
		this.nombre=nombre;
		this.dirIP=dirIP;
		this.compruebaEstado();
	}
	public String getNombre(){
		return this.nombre;
	}
	
	public InetAddress getDireccionIP(){
		return this.dirIP;
	}
	
	public Boolean getEstado(){
		return this.estado;
	}
	
	public void compruebaEstado() throws IOException{
		this.estado=this.dirIP.isReachable(5000);
	}
	
	
}
