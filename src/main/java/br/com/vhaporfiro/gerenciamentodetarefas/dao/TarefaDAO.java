package br.com.vhaporfiro.gerenciamentodetarefas.dao;

import br.com.vhaporfiro.gerenciamentodetarefas.config.ConnectionFactory;
import br.com.vhaporfiro.gerenciamentodetarefas.model.Tarefa;
import br.com.vhaporfiro.gerenciamentodetarefas.util.AlertUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TarefaDAO {

    public boolean inserir(Tarefa tarefa) {
        String sql = "INSERT INTO tarefas (titulo, status) VALUES (?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, tarefa.getTitulo());
            ps.setString(2, tarefa.getStatus());
            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    tarefa.setId(rs.getInt(1));
                }
                return true;
            }
        } catch (SQLException e) {
            AlertUtil.showError("Erro ao inserir tarefa: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    public List<Tarefa> listar() {
        List<Tarefa> tarefas = new ArrayList<>();
        String sql = "SELECT id, titulo, status FROM tarefas ORDER BY id";
        try (Connection conn = ConnectionFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Tarefa tarefa = new Tarefa();
                tarefa.setId(rs.getInt("id"));
                tarefa.setTitulo(rs.getString("titulo"));
                tarefa.setStatus(rs.getString("status"));
                tarefas.add(tarefa);
            }
        } catch (SQLException e) {
            AlertUtil.showError("Erro ao listar tarefas: " + e.getMessage());
            e.printStackTrace();
        }
        return tarefas;
    }

    public boolean atualizar(Tarefa tarefa) {
        String sql = "UPDATE tarefas SET titulo = ?, status = ? WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, tarefa.getTitulo());
            ps.setString(2, tarefa.getStatus());
            ps.setInt(3, tarefa.getId());
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            AlertUtil.showError("Erro ao atualizar tarefa: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    public boolean remover(int id) {
        String sql = "DELETE FROM tarefas WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            AlertUtil.showError("Erro ao remover tarefa: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }
}

