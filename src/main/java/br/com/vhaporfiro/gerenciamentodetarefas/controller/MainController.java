package br.com.vhaporfiro.gerenciamentodetarefas.controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import br.com.vhaporfiro.gerenciamentodetarefas.dao.TarefaDAO;
import br.com.vhaporfiro.gerenciamentodetarefas.model.Tarefa;
import br.com.vhaporfiro.gerenciamentodetarefas.util.AlertUtil;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class MainController {

    @FXML
    private TextField txtTarefa;

    @FXML
    private TextField txtDescricao;

    @FXML
    private DatePicker dpDataEntrega;

    @FXML
    private ComboBox<String> cmbStatus;

    @FXML
    private ListView<Tarefa> listTarefas;

    // Nova Label para exibir data/hora atual
    @FXML
    private Label lblDataHora;

    private TarefaDAO tarefaDAO;
    private ObservableList<Tarefa> tarefasObservable;

    //Configurar a interface logo que a tela principal é carregada.
    @FXML
    public void initialize() {
        tarefaDAO = new TarefaDAO();
        // Inicializa o ComboBox com os status
        cmbStatus.setItems(FXCollections.observableArrayList("A Fazer", "Em Andamento", "Concluído"));
        cmbStatus.getSelectionModel().selectFirst();
        // Carrega a lista de tarefas
        loadTarefas();
        // Configura evento de duplo clique para editar/excluir
        listTarefas.setOnMouseClicked((MouseEvent event) -> {
            if (event.getClickCount() == 2) {
                Tarefa tarefaSelecionada = listTarefas.getSelectionModel().getSelectedItem();
                if (tarefaSelecionada != null) {
                    openEditPopup(tarefaSelecionada);
                }
            }
        });
        // Inicia thread para atualizar data e hora em tempo real
        iniciarAtualizacaoDataHora();
    }

    //Adicionar uma nova tarefa à lista.
    @FXML
    public void handleAdicionar() {
        String titulo = txtTarefa.getText().trim();
        String descricao = txtDescricao.getText().trim();
        String status = cmbStatus.getValue();

        if (titulo.isEmpty()) {
            AlertUtil.showError("O campo de tarefa não pode estar vazio!");
            return;
        }

        if (dpDataEntrega.getValue() != null && dpDataEntrega.getValue().isBefore(java.time.LocalDate.now())) {
            AlertUtil.showError("Data de entrega não pode ser anterior ao dia atual!");
            return;
        }

        Tarefa novaTarefa = new Tarefa(titulo, descricao, dpDataEntrega.getValue(), status);
        boolean success = tarefaDAO.inserir(novaTarefa);
        if (success) {
            AlertUtil.showInfo("Tarefa adicionada com sucesso!");
            txtTarefa.clear();
            txtDescricao.clear();
            dpDataEntrega.setValue(null);
            cmbStatus.getSelectionModel().selectFirst();
            loadTarefas();
        } else {
            AlertUtil.showError("Erro ao adicionar tarefa!");
        }
    }

    //Atualizar a lista de tarefas exibida na interface.
    public void loadTarefas() {
        List<Tarefa> tarefas = tarefaDAO.listar();
        tarefasObservable = FXCollections.observableArrayList(tarefas);
        listTarefas.setItems(tarefasObservable);
    }

    //Abrir a janela (popup) para edição ou exclusão da tarefa selecionada.
    private void openEditPopup(Tarefa tarefa) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/com/vhaporfiro/gerenciamentodetarefas/EditTaskPopup.fxml"));
            Parent root = loader.load();
            // Passa a tarefa selecionada e o controller principal para o popup
            EditTaskController controller = loader.getController();
            controller.setTarefa(tarefa);
            controller.setMainController(this);
            // Cria e exibe a nova janela em modo modal
            Stage stage = new Stage();
            stage.setTitle("Editar Tarefa");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
            AlertUtil.showError("Erro ao abrir a janela de edição: " + e.getMessage());
        }
    }

    // Metodo para iniciar a atualização da data e hora atual em tempo real
    private void iniciarAtualizacaoDataHora() {
        Thread thread = new Thread(() -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            while (true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Platform.runLater(() -> {
                    lblDataHora.setText(LocalDateTime.now().format(formatter));
                });
            }
        });
        thread.setDaemon(true);
        thread.start();
    }
}
