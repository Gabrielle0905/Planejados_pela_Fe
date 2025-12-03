package gui;

import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import controller.MaeController;
import model.Mae;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class CadastrarMaes {
    
    private MaeController maeController = new MaeController();
    
    public void mostrar(Stage cadastroStage) {
        
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
                tela.mostrar(cadastroStage);
            } catch(Exception ex) {
                ex.printStackTrace();
            }
        });
        
        btaniversariantes.setOnAction(e -> {
            Aniversariantes tela = new Aniversariantes();
            try {
                tela.mostrar(cadastroStage);
            } catch(Exception ex) {
                ex.printStackTrace();
            }
        });
        
        Text titulo = new Text("CADASTRAR MÃES");
        titulo.setId("titulo");
        
        Label labelNome = new Label("Nome:");
        labelNome.getStyleClass().add("label-form");
        
        TextField campoNome = new TextField();
        campoNome.setPromptText("Digite o nome completo");
        campoNome.setPrefWidth(400);
        campoNome.getStyleClass().add("campo-texto");
        
        HBox linhaNome = new HBox(20);
        linhaNome.setAlignment(Pos.CENTER_LEFT);
        linhaNome.getChildren().addAll(labelNome, campoNome);
        
        Label labelData = new Label("Data de aniversário:");
        labelData.getStyleClass().add("label-form");
        
        TextField campoData = new TextField();
        campoData.setPromptText("DD/MM/AAAA");
        campoData.setPrefWidth(150);
        campoData.getStyleClass().add("campo-texto");
        
        HBox linhaData = new HBox(20);
        linhaData.setAlignment(Pos.CENTER_LEFT);
        linhaData.getChildren().addAll(labelData, campoData);
        
        Label labelTelefone = new Label("Telefone:");
        labelTelefone.getStyleClass().add("label-form");
        
        TextField campoTelefone = new TextField();
        campoTelefone.setPromptText("Apenas números");
        campoTelefone.setPrefWidth(200);
        campoTelefone.getStyleClass().add("campo-texto");
        
        HBox linhaTelefone = new HBox(20);
        linhaTelefone.setAlignment(Pos.CENTER_LEFT);
        linhaTelefone.getChildren().addAll(labelTelefone, campoTelefone);
        
        Label labelEndereco = new Label("Endereço:");
        labelEndereco.getStyleClass().add("label-form");
        
        TextField campoEndereco = new TextField();
        campoEndereco.setPromptText("Rua, número, bairro, cidade");
        campoEndereco.setPrefWidth(400);
        campoEndereco.getStyleClass().add("campo-texto");
        
        HBox linhaEndereco = new HBox(20);
        linhaEndereco.setAlignment(Pos.CENTER_LEFT);
        linhaEndereco.getChildren().addAll(labelEndereco, campoEndereco);
        
        Label mensagemErro = new Label();
        mensagemErro.getStyleClass().add("mensagem-erro");
        
        Button btCadastrar = new Button("Cadastrar");
        btCadastrar.getStyleClass().add("botao");
        
        btCadastrar.setOnAction(e -> {
            boolean sucesso = cadastrarMae(
                campoNome.getText(),
                campoData.getText(),
                campoTelefone.getText(),
                campoEndereco.getText(),
                mensagemErro
            );
            
            if (sucesso) {
                campoNome.clear();
                campoData.clear();
                campoTelefone.clear();
                campoEndereco.clear();
            }
        });
        
        VBox card = new VBox(20);
        card.setId("card");
        card.setPrefWidth(600);
        card.setPadding(new Insets(30));
        card.setAlignment(Pos.CENTER);
        
        card.getChildren().addAll(
            linhaNome,
            linhaData,
            linhaTelefone,
            linhaEndereco,
            mensagemErro,
            btCadastrar
        );
        
        VBox layout = new VBox(30);
        layout.setAlignment(Pos.TOP_CENTER);
        layout.setPadding(new Insets(50, 0, 0, 0));
        layout.setId("layout");
        layout.setFillWidth(false);
        
        layout.getChildren().addAll(titulo, card);
        
        BorderPane root = new BorderPane();
        root.setTop(topo);
        root.setCenter(layout);
        
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/gui/cadastrarmaes.css").toExternalForm());
        
        cadastroStage.setScene(scene);
        cadastroStage.setTitle("Planejados pela Fé");
        cadastroStage.setFullScreen(true);
    }
    
    private boolean cadastrarMae(String nome, String dataStr, String telefoneStr, String endereco, Label mensagemErro) {
        if (nome == null || nome.trim().isEmpty()) {
            mensagemErro.setText("Erro: Nome é obrigatório!");
            return false;
        }
        
        if (dataStr == null || dataStr.trim().isEmpty()) {
            mensagemErro.setText("Erro: Data de aniversário é obrigatória!");
            return false;
        }
        
        if (telefoneStr == null || telefoneStr.trim().isEmpty()) {
            mensagemErro.setText("Erro: Telefone é obrigatório!");
            return false;
        }
        
        LocalDate dataNascimento;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            dataNascimento = LocalDate.parse(dataStr, formatter);
        } catch (DateTimeParseException e) {
            mensagemErro.setText("Erro: Formato de data inválido! Use DD/MM/AAAA");
            return false;
        }
        
        int telefone;
        try {
            telefone = Integer.parseInt(telefoneStr.trim());
        } catch (NumberFormatException e) {
            mensagemErro.setText("Erro: Telefone deve conter apenas números!");
            return false;
        }
        
        Mae mae = new Mae();
        mae.setNome(nome.trim());
        mae.setDataNasci(dataNascimento);
        mae.setTelefone(telefone);
        mae.setEndereco(endereco != null ? endereco.trim() : "");
        
        try {
            maeController.cadastrarMae(mae);
            mensagemErro.setText("Mãe cadastrada com sucesso!");
            mensagemErro.setStyle("-fx-text-fill: green;");
            return true;
        } catch (Exception e) {
            mensagemErro.setText("Erro ao cadastrar mãe no banco de dados!");
            return false;
        }
    }
}