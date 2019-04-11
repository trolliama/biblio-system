/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bibliotecacepi.Livros;

import bibliotecacepi.Autor;
import bibliotecacepi.Editora;
import connections.AutorDAO;
import connections.EditoraDAO;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author caio
 */
public class FXMLCadastroLivrosController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    private String titulo;
    private Integer volume;
    private Autor autor = new Autor();
    private Editora editora = new Editora();
    
    @FXML
    private TextField tituloTextField;
    
    @FXML
    private TextField volumeTextField;
    
    @FXML
    private TextField autorTextField;
    
    @FXML
    private TextField editoraTextField;
    
    @FXML
    public void cadastrarLivro(ActionEvent event) throws SQLException{
        autor.setNome(autorTextField.getText());
        editora.setEditora(editoraTextField.getText());
        
        verificaAutor();
    }
    
    public void verificaAutor() throws SQLException{
        if(new AutorDAO().search(autor) == false){
            new AutorDAO().adiciona(autor);
        }
    }
    
    public void verificaEditora() throws SQLException{
        if(new EditoraDAO().search(editora) == false){
            new EditoraDAO().adiciona(editora);
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
}
