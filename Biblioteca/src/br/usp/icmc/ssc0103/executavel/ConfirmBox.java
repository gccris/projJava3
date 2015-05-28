package br.usp.icmc.ssc0103.executavel;
import javafx.scene.*;
import javafx.stage.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;

public class ConfirmBox {
	static int resposta;
	
	public static int display(String title, String message){
		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle(title);
		window.setMinWidth(250);
		
		Label text = new Label();
		text.setText(message);
		
		Button yes = new Button("SIM");
		Button no = new Button("NÃO");
		Button cancel = new Button("CANCELAR");
		
		yes.setOnAction(e -> {
			resposta = 1;
			window.close();
		});
		no.setOnAction(e -> {
			resposta = 0;
			window.close();
		});
		cancel.setOnAction(e -> {
			resposta = 2;
			window.close();
		});
		
		HBox buttons = new HBox(20);
		buttons.getChildren().addAll(yes, no, cancel);
		buttons.setAlignment(Pos.CENTER);
		VBox layout = new VBox(20);
		layout.getChildren().addAll(text, buttons);
		layout.setAlignment(Pos.CENTER);
		
		Scene scene = new Scene(layout);
		window.setScene(scene);
		window.showAndWait();
		
		return resposta;
	}
}
