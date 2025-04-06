package br.com.vhaporfiro.gerenciamentodetarefas.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    private ConnectionFactory() {};
    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(
                    "jdbc:postgresql://localhost/poo_bd_lista_tarefas",
                    "postgres",
                    "Povobonito1234"
            );
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
