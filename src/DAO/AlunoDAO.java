package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

import Models.Aluno;

public class AlunoDAO {
	private Connection conexao;

	public AlunoDAO(Connection conexao) {
		this.conexao = conexao;
	}
	
	public List<Aluno> getAll(){
		List<Aluno> alunos = new ArrayList<Aluno>();
		
		try {
			PreparedStatement ps = this.conexao.prepareStatement("SELECT * FROM alunos");
			
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				Aluno aluno = new Aluno();
				aluno.setId(rs.getInt("id"));
				aluno.setNome(rs.getString("nome"));
				aluno.setCpf(rs.getString("cpf"));
				aluno.setDataNasc(rs.getString("dataNasc"));
				aluno.setAltura(rs.getDouble("altura"));
				
				alunos.add(aluno);
			}
			
			ps.close();
			
			return alunos;
			
					
		}catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public List<String> getNomes(){
		List<String> nomes = new ArrayList<String>();
		
		try {
			PreparedStatement ps = this.conexao.prepareStatement("SELECT nome FROM alunos");
			
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				
				nomes.add(rs.getString("nome"));
			}
			
			ps.close();
			
			return nomes;
			
					
		}catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void insert(Aluno aluno) {
		try {
			PreparedStatement ps = this.conexao.prepareStatement("INSERT INTO alunos VALUES (?, ?, ?, ?, ?)");
			
			ps.setString(1, null);
			ps.setString(2, aluno.getNome());
			ps.setString(3, aluno.getCpf());
			ps.setString(4, aluno.getDataNasc());
			ps.setDouble(5, aluno.getAltura());
			ps.execute();
			ps.close();
					
		}catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
}
