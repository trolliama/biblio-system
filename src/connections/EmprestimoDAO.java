package connections;

import bibliotecacepi.Emprestimo;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class EmprestimoDAO {
    private Connection con;

    public EmprestimoDAO() throws SQLException {
        this.con = new ConnectionFactory().getConnection();
    }

    public void adicionaEmprestimo(int book_id, int student_id, int class_id) throws SQLException {
        String sql = "insert into emprestimo(data_emprestimo, data_devolucao, " +
                "fk_livro_id, fk_aluno_id, fk_sala_id) values(?, ?, ?, ?, ?)";

        try{
            PreparedStatement stmt = this.con.prepareStatement(sql);

            Date today = new Date();
            Date entrega = calculateDataEntrega(today);

            stmt.setDate(1, new java.sql.Date(today.getTime()));
            stmt.setDate(2, new java.sql.Date(entrega.getTime()));
            stmt.setInt(3, book_id);
            stmt.setInt(4, student_id);
            stmt.setInt(5, class_id);

            stmt.execute();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            this.con.close();
        }
    }

    public int getEmprestimoId(int id_livro, int id_aluno, int id_sala) throws SQLException {
        String sql = "select emprestimo.id from emprestimo where fk_livro_id=? and fk_aluno_id=? and fk_sala_id=?";

        int id = 0;
        try{
            PreparedStatement stmt = this.con.prepareStatement(sql);

            stmt.setInt(1, id_livro);
            stmt.setInt(2, id_aluno);
            stmt.setInt(3, id_sala);

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

    public List<Emprestimo> getAll() throws SQLException {
        List<Emprestimo> emprestimos = new ArrayList<>();
        String sql = "select emp.id, emp.data_devolucao, emp.data_emprestimo, l.titulo," +
                " l.volume, al.nome, al.sobrenome, sala.sala from emprestimo as emp " +
                "inner join livros as l on emp.fk_livro_id = l.id   " +
                "inner join aluno as al on emp.fk_aluno_id = al.id " +
                "inner join sala on emp.fk_sala_id = sala.id";

        try{
            PreparedStatement stmt = this.con.prepareStatement(sql);

            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                int id = rs.getInt("emp.id");
                Date data_emprestimo = rs.getDate("emp.data_emprestimo");
                String titulo_livro = rs.getString("l.titulo");
                int volume_livro = rs.getInt("l.volume");
                String nome_aluno = rs.getString("al.nome");
                String sobrenome_aluno = rs.getString("al.sobrenome");
                String sala = rs.getString("sala.sala");
                Date data_devolucao = rs.getDate("emp.data_devolucao");

                Emprestimo emprestimo = new Emprestimo(id, data_devolucao, data_emprestimo, titulo_livro,
                        volume_livro, nome_aluno + " " + sobrenome_aluno, sala);

                emprestimos.add(emprestimo);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);

        } finally {
            this.con.close();
            return emprestimos;
        }
    }

    public void deleteEmprestimo(int id) throws SQLException {
        String sql = "delete from emprestimo where id=?";

        try {
            PreparedStatement stmt = this.con.prepareStatement(sql);
            stmt.setInt(1, id);

            stmt.executeUpdate();


        }catch (SQLException e){
            throw new RuntimeException(e);
        }finally {
            this.con.close();
        }
    }

    public List<Date> getAllDevolutionDate() throws SQLException {
        List<Date> datas = new ArrayList<>();
        String sql = "select data_devolucao from emprestimo";

        try {
            PreparedStatement stmt = this.con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                datas.add(rs.getDate("data_devolucao"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            this.con.close();
            return datas;
        }
    }

    public Date calculateDataEntrega(Date today){

        Calendar c = Calendar.getInstance();
        c.setTime(today);
        c.add(Calendar.DATE, +7);

        return c.getTime();
    }



}
