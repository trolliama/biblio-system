package bibliotecacepi.Salas;

import bibliotecacepi.Sala;

import connections.SalaDAO;

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

public class FXMLListaSalasController implements Initializable {
    private ObservableList<Sala> data = FXCollections.observableArrayList();

    @FXML
    private TableColumn columnSala;

    @FXML
    private TableView table;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {

            data.addAll(new SalaDAO().getSalas());

            columnSala.setCellValueFactory(
                    new PropertyValueFactory<Sala, String>("sala"));
            columnSala.setCellFactory(TextFieldTableCell.forTableColumn());
            columnSala.setOnEditCommit(
                    new EventHandler<TableColumn.CellEditEvent<Sala, String>>() {
                        @Override
                        public void handle(TableColumn.CellEditEvent event) {
                            String new_value = (String) event.getNewValue();
                            Sala sala = (Sala) event.getTableView().getItems().get(
                                    event.getTablePosition().getRow());

                            try {
                                int id = new SalaDAO().getSalaId(sala.getSala());

                                new SalaDAO().editSala(id, new_value);
                                sala.setSala(new_value);

                                //new FXMLCadastroEmprestimoController().setSalasComboBox();
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                    }
            );

            this.table.setItems(data);
            setTableSpecs();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setTableSpecs(){
        table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        table.setEditable(true);

        table.setOnKeyPressed( new EventHandler<KeyEvent>() {
            public void handle(final KeyEvent keyEvent){
                ObservableList<Sala> selectedRows = table.getSelectionModel().getSelectedItems();

                if ( selectedRows != null )
                {
                    if ( keyEvent.getCode().equals( KeyCode.DELETE ) )
                    {
                        ArrayList<Sala> rows = new ArrayList<>(selectedRows);
                        Alert dialog = new Alert(Alert.AlertType.ERROR);

                        dialog.setTitle("Deletar");
                        dialog.setHeaderText("Deletar salas ");
                        dialog.setContentText("Tem certeza que deseja deletar essas salas?");
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

    private void deleteOnTable(ArrayList<Sala> rows){
        for (Sala sala : rows) {
            try {
                int id = new SalaDAO().getSalaId(sala.getSala());
                if(new SalaDAO().deleteSala(id)){
                    Alert dialog = new Alert(Alert.AlertType.ERROR);

                    dialog.setTitle("Deletar");
                    dialog.setHeaderText("Impossível deletar a sala " +
                            sala.getSala());
                    dialog.setContentText("Sala está em um registro de empréstimo.");
                    dialog.showAndWait();

                }else {
                    table.getItems().remove(sala);
                    //new FXMLCadastroEmprestimoController().setSalasComboBox();
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
