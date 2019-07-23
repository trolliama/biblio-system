/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bibliotecacepi;
import connections.AutorDAO;
import connections.EditoraDAO;
import java.sql.SQLException;


/**
 *
 * @author caio
 */
public class Livro {
    private String titulo;
    private String volume;
    private Autor autor;
    private Editora editora;
    private Integer autor_id;
    private Integer editora_id;

    /**
     * @return the titulo
     */

    public Livro(String titulo, String volume, Autor autor, Editora editora) throws SQLException {
        this.titulo = titulo;
        this.volume = volume;
        this.autor = autor;
        this.editora = editora;

        setAutor_id();
        setEditora_id();
    }
    public String getTitulo() {
        return titulo;
    }

    /**
     * @param titulo the titulo to set
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    /**
     * @return the volume
     */
    public String getVolume() {
        return volume;
    }

    /**
     * @param volume the volume to set
     */
    public void setVolume(String volume) {
        this.volume = volume;
    }

    /**
     * @return the autor_id
     */
    public Integer getAutor_id() {
        return autor_id;
    }

    /**
     */
    public void setAutor_id() throws SQLException {
        this.autor_id = new AutorDAO().getId(autor);
    }

    /**
     * @return the editora_id
     */
    public Integer getEditora_id() {
        return editora_id;
    }

    /**
     */
    public void setEditora_id() throws SQLException {
        this.editora_id = new EditoraDAO().getId(editora);
    }


    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public Editora getEditora() {
        return editora;
    }

    public void setEditora(Editora editora) {
        this.editora = editora;
    }
}
