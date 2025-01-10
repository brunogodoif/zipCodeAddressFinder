package br.com.brunogodoif.zipcodeaddressfinder.adapters.outbound;

import br.com.brunogodoif.zipcodeaddressfinder.adapters.outbound.persistence.entities.ScriptExecutionControl;
import br.com.brunogodoif.zipcodeaddressfinder.adapters.outbound.persistence.repository.ScriptExecutionRepository;
import br.com.brunogodoif.zipcodeaddressfinder.core.domain.ScriptExecutionDomain;
import br.com.brunogodoif.zipcodeaddressfinder.core.ports.outbound.ScriptExecutionAdapterPort;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
public class ScriptExecutionAdapterImpl implements ScriptExecutionAdapterPort {

    private final ScriptExecutionRepository scriptExecutionRepository;

    public ScriptExecutionAdapterImpl(ScriptExecutionRepository scriptExecutionRepository) {
        this.scriptExecutionRepository = scriptExecutionRepository;
    }

    public boolean hasScriptBeenExecuted(String scriptName) {
        Optional<ScriptExecutionControl> scriptExecutionControl = scriptExecutionRepository.findByScriptName(
                scriptName);
        return scriptExecutionControl.map(script -> script.getExecutedAt() != null).orElse(false);
    }

    public void markScriptAsExecuted(String scriptName) {
        ScriptExecutionControl control = new ScriptExecutionControl();
        control.setScriptName(scriptName);
        control.setExecutedAt(LocalDateTime.now());
        scriptExecutionRepository.save(control);
    }

    public ScriptExecutionDomain findScriptExecution(String scriptName) {
        Optional<ScriptExecutionControl> control = scriptExecutionRepository.findByScriptName(scriptName);
        return control.map(script -> new ScriptExecutionDomain(script.getScriptName(), script.getExecutedAt()))
                      .orElse(null);
    }

    public void saveScriptExecution(ScriptExecutionDomain scriptExecution) {
        ScriptExecutionControl control = new ScriptExecutionControl();
        control.setScriptName(scriptExecution.getScriptName());
        control.setExecutedAt(scriptExecution.getExecutedAt());
        scriptExecutionRepository.save(control);
    }
}