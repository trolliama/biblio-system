package connections;

import bibliotecacepi.Livro;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

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

    public Date calculateDataEntrega(Date today){

        Calendar c = Calendar.getInstance();
        c.setTime(today);
        c.add(Calendar.DATE, +7);

        return c.getTime();
    }
}
