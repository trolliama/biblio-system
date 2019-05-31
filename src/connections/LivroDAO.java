/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connections;
import bibliotecacepi.Livro;
import connections.ConnectionFactory;

import java.sql.*;

/**
 *
 * @author caio
 */
public class LivroDAO {

    private Connection con;

    public LivroDAO() throws SQLException {
        this.con = new ConnectionFactory().getConnection();
    }

    public void adiciona(Livro livro) throws SQLException {
        String sql = "insert into livros" +
                "(titulo, volume, fk_autor_id, fk_editora_id)" +
                " values(?, ?, ?, ?)";

        try {
            PreparedStatement stmt = this.con.prepareStatement(sql);

            stmt.setString(1, livro.getTitulo());
            stmt.setString(2, livro.getVolume());
            stmt.setInt(3, livro.getAutor_id());
            stmt.setInt(4, livro.getEditora_id());

            stmt.execute();
            stmt.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            this.con.close();
        }
    }

    public boolean search(String title, int volume) throws SQLException {
        boolean founded = true;
        String sql = "select titulo from livros where titulo=? and volume=?";

        try {
            PreparedStatement stmt = this.con.prepareStatement(sql);

            stmt.setString(1, title);
            stmt.setInt(2, volume);

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

//    public Array getLivro(String titulo){
//        
//    }
}
