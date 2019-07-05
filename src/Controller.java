import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

public class Controller {

    public static FXMLLoader loader = Main.loader;

    private MenuItem closeMenuItem;
    private TextField textField, keywordField;
    private TextArea resultArea;
    private RadioButton encryptionRadioButton, decryptionRadioButton;
    private Label resultLabel;
    private Button enterButton, resetButton;

    private void init(){

        closeMenuItem = (MenuItem) loader.getNamespace().get("closeMenuItem");
        closeMenuItem.setOnAction((ActionEvent event) -> { menuItemHandled(0); });

        textField = (TextField) loader.getNamespace().get("textField");
        textField.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> { focusState(newValue, 0); });

        keywordField = (TextField) loader.getNamespace().get("keywordField");
        keywordField.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> { focusState(newValue, 1); });

        encryptionRadioButton = (RadioButton) loader.getNamespace().get("encryptionRadioButton");
        encryptionRadioButton.setOnAction((ActionEvent event) -> { changeKeyLabel(); });

        decryptionRadioButton = (RadioButton) loader.getNamespace().get("decryptionRadioButton");
        decryptionRadioButton.setOnAction((ActionEvent event) -> { changeKeyLabel(); });

        resultLabel = (Label) loader.getNamespace().get("resultLabel");
        resultLabel.setVisible(false);

        resultArea = (TextArea) loader.getNamespace().get("resultArea");
        resultArea.setVisible(false);

        enterButton = (Button) loader.getNamespace().get("enterButton");
        enterButton.setOnAction((ActionEvent event) -> { enterButton(); });

        resetButton = (Button) loader.getNamespace().get("resetButton");
        resetButton.setOnAction((ActionEvent event) -> { resetButton(); });

        this.createLink(loader);
    }

    private void createLink(FXMLLoader loader) {
        Hyperlink my_website = (Hyperlink) loader.getNamespace().get("link");

        Background defaultBackground = new Background(new BackgroundFill(Color.CADETBLUE, new CornerRadii(5), Insets.EMPTY));
        Background hoveredBackground = new Background(new BackgroundFill(Color.DARKCYAN, new CornerRadii(5), Insets.EMPTY));

        my_website.setTextFill(Color.WHITE);
        my_website.setBackground(defaultBackground);

        my_website.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
            my_website.setBackground(hoveredBackground);
        });

        my_website.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
            my_website.setBackground(defaultBackground);
        });

        my_website.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String url = "https://moath.cf/";
                try {
                    java.awt.Desktop.getDesktop().browse(new URI(url));
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void focusState(boolean value, int number) {

        switch (number) {
            case 0:
                if (value) {
                    if (textField.getText().equals("e.g. Hello") || textField.getText().equals("e.g. 72101108108111")) {
                        textField.setText("");
                    } else if (textField.getText().isEmpty()) {
                        textField.setStyle(textField.getStyle() + "; -fx-border-color: silver");
                    }
                } else {
                    if (textField.getText().isEmpty()) {
                        textField.setStyle(textField.getStyle() + "; -fx-border-color: red");
                    } else {
                        // Do Nothing
                    }
                }
                break;
            case 1:
                if (value) {
                    if (keywordField.getText().equals("e.g. ACT")) {
                        keywordField.setText("");
                    } else if (keywordField.getText().isEmpty()) {
                        keywordField.setStyle(keywordField.getStyle() + "; -fx-border-color: silver");
                    }
                } else {
                    if (keywordField.getText().isEmpty()) {
                        keywordField.setStyle(keywordField.getStyle() + "; -fx-border-color: red");
                    } else {
                        // Do Nothing
                    }
                }
                break;
            default:
                // Do nothing
        }
    }

    public Controller() {
        init();
    }

    private void enterButton() {

        if (decryptionRadioButton.isSelected()) {
            if (textField.getText().isEmpty() || textField.getText().equals("e.g. 0-11382-5521-90-10224-48")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Enter your data");
                alert.show();
                return;
            }
            String plaintext;
            try {
                plaintext = new Cryptosystem().decryption(textField.getText(), keywordField.getText());
            } catch (Exception e) {
                showAlert();
                return;
            }
            resultArea.setText(plaintext);
            resultArea.setVisible(true);
            resultLabel.setVisible(true);
        } else {
            if (textField.getText().isEmpty() || textField.getText().equals("e.g. Hello")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Enter your data");
                alert.show();
                return;
            }
            String ciphertext;
            try {
                ciphertext = new Cryptosystem().encryption(textField.getText(), keywordField.getText());
            } catch (Exception e) {
                showAlert();
                return;
            }
            resultArea.setText(ciphertext);
            resultArea.setVisible(true);
            resultLabel.setVisible(true);
        }
    }

    private void showAlert() {

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText("Please make sure for your data");
        alert.show();
        return;
    }

    private void resetButton(){
        textField.setText("e.g. Hello");
        keywordField.setText("e.g. ACT");
        encryptionRadioButton.setSelected(true);
        changeKeyLabel();
        resultArea.setVisible(false);
        resultLabel.setVisible(false);
    }

    private void changeKeyLabel(){
        if (encryptionRadioButton.isSelected()){
            if (textField.getText().isEmpty())
                textField.setStyle(textField.getStyle() + "; -fx-border-color: silver");
            if (!textField.getText().isEmpty() && textField.getText().equals("e.g. Hello"))
                textField.setText("e.g. Hello");
        } else {
            if (textField.getText().isEmpty())
                textField.setStyle(textField.getStyle() + "; -fx-border-color: silver");
            if (!textField.getText().isEmpty() && textField.getText().equals("e.g. ATTACKTO"))
                textField.setText("e.g. ATTACKTO");
        }
    }

    private void menuItemHandled(int number) {
        switch (number) {
            case 0: // Close
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirm Exit");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to exit ?");
                ButtonType exitButton = new ButtonType("Exit", ButtonBar.ButtonData.OK_DONE);
                ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
                alert.getButtonTypes().setAll(exitButton, cancelButton);
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == exitButton){
                    System.exit(0);
                    // ... user chose "One"
                } else {
                    // Do Nothing ...
                }
                break;
            default:
                // Do Nothing ...
                break;
        }
    }
}