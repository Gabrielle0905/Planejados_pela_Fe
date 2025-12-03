package gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import dao.EncontroDAO;
import dao.ServicodoEncontroDAO;
import dao.MaeDAO;
import model.Encontro;
import model.StatusEncontro;
import model.ServicoDoEncontro;
import model.TipoServico;
import model.Mae;

public class EditarEncontros {
    private ComboBox<Encontro> comboEncontros;
    private DatePicker datePickerData;
    private ComboBox<StatusEncontro> comboStatus;
    private ListView<ServicoDoEncontro> listViewServicos;
    private ComboBox<TipoServico> comboServicos;
    private ComboBox<Mae> comboMaes;
    private TextField txtDescricao;

    private EncontroDAO encontroDAO;
    private ServicodoEncontroDAO servicoDAO;
    private MaeDAO maeDAO;

    private ObservableList<Encontro> encontrosList;
    private ObservableList<ServicoDoEncontro> servicosList;
    private ObservableList<TipoServico> tiposServicoList;
    private ObservableList<Mae> maesList;

    private Encontro encontroAtual;
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public void mostrar(Stage editarEncontrosStage) {
        encontroDAO = new EncontroDAO();
        servicoDAO = new ServicodoEncontroDAO();
        maeDAO = new MaeDAO();

        inicializarComponentes();

        BorderPane root = new BorderPane();
        root.getStyleClass().add("root");

        VBox cabecalho = new VBox();
        cabecalho.setPadding(new Insets(20, 20, 10, 20));

        HBox hboxVoltar = new HBox();
        hboxVoltar.setAlignment(Pos.TOP_LEFT);

        Button btnVoltar = new Button("Voltar");
        btnVoltar.getStyleClass().add("btn_voltar");
        btnVoltar.setOnAction(e -> {
            Encontros tela = new Encontros();
            try {
                tela.mostrar(editarEncontrosStage);
            } catch (Exception ex){
                ex.printStackTrace();
            }
        });

        hboxVoltar.getChildren().add(btnVoltar);

        Label tituloPagina = new Label("EDITAR ENCONTROS");
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

        VBox secaoSelecao = new VBox(10);
        secaoSelecao.getStyleClass().add("secao-selecao");
        secaoSelecao.setPadding(new Insets(15));

        Label lblSelecionar = new Label("Selecionar Encontro:");
        lblSelecionar.getStyleClass().add("label-selecao");

        HBox hboxSelecao = new HBox(10);
        hboxSelecao.setAlignment(Pos.CENTER_LEFT);

        comboEncontros = new ComboBox<>();
        comboEncontros.getStyleClass().add("campo-formulario");
        comboEncontros.setPrefWidth(400);
        comboEncontros.setPromptText("Selecione um encontro...");

        configurarComboEncontros();

        Button btnAtualizarLista = new Button("Atualizar");
        btnAtualizarLista.getStyleClass().add("botao-principal");
        btnAtualizarLista.setOnAction(e -> carregarTodosDados());

        hboxSelecao.getChildren().addAll(comboEncontros, btnAtualizarLista);
        secaoSelecao.getChildren().addAll(lblSelecionar, hboxSelecao);

        VBox formularioEncontro = new VBox(15);
        formularioEncontro.getStyleClass().add("formulario-encontro");
        formularioEncontro.setPadding(new Insets(20));
        formularioEncontro.setStyle("-fx-border-color: #ddd; -fx-border-radius: 10; -fx-border-width: 1;");

        GridPane formulario = new GridPane();
        formulario.setHgap(15);
        formulario.setVgap(15);

        Label lblData = new Label("Data:");
        lblData.getStyleClass().add("rotulo-formulario");
        datePickerData = new DatePicker();
        datePickerData.getStyleClass().add("date-picker");
        datePickerData.setPrefWidth(250);

        Label lblStatus = new Label("Status:");
        lblStatus.getStyleClass().add("rotulo-formulario");
        comboStatus = new ComboBox<>();
        comboStatus.getStyleClass().add("campo-formulario");
        comboStatus.setItems(FXCollections.observableArrayList(StatusEncontro.values()));
        comboStatus.setPrefWidth(250);

        formulario.add(lblData, 0, 0);
        formulario.add(datePickerData, 1, 0);
        formulario.add(lblStatus, 0, 1);
        formulario.add(comboStatus, 1, 1);

        formularioEncontro.getChildren().addAll(
                new Label("Informações do Encontro"),
                formulario
        );

        Label lblServicos = new Label("Serviços do Encontro");
        lblServicos.getStyleClass().add("section-title");

        VBox secaoServicos = new VBox(10);

        listViewServicos = new ListView<>();
        listViewServicos.getStyleClass().add("lista-servicos");
        listViewServicos.setPrefHeight(200);

        VBox formServico = new VBox(10);
        formServico.getStyleClass().add("painel-servicos");
        formServico.setPadding(new Insets(15));
        formServico.setStyle("-fx-border-color: #eee; -fx-border-radius: 8; -fx-border-width: 1;");

        Label lblNovoServico = new Label("Adicionar Serviço:");
        lblNovoServico.getStyleClass().add("label-servicos");

        GridPane gridServico = new GridPane();
        gridServico.setHgap(10);
        gridServico.setVgap(10);

        Label lblTipoServico = new Label("Tipo:");
        lblTipoServico.getStyleClass().add("rotulo-formulario");
        comboServicos = new ComboBox<>();
        comboServicos.getStyleClass().add("campo-formulario");
        comboServicos.setPrefWidth(250);

        Label lblMae = new Label("Mãe:");
        lblMae.getStyleClass().add("rotulo-formulario");
        comboMaes = new ComboBox<>();
        comboMaes.getStyleClass().add("campo-formulario");
        comboMaes.setPrefWidth(250);

        Label lblDescricao = new Label("Descrição:");
        lblDescricao.getStyleClass().add("rotulo-formulario");
        txtDescricao = new TextField();
        txtDescricao.getStyleClass().add("campo-formulario");
        txtDescricao.setPromptText("Opcional");
        txtDescricao.setPrefWidth(250);

        gridServico.add(lblTipoServico, 0, 0);
        gridServico.add(comboServicos, 1, 0);
        gridServico.add(lblMae, 0, 1);
        gridServico.add(comboMaes, 1, 1);
        gridServico.add(lblDescricao, 0, 2);
        gridServico.add(txtDescricao, 1, 2);

        HBox botoesServico = new HBox(10);
        Button btnAddServico = new Button("Adicionar Serviço");
        btnAddServico.getStyleClass().add("botao-principal");
        btnAddServico.setOnAction(e -> adicionarServico());

        Button btnRemoverServico = new Button("Remover Selecionado");
        btnRemoverServico.getStyleClass().add("botao-cancelar");
        btnRemoverServico.setOnAction(e -> removerServico());

        botoesServico.getChildren().addAll(btnAddServico, btnRemoverServico);

        formServico.getChildren().addAll(lblNovoServico, gridServico, botoesServico);

        secaoServicos.getChildren().addAll(listViewServicos, formServico);

        HBox botoesAcao = new HBox(15);
        botoesAcao.getStyleClass().add("container-botoes");

        Button btnSalvar = new Button("SALVAR ALTERAÇÕES");
        btnSalvar.getStyleClass().add("botao-acao-grande");
        btnSalvar.setOnAction(e -> salvarEncontro());

        Button btnCancelarEncontro = new Button("CANCELAR ENCONTRO");
        btnCancelarEncontro.getStyleClass().add("botao-cancelar");
        btnCancelarEncontro.setStyle("-fx-font-size: 16; -fx-padding: 10px 20px; -fx-background-radius: 10;");
        btnCancelarEncontro.setOnAction(e -> cancelarEncontro());

        Button btnLimpar = new Button("LIMPAR CAMPOS");
        btnLimpar.getStyleClass().add("botao-secundario");
        btnLimpar.setStyle("-fx-font-size: 16; -fx-padding: 10px 20px; -fx-background-radius: 10;");
        btnLimpar.setOnAction(e -> limparCampos());

        botoesAcao.getChildren().addAll(btnSalvar, btnCancelarEncontro, btnLimpar);

        painelEdicao.getChildren().addAll(
                lblEditarEncontro,
                secaoSelecao,
                formularioEncontro,
                lblServicos,
                secaoServicos,
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

        carregarTodosDados();

        comboEncontros.setOnAction(e -> carregarDadosEncontro());

        configurarCellFactories();

    }

    private void aplicarEstilosInline(BorderPane root) {
        root.setStyle("-fx-background-color: #f0f8ff;");
    }

    private void inicializarComponentes() {
        encontrosList = FXCollections.observableArrayList();
        servicosList = FXCollections.observableArrayList();
        tiposServicoList = FXCollections.observableArrayList(TipoServico.values());
        maesList = FXCollections.observableArrayList();
    }

    private void configurarCellFactories() {
        listViewServicos.setCellFactory(param -> new ListCell<ServicoDoEncontro>() {
            @Override
            protected void updateItem(ServicoDoEncontro servico, boolean empty) {
                super.updateItem(servico, empty);
                if (empty || servico == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    HBox container = new HBox(10);
                    container.setAlignment(Pos.CENTER_LEFT);
                    container.setPadding(new Insets(5));

                    Label marcador = new Label("•");
                    marcador.setStyle("-fx-font-size: 16px; -fx-text-fill: #2c3e50;");

                    VBox infoBox = new VBox(3);

                    String tipoFormatado = formatarNomeServico(servico.getServico());
                    Label lblTipo = new Label(tipoFormatado);
                    lblTipo.setStyle("-fx-font-weight: bold; -fx-font-size: 14px; -fx-text-fill: #2c3e50;");

                    String nomeMae = "Mãe não definida";
                    if (servico.getMaeResponsavel() != null && servico.getMaeResponsavel().getNome() != null) {
                        nomeMae = servico.getMaeResponsavel().getNome();
                    }
                    Label lblMae = new Label("Responsável: " + nomeMae);
                    lblMae.setStyle("-fx-font-size: 12px; -fx-text-fill: #555;");

                    HBox descBox = new HBox();
                    if (servico.getDescricao() != null && !servico.getDescricao().trim().isEmpty()) {
                        Label lblDesc = new Label("Descrição: " + servico.getDescricao());
                        lblDesc.setStyle("-fx-font-size: 11px; -fx-font-style: italic; -fx-text-fill: #777;");
                        descBox.getChildren().add(lblDesc);
                    }

                    infoBox.getChildren().addAll(lblTipo, lblMae, descBox);
                    container.getChildren().addAll(marcador, infoBox);

                    setGraphic(container);
                    setText(null);
                }
            }
        });
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

    private void configurarComboEncontros() {
        comboEncontros.setCellFactory(param -> new ListCell<Encontro>() {
            @Override
            protected void updateItem(Encontro encontro, boolean empty) {
                super.updateItem(encontro, empty);
                if (empty || encontro == null) {
                    setText(null);
                } else {
                    String dataFormatada = dateFormatter.format(encontro.getData());
                    String status = encontro.getStatus().toString();
                    String statusPt = traduzirStatus(status);
                    setText(String.format("%s - %s", dataFormatada, statusPt));
                }
            }
        });

        comboEncontros.setButtonCell(new ListCell<Encontro>() {
            @Override
            protected void updateItem(Encontro encontro, boolean empty) {
                super.updateItem(encontro, empty);
                if (empty || encontro == null) {
                    setText(null);
                } else {
                    String dataFormatada = dateFormatter.format(encontro.getData());
                    String statusPt = traduzirStatus(encontro.getStatus().toString());
                    setText(String.format("%s - %s", dataFormatada, statusPt));
                }
            }
        });
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

    private void carregarTodosDados() {
        carregarEncontros();
        carregarMaes();
    }

    private void carregarEncontros() {
        List<Encontro> encontros = encontroDAO.listartodos();
        encontrosList.clear();
        encontrosList.addAll(encontros);
        comboEncontros.setItems(encontrosList);
        comboServicos.setItems(tiposServicoList);
    }

    private void carregarMaes() {
        List<Mae> maes = maeDAO.listartodos();
        maesList.clear();
        maesList.addAll(maes);
        comboMaes.setItems(maesList);

        comboMaes.setCellFactory(param -> new ListCell<Mae>() {
            @Override
            protected void updateItem(Mae mae, boolean empty) {
                super.updateItem(mae, empty);
                if (empty || mae == null) {
                    setText(null);
                } else {
                    setText(mae.getIdMae() + " - " + mae.getNome());
                }
            }
        });
    }

    private void carregarDadosEncontro() {
        encontroAtual = comboEncontros.getValue();
        if (encontroAtual != null) {
            datePickerData.setValue(encontroAtual.getData());
            comboStatus.setValue(encontroAtual.getStatus());
            carregarServicosEncontro();
        } else {
            limparCampos();
        }
    }

    private void carregarServicosEncontro() {
        servicosList.clear();
        if (encontroAtual != null && encontroAtual.getIdEncontro() > 0) {
            List<ServicoDoEncontro> servicos = servicoDAO.buscarPorEncontro(encontroAtual.getIdEncontro());
            servicosList.addAll(servicos);
            encontroAtual.getServicos().clear();
            encontroAtual.getServicos().addAll(servicos);
        }
        listViewServicos.setItems(servicosList);
    }

    private void adicionarServico() {
        if (encontroAtual == null) {
            mostrarAlerta("Aviso", "Selecione um encontro primeiro!");
            return;
        }

        if (comboServicos.getValue() == null) {
            mostrarAlerta("Aviso", "Selecione um tipo de serviço!");
            return;
        }

        if (comboMaes.getValue() == null) {
            mostrarAlerta("Aviso", "Selecione uma mãe responsável!");
            return;
        }

        ServicoDoEncontro novoServico = new ServicoDoEncontro();
        novoServico.setServico(comboServicos.getValue());
        novoServico.setMaeResponsavel(comboMaes.getValue());
        novoServico.setDescricao(txtDescricao.getText());

        servicosList.add(novoServico);

        comboServicos.setValue(null);
        comboMaes.setValue(null);
        txtDescricao.clear();
    }

    private void removerServico() {
        ServicoDoEncontro servicoSelecionado = listViewServicos.getSelectionModel().getSelectedItem();
        if (servicoSelecionado != null) {
            if (servicoSelecionado.getIdServico() > 0 && encontroAtual != null) {
                Alert confirmacao = new Alert(Alert.AlertType.CONFIRMATION);
                confirmacao.setTitle("Confirmar Remoção");
                confirmacao.setHeaderText("Remover Serviço");
                confirmacao.setContentText("Deseja remover este serviço permanentemente?");

                confirmacao.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.OK) {
                        try {
                            servicoDAO.deletar(servicoSelecionado);
                            servicosList.remove(servicoSelecionado);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            } else {
                servicosList.remove(servicoSelecionado);
            }
        } else {
            mostrarAlerta("Aviso", "Selecione um serviço para remover!");
        }
    }

    private void salvarEncontro() {
        if (encontroAtual == null) {
            mostrarAlerta("Aviso", "Selecione um encontro para editar!");
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

            encontroAtual.setData(datePickerData.getValue());
            encontroAtual.setStatus(comboStatus.getValue());
            encontroDAO.atualizar(encontroAtual);

            salvarServicosEncontro();

            mostrarAlerta("Sucesso", "Encontro atualizado com sucesso!");
            carregarTodosDados();

        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Erro", "Erro ao salvar encontro: " + e.getMessage());
        }
    }

    private void salvarServicosEncontro() {
        if (encontroAtual == null || encontroAtual.getIdEncontro() == 0) {
            return;
        }

        try {
            for (ServicoDoEncontro servico : servicosList) {
                if (servico.getIdServico() == 0) {
                    servicoDAO.cadastrar(servico, encontroAtual);
                } else {
                    servicoDAO.atualizar(servico, encontroAtual);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void cancelarEncontro() {
        if (encontroAtual == null) {
            encontroAtual = comboEncontros.getValue();
        }

        if (encontroAtual == null) {
            mostrarAlerta("Aviso", "Selecione um encontro primeiro.");
            return;
        }

        final Encontro encontroFinal = encontroAtual;
        final LocalDate dataFinal = encontroAtual.getData();

        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle("Cancelar Encontro");
        alerta.setHeaderText("Deseja cancelar este encontro?");
        alerta.setContentText("Data: " + dateFormatter.format(dataFinal) +
                "\nStatus será alterado para CANCELADO");

        Optional<ButtonType> resultado = alerta.showAndWait();

        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            try {
                encontroFinal.setStatus(StatusEncontro.CANCELADO);
                encontroDAO.atualizar(encontroFinal);

                comboStatus.setValue(StatusEncontro.CANCELADO);
                mostrarAlerta("Sucesso", "Encontro cancelado!");

                carregarTodosDados();

            } catch (Exception e) {
                e.printStackTrace();
                mostrarAlerta("Erro", "Falha: " + e.getMessage());
            }
        }
    }

    private void limparCampos() {
        encontroAtual = null;
        comboEncontros.setValue(null);
        datePickerData.setValue(null);
        comboStatus.setValue(null);
        servicosList.clear();
        comboServicos.setValue(null);
        comboMaes.setValue(null);
        txtDescricao.clear();
    }

    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}