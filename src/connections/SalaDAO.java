package connections;

import com.mysql.cj.protocol.Resultset;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

    public List<String> getSalas() throws SQLException {
        String sql = "select sala from sala";
        List<String> salas = new ArrayList<String>();

        try {
            PreparedStatement stmt = this.con.prepareStatement(sql);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                salas.add(rs.getString("sala"));
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
}
