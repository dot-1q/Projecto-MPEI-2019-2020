
public class Localidade {
	
	private String nome;
	private Temperaturas temperaturas;
	
	
	public Localidade(String nome) {
		this.nome = nome;
		this.temperaturas = new Temperaturas();
		
	}
	
	public Temperaturas getTemp() {
		return temperaturas;
	}
	
	public String getName() {
		return nome;
	}
}
