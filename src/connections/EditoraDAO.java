/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connections;

import java.sql.Connection;
import bibliotecacepi.Editora;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author caio
 */
public class EditoraDAO {
    private Connection con;
    
    public EditoraDAO(){
        this.con = new ConnectionFactory().getConnection();
    }
    
    public void adiciona(Editora editora) throws SQLException{
        String sql = "insert into Editora (editora) values (?)";
        
        try {
            PreparedStatement stmt = this.con.prepareStatement(sql);
            
            stmt.setString(1, editora.getEditora());
            
            stmt.execute();
            stmt.close();
            
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally{
            this.con.close();
        }
    }
    
    public List listaSearch(String initials) throws SQLException{
        String sql = "SELECT editora FROM Editora WHERE editora LIKE ?%";
        List<Editora> editoras = new ArrayList<Editora>();
        
        try{
            PreparedStatement stmt = this.con.prepareStatement(sql);
            
            stmt.setString(1, initials);
            
            ResultSet rs = stmt.executeQuery();
            
            while(rs.next()){
                String nome = rs.getString("editora");
                
                Editora editora = new Editora();
                editora.setEditora(nome);
                
                editoras.add(editora);
            }
            
            rs.close();
            stmt.close();
            
            return editoras;
        } catch(SQLException e){
            throw new RuntimeException(e);
        } finally {
            this.con.close();
        }
    }
    
    public boolean search(Editora editora) throws SQLException{
        Boolean bool = true;
        String sql = "SELECT editora FROM editora WHERE editora=?";
        
        PreparedStatement stmt = this.con.prepareStatement(sql);
        stmt.setString(1, editora.getEditora());
        
        ResultSet rs = stmt.executeQuery();
        
        rs.next();
        rs.getString("editora");
        
        if (rs.wasNull()){
            bool = false;
        }
        
        return bool;
    }
}
