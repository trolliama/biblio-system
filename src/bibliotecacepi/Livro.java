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
    private Integer autor_id;
    private Integer editora_id;

    /**
     * @return the titulo
     */
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
     * @param autor_id the autor_id to set
     */
    public void setAutor_id(Autor autor) throws SQLException {
        this.autor_id = new AutorDAO().getId(autor);
    }

    /**
     * @return the editora_id
     */
    public Integer getEditora_id() {
        return editora_id;
    }

    /**
     * @param editora_id the editora_id to set
     */
    public void setEditora_id(Editora editora) throws SQLException {
        this.editora_id = new EditoraDAO().getId(editora);
    }
    
    
}
