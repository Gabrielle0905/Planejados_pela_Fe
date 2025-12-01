package gui;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class Inicio extends Application {
	
	@Override
	public void start(Stage inicioStage) {
		
		Text titulo = new Text("Bem Vinda!");
		titulo.setId("titulo");
		
		Image img = new Image(getClass().getResource("/images/logo.png").toString());
		ImageView imgView = new ImageView(img);
		imgView.setId("logo");
		
		Button botao = new Button("Entrar");
		botao.setId("botao");
		
		botao.setOnAction(e -> {
			Encontros encontros = new Encontros();
			try {
				encontros.start(inicioStage);
			}catch(Exception ex) {
				ex.printStackTrace();
			}
		});
		
		VBox layout = new VBox(20);
		layout.setAlignment(Pos.CENTER);
		layout.getChildren().addAll(titulo, imgView, botao);
		layout.setId("layout");
		
		Scene scene = new Scene(layout);
		scene.getStylesheets().add(getClass().getResource("/gui/inicio.css").toExternalForm());
		
		inicioStage.setTitle("Planejados pela Fé");
		inicioStage.setScene(scene);
		inicioStage.setFullScreen(true);
		inicioStage.setFullScreenExitHint("");
		inicioStage.show();
	}
	
	public static void main(String[] args) {
        launch(args);
    }
	
	

}