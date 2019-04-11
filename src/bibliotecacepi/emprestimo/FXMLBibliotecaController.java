/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bibliotecacepi.emprestimo;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import bibliotecacepi.Livros.CadastroLivros;
import javafx.stage.Stage;
/**
 *
 * @author caio
 */
public class FXMLBibliotecaController implements Initializable {
    
    @FXML
    private Label label;
    
    @FXML
    private TextField titleBookTextField;
    
    @FXML
    private TextField nameStudentTextField;
    
    @FXML
    private Button concludedButton;
    
    @FXML
    private void openCadastroLivro(ActionEvent event) throws IOException{
        CadastroLivros window = new CadastroLivros();
        window.start(new Stage());
    }
    @FXML
    private void searchTitleBook(KeyEvent event){
        System.out.println(event.getText());
    }
    
    @FXML
    private void searchNameStudent(KeyEvent event){
        System.out.println(event.getText());
    }
    
    @FXML
    private void concludedButtonAction(ActionEvent event) {
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
