/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connections;
import bibliotecacepi.Livro;
import connections.ConnectionFactory;
import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
/**
 *
 * @author caio
 */
public class LivroDAO {
    
    private Connection con;
    
    public LivroDAO(){
        this.con = new ConnectionFactory().getConnection();
    }
    
    public void adiciona(Livro livro) throws SQLException{
        String sql = "insert into Livros" +
            "(titulo, volume, fk_autor_id, fk_editora_id)" +
            "(?, ?, ?, ?)";
        
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
        } finally{
            this.con.close();
        }
    }
    
//    public Array getLivro(String titulo){
//        
//    }
}
