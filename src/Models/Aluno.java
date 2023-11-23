package Models;

public class Aluno {
	private int id;
	private String nome;
	private String cpf;
	private String dataNasc;
	private double altura;
	
	
	
	public Aluno(String nome, String cpf, String dataNasc, double peso, double altura) {
		this.nome = nome;
		this.cpf = cpf;
		this.dataNasc = dataNasc;
		this.altura = altura;
	}
	
	public Aluno() {
		
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}



	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}


	public String getCpf() {
		return cpf;
	}


	public void setCpf(String cpf) {
		this.cpf = cpf;
	}


	public String getDataNasc() {
		return dataNasc;
	}


	public void setDataNasc(String dataNasc) {
		this.dataNasc = dataNasc;
	}


	public double getAltura() {
		return altura;
	}


	public void setAltura(double altura) {
		this.altura = altura;
	}
	
}
