package bibliotecacepi;

import java.util.Date;

public class Emprestimo {
    private Date data_emprestimo;
    private Date data_devolucao;
    private String titulo_livro;
    private int volume_livro;
    private int id;
    private String aluno;
    private String sala;

    public Emprestimo(int id, Date data_devolucao, Date data_emprestimo, String titulo_livro, int volume_livro, String aluno, String sala){
        this.setData_devolucao(data_devolucao);
        this.setData_emprestimo(data_emprestimo);
        this.setTitulo_livro(titulo_livro);
        this.setVolume_livro(volume_livro);
        this.setAluno(aluno);
        this.setSala(sala);
        this.setId(id);
    }


    public Date getData_emprestimo() {
        return data_emprestimo;
    }

    public void setData_emprestimo(Date data_emprestimo) {
        this.data_emprestimo = data_emprestimo;
    }

    public Date getData_devolucao() {
        return data_devolucao;
    }

    public void setData_devolucao(Date data_devolucao) {
        this.data_devolucao = data_devolucao;
    }

    public String getTitulo_livro() {
        return titulo_livro;
    }

    public void setTitulo_livro(String titulo_livro) {
        this.titulo_livro = titulo_livro;
    }

    public int getVolume_livro() {
        return volume_livro;
    }

    public void setVolume_livro(int volume_livro) {
        this.volume_livro = volume_livro;
    }

    public String getAluno() {
        return aluno;
    }

    public void setAluno(String aluno) {
        this.aluno = aluno;
    }

    public String getSala() {
        return sala;
    }

    public void setSala(String sala) {
        this.sala = sala;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
