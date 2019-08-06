/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bibliotecacepi.emprestimo;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import bibliotecacepi.Alunos.CadastroAlunos;
import bibliotecacepi.Alunos.ListaAlunos;
import bibliotecacepi.Livros.ListaLivros;
import bibliotecacepi.Sala;
import bibliotecacepi.Salas.CadastroSalas;
import bibliotecacepi.Salas.ListaSalas;

import connections.AlunoDAO;
import connections.EmprestimoDAO;
import connections.LivroDAO;
import connections.SalaDAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import bibliotecacepi.Livros.CadastroLivros;
import javafx.stage.Stage;

/**
 *
 * @author caio
 */
public class FXMLCadastroEmprestimoController implements Initializable {

    ObservableList<String> salas = FXCollections.observableArrayList();

    @FXML
    private ComboBox salaComboBox;
    
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
    private void openCadastroSalas(ActionEvent event) throws Exception {
        CadastroSalas window = new CadastroSalas();
        window.start(new Stage());
    }

    @FXML
    private void openListaAlunos(ActionEvent event) throws IOException {
        ListaAlunos window = new ListaAlunos();
        window.start(new Stage());
    }

    @FXML
    private void openListaLivros(ActionEvent event) throws IOException {
        ListaLivros window = new ListaLivros();
        window.start(new Stage());
    }

    @FXML
    private void openListaSalas(ActionEvent event) throws IOException{
        ListaSalas window = new ListaSalas();
        window.start(new Stage());
    }

    @FXML
    private void openListaEmprestimos(ActionEvent event) throws Exception {
        ListaEmprestimo window = new ListaEmprestimo();
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
            dialog.setContentText("Campos não foram preenchidos.\nCampo volume contém letras");
            dialog.showAndWait();

            return;                    //write some here;

        }

        String titulo = titleBookTextField.getText();
        String volume = volumeTextField.getText();
        String nome_aluno = nameStudentTextField.getText();
        String sobrenome_aluno = sobrenomeStudentTextField.getText();
        String sala = (String) salaComboBox.getValue();

        if (!verificaLivroCadastrado(titulo, volume) || !verificaNameStudent(nome_aluno, sobrenome_aluno)){
            return;
        }

        callEmprestimoDAO(titulo, volume, nome_aluno, sobrenome_aluno, sala);
    }

    private void callEmprestimoDAO(String titulo, String volume, String nome_aluno, String sobrenome_aluno, String sala) throws SQLException {
        int book_id = new LivroDAO().getLivroId(titulo, Integer.parseInt(volume));
        int student_id = new AlunoDAO().getAlunoId(nome_aluno, sobrenome_aluno);
        int sala_id = new SalaDAO().getSalaId(sala);

        new EmprestimoDAO().adicionaEmprestimo(book_id, student_id, sala_id);

        Alert dialog = new Alert(Alert.AlertType.INFORMATION);

        dialog.setTitle("Informação do empréstimo");
        dialog.setHeaderText("Empréstimo efetuado!");
        dialog.setContentText("O empréstimo foi efetuado com sucesso.\n Entrega: daqui a 7 dias");
        dialog.showAndWait();
    }

    private boolean verificaCampos() {
        boolean campos_vazios = false;

        List<String> fields = new ArrayList<>();

        fields.add(titleBookTextField.getText());
        fields.add(nameStudentTextField.getText());
        fields.add(sobrenomeStudentTextField.getText());

        for (int i = 0; i < 3; i++) {
            if ("".equals(fields.get(i))) {
                campos_vazios = true;
                break;
            }
        }

        return campos_vazios;
    }

    private boolean verificaLivroCadastrado(String titulo, String volume) throws SQLException {
        boolean exists = true;
        if (!new LivroDAO().search(titulo, Integer.parseInt(volume))) {
            exists = false;
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
                        Logger.getLogger(FXMLCadastroEmprestimoController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
        }

        return exists;
    }

    private boolean verificaNameStudent(String nome, String sobrenome) throws SQLException {
        boolean exists = true;
        if (!new AlunoDAO().search(nome, sobrenome)) {
            exists = false;
            Alert dialog = new Alert(Alert.AlertType.INFORMATION);

            dialog.setTitle("Informação dos campos");
            dialog.setHeaderText("Aluno não existe");
            dialog.setContentText("Aluno não existe deseja cadastra-lo? ");

            dialog.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    try {
                        CadastroAlunos window = new CadastroAlunos();
                        window.start(new Stage());

                    } catch (IOException ex) {
                        Logger.getLogger(FXMLCadastroEmprestimoController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
        }

        return exists;
    }

    public void setSalasComboBox() throws SQLException {
        List<Sala> salas_rs = new SalaDAO().getSalas();
        List<String> salas_name = new ArrayList<>();

        salas_rs.forEach(sala -> salas_name.add(sala.getSala()));

        salas.addAll(salas_name);

        salaComboBox.setItems(salas);
        salaComboBox.setValue(salas_name.get(0));
    }

    public void checkForDelays() throws SQLException {
       List<Date> dates = new EmprestimoDAO().getAllDevolutionDate();

        for (Date date : dates) {
            if(date.before(new Date())){
                Alert dialog = new Alert(Alert.AlertType.WARNING);

                dialog.setTitle("Livro Atrasado");
                dialog.setHeaderText("Aluno devendo livro");
                dialog.setContentText("Alunos com livros atrasados! ");

                dialog.showAndWait();
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        try {
            setSalasComboBox();
            checkForDelays();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }    
    
}
