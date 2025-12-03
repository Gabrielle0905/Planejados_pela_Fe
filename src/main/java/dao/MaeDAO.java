package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;
import factory.ConnectionFactory;
import model.Mae;

public class MaeDAO {
	
	public MaeDAO() {}
	
	public void cadastrar (Mae mae) {
		String sql = "INSERT INTO Mae(nome, dataNasci, telefone, endereco) VALUES (?, ?, ?, ?)";
		
		try (Connection conn = ConnectionFactory.getConnection()){
			
			PreparedStatement stmt = conn.prepareStatement(sql); //padrao
			stmt.setString(1, mae.getNome());
			stmt.setDate(2, java.sql.Date.valueOf(mae.getDataNasci())); //faço isso por Date diferente de LOCALDATE
			stmt.setInt(3, mae.getTelefone());
			stmt.setString(4, mae.getEndereco());
			
			stmt.executeUpdate(); //update é usado pra qualquer alteração (INSERT, UPDATE e DELETE)
		
		}catch(SQLException e) {
			System.out.println("Erro ao cadastrar mãe no banco");
			e.printStackTrace(); //imprime o erro no console
		}
	}
	
	public void atualizar (Mae mae) {
		String sql = "UPDATE Mae SET nome = ?, dataNasci = ?, telefone = ?, endereco = ? WHERE idMae = ?";
		
		try (Connection conn = ConnectionFactory.getConnection()){
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, mae.getNome());
			stmt.setDate(2, java.sql.Date.valueOf(mae.getDataNasci())); //faço isso por Date diferente de LOCALDATE
			stmt.setInt(3, mae.getTelefone());
			stmt.setString(4, mae.getEndereco());
			stmt.setInt(5, mae.getIdMae());
			
			stmt.executeUpdate();
		
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void deletar (Mae mae) {
		String sql = "DELETE FROM Mae WHERE idMae = ?";
		
		try (Connection conn = ConnectionFactory.getConnection()){
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, mae.getIdMae());
			
			stmt.executeUpdate();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public Mae buscarId(int idMae) {
		String sql = "SELECT * FROM Mae WHERE idMae = ?";
		
		try (Connection conn = ConnectionFactory.getConnection()){
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, idMae);
			
			ResultSet rs = stmt.executeQuery();
			
			if(rs.next()) {
				Mae mae = new Mae();
				mae.setIdMae(rs.getInt("idMae"));
				mae.setNome(rs.getString("nome"));
				mae.setDataNasci(rs.getDate("dataNasci").toLocalDate());
				mae.setTelefone(rs.getInt("telefone"));
				mae.setEndereco(rs.getString("endereco"));
				return mae;	
			}
		}catch(SQLException e) {
				e.printStackTrace();
		}
		return null;
	}
	
	public List<Mae> listartodos(){
		String sql = "SELECT * FROM Mae";
		List<Mae> lista = new ArrayList<>();
		
		try (Connection conn = ConnectionFactory.getConnection()){
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()){
				Mae mae = new Mae();
				mae.setIdMae(rs.getInt("idMae"));
				mae.setNome(rs.getString("nome"));
				mae.setDataNasci(rs.getDate("dataNasci").toLocalDate());
				mae.setTelefone(rs.getInt("telefone"));
				mae.setEndereco(rs.getString("endereco"));
				
				lista.add(mae);
			}	
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return lista;
	}
	
	public List<Mae> aniversariantes(){
		String sql = "SELECT * FROM Mae WHERE DAY(dataNasci) = ? AND MONTH(dataNasci) = ? ";
		List<Mae> listaAniversario = new ArrayList<>();
		
		try(Connection conn = ConnectionFactory.getConnection()){
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, LocalDate.now().getDayOfMonth());
			stmt.setInt(2, LocalDate.now().getMonthValue());
			
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				Mae mae = new Mae();
				mae.setIdMae(rs.getInt("idMae"));
				mae.setNome(rs.getString("nome"));
				
				listaAniversario.add(mae);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return listaAniversario;
	}

    public Mae buscarPorNome(String nome) {
        String sql = "SELECT * FROM Mae WHERE nome = ?";

        try (Connection conn = ConnectionFactory.getConnection()){

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, nome);

            ResultSet rs = stmt.executeQuery();

            if(rs.next()) {
                Mae mae = new Mae();
                mae.setIdMae(rs.getInt("idMae"));
                mae.setNome(rs.getString("nome"));
                mae.setDataNasci(rs.getDate("dataNasci").toLocalDate());
                mae.setTelefone(rs.getInt("telefone"));
                mae.setEndereco(rs.getString("endereco"));
                return mae;
            }
        }catch(SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


}
