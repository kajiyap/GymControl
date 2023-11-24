package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
	
	
	public Aluno getById(int id) {
		try {
			PreparedStatement ps = this.conexao.prepareStatement("SELECT * FROM alunos WHERE id=?");
			ps.setInt(1, id);
			
			ResultSet rs = ps.executeQuery();
			
			Aluno aluno = new Aluno();
			
			while (rs.next()) {
				aluno.setId(rs.getInt("id"));
				aluno.setNome(rs.getString("nome"));
				aluno.setCpf(rs.getString("cpf"));
				aluno.setDataNasc(rs.getString("dataNasc"));
				aluno.setAltura(rs.getDouble("altura"));
			}
			
			ps.close();
			
			return aluno;
			
					
		}catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	
	public Aluno getByNome(String nome) {
		try {
			PreparedStatement ps = this.conexao.prepareStatement("SELECT * FROM alunos WHERE nome=?");
			ps.setString(1, nome);
			
			ResultSet rs = ps.executeQuery();
			
			Aluno aluno = new Aluno();
			
			while (rs.next()) {
				aluno.setId(rs.getInt("id"));
				aluno.setNome(rs.getString("nome"));
				aluno.setCpf(rs.getString("cpf"));
				aluno.setDataNasc(rs.getString("dataNasc"));
				aluno.setAltura(rs.getDouble("altura"));
			}
			
			ps.close();
			
			return aluno;
			
					
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
	public void update(Aluno aluno) {
		try {
			PreparedStatement ps = this.conexao.prepareStatement("UPDATE alunos SET nome=?, cpf=?, dataNasc=?, altura=? WHERE id=?");
			
			ps.setString(1, aluno.getNome());
			ps.setString(2, aluno.getCpf());
			ps.setString(3, aluno.getDataNasc());
			ps.setDouble(4, aluno.getAltura());
			ps.setInt(5, aluno.getId());
			ps.executeUpdate();
			
			int rowsUpdated = ps.executeUpdate();
			ps.close();
					
		}catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void delete(Aluno aluno) {
		try {
			// Delete histPeso do aluno
			PreparedStatement ps = this.conexao.prepareStatement("DELETE FROM histPeso WHERE idAluno=?");
			
			ps.setInt(1, aluno.getId());
			
			ps.execute();
			
			// Delete aluno
			ps = this.conexao.prepareStatement("DELETE FROM alunos WHERE id=?");
			
			ps.setInt(1, aluno.getId());
			
			ps.execute();
			ps.close();
					
		}catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
}
