package gui;

import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.Month;

import dao.EncontroDAO;
import model.Encontro;
import java.util.List;
import java.util.ArrayList;

public class Encontros{
	
	public void mostrar(Stage encontroStage) {
		
		Button btencontros = new Button ("Encontros");
		btencontros.getStyleClass().add("botaotopo");
		
		Label barra = new Label("|");
		barra.getStyleClass().add("barra");
		
		Button btcadastromae = new Button ("Cadastrar Mães");
		btcadastromae.getStyleClass().add("botaotopo");
		
		Label barra2 = new Label("|");
		barra2.getStyleClass().add("barra");

		Button btaniversariantes = new Button ("Aniversariantes");
		btaniversariantes.getStyleClass().add("botaotopo");

		HBox topo = new HBox(40);
		topo.setAlignment(Pos.CENTER);
		topo.getChildren().addAll(btencontros, barra, btcadastromae, barra2, btaniversariantes);
		topo.setId("topo");
		
		btencontros.setOnAction(e -> {
			
		});
		
		btcadastromae.setOnAction(e -> {
			CadastrarMaes tela = new CadastrarMaes();
			try {
				tela.mostrar(encontroStage);
			}catch(Exception ex) {
				ex.printStackTrace();
			}
		});
		
		btaniversariantes.setOnAction(e -> {
			Aniversariantes tela = new Aniversariantes();
			try {
				tela.mostrar(encontroStage);
			}catch(Exception ex) {
				ex.printStackTrace();
			}
		});
		
		Text titulo = new Text("ENCONTROS");
		titulo.setId("titulo");
		
		Button btCadastrar = new Button("Cadastrar Encontro");
		btCadastrar.getStyleClass().add("botao");
		
		btCadastrar.setOnAction(e -> {
			CadastrarEncontros tela = new CadastrarEncontros();
			try {
				tela.mostrar(encontroStage);
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
		
		VBox card = new VBox(20); 
		card.setId("card");
		card.setPrefWidth(500);
		card.setPadding(new Insets(10, 0, 10, 50));
		card.setAlignment(Pos.CENTER);
		
		if (encontrosProximos.isEmpty()) {
			Label mensagem = new Label("Não há encontros este mês");
			mensagem.setId("mensagem");
			card.getChildren().add(mensagem);
		}else {
			for (Encontro e: encontrosProximos) {
				Label id =  new Label("ID: " + e.getIdEncontro());
				Label data = new Label("Data: " + e.getData().toString());
				Button editar = new Button("Editar");

                editar.setOnAction(event -> {
                    EditarEncontros tela = new EditarEncontros();
                    try {
                        tela.mostrar(encontroStage);
                    } catch (Exception ex){
                        ex.printStackTrace();
                    }
                });
				
				HBox infos = new HBox(10);
				infos.setAlignment(Pos.CENTER_LEFT);
				infos.setId("infos");
				infos.getChildren().addAll(id, data, editar);	
				card.getChildren().add(infos);
			}
		}
		
		Button btTodosEnc = new Button("Todos Encontros");
		btTodosEnc.getStyleClass().add("botao");

		
		btTodosEnc.setOnAction(e -> {
			TodosEncontros tela = new TodosEncontros();
			try {
				tela.mostrar(encontroStage);
			}catch(Exception ex) {
				ex.printStackTrace();
			}
		});
		
		VBox layout = new VBox(30);
		layout.setAlignment(Pos.TOP_CENTER);
		layout.setPadding(new Insets(50, 0, 0, 0));
		layout.setId("layout");
		layout.setFillWidth(false);
		layout.getChildren().addAll(titulo, btCadastrar, subtitulo, card, btTodosEnc);
		
		BorderPane root = new BorderPane();
		root.setTop(topo);
		root.setCenter(layout);
		
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/gui/encontros.css").toExternalForm());
		
		encontroStage.setScene(scene);
		encontroStage.setTitle("Planejados pela Fé");
		encontroStage.setFullScreen(true);
	}


}