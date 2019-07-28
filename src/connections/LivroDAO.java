/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connections;
import bibliotecacepi.Autor;
import bibliotecacepi.Editora;
import bibliotecacepi.Livro;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

    public int getLivroId(String titulo, int volume) throws SQLException {
        String sql = "select id from livros where titulo=? and volume=?";
        int id = 0;
        try{
            PreparedStatement stmt = this.con.prepareStatement(sql);

            stmt.setString(1, titulo);
            stmt.setInt(2, volume);

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

    public List<Livro> getLivros() throws SQLException {
        String sql = "select livros.id, livros.titulo, livros.volume, autor.nome, editora.editora from livros " +
                "inner join autor on livros.fk_autor_id = autor.id " +
                "inner join editora on livros.fk_editora_id = editora.id;";
        List<Livro> data = new ArrayList<>();

        try {
            PreparedStatement stmt = this.con.prepareStatement(sql);

            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                int id = rs.getInt("livros.id");
                String title = rs.getString("livros.titulo");
                String volume = String.valueOf(rs.getInt("livros.volume"));

                Autor autor = new Autor();
                autor.setNome(rs.getString("autor.nome"));

                Editora editora = new Editora();
                editora.setEditora(rs.getString("editora.editora"));

                Livro livro = new Livro(id, title, volume, autor, editora);
                data.add(livro);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally{
            this.con.close();
            return data;
        }
    }

    public boolean deleteLivro(int id) throws SQLException {
        String sql = "delete from livros where id=?";
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

    public void editLivro(int id, String campo, String new_value) throws SQLException {
        String sql = "update livros set " + campo + "=? where id=?";

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

    public void editLivro(int id, String campo, int new_id) throws SQLException {
        String sql = "update livros set " + campo + "=? where id=?";

        try {
            PreparedStatement stmt = this.con.prepareStatement(sql);

            stmt.setInt(1, new_id);
            stmt.setInt(2, id);

            stmt.executeUpdate();

        }catch (SQLException e){
            throw new RuntimeException(e);

        }finally {
            this.con.close();
        }
    }
}
