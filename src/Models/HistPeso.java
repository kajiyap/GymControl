package Models;

public class HistPeso {
	private int id;
	private int idAluno;
	private double peso;
	private String dataReg;
	
	public HistPeso(double peso, String data, int idAluno) {
		this.idAluno = idAluno;
		this.peso = peso;
		this.dataReg = data;
	}
	
	public HistPeso() {
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdAluno() {
		return idAluno;
	}

	public void setIdAluno(int idAluno) {
		this.idAluno = idAluno;
	}

	public double getPeso() {
		return peso;
	}

	public void setPeso(double peso) {
		this.peso = peso;
	}

	public String getData() {
		return dataReg;
	}

	public void setData(String data) {
		this.dataReg = data;
	}
	
	public double calcIMC (double peso, double altura) {
		return peso/altura*altura;
	}
}
