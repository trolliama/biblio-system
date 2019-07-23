package bibliotecacepi.Alunos;

import bibliotecacepi.Aluno;
import bibliotecacepi.Livro;
import connections.AlunoDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class FXMLListaAlunosController implements Initializable {
    private ObservableList<Aluno> data = FXCollections.observableArrayList();
    @FXML
    private TableView table;

    @FXML
    private TableColumn columnNome;

    @FXML
    private TableColumn columnSobrenome;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            data.addAll(new AlunoDAO().getAlunos());

            columnNome.setCellValueFactory(
                    new PropertyValueFactory<Aluno, String>("nome"));
            columnSobrenome.setCellValueFactory(
                    new PropertyValueFactory<Aluno, String>("sobrenome"));

            table.setItems(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
