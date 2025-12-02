package gui;

import dao.EncontroDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.*;
import model.Encontro;
import model.StatusEncontro;
import model.ServicoDoEncontro;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

public class TodosEncontros {

    private Encontro encontroAtual;

    public void mostrar(Stage todosencontrosStage) {

        VBox center = new VBox(30);
        center.getStyleClass().add("center");
        center.setAlignment(Pos.TOP_CENTER);
        center.setFillWidth(true);

        // Botão voltar (alinhado à esquerda)
        Button voltar = new Button("Voltar");
        voltar.getStyleClass().add("btn_voltar");

        // Container para o botão voltar (mantém à esquerda)
        HBox voltarContainer = new HBox(voltar);
        voltarContainer.setAlignment(Pos.TOP_LEFT);
        HBox.setHgrow(voltarContainer, Priority.ALWAYS);

        // Seção 1: Todos os Encontros
        VBox encontrosSection = new VBox(15);
        encontrosSection.setAlignment(Pos.TOP_CENTER);
        encontrosSection.setMaxWidth(Double.MAX_VALUE);
        encontrosSection.getStyleClass().add("encontros-section");

        Label tituloTodosEncontros = new Label("Todos Encontros");
        tituloTodosEncontros.getStyleClass().add("title-label");

        HBox tituloContainer = new HBox(tituloTodosEncontros);
        tituloContainer.setAlignment(Pos.CENTER);
        HBox.setHgrow(tituloContainer, Priority.ALWAYS);

        TableView<Encontro> todosEncontrosTable = new TableView<>();
        todosEncontrosTable.getStyleClass().add("encontroTable");
        todosEncontrosTable.setPrefWidth(400);
        todosEncontrosTable.setMaxWidth(400);
        todosEncontrosTable.setPrefHeight(250);

        TableColumn<Encontro, LocalDate> dataCol = new TableColumn<>("Dia:");
        TableColumn<Encontro, StatusEncontro> statusCol = new TableColumn<>("Status:");

        dataCol.setPrefWidth(180);
        statusCol.setPrefWidth(180);

        dataCol.setCellValueFactory(new PropertyValueFactory<>("data"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));

        todosEncontrosTable.getColumns().addAll(dataCol, statusCol);
        todosEncontrosTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        EncontroDAO dao = new EncontroDAO();
        List<Encontro> encontros = dao.listartodos();
        ObservableList<Encontro> encontrosObservable = FXCollections.observableArrayList(encontros);
        todosEncontrosTable.setItems(encontrosObservable);

        // Container para a tabela (centralizado)
        HBox tabelaContainer = new HBox(todosEncontrosTable);
        tabelaContainer.setAlignment(Pos.CENTER);
        HBox.setHgrow(tabelaContainer, Priority.ALWAYS);

        encontrosSection.getChildren().addAll(tituloContainer, tabelaContainer);

        Separator separador = new Separator();
        separador.getStyleClass().add("separator");
        separador.setMaxWidth(600);

        // Seção 2: Exportar Encontro
        VBox exportarSection = new VBox(15);
        exportarSection.setAlignment(Pos.TOP_CENTER);
        exportarSection.setMaxWidth(600);
        exportarSection.getStyleClass().add("busca-section");

        Label tituloExportar = new Label("Exportar Encontro");
        tituloExportar.getStyleClass().add("section-title");

        HBox tituloExportarContainer = new HBox(tituloExportar);
        tituloExportarContainer.setAlignment(Pos.CENTER);
        HBox.setHgrow(tituloExportarContainer, Priority.ALWAYS);

        Label buscarLabel = new Label("Buscar por data:");
        buscarLabel.getStyleClass().add("busca-label");

        HBox buscarLabelContainer = new HBox(buscarLabel);
        buscarLabelContainer.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(buscarLabelContainer, Priority.ALWAYS);

        DatePicker dataPicker = new DatePicker(LocalDate.now());
        dataPicker.getStyleClass().add("date-picker");
        dataPicker.setPrefWidth(200);

        Button buscarButton = new Button("Buscar");
        buscarButton.getStyleClass().add("search-button");

        HBox buscaBox = new HBox(15, dataPicker, buscarButton);
        buscaBox.getStyleClass().add("hbox-busca");
        buscaBox.setAlignment(Pos.CENTER);

        Label infoEncontroLabel = new Label("");
        infoEncontroLabel.getStyleClass().add("encontro-info-label");
        infoEncontroLabel.setVisible(false);

        HBox infoLabelContainer = new HBox(infoEncontroLabel);
        infoLabelContainer.setAlignment(Pos.CENTER);
        HBox.setHgrow(infoLabelContainer, Priority.ALWAYS);

        TableView<ServicoDoEncontro> servicosTable = new TableView<>();
        servicosTable.getStyleClass().add("servicos-table");
        servicosTable.setPrefHeight(200);
        servicosTable.setPrefWidth(400);
        servicosTable.setVisible(false);

        TableColumn<ServicoDoEncontro, String> servicoCol = new TableColumn<>("Serviços:");
        servicoCol.setPrefWidth(380);
        servicoCol.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(cellData.getValue().toString()));
        servicosTable.getColumns().add(servicoCol);
        servicosTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        HBox servicosContainer = new HBox(servicosTable);
        servicosContainer.setAlignment(Pos.CENTER);
        HBox.setHgrow(servicosContainer, Priority.ALWAYS);

        Button exportarEncontro = new Button("Exportar Encontro");
        exportarEncontro.getStyleClass().add("export-button");

        HBox exportarButtonContainer = new HBox(exportarEncontro);
        exportarButtonContainer.setAlignment(Pos.CENTER);
        HBox.setHgrow(exportarButtonContainer, Priority.ALWAYS);

        // Adicionar ação ao botão buscar
        buscarButton.setOnAction(event -> {
            LocalDate dataSelecionada = dataPicker.getValue();
            if (dataSelecionada != null) {
                Optional<Encontro> encontroEncontrado = encontros.stream()
                        .filter(e -> e.getData().equals(dataSelecionada))
                        .findFirst();

                if (encontroEncontrado.isPresent()) {
                    encontroAtual = encontroEncontrado.get();
                    String dataFormatada = dataSelecionada.format(DateTimeFormatter.ofPattern("dd/MM"));
                    infoEncontroLabel.setText("Encontro do dia " + dataFormatada);
                    infoEncontroLabel.setVisible(true);

                    ObservableList<ServicoDoEncontro> servicosObservable =
                            FXCollections.observableArrayList(encontroAtual.getServicos());
                    servicosTable.setItems(servicosObservable);
                    servicosTable.setVisible(true);
                } else {
                    encontroAtual = null;
                    infoEncontroLabel.setText("Nenhum encontro para esta data");
                    infoEncontroLabel.setVisible(true);
                    servicosTable.setVisible(false);
                }
            }
        });

        // Adicionar ação ao botão exportar
        exportarEncontro.setOnAction(event -> {
            if (encontroAtual != null) {
                exportarParaTxt(encontroAtual);
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Aviso");
                alert.setHeaderText("Nenhum encontro selecionado");
                alert.setContentText("Por favor, busque um encontro primeiro antes de exportar.");
                alert.showAndWait();
            }
        });

        // Adicionar todos os elementos à seção de exportar
        exportarSection.getChildren().addAll(
                tituloExportarContainer,
                buscarLabelContainer,
                buscaBox,
                infoLabelContainer,
                servicosContainer,
                exportarButtonContainer
        );

        // Adicionar todas as seções ao layout principal
        center.getChildren().addAll(
                voltarContainer,
                encontrosSection,
                separador,
                exportarSection
        );

        ScrollPane centerScrollPane = new ScrollPane();
        centerScrollPane.getStyleClass().add("scroll-pane");
        centerScrollPane.setContent(center);
        centerScrollPane.setFitToWidth(true);
        centerScrollPane.setFitToHeight(true);
        centerScrollPane.setPadding(new javafx.geometry.Insets(10));

        HBox root = new HBox(centerScrollPane);
        root.setAlignment(Pos.CENTER);
        HBox.setHgrow(centerScrollPane, Priority.ALWAYS);

        Scene scene = new Scene(root, 900, 700);
        scene.getStylesheets().add(getClass().getResource("/gui/todosencontros.css").toExternalForm());

        todosencontrosStage.setTitle("Planejados pela Fé - Todos os Encontros");
        todosencontrosStage.setScene(scene);
        todosencontrosStage.setFullScreen(true);
        todosencontrosStage.setFullScreenExitHint("");
        todosencontrosStage.show();
    }

    private void exportarParaTxt(Encontro encontro) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Salvar Encontro como TXT");

        String nomeArquivo = "encontro_" + encontro.getData().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) + ".txt";
        fileChooser.setInitialFileName(nomeArquivo);

        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Arquivos de Texto (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);

        File file = fileChooser.showSaveDialog(null);

        if (file != null) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write("========================================");
                writer.newLine();
                writer.write("         ENCONTRO - " + encontro.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                writer.newLine();
                writer.write("========================================");
                writer.newLine();
                writer.newLine();

                writer.write("INFORMAÇÕES DO ENCONTRO:");
                writer.newLine();
                writer.write("------------------------");
                writer.newLine();
                writer.write("ID: " + encontro.getIdEncontro());
                writer.newLine();
                writer.write("Data: " + encontro.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                writer.newLine();
                writer.write("Status: " + encontro.getStatus());
                writer.newLine();
                writer.newLine();

                writer.write("SERVIÇOS DO ENCONTRO:");
                writer.newLine();
                writer.write("--------------------");
                writer.newLine();

                List<ServicoDoEncontro> servicos = encontro.getServicos();
                if (servicos.isEmpty()) {
                    writer.write("Nenhum serviço cadastrado para este encontro.");
                    writer.newLine();
                } else {
                    for (int i = 0; i < servicos.size(); i++) {
                        ServicoDoEncontro servico = servicos.get(i);
                        writer.write((i + 1) + ". " + servico.getServico());
                        writer.newLine();

                        if (servico.getDescricao() != null && !servico.getDescricao().isEmpty()) {
                            writer.write("   Descrição: " + servico.getDescricao());
                            writer.newLine();
                        }

                        if (servico.getMaeResponsavel() != null) {
                            writer.write("   Responsável: " + servico.getMaeResponsavel().getNome());
                            writer.newLine();
                        }
                        writer.newLine();
                    }
                }

                writer.newLine();
                writer.write("========================================");
                writer.newLine();
                writer.write("Exportado em: " + LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                writer.newLine();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Exportação Concluída");
                alert.setHeaderText("Encontro exportado com sucesso!");
                alert.setContentText("Arquivo salvo em: " + file.getAbsolutePath());
                alert.showAndWait();

            } catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erro na Exportação");
                alert.setHeaderText("Não foi possível exportar o encontro");
                alert.setContentText("Erro: " + e.getMessage());
                alert.showAndWait();
            }
        }
    }
}