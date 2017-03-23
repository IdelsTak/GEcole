/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.centre;

import DAO.DAO;
import DAO.EleveDAO;
import DAO.ParentDAO;
import GUI.LoginController;
import GUI.Tests;
import Models.Eleve;
import Models.Parent;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;
import main_pack.Main_class;

/**
 * FXML Controller class
 *
 * @author Chazzone
 */
public class ajoutEleveController implements Initializable {

    @FXML
    private ImageView image;

    @FXML
    private JFXComboBox<String> ville;

    @FXML
    private JFXTextField telP;

    @FXML
    private JFXRadioButton garcon;

    @FXML
    private JFXTextField profP;

    @FXML
    private JFXTextField addresse;

    @FXML
    private JFXTextField profM;

    @FXML
    private JFXRadioButton fille;

    @FXML
    private JFXTextField nom;

    @FXML
    private JFXTextField nomM;

    @FXML
    private JFXTextField emailP;

    @FXML
    private JFXDatePicker dnaissance;

    @FXML
    private JFXTextField codepostal;

    @FXML
    private JFXTextField nomP;

    @FXML
    private JFXTextField lnaissance;

    @FXML
    private JFXTextField prenom;

    @FXML
    private JFXTextField email;
    @FXML
    private JFXComboBox<Integer> niveau;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ToggleGroup group = new ToggleGroup();
        garcon.setToggleGroup(group);
        fille.setToggleGroup(group);
        garcon.setSelected(true);
        ville.setItems(FXCollections.observableArrayList(
                "Ariana", "Beja", "Ben Arous", "Bizerte", "Gabes", "Gafsa", "Jendouba", "Kairouan", "Kasserine", "Kebili", "Kef", "Manouba", "Medenine", "Monastir", "Nabeul", "Sfax", "Sidi Bouzid", "Siliana", "Sousse", "Tatatouine", "Tozeur", "Tunis", "Zaghouan"
        ));
        niveau.setItems(FXCollections.observableArrayList(1,2,3,4,5,6));
    }

    @FXML
    void click_image(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une image pour l'eleve");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Images", "*.jpeg", "*.png", "*.jpg"),
                new FileChooser.ExtensionFilter("JPEG", "*.jpeg"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
        );
        File file = fileChooser.showOpenDialog(new javafx.stage.Stage());
        if (file != null) {
            try {
                BufferedImage bufferedImage = ImageIO.read(file);
                Image i = SwingFXUtils.toFXImage(bufferedImage, null);
                image.setImage(i);
            } catch (IOException x) {
            }
        }
    }

    @FXML private void click_ajouter(ActionEvent event) {
        String erreur = "";
        if (!Tests.email(email.getText())) {
            erreur += "Erreur Email Eleve\n";
            email.clear();
            email.setPromptText("Email (*@*.*)");
            email.setStyle("-fx-prompt-text-fill:red");
        }
        if (!Tests.telephone(telP.getText())) {
            erreur += "Erreur Numero Telephone\n";
            telP.clear();
            telP.setPromptText("Téléphone (8 Chiffre)");
            telP.setStyle("-fx-prompt-text-fill:red");
        }
        if (!Tests.chaine(nom.getText(), 20, false)) {
            erreur += "Erreur Nom Eleve\n";
            nom.clear();
            nom.setPromptText("Nom (Que des lettres)");
            nom.setStyle("-fx-prompt-text-fill:red");
        }
        if (!Tests.chaine(prenom.getText(), 20, false)) {
            erreur += "Erreur Prenom Eleve\n";
            prenom.clear();
            prenom.setPromptText("Prenom (Que des lettres)");
            prenom.setStyle("-fx-prompt-text-fill:red");
        }
        if (!Tests.chaine(addresse.getText(), 20, true)) {
            erreur += "Erreur addresse\n";
            addresse.clear();
            addresse.setPromptText("Adresse (Trop long)");
            addresse.setStyle("-fx-prompt-text-fill:red");
        }
        if (!Tests.code_postal(codepostal.getText())) {
            erreur += "Erreur Code Postal\n";
            codepostal.clear();
            codepostal.setPromptText("Code Postal (4 Chiffre seulement)");
            codepostal.setStyle("-fx-prompt-text-fill:red");
        }
        LocalDate d = dnaissance.getValue();
        if (d == null) {
            erreur += "Erreur Date , Selectionner une\n";
            dnaissance.setValue(null);
            dnaissance.setPromptText("Date de Naissance");
            dnaissance.setStyle("-fx-prompt-text-fill:red");
        }
        if (d != null && !Tests.date_naissance(Date.from(d.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()))) {
            erreur += "Erreur Date Naissance invalide\n";
            dnaissance.setValue(null);
            dnaissance.setPromptText("Date de Naissance");
            dnaissance.setStyle("-fx-prompt-text-fill:red");
        }
        if (!Tests.chaine(lnaissance.getText(), 20, false)) {
            erreur += "Erreur Lieu Naissance\n";
            lnaissance.clear();
            lnaissance.setPromptText("Lieu Naissance (Saisir que des lettres)");
            lnaissance.setStyle("-fx-prompt-text-fill:red");
        }
        if (ville.getSelectionModel().getSelectedIndex() == -1) {
            erreur += "Erreur Ville : selectionner une svp\n";
            ville.setValue("");
            ville.setPromptText("Ville");
            ville.setStyle("-fx-prompt-text-fill:red");
        }
        if (niveau.getSelectionModel().getSelectedIndex() == -1) {
            erreur += "Erreur Ville : selectionner une svp\n";
            niveau.setValue(-1);
            niveau.setPromptText("Niveau");
            niveau.setStyle("-fx-prompt-text-fill:red");
        }
        // donnee des parents
        if (!Tests.chaine(nomP.getText(), 20, false)) {
            erreur += "Saisir que des lettres";
            nomP.clear();
            nomP.setPromptText("Nom du Père (Saisir que des lettres)");
            nomP.setStyle("-fx-prompt-text-fill:red");
        }

        if (!Tests.chaine(nomM.getText(), 20, false)) {
            erreur += "Erreur Nom Mere\n";
            nomM.clear();
            nomM.setPromptText("Nom de la Mère(Saisir que des lettres)");
            nomM.setStyle("-fx-prompt-text-fill:red");
        }
        if (!Tests.chaine(profM.getText(), 20, false)) {
            erreur += "Erreur Profession Mere\n";
            profM.clear();
            profM.setPromptText("Prefession de la Mère (Saisir que des lettres)");
            profM.setStyle("-fx-prompt-text-fill:red");
        }
        if (!Tests.chaine(profP.getText(), 20, false)) {
            erreur += "Erreur Profession Pere\n";
            profP.clear();
            profP.setPromptText("Profession du Père (Saisir que des lettres)");
            profP.setStyle("-fx-prompt-text-fill:red");
        }
        if (!Tests.email(emailP.getText())) {
            erreur += "Erreur Email parent\n";
            emailP.clear();
            emailP.setPromptText("Email Parents (Erreur Format (*@*.*)");
            emailP.setStyle("-fx-prompt-text-fill:red");
        }
        if (!Tests.telephone(telP.getText())) {
            erreur += "Erreur telephone Parent\n";
            telP.clear();
            telP.setPromptText("Telephone Parent (Numéro de 8 chiffres)");
            telP.setStyle("-fx-prompt-text-fill:red");
        }
        if (erreur.isEmpty()) {
            DAO elevedao = new EleveDAO();
            Eleve eleve = new Eleve();
            // ajout parent
            Parent p = new Parent(-1,nomP.getText(),profP.getText(),nomM.getText(),profM.getText(),telP.getText(),emailP.getText());
            ParentDAO daop = new ParentDAO();
            daop.create(p);
            int id_parent = daop.dernier();
            if (id_parent == -1)
                return;
            eleve.setNom(nom.getText());
            eleve.setPrenom(prenom.getText());
            eleve.setAdresse(addresse.getText());
            eleve.setVille(ville.getSelectionModel().getSelectedItem());
            eleve.setCodeP(Integer.parseInt(codepostal.getText()));
            eleve.setDateNaiss(Date.from(d.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
            eleve.setLieuNaiss(lnaissance.getText());
            if (garcon.isSelected()) {
                eleve.setSex("M");
            } else {
                eleve.setSex("F");
            }
            eleve.setEmail(email.getText());
            eleve.setRef_niv(0);
            eleve.setRef_c(0);
            eleve.setRef_p(id_parent);
            String niv= niveau.getSelectionModel().getSelectedItem()+"2016";
            eleve.setRef_niv(Integer.parseInt(niv));

            if (elevedao.create(eleve)) {
                Alert conf = new Alert(Alert.AlertType.INFORMATION);
                conf.setTitle("Success!");
                conf.setHeaderText("L'operation de l'ajout dde l'élève est effectuée avec succés");
                conf.setContentText("1 tuple eleve ajouter a la base de donnee\n"
                                  + "1 tuple parent ajouter a la base de donnee");
                conf.showAndWait();
                reinit();

            } else {
                Alert conf = new Alert(Alert.AlertType.INFORMATION);
                conf.setTitle("Erreur!");
                conf.setHeaderText("Une erreur s'est produite lors de l'ajout");
                conf.setContentText("Aucun élève n'a été ajouté à la base de données :(");
                conf.showAndWait();
            }
    
        } else {
            System.out.println(erreur);
            Alert conf = new Alert(Alert.AlertType.INFORMATION);
            conf.setTitle("Erreur!");
            conf.setHeaderText("des erreur sont produite lors de l'ajout");
            conf.setContentText(erreur + "\n\n\n\n\n\n\nVerifiez les donnes et reessayer ");
            conf.showAndWait();
        }
}


    @FXML private void click_reinitialiser(ActionEvent event) {
        reinit();
    }

    private void reinit() {
        garcon.setSelected(true);
        nom.setText("");
        prenom.setText("");
        ville.getSelectionModel().clearSelection();
        ville.setValue("");
        ville.setPromptText("Ville Naissance");
        niveau.getSelectionModel().clearSelection();
        niveau.setValue(-1);
        niveau.setPromptText("Niveau");
        addresse.setText("");
        codepostal.setText("");
        nomP.setText("");
        profP.setText("");
        nomM.setText("");
        profM.setText("");
        telP.setText("");
        email.setText("");
        lnaissance.setText("");
        emailP.setText("");
        try {
            BufferedImage bufferedImage = ImageIO.read(getClass().getResource("../image/default-eleve.jpg"));
            Image i = SwingFXUtils.toFXImage(bufferedImage, null);
            image.setImage(i);
        } catch (IOException x) {
            System.out.println("Erreur : " + x);
        }

    }

    @FXML
    private void click_retour(ActionEvent event) {
        try {
            URL loader = getClass().getResource("../mainwindow.fxml");
            AnchorPane middle = FXMLLoader.load(loader);

            BorderPane border = Main_class.getRoot();
            border.setCenter(middle);
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void click_trouver(ActionEvent event) {
        try {
            URL loader = getClass().getResource("gestionEleve.fxml");
            AnchorPane middle = FXMLLoader.load(loader);

            BorderPane border = Main_class.getRoot();
            border.setCenter(middle);
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
