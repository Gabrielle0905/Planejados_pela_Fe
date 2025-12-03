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
    private ListView<Encontro> listViewProximosEncontros;
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
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM");

    public void mostrar(Stage editarEncontrosStage) {
        encontroDAO = new EncontroDAO();
        servicoDAO = new ServicodoEncontroDAO();
        maeDAO = new MaeDAO();

        // Inicializar componentes
        inicializarComponentes();

        // Layout principal
        BorderPane root = new BorderPane();
        root.getStyleClass().add("root");

        // ========== CABEÇALHO ==========
        VBox cabecalho = new VBox();
        cabecalho.setPadding(new Insets(20, 20, 10, 20));

        // Botão voltar
        HBox hboxVoltar = new HBox();
        hboxVoltar.setAlignment(Pos.TOP_LEFT);

        Button btnVoltar = new Button("←");
        btnVoltar.getStyleClass().add("btn_voltar");
        btnVoltar.setOnAction(e -> {
            editarEncontrosStage.close();
        });

        hboxVoltar.getChildren().add(btnVoltar);

        // Título principal
        Label tituloPagina = new Label("EDITAR ENCONTROS");
        tituloPagina.getStyleClass().addAll("title-label", "title");

        cabecalho.getChildren().addAll(hboxVoltar, tituloPagina);

        // ========== CONTEÚDO PRINCIPAL ==========
        VBox conteudoPrincipal = new VBox(20);
        conteudoPrincipal.setPadding(new Insets(10, 20, 20, 20));
        conteudoPrincipal.getStyleClass().add("center");

        // Grid para os painéis
        GridPane gridPaineis = new GridPane();
        gridPaineis.getStyleClass().add("grid-paineis");
        gridPaineis.setHgap(20);
        gridPaineis.setVgap(20);

        // ========== PAINEL ESQUERDO - PRÓXIMOS ENCONTROS ==========
        VBox painelEsquerdo = new VBox(15);
        painelEsquerdo.getStyleClass().add("painel-esquerdo");
        painelEsquerdo.setPrefWidth(350);

        // Próximos encontros
        Label lblProximos = new Label("Próximos Encontros");
        lblProximos.getStyleClass().add("section-title");

        listViewProximosEncontros = new ListView<>();
        listViewProximosEncontros.getStyleClass().add("lista-encontros");
        listViewProximosEncontros.setPrefHeight(300);

        painelEsquerdo.getChildren().addAll(lblProximos, listViewProximosEncontros);

        // ========== PAINEL DIREITO - EDIÇÃO ==========
        VBox painelDireito = new VBox(15);
        painelDireito.getStyleClass().add("painel-direito");

        Label lblEditarEncontro = new Label("Editar Encontro");
        lblEditarEncontro.getStyleClass().add("section-title");

        // Seção de seleção
        VBox secaoSelecao = new VBox(10);
        secaoSelecao.getStyleClass().add("secao-selecao");
        secaoSelecao.setPadding(new Insets(15));

        Label lblSelecionar = new Label("Selecionar Encontro:");
        lblSelecionar.getStyleClass().add("label-selecao");

        HBox hboxSelecao = new HBox(10);
        hboxSelecao.setAlignment(Pos.CENTER_LEFT);

        comboEncontros = new ComboBox<>();
        comboEncontros.getStyleClass().add("campo-formulario");
        comboEncontros.setPrefWidth(300);

        Button btnAtualizarLista = new Button("Atualizar");
        btnAtualizarLista.getStyleClass().add("botao-principal");
        btnAtualizarLista.setOnAction(e -> carregarTodosDados());

        hboxSelecao.getChildren().addAll(comboEncontros, btnAtualizarLista);
        secaoSelecao.getChildren().addAll(lblSelecionar, hboxSelecao);

        // Formulário de edição
        GridPane formulario = new GridPane();
        formulario.setHgap(15);
        formulario.setVgap(15);
        formulario.setPadding(new Insets(15, 0, 0, 0));

        // Campo Data
        Label lblData = new Label("Data:");
        lblData.getStyleClass().add("rotulo-formulario");
        datePickerData = new DatePicker();
        datePickerData.getStyleClass().add("date-picker");
        datePickerData.setPrefWidth(200);

        // Campo Status
        Label lblStatus = new Label("Status:");
        lblStatus.getStyleClass().add("rotulo-formulario");
        comboStatus = new ComboBox<>();
        comboStatus.getStyleClass().add("campo-formulario");
        comboStatus.setItems(FXCollections.observableArrayList(StatusEncontro.values()));
        comboStatus.setPrefWidth(200);

        formulario.add(lblData, 0, 0);
        formulario.add(datePickerData, 1, 0);
        formulario.add(lblStatus, 0, 1);
        formulario.add(comboStatus, 1, 1);

        // Seção de serviços
        Label lblServicos = new Label("Serviços do Encontro");
        lblServicos.getStyleClass().add("section-title");

        VBox secaoServicos = new VBox(10);

        // Lista de serviços
        listViewServicos = new ListView<>();
        listViewServicos.getStyleClass().add("lista-encontros");
        listViewServicos.setPrefHeight(150);

        // Formulário para adicionar serviço
        VBox formServico = new VBox(10);
        formServico.getStyleClass().add("painel-servicos");
        formServico.setPadding(new Insets(15));

        Label lblNovoServico = new Label("Adicionar Serviço:");
        lblNovoServico.getStyleClass().add("label-servicos");

        GridPane gridServico = new GridPane();
        gridServico.setHgap(10);
        gridServico.setVgap(10);

        // Tipo de serviço
        Label lblTipoServico = new Label("Tipo:");
        lblTipoServico.getStyleClass().add("rotulo-formulario");
        comboServicos = new ComboBox<>();
        comboServicos.getStyleClass().add("campo-formulario");
        comboServicos.setPrefWidth(200);

        // Mãe responsável
        Label lblMae = new Label("Mãe:");
        lblMae.getStyleClass().add("rotulo-formulario");
        comboMaes = new ComboBox<>();
        comboMaes.getStyleClass().add("campo-formulario");
        comboMaes.setPrefWidth(200);

        // Descrição
        Label lblDescricao = new Label("Descrição:");
        lblDescricao.getStyleClass().add("rotulo-formulario");
        txtDescricao = new TextField();
        txtDescricao.getStyleClass().add("campo-formulario");
        txtDescricao.setPromptText("Opcional");

        gridServico.add(lblTipoServico, 0, 0);
        gridServico.add(comboServicos, 1, 0);
        gridServico.add(lblMae, 0, 1);
        gridServico.add(comboMaes, 1, 1);
        gridServico.add(lblDescricao, 0, 2);
        gridServico.add(txtDescricao, 1, 2);

        // Botões de serviços
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

        // Botões de ação
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

        // Montar painel direito
        painelDireito.getChildren().addAll(
                lblEditarEncontro,
                secaoSelecao,
                formulario,
                lblServicos,
                secaoServicos,
                botoesAcao
        );

        // Adicionar painéis ao grid
        gridPaineis.add(painelEsquerdo, 0, 0);
        gridPaineis.add(painelDireito, 1, 0);

        // Montar conteúdo principal
        conteudoPrincipal.getChildren().addAll(gridPaineis);

        // Configurar layout principal
        root.setTop(cabecalho);
        root.setCenter(conteudoPrincipal);

        // Configurar cena
        Scene scene = new Scene(root, 1200, 750);

        // Carregar CSS
        try {
            scene.getStylesheets().add(getClass().getResource("editarencontros.css").toExternalForm());
        } catch (Exception e) {
            System.out.println("CSS não encontrado: " + e.getMessage());
            // Usar estilos inline como fallback
            aplicarEstilosInline(root);
        }

        // Configurar stage
        editarEncontrosStage.setTitle("Planejados pela Fé - Editar Encontros");
        editarEncontrosStage.setScene(scene);
        editarEncontrosStage.show();

        // Carregar dados iniciais
        carregarTodosDados();

        // Configurar listeners
        comboEncontros.setOnAction(e -> carregarDadosEncontro());

        // Configurar cell factories
        configurarCellFactories();
    }

    private void aplicarEstilosInline(BorderPane root) {
        // Fallback para quando o CSS não for encontrado
        root.setStyle("-fx-background-color: #f0f8ff;");
    }

    private void inicializarComponentes() {
        encontrosList = FXCollections.observableArrayList();
        servicosList = FXCollections.observableArrayList();

        // Carregar todos os tipos de serviço disponíveis
        tiposServicoList = FXCollections.observableArrayList(TipoServico.values());

        // Inicializar lista de mães
        maesList = FXCollections.observableArrayList();
    }

    private void configurarCellFactories() {
        // Configurar listViewProximosEncontros para mostrar cartões
        listViewProximosEncontros.setCellFactory(param -> new ListCell<Encontro>() {
            private final VBox container = new VBox(5);
            private final HBox linha1 = new HBox(10);
            private final Label dataLabel = new Label();
            private final Label statusLabel = new Label();

            {
                linha1.getChildren().addAll(dataLabel, statusLabel);
                container.getChildren().add(linha1);
                container.setPadding(new Insets(10));
                container.getStyleClass().add("cartao-encontro");
            }

            @Override
            protected void updateItem(Encontro encontro, boolean empty) {
                super.updateItem(encontro, empty);
                if (empty || encontro == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    dataLabel.setText(dateFormatter.format(encontro.getData()) + " - LOCAL");
                    dataLabel.getStyleClass().add("cartao-data");

                    statusLabel.setText(encontro.getStatus().toString());
                    statusLabel.getStyleClass().add("cartao-status");
                    statusLabel.getStyleClass().add(getClasseStatus(encontro.getStatus()));

                    setGraphic(container);
                    setText(null);
                }
            }
        });

        // Configurar comboEncontros
        comboEncontros.setCellFactory(param -> new ListCell<Encontro>() {
            @Override
            protected void updateItem(Encontro item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(String.format("ID: %d - %s - %s",
                            item.getIdEncontro(),
                            item.getData(),
                            item.getStatus()));
                }
            }
        });

        // Configurar listViewServicos
        listViewServicos.setCellFactory(param -> new ListCell<ServicoDoEncontro>() {
            @Override
            protected void updateItem(ServicoDoEncontro servico, boolean empty) {
                super.updateItem(servico, empty);
                if (empty || servico == null) {
                    setText(null);
                } else {
                    String texto = "• " + servico.getServico().name();
                    if (servico.getMaeResponsavel() != null) {
                        texto += " - " + servico.getMaeResponsavel().getNome();
                    }
                    if (servico.getDescricao() != null && !servico.getDescricao().isEmpty()) {
                        texto += " (" + servico.getDescricao() + ")";
                    }
                    setText(texto);
                }
            }
        });
    }

    private String getClasseStatus(StatusEncontro status) {
        switch (status) {
            case NAO_REALIZADO:
                return "status-nao-realizado";
            case REALIZADO:
                return "status-realizado";
            case FUTURO:
                return "status-futuro";
            case CANCELADO:
                return "status-cancelado";
            default:
                return "";
        }
    }

    private void carregarTodosDados() {
        carregarEncontros();
        carregarMaes();
        carregarProximosEncontros();
    }

    private void carregarEncontros() {
        List<Encontro> encontros = encontroDAO.listartodos();
        encontrosList.clear();
        encontrosList.addAll(encontros);
        comboEncontros.setItems(encontrosList);

        // Carregar tipos de serviço
        comboServicos.setItems(tiposServicoList);
    }

    private void carregarProximosEncontros() {
        // Filtrar apenas encontros futuros
        ObservableList<Encontro> proximos = FXCollections.observableArrayList();
        LocalDate hoje = LocalDate.now();

        for (Encontro encontro : encontrosList) {
            if (!encontro.getData().isBefore(hoje) &&
                    encontro.getStatus() != StatusEncontro.CANCELADO) {
                proximos.add(encontro);
            }
        }

        listViewProximosEncontros.setItems(proximos);
    }

    private void carregarMaes() {
        List<Mae> maes = maeDAO.listartodos();
        maesList.clear();
        maesList.addAll(maes);
        comboMaes.setItems(maesList);

        // Configurar exibição
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
            if (encontroAtual.getServicos() != null && !encontroAtual.getServicos().isEmpty()) {
                servicosList.addAll(encontroAtual.getServicos());
            }
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
        encontroAtual = comboEncontros.getValue();
        if (encontroAtual != null) {
            Alert confirmacao = new Alert(Alert.AlertType.CONFIRMATION);
            confirmacao.setTitle("Confirmar Cancelamento");
            confirmacao.setHeaderText("Cancelar Encontro");
            confirmacao.setContentText("Deseja cancelar este encontro?\n(Status será alterado para CANCELADO)");

            confirmacao.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    try {
                        encontroAtual.setStatus(StatusEncontro.CANCELADO);
                        encontroDAO.atualizar(encontroAtual);
                        mostrarAlerta("Sucesso", "Encontro cancelado com sucesso!");
                        carregarTodosDados();
                        limparCampos();
                    } catch (Exception e) {
                        e.printStackTrace();
                        mostrarAlerta("Erro", "Erro ao cancelar encontro: " + e.getMessage());
                    }
                }
            });
        } else {
            mostrarAlerta("Aviso", "Selecione um encontro para cancelar.");
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