package br.com.brunogodoif.zipcodeaddressfinder.core.domain;

import java.time.LocalDateTime;

public class ScriptExecutionDomain {

    private String scriptName;
    private LocalDateTime executedAt;

    public ScriptExecutionDomain(String scriptName, LocalDateTime executedAt) {
        this.scriptName = scriptName;
        this.executedAt = executedAt;
    }

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

    public boolean isExecuted() {
        return executedAt != null;
    }

    public void markAsExecuted() {
        this.executedAt = LocalDateTime.now();
    }
}