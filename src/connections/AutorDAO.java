/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connections;

import java.sql.*;

import bibliotecacepi.Autor;

/**
 *
 * @author caio
 */
public class AutorDAO {
    
    private Connection con;
    
    public AutorDAO() throws SQLException {
        this.con = new ConnectionFactory().getConnection();
    }
    
    public void adiciona(Autor autor) throws SQLException{
        String sql = "insert into autor (nome) values (?)";
        
        try {
            PreparedStatement stmt = this.con.prepareStatement(sql);
            
            stmt.setString(1, autor.getNome());
            
            stmt.execute();
            stmt.close();
            
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally{
            this.con.close();
        }
    }
    
    public boolean search(Autor autor) throws SQLException{
        Boolean bool = true;
        
        try{
            String sql = "SELECT nome FROM autor WHERE nome=?";

            PreparedStatement stmt = this.con.prepareStatement(sql);
            stmt.setString(1, autor.getNome());

            ResultSet rs = stmt.executeQuery();

            if(rs.next() == false){
                bool = false;
            };

            return bool;
        }catch(SQLException e){
            throw new RuntimeException(e);
        } finally {
            this.con.close();
        }
    }
    
    public Integer getId(Autor autor) throws SQLException{
        try {
            String sql = "SELECT id FROM autor WHERE nome=?";

            PreparedStatement stmt = this.con.prepareStatement(sql);
            stmt.setString(1, autor.getNome());
            
            ResultSet rs = stmt.executeQuery();
            
            if(!rs.next()){
                return 0;
            }
            Integer id = rs.getInt("id");
            
            return id;
            
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally{
            this.con.close();
        }
    }

    public Integer getId(String name_autor) throws SQLException{
        Autor autor = new Autor();
        autor.setNome(name_autor);

        return getId(autor);
    }

//    public void editAutor(int id, String new_value) throws SQLException {
//        String sql = "update autor set nome=? where id=?";
//
//        try {
//            PreparedStatement stmt = this.con.prepareStatement(sql);
//
//            stmt.setString(1, new_value);
//            stmt.setInt(2, id);
//
//            stmt.executeUpdate();
//
//        }catch (SQLException e){
//            throw new RuntimeException(e);
//
//        }finally {
//            this.con.close();
//        }
//    }

    public boolean delete(int id) throws SQLException {
        String sql = "delete from autor where id=?";
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
