package connections;

import bibliotecacepi.Sala;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SalaDAO {

    private Connection con;

    public SalaDAO() throws SQLException {
        this.con = new ConnectionFactory().getConnection();
    }

    public void adicionaSala(String sala) throws SQLException {
        String sqlStatement = "insert into sala(sala) values(?)";

        try {
            PreparedStatement stmt = this.con.prepareStatement(sqlStatement);
            stmt.setString(1, sala);

            stmt.execute();
            stmt.close();
        } catch (SQLException e){
            new RuntimeException(e);
        } finally {
            this.con.close();
        }
    }

    public List<Sala> getSalas() throws SQLException {
        String sql = "select sala from sala";
        List<Sala> salas = new ArrayList<>();

        try {
            PreparedStatement stmt = this.con.prepareStatement(sql);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Sala sala = new Sala();
                sala.setSala(rs.getString("sala"));
                salas.add(sala);
            }

            stmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally{
            this.con.close();
            return salas;
        }
    }

    public int getSalaId(String sala) throws SQLException {
        String sql = "select id from sala where sala=?";
        int id = 0;
        try{
            PreparedStatement stmt = this.con.prepareStatement(sql);

            stmt.setString(1, sala);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                id = rs.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            this.con.close();
            return id;
        }
    }

    public boolean search(String sala) throws SQLException {
        boolean founded = true;
        String sql = "select sala from sala where sala=?";

        try {
            PreparedStatement stmt = this.con.prepareStatement(sql);

            stmt.setString(1, sala);

            ResultSet rs = stmt.executeQuery();

            if (rs.next() == false) {
                founded = false;
            }

            stmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.con.close();
        }

        return founded;
    }

    public void editSala(int id, String new_value) throws SQLException {
        String sql = "update sala set sala=? where id=?";

        try {
            PreparedStatement stmt = this.con.prepareStatement(sql);

            stmt.setString(1, new_value);
            stmt.setInt(2, id);

            stmt.executeUpdate();

        }catch (SQLException e){
            throw new RuntimeException(e);

        }finally {
            this.con.close();
        }
    }

    public boolean deleteSala(int id) throws SQLException {
        String sql = "delete from sala where id=?";
        boolean fk_error = false;

        try {
            PreparedStatement stmt = this.con.prepareStatement(sql);
            stmt.setInt(1, id);

            try {
                stmt.executeUpdate();
            } catch(SQLIntegrityConstraintViolationException e){
                fk_error = true;
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }finally {
            this.con.close();
            return fk_error;
        }
    }
}
