package gui;

import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import controller.EncontroController;
import controller.ServicoDoEncontroController;
import controller.MaeController;
import model.Encontro;
import model.ServicoDoEncontro;
import model.TipoServico;
import model.StatusEncontro;
import model.Mae;
import java.time.LocalDate;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class CadastrarEncontros {
    
    private EncontroController encontroController = new EncontroController();
    private ServicoDoEncontroController servicoController = new ServicoDoEncontroController();
    private MaeController maeController = new MaeController();
    private Map<TipoServico, ComboBox<Mae>> comboboxMap = new HashMap<>();
    
    public void mostrar(Stage encontroStage) {
        
        Text titulo = new Text("CADASTRAR ENCONTRO");
        titulo.setId("titulo");
        
        Button btVoltar = new Button("Voltar");
        btVoltar.getStyleClass().add("btn_voltar");
        
        btVoltar.setOnAction(e -> {
            Encontros tela = new Encontros();
            try {
                tela.mostrar(encontroStage);
            } catch(Exception ex) {
                ex.printStackTrace();
            }
        });
        
        Label labelData = new Label("Data do encontro:");
        labelData.getStyleClass().add("label-form");
        
        DatePicker dataPicker = new DatePicker();
        dataPicker.setValue(LocalDate.now());
        dataPicker.setPrefWidth(200);
        dataPicker.getStyleClass().add("campo-data");
        
        HBox linhaData = new HBox(20);
        linhaData.setAlignment(Pos.CENTER_LEFT);
        linhaData.getChildren().addAll(labelData, dataPicker);
        
        Text subtitulo = new Text("Serviços:");
        subtitulo.setId("subtitulo");
        
        List<Mae> todasMaes = maeController.listarMaes();
        
        VBox card = new VBox(15);
        card.setId("card");
        card.setPrefWidth(800);
        card.setPadding(new Insets(30));
        
        for (TipoServico servico : TipoServico.values()) {
            String nomeFormatado = formatarNomeServico(servico);
            
            Label labelServico = new Label(nomeFormatado + ":");
            labelServico.getStyleClass().add("label-servico");
            labelServico.setPrefWidth(220);
            
            ComboBox<Mae> comboBox = new ComboBox<>();
            comboBox.getItems().addAll(todasMaes);
            comboBox.setPromptText("Selecione uma mãe");
            comboBox.setPrefWidth(300);
            comboBox.getStyleClass().add("combo-box");
            
            if (!todasMaes.isEmpty()) {
                comboBox.setValue(todasMaes.get(0));
            }
            
            comboboxMap.put(servico, comboBox);
            
            HBox linhaServico = new HBox(20);
            linhaServico.setAlignment(Pos.CENTER_LEFT);
            linhaServico.getChildren().addAll(labelServico, comboBox);
            
            card.getChildren().add(linhaServico);
        }
        
        HBox separadorBox = new HBox();
        separadorBox.setPrefHeight(20);
        
        Label mensagemErro = new Label();
        mensagemErro.getStyleClass().add("mensagem-erro");
        
        Button btCadastrar = new Button("Cadastrar");
        btCadastrar.getStyleClass().add("botao");
        
        btCadastrar.setOnAction(e -> {
            boolean sucesso = cadastrarEncontro(
                dataPicker.getValue(),
                comboboxMap,
                mensagemErro
            );
        });
        
        VBox layout = new VBox(30);
        layout.setAlignment(Pos.TOP_CENTER);
        layout.setPadding(new Insets(50, 0, 50, 0));
        layout.setId("layout");
        layout.setFillWidth(false);
        
        layout.getChildren().addAll(
            titulo,
            btVoltar,
            linhaData,
            subtitulo,
            card,
            separadorBox,
            mensagemErro,
            btCadastrar
        );
        
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(layout);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.getStyleClass().add("scroll-pane-tela");
        
        BorderPane root = new BorderPane();
        root.setCenter(scrollPane);
        
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/gui/cadastrarencontros.css").toExternalForm());
        
        encontroStage.setScene(scene);
        encontroStage.setTitle("Planejados pela Fé");
        encontroStage.setFullScreen(true);
    }
    
    private String formatarNomeServico(TipoServico servico) {
        switch(servico) {
            case MUSICA: return "Música";
            case RECEPCAO_MAES: return "Recepção de Mães";
            case ACOLHIDA: return "Acolhida";
            case TERCO: return "Terço";
            case FORMACAO: return "Formação";
            case MOMENTO_ORACIONAL: return "Momento oracional";
            case PROCLAMACAO_VITORIA: return "Proclamação da Vitória";
            case SORTEIO_DAS_FLORES: return "Sorteio das Flores";
            case ENCERRAMENTO: return "Encerramento";
            case ARRUMACAO_CAPELA: return "Arrumação da Capela";
            case QUEIMA_PEDIDOS: return "Queima dos Pedidos";
            case COMPRA_FLORES: return "Compra das Flores";
            default: return servico.name();
        }
    }
    
    private boolean cadastrarEncontro(LocalDate data, Map<TipoServico, ComboBox<Mae>> comboboxMap, Label mensagemErro) {
        if (data == null) {
            mensagemErro.setText("Erro: Selecione uma data!");
            return false;
        }
        
        if (data.isBefore(LocalDate.now())) {
            mensagemErro.setText("Erro: Data não pode ser no passado!");
            return false;
        }
        
        try {
            Encontro encontro = new Encontro();
            encontro.setData(data);
            encontro.setStatus(StatusEncontro.FUTURO);
            
            int idEncontro = encontroController.cadastrar(encontro);
            
            if (idEncontro <= 0) {
                mensagemErro.setText("Erro: Não foi possível cadastrar o encontro!");
                return false;
            }
            
            encontro.setIdEncontro(idEncontro);
            
            for (Map.Entry<TipoServico, ComboBox<Mae>> entry : comboboxMap.entrySet()) {
                TipoServico tipoServico = entry.getKey();
                ComboBox<Mae> comboBox = entry.getValue();
                
                Mae maeSelecionada = comboBox.getValue();
                if (maeSelecionada != null) {
                    ServicoDoEncontro servico = new ServicoDoEncontro();
                    servico.setServico(tipoServico);
                    servico.setMaeResponsavel(maeSelecionada);
                    servico.setDescricao("Serviço: " + tipoServico.name());
                    
                    servicoController.cadastrar(servico, encontro);
                }
            }
            
            mensagemErro.setText("Encontro cadastrado com sucesso!");
            mensagemErro.setStyle("-fx-text-fill: green;");
            return true;
            
        } catch (Exception e) {
            mensagemErro.setText("Erro ao cadastrar encontro");
            e.printStackTrace();
            return false;
        }
    }
}