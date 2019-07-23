package bibliotecacepi.Salas;

import bibliotecacepi.Livros.FXMLCadastroLivrosController;
import connections.SalaDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FXMLCadastroSalaController implements Initializable {
    @FXML
    private TextField salaTextField;

    @FXML
    private Button cadastrarButton;

    @FXML
    public void cadastrarSala(ActionEvent event) throws SQLException {
        String sala = salaTextField.getText();

        if (new SalaDAO().search(sala)) {
            Alert dialog = new Alert(Alert.AlertType.ERROR);

            dialog.setTitle("Sala já cadastrado");
            dialog.setHeaderText("Sala cadastrado");
            dialog.setContentText("A sala já está cadastrado. Deseja cadastrar mesmo assim?");

            dialog.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    try {
                        new SalaDAO().adicionaSala(sala);
                    } catch (SQLException ex) {
                        Logger.getLogger(FXMLCadastroLivrosController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });

            return;
        }
        new SalaDAO().adicionaSala(sala);

        Stage stage = (Stage) cadastrarButton.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
