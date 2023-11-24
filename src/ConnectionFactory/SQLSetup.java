package ConnectionFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SQLSetup {
	private Connection conexao;

	public static void main(String[] args) {
		SQLSetup setup = new SQLSetup(ConnectionFactory.getConnection());
		
		setup.executa("setup.sql");

	}

	public SQLSetup(Connection conexao) {
		this.conexao = conexao;
	}
	
	
	public void executa(String arquivo) {
		try {
			String path = getClass().getResource(arquivo).getFile();
			
			FileReader reader = new FileReader(path);
			BufferedReader bufferedReader = new BufferedReader(reader);
			
			String line;
			StringBuilder builder = new StringBuilder();
			
			while( (line = bufferedReader.readLine()) != null) {
				line.trim();
				
				if (line.isEmpty() || line.startsWith("--"))
	                continue;
				
				builder.append(line);
				
				if (line.endsWith(";")) {
					try {
						PreparedStatement ps = this.conexao.prepareStatement(builder.toString());
						
						ps.execute();
						
						System.out.println("[Sucesso] - " + builder.toString());
						
						builder.setLength(0);
						
					}catch(SQLException e) {
						System.out.println("[Falhou] - " + builder.toString());
						e.printStackTrace();
						
					}
				}
				
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	public Connection getConexao() {
		return conexao;
	}

	public void setConexao(Connection conexao) {
		this.conexao = conexao;
	}
	
	

}
