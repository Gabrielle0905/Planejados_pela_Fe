package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;
import factory.ConnectionFactory;
import model.Encontro;
import model.StatusEncontro;

public class EncontroDAO {
	
	public EncontroDAO() {}

    public int cadastrar(Encontro encontro) {
        String sql = "INSERT INTO Encontro(data, statusEncontro) VALUES (?,?)";

        try (Connection conn = ConnectionFactory.getConnection()) {

            PreparedStatement stmt = conn.prepareStatement(
                    sql,
                    PreparedStatement.RETURN_GENERATED_KEYS
            );
            stmt.setDate(1, java.sql.Date.valueOf(encontro.getData()));
            stmt.setString(2, encontro.getStatus().name());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }
	
	public void atualizar(Encontro encontro) {
		String sql = "UPDATE Encontro SET data = ?, statusEncontro = ? WHERE idEncontro = ? AND data > ?";
		
		try(Connection conn = ConnectionFactory.getConnection()){
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setDate(1, java.sql.Date.valueOf(encontro.getData()));
			stmt.setString(2, encontro.getStatus().name());
			stmt.setInt(3, encontro.getIdEncontro());
			stmt.setDate(4, java.sql.Date.valueOf(LocalDate.now()));
			
			stmt.executeUpdate();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void deletar(Encontro encontro) {
		String sql = "DELETE FROM Encontro WHERE idEncontro = ? AND data > ?";
		
		try(Connection conn = ConnectionFactory.getConnection()){
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, encontro.getIdEncontro());
			stmt.setDate(2, java.sql.Date.valueOf(LocalDate.now()));
			
			stmt.executeUpdate();
		
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public Encontro buscarId(int idEncontro) {
		String sql = "SELECT * FROM Encontro WHERE idEncontro = ?";
		
		try(Connection conn = ConnectionFactory.getConnection()){
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, idEncontro);
			
			ResultSet rs = stmt.executeQuery();
			
			if(rs.next()) {
				Encontro encontro = new Encontro();
				encontro.setIdEncontro(rs.getInt("idEncontro"));
				encontro.setData(rs.getDate("data").toLocalDate());
				encontro.setStatus(StatusEncontro.valueOf(rs.getString("statusEncontro")));
				return encontro;
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<Encontro> listartodos(){
		String sql = "SELECT * FROM Encontro";
		List<Encontro> lista = new ArrayList<>();
		
		try(Connection conn = ConnectionFactory.getConnection()){
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				Encontro encontro = new Encontro();
				encontro.setIdEncontro(rs.getInt("idEncontro"));
				encontro.setData(rs.getDate("data").toLocalDate());
				encontro.setStatus(StatusEncontro.valueOf(rs.getString("statusEncontro")));
				
				lista.add(encontro);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return lista;
	}

}
