package br.com.vhaporfiro.gerenciamentodetarefas.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import br.com.vhaporfiro.gerenciamentodetarefas.dao.TarefaDAO;
import br.com.vhaporfiro.gerenciamentodetarefas.model.Tarefa;
import br.com.vhaporfiro.gerenciamentodetarefas.util.AlertUtil;

public class EditTaskController {

    @FXML
    private TextField txtEditarTarefa;

    @FXML
    private TextField txtDescricao;

    @FXML
    private DatePicker dpDataEntrega;

    @FXML
    private ComboBox<String> cmbStatus;

    private Tarefa tarefa;
    private TarefaDAO tarefaDAO = new TarefaDAO();
    private MainController mainController;

    //Este metodo é responsável por receber a tarefa que será editada.
    public void setTarefa(Tarefa tarefa) {
        this.tarefa = tarefa;
        txtEditarTarefa.setText(tarefa.getTitulo());
        txtDescricao.setText(tarefa.getDescricao());
        dpDataEntrega.setValue(tarefa.getDataEntrega());
        cmbStatus.setValue(tarefa.getStatus());
    }

    //Permite que o controller da janela de edição conheça o controller principal.
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @FXML
    public void initialize() {
        // Preenche o ComboBox com os status disponíveis.
        cmbStatus.setItems(FXCollections.observableArrayList("A Fazer", "Em Andamento", "Concluído"));
    }

    //Atualizar a tarefa com o novo título fornecido pelo usuario.
    @FXML
    public void handleSalvar() {
        String novoTitulo = txtEditarTarefa.getText().trim();
        if (novoTitulo.isEmpty()) {
            AlertUtil.showError("O campo de tarefa não pode estar vazio!");
            return;
        }

        if (dpDataEntrega.getValue() != null && dpDataEntrega.getValue().isBefore(java.time.LocalDate.now())) {
            AlertUtil.showError("Data de entrega não pode ser anterior ao dia atual!");
            return;
        }

        tarefa.setTitulo(novoTitulo);
        tarefa.setDescricao(txtDescricao.getText().trim());
        tarefa.setDataEntrega(dpDataEntrega.getValue());
        tarefa.setStatus(cmbStatus.getValue());

        boolean success = tarefaDAO.atualizar(tarefa);
        if (success) {
            AlertUtil.showInfo("Tarefa atualizada com sucesso!");
            if (mainController != null) {
                mainController.loadTarefas();
            }
            fecharJanela();
        } else {
            AlertUtil.showError("Erro ao atualizar a tarefa!");
        }
    }

    //Excluir a tarefa atual.
    @FXML
    public void handleExcluir() {
        boolean success = tarefaDAO.remover(tarefa.getId());
        if (success) {
            AlertUtil.showInfo("Tarefa removida com sucesso!");
            if (mainController != null) {
                mainController.loadTarefas();
            }
            fecharJanela();
        } else {
            AlertUtil.showError("Erro ao remover a tarefa!");
        }
    }

    //Fechar a janela de edição.
    private void fecharJanela() {
        Stage stage = (Stage) txtEditarTarefa.getScene().getWindow();
        stage.close();
    }
}
