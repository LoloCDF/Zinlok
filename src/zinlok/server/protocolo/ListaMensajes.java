package zinlok.server.protocolo;

public class ListaMensajes implements ListaMensajesInterfaz {
	
	private String[] comandos = {"request","send","ok","failure"};
	private String[] parametros = {"0","1"};
	
	public ListaMensajes(){
	}
	
	public String[] comandosValidos(){
		return this.comandos;
	}
	
	public String[] parametrosValidos(){
		return this.parametros;
	}
}
