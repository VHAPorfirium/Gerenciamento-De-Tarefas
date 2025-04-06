module br.com.vhaporfiro.gerenciamentodetarefas {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    // Abre para reflexão os pacotes que contêm classes acessadas via FXML:
    opens br.com.vhaporfiro.gerenciamentodetarefas to javafx.fxml;
    opens br.com.vhaporfiro.gerenciamentodetarefas.controller to javafx.fxml;
    opens br.com.vhaporfiro.gerenciamentodetarefas.config to javafx.fxml;

    // Exporta os pacotes para acesso por outros módulos, se necessário
    exports br.com.vhaporfiro.gerenciamentodetarefas;
    exports br.com.vhaporfiro.gerenciamentodetarefas.controller;
    exports br.com.vhaporfiro.gerenciamentodetarefas.dao;
    exports br.com.vhaporfiro.gerenciamentodetarefas.model;
    exports br.com.vhaporfiro.gerenciamentodetarefas.util;
    exports br.com.vhaporfiro.gerenciamentodetarefas.config;
}