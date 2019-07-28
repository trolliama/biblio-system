package connections;

import bibliotecacepi.Aluno;
import connections.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AlunoDAO {

    private Connection con;

    public AlunoDAO() throws SQLException {
        this.con = new ConnectionFactory().getConnection();
    }

    public void adicionaAluno(Aluno aluno) throws SQLException {
        String sql = "insert into aluno(nome, sobrenome) values(?, ?)";

        try {
            PreparedStatement stmt = this.con.prepareStatement(sql);

            stmt.setString(1, aluno.getNome());
            stmt.setString(2, aluno.getSobrenome());

            stmt.execute();
            stmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.con.close();
        }
    }

    public boolean search(String nome, String sobrenome) throws SQLException {
        boolean founded = true;
        String sql = "select nome from aluno where nome=? and sobrenome=?";

        try {
            PreparedStatement stmt = this.con.prepareStatement(sql);

            stmt.setString(1, nome);
            stmt.setString(2, sobrenome);

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

    public int getAlunoId(String nome, String sobrenome) throws SQLException {
        String sql = "select id from aluno where nome=? and sobrenome=?";
        int id = 0;
        try{
            PreparedStatement stmt = this.con.prepareStatement(sql);

            stmt.setString(1, nome);
            stmt.setString(2, sobrenome);

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

    public List<Aluno> getAlunos() throws SQLException {
        String sql = "select nome, sobrenome from aluno";
        List<Aluno> data = new ArrayList<>();

        try {
            PreparedStatement ps = this.con.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                Aluno aluno = new Aluno();

                aluno.setNome(rs.getString("nome"));
                aluno.setSobrenome(rs.getString("sobrenome"));

                data.add(aluno);
            }

        }catch(SQLException e){
            new RuntimeException(e);
        } finally {
            this.con.close();
            return data;
        }
    }

    public boolean deleteAluno(int id) throws SQLException {
        String sql = "delete from aluno where id=?";
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
            new RuntimeException(e);
        }finally {
            this.con.close();
            return fk_error;
        }
    }

    public void editAluno(int id, String campo, String new_value) throws SQLException {
        String sql = "update aluno set " + campo + "=? where id=?";

        try {
            PreparedStatement stmt = this.con.prepareStatement(sql);

            stmt.setString(1, new_value);
            stmt.setInt(2, id);

            stmt.executeUpdate();

        }catch (SQLException e){
            System.out.println(e);
            new RuntimeException(e);

        }finally {
            this.con.close();
        }
    }
}
