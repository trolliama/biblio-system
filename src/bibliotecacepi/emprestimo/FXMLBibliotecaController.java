/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bibliotecacepi.emprestimo;

import bibliotecacepi.Alunos.CadastroAlunos;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import connections.AlunoDAO;
import connections.LivroDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import bibliotecacepi.Livros.CadastroLivros;
import javafx.stage.Stage;
/**
 *
 * @author caio
 */
public class FXMLBibliotecaController implements Initializable {

    ObservableList<String> salas = FXCollections.observableArrayList("7A", "7B", "8B");

    @FXML
    private ComboBox salaStudentTextField;
    
    @FXML
    private TextField titleBookTextField;
    
    @FXML
    private TextField nameStudentTextField;

    @FXML
    private TextField sobrenomeStudentTextField;

    @FXML
    private TextField volumeTextField;

    @FXML
    private void openCadastroLivro(ActionEvent event) throws IOException{
        CadastroLivros window = new CadastroLivros();
        window.start(new Stage());
    }
    
    @FXML
    private void openCadastroAlunos(ActionEvent event) throws IOException{
        CadastroAlunos window = new CadastroAlunos();
        window.start(new Stage());
    }
    
    @FXML
    private void concludedButtonAction(ActionEvent event) throws SQLException {

        try{
            int valor = Integer.parseInt(volumeTextField.getText());
            if(verificaCampos()){
                throw new NumberFormatException();
            }
        }catch (NumberFormatException e){
            Alert dialog = new Alert(Alert.AlertType.ERROR);

            dialog.setTitle("Informação dos campos");
            dialog.setHeaderText("Campos incorretos");
            dialog.setContentText("Campos não foram preenchidos, ou campo volume contém letras");
            dialog.showAndWait();

            return;
        }

        String titulo = titleBookTextField.getText();
        String volume = volumeTextField.getText();
        String nome_aluno = nameStudentTextField.getText();
        String sobrenome_aluno = sobrenomeStudentTextField.getText();

        verificaLivroCadastrado(titulo, volume);
        verificaNameStudent(nome_aluno, sobrenome_aluno);

    }

    public boolean verificaCampos() {
        boolean campos_vazios = false;

        List<String> fields = new ArrayList<>();

        fields.add(titleBookTextField.getText());
        fields.add(nameStudentTextField.getText());
        fields.add(sobrenomeStudentTextField.getText());
        fields.add((String) salaStudentTextField.getValue());

        for (int i = 0; i < 4; i++) {
            if ("".equals(fields.get(i))) {
                campos_vazios = true;
                break;
            }
        }

        return campos_vazios;
    }

    public void verificaLivroCadastrado(String titulo, String volume) throws SQLException {
        if (!new LivroDAO().search(titulo, Integer.parseInt(volume))) {
            Alert dialog = new Alert(Alert.AlertType.INFORMATION);

            dialog.setTitle("Informação dos campos");
            dialog.setHeaderText("Livro não existe");
            dialog.setContentText("Livro não existe deseja cadastra-lo? ");

            dialog.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    try {
                        CadastroLivros window = new CadastroLivros();
                        window.start(new Stage());

                    } catch (IOException ex) {
                        System.out.println("tatata tatata he o barulho que ela faz quando começa a quicar!!");
                        Logger.getLogger(FXMLBibliotecaController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
        }
    }

    private void verificaNameStudent(String nome, String sobrenome) throws SQLException {
        if (!new AlunoDAO().search(nome, sobrenome)) {
            Alert dialog = new Alert(Alert.AlertType.INFORMATION);

            dialog.setTitle("Informação dos campos");
            dialog.setHeaderText("Livro não existe");
            dialog.setContentText("Livro não existe deseja cadastra-lo? ");

            dialog.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    try {
                        CadastroAlunos window = new CadastroAlunos();
                        window.start(new Stage());

                    } catch (IOException ex) {
                        System.out.println("tatata tatata he o barulho que ela faz quando começa a quicar!!");
                        Logger.getLogger(FXMLBibliotecaController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        salaStudentTextField.setItems(salas);
        salaStudentTextField.setValue("7A");
    }    
    
}
