package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import factory.ConnectionFactory;
import model.ServicoDoEncontro;
import model.Encontro;
import model.TipoServico;
import model.Mae;

public class ServicodoEncontroDAO {

    public ServicodoEncontroDAO() {}

    public void cadastrar(ServicoDoEncontro servico, Encontro encontro) {
        String sql = "INSERT INTO ServicoDoEncontro(tipo, mae, descricao, id_Encontro) VALUES (?,?,?,?)";

        try(Connection conn = ConnectionFactory.getConnection()){

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, servico.getServico().name());
            stmt.setString(2, servico.getMaeResponsavel().getNome());
            stmt.setString(3, servico.getDescricao());
            stmt.setInt(4, encontro.getIdEncontro());

            stmt.executeUpdate();

        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public void atualizar(ServicoDoEncontro servico, Encontro encontro) {
        String sql = "UPDATE ServicoDoEncontro SET tipo = ?, mae = ?, descricao = ?, id_Encontro = ? WHERE idServico = ?";

        try(Connection conn = ConnectionFactory.getConnection()){

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, servico.getServico().name());
            stmt.setString(2, servico.getMaeResponsavel().getNome());
            stmt.setString(3, servico.getDescricao());
            stmt.setInt(4, encontro.getIdEncontro());
            stmt.setInt(5, servico.getIdServico());

            stmt.executeUpdate();

        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public void deletar(ServicoDoEncontro servico) {
        String sql = "DELETE FROM ServicoDoEncontro WHERE idServico = ?";

        try(Connection conn = ConnectionFactory.getConnection()){

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, servico.getIdServico());

            stmt.executeUpdate();

        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public ServicoDoEncontro buscarId(int idServico) {
        String sql = "SELECT * FROM ServicoDoEncontro WHERE idServico = ?";

        try(Connection conn = ConnectionFactory.getConnection()){

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idServico);

            ResultSet rs = stmt.executeQuery();

            if(rs.next()) {
                ServicoDoEncontro servico = new ServicoDoEncontro();
                servico.setIdServico(rs.getInt("idServico"));
                servico.setServico(TipoServico.valueOf(rs.getString("tipo")));

                // Buscar objeto Mae completo pelo nome
                String nomeMae = rs.getString("mae");
                MaeDAO maeDAO = new MaeDAO();
                Mae mae = maeDAO.buscarPorNome(nomeMae);

                if (mae != null) {
                    servico.setMaeResponsavel(mae);
                } else {
                    // Fallback: criar Mae só com o nome
                    Mae maeFallback = new Mae();
                    maeFallback.setNome(nomeMae);
                    servico.setMaeResponsavel(maeFallback);
                }

                servico.setDescricao(rs.getString("descricao"));

                return servico;
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // MÉTODO CRÍTICO: Buscar serviços por encontro
    public List<ServicoDoEncontro> buscarPorEncontro(int idEncontro) {
        String sql = "SELECT * FROM ServicoDoEncontro WHERE id_Encontro = ? ORDER BY idServico";
        List<ServicoDoEncontro> lista = new ArrayList<>();

        try(Connection conn = ConnectionFactory.getConnection()){

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idEncontro);

            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                ServicoDoEncontro servico = new ServicoDoEncontro();
                servico.setIdServico(rs.getInt("idServico"));
                servico.setServico(TipoServico.valueOf(rs.getString("tipo")));

                // Buscar objeto Mae completo pelo nome
                String nomeMae = rs.getString("mae");
                MaeDAO maeDAO = new MaeDAO();
                Mae mae = maeDAO.buscarPorNome(nomeMae);

                if (mae != null) {
                    servico.setMaeResponsavel(mae);
                } else {
                    // Fallback: criar Mae só com o nome
                    Mae maeFallback = new Mae();
                    maeFallback.setNome(nomeMae);
                    servico.setMaeResponsavel(maeFallback);
                }

                servico.setDescricao(rs.getString("descricao"));

                lista.add(servico);
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public List<ServicoDoEncontro> listartodos() {
        String sql = "SELECT * FROM ServicoDoEncontro ORDER BY id_Encontro, idServico";
        List<ServicoDoEncontro> lista = new ArrayList<>();

        try(Connection conn = ConnectionFactory.getConnection()){

            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                ServicoDoEncontro servico = new ServicoDoEncontro();
                servico.setIdServico(rs.getInt("idServico"));
                servico.setServico(TipoServico.valueOf(rs.getString("tipo")));

                // Buscar objeto Mae completo pelo nome
                String nomeMae = rs.getString("mae");
                MaeDAO maeDAO = new MaeDAO();
                Mae mae = maeDAO.buscarPorNome(nomeMae);

                if (mae != null) {
                    servico.setMaeResponsavel(mae);
                } else {
                    // Fallback: criar Mae só com o nome
                    Mae maeFallback = new Mae();
                    maeFallback.setNome(nomeMae);
                    servico.setMaeResponsavel(maeFallback);
                }

                servico.setDescricao(rs.getString("descricao"));

                lista.add(servico);
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}