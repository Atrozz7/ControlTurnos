package es.studium.ControlTurnos;

public class Ticket {

	String numero;
	String fecha;
	String hora;
	
	public Ticket() {
		numero="";
		fecha="";
		hora="";
	}
	
	public Ticket(String numero, String fecha,String hora) {
		this.numero=numero;
		this.fecha=fecha;
		this.hora=hora;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}
	
	
}
