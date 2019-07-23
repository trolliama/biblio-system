package bibliotecacepi.Livros;

import bibliotecacepi.Livro;
import connections.LivroDAO;
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

public class FXMLListaLivrosController implements Initializable {
    private ObservableList<Livro> data = FXCollections.observableArrayList();

    @FXML
    private TableView table;

    @FXML
    private TableColumn columnTitulo;

    @FXML
    private TableColumn columnAutor;

    @FXML
    private TableColumn columnEditora;

    @FXML
    private TableColumn columnVolume;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            data.addAll(new LivroDAO().getLivros());

            columnTitulo.setCellValueFactory(
                    new PropertyValueFactory<Livro, String>("titulo"));
            columnAutor.setCellValueFactory(
                    new PropertyValueFactory<Livro, String>("autor"));
            columnEditora.setCellValueFactory(
                    new PropertyValueFactory<Livro, String>("editora"));
            columnVolume.setCellValueFactory(
                    new PropertyValueFactory<Livro, String>("volume"));

            table.setItems(data);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
