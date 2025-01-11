package br.com.brunogodoif.zipcodeaddressfinder.adapters.outbound.persistence.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "script_execution_control")
public class ScriptExecutionControl implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id private String scriptName;
    private LocalDateTime executedAt;

    // Getters e Setters
    public String getScriptName() {
        return scriptName;
    }

    public void setScriptName(String scriptName) {
        this.scriptName = scriptName;
    }

    public LocalDateTime getExecutedAt() {
        return executedAt;
    }

    public void setExecutedAt(LocalDateTime executedAt) {
        this.executedAt = executedAt;
    }

}
