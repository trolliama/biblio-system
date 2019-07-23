/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bibliotecacepi.Alunos;

import java.net.URL;
import java.sql.SQLException;
import java.text.Normalizer;
import java.util.ResourceBundle;

import bibliotecacepi.Aluno;
import bibliotecacepi.Livros.FXMLCadastroLivrosController;
import connections.AlunoDAO;
import connections.LivroDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

/**
 * FXML FXMLCadastroSalaController class
 *
 * @author caio
 */
public class FXMLCadastroAlunosController implements Initializable {

    /**
     * Initializes the controller class.
     */
    private String nome;
    private String sobrenome;
    
    @FXML
    private TextField nomeTextField;
    
    @FXML
    private TextField sobrenomeTextField;

    @FXML
    private Button cadastroButton;

    @FXML
    public void cadastrarAluno(ActionEvent event) throws SQLException {
        nome = nomeTextField.getText();
        sobrenome = sobrenomeTextField.getText();

        System.out.println(nome.length());
        System.out.println(sobrenome.length());

        if(verificaCampos()){
            Aluno aluno = new Aluno();

            aluno.setNome(nome);
            aluno.setSobrenome(sobrenome);

            if (new AlunoDAO().search(nome, sobrenome)) {
                Alert dialog = new Alert(Alert.AlertType.INFORMATION);

                dialog.setTitle("Aluno já cadastrado");
                dialog.setHeaderText("Aluno cadastrado");
                dialog.setContentText("O aluno já está cadastrado. Deseja cadastrar mesmo assim?");

                dialog.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.OK) {
                        try {
                            new AlunoDAO().adicionaAluno(aluno);
                        } catch (SQLException ex) {
                            Logger.getLogger(FXMLCadastroLivrosController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                });

                return;
            }

            new AlunoDAO().adicionaAluno(aluno);

            Stage stage = (Stage) cadastroButton.getScene().getWindow();
            stage.close();

        } else{
            callDialog();
        }
    }
    
    public boolean verificaCampos(){
        boolean safe = true;
        String pattern_str = "[a-zA-Z]+";
        Pattern regex = Pattern.compile(pattern_str);

        String parsed_name = Normalizer.normalize(nome, Normalizer.Form.NFKD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
        String parsed_sobrenome =  Normalizer.normalize(sobrenome, Normalizer.Form.NFKD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");

        Matcher nome_matches = regex.matcher(parsed_name);
        Matcher sobrenome_matches = regex.matcher(parsed_sobrenome);

        if(!nome_matches.matches() | !sobrenome_matches.matches()){
            safe = false;
        }
        
        return safe;
    }
    
    public void callDialog(){
        Alert dialog = new Alert(Alert.AlertType.ERROR);
        
        dialog.setTitle("Informação dos campos");
        dialog.setHeaderText("Campos incorretos");
        dialog.setContentText("Campos não foram preenchidos, contém símbolos ou contém números");
        dialog.showAndWait();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
}
