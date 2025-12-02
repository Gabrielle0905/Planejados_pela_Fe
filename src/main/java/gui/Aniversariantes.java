package gui;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;

public class Aniversariantes{

	public void mostrar(Stage aniversariantesStage) {
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
				tela.mostrar(aniversariantesStage);
			}catch(Exception ex) {
				ex.printStackTrace();
			}
		});
		
		btcadastromae.setOnAction(e -> {
			CadastrarMaes tela = new CadastrarMaes();
			try {
				tela.mostrar(aniversariantesStage);
			}catch(Exception ex) {
				ex.printStackTrace();
			}
		});
		
		btaniversariantes.setOnAction(e -> {
			
		});
	}
}