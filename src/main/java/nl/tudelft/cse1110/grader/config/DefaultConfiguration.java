package nl.tudelft.cse1110.grader.config;

import nl.tudelft.cse1110.codechecker.engine.CheckScript;

import java.util.List;

public class DefaultConfiguration implements Configuration {

    private final String workingDir;
    private final String librariesDir;
    private final String reportsDir;
    private final CheckScript codeCheckerScript;
    private List<String> fullClassNames;

    public DefaultConfiguration(String workingDir, String librariesDir, String reportsDir, CheckScript codeCheckerScript) {
        this.workingDir = workingDir;
        this.librariesDir = librariesDir;
        this.reportsDir = reportsDir;
        this.codeCheckerScript = codeCheckerScript;
    }

    @Override
    public String getWorkingDir() {
        return this.workingDir;
    }

    @Override
    public String getLibrariesDir() {
        return this.librariesDir;
    }

    public String getReportsDir() {
        return reportsDir;
    }

    @Override
    public CheckScript getCodeCheckerScript() {
        return this.codeCheckerScript;
    }

    @Override
    public void setNewClassNames(List<String> fullClassNames) {
        this.fullClassNames = fullClassNames;
    }

    @Override
    public List<String> getNewClassNames() {
        return fullClassNames;
    }
}
