package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Models.Aluno;
import Models.HistPeso;

public class HistPesoDAO {
	private Connection conexao;

	public HistPesoDAO(Connection conexao) {
		this.conexao = conexao;
	}
	
	public List<HistPeso> getAll(int idAluno){
		List<HistPeso> pesos = new ArrayList<HistPeso>();
		
		try {
			PreparedStatement ps = this.conexao.prepareStatement("SELECT * FROM histPeso WHERE idAluno=?");
			ps.setInt(1, idAluno);
			
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				HistPeso peso = new HistPeso();
				peso.setId(rs.getInt("id"));
				peso.setPeso(rs.getDouble("peso"));
				peso.setData(rs.getString("dataReg"));
				peso.setIdAluno(rs.getInt("idAluno"));
				
				
				pesos.add(peso);
			}
			
			ps.close();
			
			return pesos;
			
					
		}catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	public List<String> getDatas(int idAluno){
		List<String> datas = new ArrayList<String>();
		
		try {
			PreparedStatement ps = this.conexao.prepareStatement("SELECT dataReg FROM histPeso WHERE idAluno=?");
			
			ps.setInt(1, idAluno);
			
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				
				datas.add(rs.getString("dataReg"));
			}
			
			ps.close();
			
			return datas;
			
					
		}catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public HistPeso getByData(String data, int idAluno) {
		try {
			PreparedStatement ps = this.conexao.prepareStatement("SELECT * FROM histPeso WHERE dataReg=? AND idAluno=?");
			ps.setString(1, data);
			ps.setInt(2, idAluno);
			
			ResultSet rs = ps.executeQuery();
			
			HistPeso histPeso = new HistPeso();
			
			while (rs.next()) {
				histPeso.setId(rs.getInt("id"));
				histPeso.setPeso(rs.getDouble("peso"));
				histPeso.setData(rs.getString("dataReg"));
				histPeso.setIdAluno(rs.getInt("idAluno"));
			}
			
			ps.close();
			
			return histPeso;
			
					
		}catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void insert(HistPeso histPeso) {
		try {
			PreparedStatement ps = this.conexao.prepareStatement("INSERT INTO histPeso VALUES (?, ?, ?, ?)");
			
			ps.setString(1, null);
			ps.setString(2, histPeso.getData());
			ps.setDouble(3, histPeso.getPeso());
			ps.setInt(4, histPeso.getIdAluno());
			
			ps.execute();
			ps.close();
					
		}catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void update(HistPeso histPeso) {
		try {
			PreparedStatement ps = this.conexao.prepareStatement("UPDATE alunos SET dataReg=?, peso=? WHERE id=?");
			
			ps.setString(1, histPeso.getData());
			ps.setDouble(2, histPeso.getPeso());
			
			ps.executeUpdate();
			ps.close();
			
		}catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	public void delete(HistPeso histPeso) {
		try {
			PreparedStatement ps = this.conexao.prepareStatement("DELETE FROM histPeso WHERE id=?");
			
			ps.setInt(1, histPeso.getId());
			
			ps.execute();
		}catch (SQLException e) {
		throw new RuntimeException(e);
		}
	}
}
