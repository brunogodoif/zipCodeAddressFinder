package br.com.brunogodoif.zipcodeaddressfinder.core.ports.outbound;

import br.com.brunogodoif.zipcodeaddressfinder.core.domain.ScriptExecutionDomain;

public interface ScriptExecutionAdapterPort {

    boolean hasScriptBeenExecuted(String scriptName);

    void markScriptAsExecuted(String scriptName);

    ScriptExecutionDomain findScriptExecution(String scriptName);

    void saveScriptExecution(ScriptExecutionDomain scriptExecution);
}
