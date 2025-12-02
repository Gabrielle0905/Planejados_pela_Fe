package gui;


import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;

public class CadastrarMaes{
	
	public void mostrar(Stage maesStage) {
		Button btencontros = new Button ("Encontros");
		Button btcadastromae = new Button ("Cadastrar Mães");
		Button btaniversariantes = new Button ("Aniversariantes");
		
		HBox topo = new HBox(20);
		topo.setAlignment(Pos.CENTER);
		topo.getChildren().addAll(btencontros, btcadastromae, btaniversariantes);
		topo.setId("topo");
		
		btencontros.setOnAction(e -> {
			Encontros tela= new Encontros();
			try {
				tela.mostrar(maesStage);
			}catch(Exception ex) {
				ex.printStackTrace();
			}
		});
		
		btcadastromae.setOnAction(e -> {

		});
		
		btaniversariantes.setOnAction(e -> {
			Aniversariantes tela = new Aniversariantes();
			try {
				tela.mostrar(maesStage);
			}catch(Exception ex) {
				ex.printStackTrace();
			}
		});
	}
	
}