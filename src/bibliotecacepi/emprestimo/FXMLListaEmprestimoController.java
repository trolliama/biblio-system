package bibliotecacepi.emprestimo;

import bibliotecacepi.Emprestimo;
import connections.EmprestimoDAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

public class FXMLListaEmprestimoController implements Initializable {
    private ObservableList<Emprestimo> data = FXCollections.observableArrayList();

    @FXML
    private TableColumn columnEmprestado;

    @FXML
    private TableColumn columnLivro;

    @FXML
    private TableColumn columnVolume;

    @FXML
    private TableColumn columnAluno;

    @FXML
    private TableColumn columnSala;

    @FXML
    private TableColumn columnDevolver;

    @FXML
    private TableView table;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {

            data.addAll(new EmprestimoDAO().getAll());

            columnEmprestado.setCellValueFactory(
                    new PropertyValueFactory<Emprestimo, Date>("data_emprestimo"));


            columnLivro.setCellValueFactory(
                    new PropertyValueFactory<Emprestimo, String>("titulo_livro"));

            columnVolume.setCellValueFactory(
                    new PropertyValueFactory<Emprestimo, Integer>("volume_livro"));

            columnAluno.setCellValueFactory(
                    new PropertyValueFactory<Emprestimo, String>("aluno"));

            columnSala.setCellValueFactory(
                    new PropertyValueFactory<Emprestimo, String>("sala"));

            columnDevolver.setCellValueFactory(
                    new PropertyValueFactory<Emprestimo, Date>("data_devolucao"));

            this.table.setItems(data);
            setTableSpecs();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setTableSpecs(){
        table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        table.setEditable(true);

        table.setOnKeyPressed( new EventHandler<KeyEvent>()
        {
            public void handle(final KeyEvent keyEvent){
                ObservableList<Emprestimo> selectedRows = table.getSelectionModel().getSelectedItems();

                if ( selectedRows != null )
                {
                    if ( keyEvent.getCode().equals( KeyCode.DELETE ) )
                    {
                        //Delete or whatever you like:
                        ArrayList<Emprestimo> rows = new ArrayList<>(selectedRows);
                        Alert dialog = new Alert(Alert.AlertType.ERROR);

                        dialog.setTitle("Deletar");
                        dialog.setHeaderText("Deletar empréstimos? ");
                        dialog.setContentText("Tem certeza que deseja \ndeletar esses empréstimos?");
                        dialog.showAndWait().ifPresent(response -> {
                            if (response == ButtonType.OK) {
                                deleteOnTable(rows);
                            }
                        });
                    }
                }
            }
        } );
    }

    private void deleteOnTable(ArrayList<Emprestimo> rows){
        for (Emprestimo emprestimo : rows) {
            try {
                new EmprestimoDAO().deleteEmprestimo(emprestimo.getId());
                table.getItems().remove(emprestimo);

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
