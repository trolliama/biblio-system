package bibliotecacepi.Livros;

import bibliotecacepi.Livro;
import bibliotecacepi.Autor;
import bibliotecacepi.Editora;

import connections.LivroDAO;
import connections.AutorDAO;
import connections.EditoraDAO;

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

public class FXMLListaLivrosController implements Initializable {
    private ObservableList<Livro> data = FXCollections.observableArrayList();

    @FXML
    private TableColumn columnTitulo;

    @FXML
    private TableColumn columnAutor;

    @FXML
    private TableColumn columnEditora;

    @FXML
    private TableColumn columnVolume;

    @FXML
    private TableView table;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {

            data.addAll(new LivroDAO().getLivros());

            columnTitulo.setCellValueFactory(
                    new PropertyValueFactory<Livro, String>("titulo"));
            columnTitulo.setCellFactory(TextFieldTableCell.forTableColumn());
            columnTitulo.setOnEditCommit(
                    new EventHandler<TableColumn.CellEditEvent<Livro, String>>() {
                        @Override
                        public void handle(TableColumn.CellEditEvent event) {
                            String new_value = (String) event.getNewValue();
                            Livro livro = (Livro) event.getTableView().getItems().get(
                                    event.getTablePosition().getRow());

                            try {
                                new LivroDAO().editLivro(livro.getId(), "titulo", new_value);
                                livro.setTitulo(new_value);

                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                    }
            );
            columnAutor.setCellValueFactory(
                    new PropertyValueFactory<Autor, String>("autor"));
            columnAutor.setCellFactory(TextFieldTableCell.forTableColumn());
            columnAutor.setOnEditCommit(
                    new EventHandler<TableColumn.CellEditEvent<Livro, String>>() {
                        @Override
                        public void handle(TableColumn.CellEditEvent event) {
                            String new_value = (String) event.getNewValue();
                            Livro livro = (Livro) event.getTableView().getItems().get(
                                    event.getTablePosition().getRow());
                            int old_autor_id = livro.getAutor_id();
                            try {
                                int id = new AutorDAO().getId(new_value);
                                Autor autor = new Autor();

                                if(id == 0){
                                    autor.setNome(new_value);

                                    new AutorDAO().adiciona(autor);
                                    id = new AutorDAO().getId(autor);
                                }
                                new LivroDAO().editLivro(livro.getId(),"fk_autor_id", id);

                                autor.setNome(new_value);
                                livro.setAutor(autor);

                                new AutorDAO().delete(old_autor_id);
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                    }
            );
            columnEditora.setCellValueFactory(
                    new PropertyValueFactory<Livro, Editora>("editora"));
            columnEditora.setCellFactory(TextFieldTableCell.forTableColumn());
            columnEditora.setOnEditCommit(
                    new EventHandler<TableColumn.CellEditEvent<Livro, String>>() {
                        @Override
                        public void handle(TableColumn.CellEditEvent event) {
                            String new_value = (String) event.getNewValue();
                            Livro livro = (Livro) event.getTableView().getItems().get(
                                    event.getTablePosition().getRow());
                            int old_editora_id = livro.getEditora_id();

                            try {
                                int id = new EditoraDAO().getId(new_value);
                                Editora editora = new Editora();

                                if(id == 0){
                                    editora.setEditora(new_value);

                                    new EditoraDAO().adiciona(editora);
                                    id = new EditoraDAO().getId(editora);
                                }
                                new LivroDAO().editLivro(livro.getId(),"fk_editora_id", id);

                                editora.setEditora(new_value);
                                livro.setEditora(editora);

                                new EditoraDAO().delete(old_editora_id);

                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                    }
            );
            columnVolume.setCellValueFactory(
                    new PropertyValueFactory<Livro, String>("volume"));
            columnVolume.setCellFactory(TextFieldTableCell.forTableColumn());
            columnVolume.setOnEditCommit(
                    new EventHandler<TableColumn.CellEditEvent<Livro, String>>() {
                        @Override
                        public void handle(TableColumn.CellEditEvent event) {
                            String new_value = (String) event.getNewValue();
                            Livro livro = (Livro) event.getTableView().getItems().get(
                                    event.getTablePosition().getRow());

                            try {
                                new LivroDAO().editLivro(livro.getId(), "volume", new_value);
                                livro.setVolume(new_value);

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

        table.setOnKeyPressed( new EventHandler<KeyEvent>()
        {
            public void handle(final KeyEvent keyEvent){
                ObservableList<Livro> selectedRows = table.getSelectionModel().getSelectedItems();

                if ( selectedRows != null )
                {
                    if ( keyEvent.getCode().equals( KeyCode.DELETE ) )
                    {
                        //Delete or whatever you like:
                        ArrayList<Livro> rows = new ArrayList<>(selectedRows);
                        Alert dialog = new Alert(Alert.AlertType.ERROR);

                        dialog.setTitle("Deletar");
                        dialog.setHeaderText("Deletar livros ");
                        dialog.setContentText("Tem certeza que deseja deletar esses livros?");
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

    private void deleteOnTable(ArrayList<Livro> rows){
        for (Livro livro : rows) {
            try {
                if(new LivroDAO().deleteLivro(livro.getId())){
                    Alert dialog = new Alert(Alert.AlertType.ERROR);

                    dialog.setTitle("Emprestado");
                    dialog.setHeaderText("Impossível deletar o livro " +
                            livro.getTitulo() + " volume " + livro.getVolume());
                    dialog.setContentText("Livro está emprestado.");
                    dialog.showAndWait();

                }else {
                    table.getItems().remove(livro);
                    new AutorDAO().delete(livro.getAutor_id());
                    new EditoraDAO().delete(livro.getEditora_id());
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
