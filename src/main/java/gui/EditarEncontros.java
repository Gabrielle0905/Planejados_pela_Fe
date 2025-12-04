package gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import dao.EncontroDAO;
import model.Encontro;
import model.StatusEncontro;

public class EditarEncontros {
    private DatePicker datePickerData;
    private ComboBox<StatusEncontro> comboStatus;

    private EncontroDAO encontroDAO;

    private Encontro encontroAtual;
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public void mostrar(Stage editarEncontrosStage, Encontro encontroSelecionado) {
        this.encontroAtual = encontroSelecionado;

        encontroDAO = new EncontroDAO();

        if (encontroAtual == null) {
            mostrarAlerta("Erro", "Nenhum encontro selecionado para edição!");
            voltarParaEncontros(editarEncontrosStage);
            return;
        }

        BorderPane root = new BorderPane();
        root.getStyleClass().add("root");

        VBox cabecalho = new VBox();
        cabecalho.setPadding(new Insets(20, 20, 10, 20));

        HBox hboxVoltar = new HBox();
        hboxVoltar.setAlignment(Pos.TOP_LEFT);

        Button btnVoltar = new Button("Voltar");
        btnVoltar.getStyleClass().add("btn_voltar");
        btnVoltar.setOnAction(e -> voltarParaEncontros(editarEncontrosStage));

        hboxVoltar.getChildren().add(btnVoltar);

        Label tituloPagina = new Label("EDITAR ENCONTRO");
        tituloPagina.getStyleClass().addAll("title-label", "title");

        cabecalho.getChildren().addAll(hboxVoltar, tituloPagina);

        VBox conteudoPrincipal = new VBox(20);
        conteudoPrincipal.setPadding(new Insets(10, 20, 20, 20));
        conteudoPrincipal.getStyleClass().add("center");

        VBox painelEdicao = new VBox(15);
        painelEdicao.getStyleClass().add("painel-edicao");
        painelEdicao.setPrefWidth(900);

        Label lblEditarEncontro = new Label("Editar Encontro");
        lblEditarEncontro.getStyleClass().add("section-title");

        VBox secaoInfoEncontro = new VBox(10);
        secaoInfoEncontro.getStyleClass().add("secao-info");
        secaoInfoEncontro.setPadding(new Insets(15));
        secaoInfoEncontro.setStyle("-fx-border-color: #ddd; -fx-border-radius: 10; -fx-border-width: 1; -fx-background-color: #f8f9fa;");

        Label lblInfoTitulo = new Label("Encontro Selecionado:");
        lblInfoTitulo.getStyleClass().add("label-info-titulo");

        Label lblIdEncontro = new Label("ID: " + encontroAtual.getIdEncontro());
        lblIdEncontro.getStyleClass().add("label-info");

        Label lblDataOriginal = new Label("Data original: " + dateFormatter.format(encontroAtual.getData()));
        lblDataOriginal.getStyleClass().add("label-info");

        Label lblStatusOriginal = new Label("Status atual: " + traduzirStatus(encontroAtual.getStatus().toString()));
        lblStatusOriginal.getStyleClass().add("label-info");

        secaoInfoEncontro.getChildren().addAll(lblInfoTitulo, lblIdEncontro, lblDataOriginal, lblStatusOriginal);

        VBox formularioEncontro = new VBox(15);
        formularioEncontro.getStyleClass().add("formulario-encontro");
        formularioEncontro.setPadding(new Insets(20));
        formularioEncontro.setStyle("-fx-border-color: #ddd; -fx-border-radius: 10; -fx-border-width: 1;");

        Label lblFormTitulo = new Label("Informações do Encontro");
        lblFormTitulo.getStyleClass().add("subtitle");

        GridPane formulario = new GridPane();
        formulario.setHgap(15);
        formulario.setVgap(15);

        Label lblData = new Label("Nova Data:");
        lblData.getStyleClass().add("rotulo-formulario");
        datePickerData = new DatePicker();
        datePickerData.getStyleClass().add("date-picker");
        datePickerData.setPrefWidth(250);
        datePickerData.setValue(encontroAtual.getData());

        Label lblStatus = new Label("Novo Status:");
        lblStatus.getStyleClass().add("rotulo-formulario");
        comboStatus = new ComboBox<>();
        comboStatus.getStyleClass().add("campo-formulario");
        comboStatus.setItems(FXCollections.observableArrayList(StatusEncontro.values()));
        comboStatus.setPrefWidth(250);
        comboStatus.setValue(encontroAtual.getStatus());

        formulario.add(lblData, 0, 0);
        formulario.add(datePickerData, 1, 0);
        formulario.add(lblStatus, 0, 1);
        formulario.add(comboStatus, 1, 1);

        formularioEncontro.getChildren().addAll(lblFormTitulo, formulario);

        HBox botoesAcao = new HBox(15);
        botoesAcao.getStyleClass().add("container-botoes");
        botoesAcao.setAlignment(Pos.CENTER);

        Button btnSalvar = new Button("SALVAR ALTERAÇÕES");
        btnSalvar.getStyleClass().add("botao-acao-grande");
        btnSalvar.setOnAction(e -> salvarEncontro());

        Button btnCancelarEncontro = new Button("CANCELAR ENCONTRO");
        btnCancelarEncontro.getStyleClass().add("botao-cancelar");
        btnCancelarEncontro.setStyle("-fx-font-size: 16; -fx-padding: 10px 20px; -fx-background-radius: 10;");
        btnCancelarEncontro.setOnAction(e -> cancelarEncontro());

        botoesAcao.getChildren().addAll(btnSalvar, btnCancelarEncontro);

        painelEdicao.getChildren().addAll(
                lblEditarEncontro,
                secaoInfoEncontro,
                formularioEncontro,
                botoesAcao
        );

        conteudoPrincipal.getChildren().addAll(painelEdicao);

        root.setTop(cabecalho);
        root.setCenter(conteudoPrincipal);

        Scene scene = new Scene(root, 1200, 750);

        try {
            scene.getStylesheets().add(getClass().getResource("editarencontros.css").toExternalForm());
        } catch (Exception e) {
            root.setStyle("-fx-background-color: #f0f8ff;");
        }

        editarEncontrosStage.setTitle("Planejados pela Fé");
        editarEncontrosStage.setScene(scene);
        editarEncontrosStage.setFullScreen(true);
    }

    private String traduzirStatus(String status) {
        switch (status) {
            case "NAO_REALIZADO":
                return "Não Realizado";
            case "REALIZADO":
                return "Realizado";
            case "CANCELADO":
                return "Cancelado";
            default:
                return status;
        }
    }

    private void salvarEncontro() {
        if (encontroAtual == null) {
            mostrarAlerta("Aviso", "Nenhum encontro selecionado para editar!");
            return;
        }

        try {
            if (datePickerData.getValue() == null) {
                mostrarAlerta("Erro", "Data é obrigatória!");
                return;
            }

            if (comboStatus.getValue() == null) {
                mostrarAlerta("Erro", "Status é obrigatório!");
                return;
            }

            // Verificar se houve alterações
            boolean dataAlterada = !datePickerData.getValue().equals(encontroAtual.getData());
            boolean statusAlterado = comboStatus.getValue() != encontroAtual.getStatus();

            if (!dataAlterada && !statusAlterado) {
                mostrarAlerta("Informação", "Nenhuma alteração detectada.");
                return;
            }

            encontroAtual.setData(datePickerData.getValue());
            encontroAtual.setStatus(comboStatus.getValue());
            encontroDAO.atualizar(encontroAtual);

            mostrarAlerta("Sucesso", "Encontro atualizado com sucesso!");
            voltarParaEncontros((Stage) datePickerData.getScene().getWindow());

        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Erro", "Erro ao salvar encontro: " + e.getMessage());
        }
    }

    private void cancelarEncontro() {
        if (encontroAtual == null) {
            mostrarAlerta("Aviso", "Nenhum encontro selecionado.");
            return;
        }

        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle("Cancelar Encontro");
        alerta.setHeaderText("Deseja cancelar este encontro?");
        alerta.setContentText("Data: " + dateFormatter.format(encontroAtual.getData()) +
                "\nStatus será alterado para CANCELADO\n\n" +
                "Esta ação não poderá ser desfeita.");

        Optional<ButtonType> resultado = alerta.showAndWait();

        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            try {
                encontroAtual.setStatus(StatusEncontro.CANCELADO);
                encontroDAO.atualizar(encontroAtual);

                comboStatus.setValue(StatusEncontro.CANCELADO);
                mostrarAlerta("Sucesso", "Encontro cancelado com sucesso!");

            } catch (Exception e) {
                e.printStackTrace();
                mostrarAlerta("Erro", "Falha ao cancelar encontro: " + e.getMessage());
            }
        }
    }

    private void voltarParaEncontros(Stage stage) {
        Encontros tela = new Encontros();
        try {
            tela.mostrar(stage);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}