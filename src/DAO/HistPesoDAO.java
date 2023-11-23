package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
				
				
				pesos.add(peso);
			}
			
			ps.close();
			
			return pesos;
			
					
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
	
}
