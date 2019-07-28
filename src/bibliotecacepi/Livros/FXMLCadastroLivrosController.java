/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bibliotecacepi.Livros;

import bibliotecacepi.Autor;
import bibliotecacepi.Editora;
import bibliotecacepi.Livro;
import connections.AutorDAO;
import connections.EditoraDAO;
import connections.LivroDAO;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML FXMLCadastroSalaController class
 *
 * @author caio
 */
public class FXMLCadastroLivrosController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    private String titulo;
    private String volume;
    private final Autor autor = new Autor();
    private final Editora editora = new Editora();
    
    @FXML
    private TextField tituloTextField;
    
    @FXML
    private TextField volumeTextField;
    
    @FXML
    private TextField autorTextField;
    
    @FXML
    private TextField editoraTextField;

    @FXML
    private Button cadastroButton;

    @FXML
    public void cadastrarLivro(ActionEvent event) throws SQLException{
        try{
            double valor = Integer.parseInt(volumeTextField.getText());
            if(verificaCampos()){
                throw new NumberFormatException();
            }
        }catch (NumberFormatException e){
            Alert dialog = new Alert(Alert.AlertType.ERROR);
            
            dialog.setTitle("Informação dos campos");
            dialog.setHeaderText("Campos incorretos");
            dialog.setContentText("Campos não foram preenchidos\nCampo volume contém letras");
            dialog.showAndWait();
            
            return;
        }
        
        autor.setNome(autorTextField.getText());
        editora.setEditora(editoraTextField.getText());
        
        verificaAutor();
        verificaEditora();
        
        titulo = tituloTextField.getText();
        volume = volumeTextField.getText();

        Livro livro = new Livro(0,titulo, volume, autor, editora);

        if (new LivroDAO().search(titulo, Integer.parseInt(volume))) {
            Alert dialog = new Alert(Alert.AlertType.INFORMATION);

            dialog.setTitle("Livro já cadastrado");
            dialog.setHeaderText("Livro cadastrado");
            dialog.setContentText("O livro já está cadastrado.\nDeseja cadastrar mesmo assim?");

            dialog.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    try {
                        new LivroDAO().adiciona(livro);
                    } catch (SQLException ex) {
                        Logger.getLogger(FXMLCadastroLivrosController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });

            return;
        }
        new LivroDAO().adiciona(livro);

        Stage stage = (Stage) cadastroButton.getScene().getWindow();
        stage.close();
    }
    
    public boolean verificaCampos(){
        boolean campos_vazios = false;
        
        List<String> fields = new ArrayList<>();

        fields.add(tituloTextField.getText());
        fields.add(autorTextField.getText());
        fields.add(editoraTextField.getText());
        
        for(int i=0; i < 3; i++){
            if("".equals(fields.get(i))){
                campos_vazios = true;
                break;
            }
        }
        
        return campos_vazios;
        
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
