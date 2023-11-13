package Models;

public class HistPeso {
	private double peso;
	private double altura;
	private String data;
	
	public HistPeso(double peso, double altura, String data) {
		super();
		this.peso = peso;
		this.altura = altura;
		this.data = data;
	}

	public double getPeso() {
		return peso;
	}

	public void setPeso(double peso) {
		this.peso = peso;
	}

	public double getAltura() {
		return altura;
	}

	public void setAltura(double altura) {
		this.altura = altura;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
	
	public double calcIMC (double peso, double altura) {
		return peso/altura*altura;
	}
}
