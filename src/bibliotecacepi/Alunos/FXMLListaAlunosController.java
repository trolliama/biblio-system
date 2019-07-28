package bibliotecacepi.Alunos;

import bibliotecacepi.Aluno;
import connections.AlunoDAO;
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
            columnNome.setCellFactory(TextFieldTableCell.forTableColumn());
            columnNome.setOnEditCommit(
                    new EventHandler<TableColumn.CellEditEvent<Aluno, String>>() {
                        @Override
                        public void handle(TableColumn.CellEditEvent event) {
                            String new_value = (String) event.getNewValue();
                            Aluno aluno = (Aluno) event.getTableView().getItems().get(
                                    event.getTablePosition().getRow());

                            try {
                                int id = new AlunoDAO().getAlunoId(aluno.getNome(),aluno.getSobrenome());

                                new AlunoDAO().editAluno(id, "nome", new_value);
                                aluno.setNome(new_value);

                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                    }
            );

            columnSobrenome.setCellValueFactory(
                    new PropertyValueFactory<Aluno, String>("sobrenome"));
            columnSobrenome.setCellFactory(TextFieldTableCell.forTableColumn());
            columnSobrenome.setOnEditCommit(
                    new EventHandler<TableColumn.CellEditEvent<Aluno, String>>() {
                        @Override
                        public void handle(TableColumn.CellEditEvent event) {
                            String new_value = (String) event.getNewValue();
                            Aluno aluno = (Aluno) event.getTableView().getItems().get(
                                    event.getTablePosition().getRow());

                            try {
                                int id = new AlunoDAO().getAlunoId(aluno.getNome(),aluno.getSobrenome());

                                new AlunoDAO().editAluno(id, "sobrenome", new_value);
                                aluno.setSobrenome(new_value);

                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                    }
            );

            table.setItems(data);
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
                ObservableList<Aluno> selectedRows = table.getSelectionModel().getSelectedItems();

                if ( selectedRows != null )
                {
                    if ( keyEvent.getCode().equals( KeyCode.DELETE ) )
                    {
                        //Delete or whatever you like:
                        ArrayList<Aluno> rows = new ArrayList<>(selectedRows);
                        Alert dialog = new Alert(Alert.AlertType.ERROR);

                        dialog.setTitle("Deletar");
                        dialog.setHeaderText("Deletar alunos ");
                        dialog.setContentText("Tem certeza que deseja deletar esses alunos?");
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

    private void deleteOnTable(ArrayList<Aluno> rows){
        for (Aluno aluno : rows) {
            try {
                int id = new AlunoDAO().getAlunoId(aluno.getNome(), aluno.getSobrenome());
                if(new AlunoDAO().deleteAluno(id)){
                    Alert dialog = new Alert(Alert.AlertType.ERROR);

                    dialog.setTitle("Devendo");
                    dialog.setHeaderText("Impossível deletar o aluno " +
                            aluno.getNome() + " " + aluno.getSobrenome());
                    dialog.setContentText("Aluno ainda está devendo.");
                    dialog.showAndWait();

                }else {
                    table.getItems().remove(aluno);
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
