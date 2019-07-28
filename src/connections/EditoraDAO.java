/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connections;

import java.sql.*;

import bibliotecacepi.Editora;

/**
 *
 * @author caio
 */
public class EditoraDAO {
    private Connection con;
    
    public EditoraDAO() throws SQLException {
        this.con = new ConnectionFactory().getConnection();
    }
    
    public void adiciona(Editora editora) throws SQLException{
        String sql = "insert into editora (editora) values (?)";
        
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
    
    public boolean search(Editora editora) throws SQLException{
        Boolean bool = true;
        String sql = "SELECT editora FROM editora WHERE editora=?";
        
        PreparedStatement stmt = this.con.prepareStatement(sql);
        stmt.setString(1, editora.getEditora());
        
        ResultSet rs = stmt.executeQuery();
        
        if(rs.next() == false){
            bool = false;
        }
;
        
        return bool;
    }
    
    public Integer getId(Editora editora) throws SQLException{
        try {
            String sql = "SELECT id FROM editora WHERE editora=?";

            PreparedStatement stmt = this.con.prepareStatement(sql);
            stmt.setString(1, editora.getEditora());
            
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

    public Integer getId(String name_editora) throws SQLException{
        Editora editora = new Editora();
        editora.setEditora(name_editora);

        return getId(editora);
    }

//    public void editEditora(int id, String new_value) throws SQLException {
//        String sql = "update editora set editora=? where id=?";
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
//            System.out.println(e);
//            new RuntimeException(e);
//
//        }finally {
//            this.con.close();
//        }
//    }

    public boolean delete(int id) throws SQLException {
        String sql = "delete from editora where id=?";
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
