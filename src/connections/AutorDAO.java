/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connections;

import java.sql.Connection;
import bibliotecacepi.Autor;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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
    
//    public List listaSearch(String initials) throws SQLException{
//        String sql = "SELECT nome FROM autor WHERE nome LIKE ?%";
//        List<Autor> autores = new ArrayList<Autor>();
//
//        try{
//            PreparedStatement stmt = this.con.prepareStatement(sql);
//
//            stmt.setString(1, initials);
//
//            ResultSet rs = stmt.executeQuery();
//
//            while(rs.next()){
//                String nome = rs.getString("nome");
//
//                Autor autor = new Autor();
//                autor.setNome(nome);
//
//                autores.add(autor);
//            }
//
//            rs.close();
//            stmt.close();
//
//            return autores;
//        } catch(SQLException e){
//            throw new RuntimeException(e);
//        } finally {
//            this.con.close();
//        }
//    }
    
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
            
            rs.next();
            Integer id = rs.getInt("id");
            
            return id;
            
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally{
            this.con.close();
        }
    }
}
