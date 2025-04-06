module br.com.vhaporfiro.gerenciamentodetarefas {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens br.com.vhaporfiro.gerenciamentodetarefas to javafx.fxml;
    exports br.com.vhaporfiro.gerenciamentodetarefas;
}