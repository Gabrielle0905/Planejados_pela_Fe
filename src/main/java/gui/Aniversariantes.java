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
import controller.MaeController;
import model.Mae;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.Month;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class Aniversariantes {
    
    private MaeController maeController = new MaeController();
    
    public void mostrar(Stage aniversarioStage) {
        
        Button btencontros = new Button("Encontros");
        btencontros.getStyleClass().add("botaotopo");
        
        Label barra = new Label("|");
        barra.getStyleClass().add("barra");
        
        Button btcadastromae = new Button("Cadastrar Mães");
        btcadastromae.getStyleClass().add("botaotopo");
        
        Label barra2 = new Label("|");
        barra2.getStyleClass().add("barra");

        Button btaniversariantes = new Button("Aniversariantes");
        btaniversariantes.getStyleClass().add("botaotopo");

        HBox topo = new HBox(40);
        topo.setAlignment(Pos.CENTER);
        topo.getChildren().addAll(btencontros, barra, btcadastromae, barra2, btaniversariantes);
        topo.setId("topo");
        
        btencontros.setOnAction(e -> {
            Encontros tela = new Encontros();
            try {
                tela.mostrar(aniversarioStage);
            } catch(Exception ex) {
                ex.printStackTrace();
            }
        });
        
        btcadastromae.setOnAction(e -> {
            CadastrarMaes tela = new CadastrarMaes();
            try {
                tela.mostrar(aniversarioStage);
            } catch(Exception ex) {
                ex.printStackTrace();
            }
        });
        
        Text titulo = new Text("ANIVERSARIANTES");
        titulo.setId("titulo");
        
        LocalDate hoje = LocalDate.now();
        Month mesAtual = hoje.getMonth();
        String mesNome = mesAtual.getDisplayName(
            java.time.format.TextStyle.FULL, 
            new Locale("pt", "BR")
        ).toUpperCase();
        
        Text subtitulo = new Text("Mês de " + mesNome);
        subtitulo.setId("subtitulo");
        
        List<Mae> todasMaes = maeController.listarMaes();
        
        List<Mae> aniversariantesMes = todasMaes.stream()
            .filter(mae -> mae.getDataNasci() != null)
            .filter(mae -> mae.getDataNasci().getMonth() == mesAtual)
            .sorted((m1, m2) -> m1.getDataNasci().getDayOfMonth() - m2.getDataNasci().getDayOfMonth())
            .collect(Collectors.toList());
        
        VBox card = new VBox(15); 
        card.setId("card");
        card.setPrefWidth(500);
        card.setPadding(new Insets(30));
        card.setAlignment(Pos.TOP_CENTER);
        
        if (aniversariantesMes.isEmpty()) {
            Label mensagem = new Label("Não há aniversariantes este mês");
            mensagem.setId("mensagem");
            card.getChildren().add(mensagem);
        } else {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM");
            
            for (Mae mae : aniversariantesMes) {
                String dataFormatada = mae.getDataNasci().format(formatter);
                String nome = mae.getNome();
                
                Label info = new Label(dataFormatada + " - " + nome);
                info.getStyleClass().add("info-aniversario");
                
                card.getChildren().add(info);
            }
        }
        
        VBox layout = new VBox(30);
        layout.setAlignment(Pos.TOP_CENTER);
        layout.setPadding(new Insets(50, 0, 0, 0));
        layout.setId("layout");
        layout.setFillWidth(false);
        
        layout.getChildren().addAll(titulo, subtitulo, card);
        
        BorderPane root = new BorderPane();
        root.setTop(topo);
        root.setCenter(layout);
        
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/gui/encontros.css").toExternalForm());
        
        aniversarioStage.setScene(scene);
        aniversarioStage.setTitle("Planejados pela Fé");
        aniversarioStage.setFullScreen(true);
    }
}