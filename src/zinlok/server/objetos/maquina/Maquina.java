package zinlok.server.objetos.maquina;

import java.io.IOException;
import java.net.InetAddress;

import zinlok.server.objetos.Objeto;

public class Maquina extends Objeto implements MaquinaInterfaz {
	private Boolean candado = false;
	
	public Maquina (String nombre, InetAddress dirIP) throws IOException{
		this.nombre=nombre;
		this.dirIP=dirIP;
		this.compruebaEstado();
	}
	
	public void cerrarCandado(){
		this.candado=true;
	}
	
	public void abreCandado(){
		this.candado=false;
	}
	
	public Boolean getCandado(){
		return this.candado;
	}
}
