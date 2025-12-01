package gui;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.Month;

import dao.EncontroDAO;
import model.Encontro;
import java.util.List;
import java.util.ArrayList;

public class Encontros extends Application{
	
	@Override
	public void start(Stage encontroStage) {
		
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
				tela.start(encontroStage);
			}catch(Exception ex) {
				ex.printStackTrace();
			}
		});
		
		btcadastromae.setOnAction(e -> {
			CadastrarMaes tela = new CadastrarMaes();
			try {
				tela.start(encontroStage);
			}catch(Exception ex) {
				ex.printStackTrace();
			}
		});
		
		btaniversariantes.setOnAction(e -> {
			Aniversariantes tela = new Aniversariantes();
			try {
				tela.start(encontroStage);
			}catch(Exception ex) {
				ex.printStackTrace();
			}
		});
		
		Text titulo = new Text("ENCONTROS");
		titulo.setId("titulo");
		
		Button btCadastrar = new Button("Cadastrar Encontro");
		btCadastrar.setId("botaoCadastrar");
		
		btCadastrar.setOnAction(e -> {
			CadastrarEncontros tela = new CadastrarEncontros();
			try {
				tela.start(encontroStage);
			}catch(Exception ex) {
				ex.printStackTrace();
			}
		});
		
		Text subtitulo = new Text("Próximos Encontros");
		subtitulo.setId("subtitulo");
		
		EncontroDAO encontroDAO = new EncontroDAO();
		List<Encontro> encontros = encontroDAO.listartodos();
		
		LocalDate hoje = LocalDate.now();
		Month mesAtual = hoje.getMonth();
		
		List<Encontro> encontrosProximos = new ArrayList<>();
		for (Encontro e: encontros) {
			if (e.getData().getMonth() == mesAtual) {
				encontrosProximos.add(e);
			}
		}	
		
		VBox card = new VBox(10); 
		card.setId("card");
		card.setPrefWidth(200); 
		card.setAlignment(Pos.CENTER);
		
		for (Encontro e: encontrosProximos) {
			Label id =  new Label("ID: " + e.getIdEncontro());
			Label data = new Label("Data: " + e.getData().toString());
			Button editar = new Button("Editar");
			
			HBox infos = new HBox(10);
			infos.setAlignment(Pos.CENTER_LEFT);
			infos.setId("infos");
			infos.getChildren().addAll(id, data, editar);	
			card.getChildren().add(infos);
		}
		
		Button btTodosEnc = new Button("Todos Encontros");
		btTodosEnc.setId("botaotodos");
		
		btTodosEnc.setOnAction(e -> {
			TodosEncontros tela = new TodosEncontros();
			try {
				tela.start(encontroStage);
			}catch(Exception ex) {
				ex.printStackTrace();
			}
		});
		
		VBox layout = new VBox(20);
		layout.setAlignment(Pos.CENTER);
		layout.setId("layout");
		layout.getChildren().addAll(topo, titulo, btCadastrar, subtitulo, card, btTodosEnc);
		
		Scene scene = new Scene(layout);
		scene.getStylesheets().add(getClass().getResource("/gui/encontros.css").toExternalForm());
		
		encontroStage.setScene(scene);
	}
	
	public static void main(String[]args) {
		launch(args);
	}

}