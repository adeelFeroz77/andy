package integration;

import nl.tudelft.cse1110.andy.result.Result;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.time.Duration.ofSeconds;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;

@EnabledOnOs({OS.LINUX, OS.MAC})
public class ExternalProcessTest extends IntegrationTestBase {

    @BeforeAll
    static void copyShellScripts() throws IOException {
        Files.writeString(Path.of("/tmp/andy_test_external_process_end_signal.sh"), """
                echo "test 123"
                echo "endsignal"
                sleep 15
                echo "should be killed before this"
                """);
    }

    @AfterAll
    static void shellCleanup() throws IOException {
        Files.deleteIfExists(Path.of("/tmp/andy_test_external_process_end_signal.sh"));
    }

    @Test
    void externalProcessGracefulExit() {
        Result result = run("EmptyLibrary", "EmptySolution",
                "ExternalProcessGracefulExitConfiguration");

        assertThat(result.getExternalProcessOutput()).isEqualTo("hello\n");
    }

    @Test
    void externalProcessEndSignal() {
        assertTimeoutPreemptively(ofSeconds(10), () -> {

            Result result = run("EmptyLibrary", "EmptySolution",
                    "ExternalProcessEndSignalConfiguration");

            assertThat(result.getExternalProcessOutput())
                    .contains("test 123\nendsignal\n")
                    .doesNotContain("should be killed before this");

        });
    }
}