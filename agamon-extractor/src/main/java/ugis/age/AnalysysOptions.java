package ugis.age;

import org.kohsuke.args4j.Option;

public class AnalysysOptions {

    @Option(name = "-start", aliases = "-s", usage = "Timestamp when to start the analysys", required = true)
    private String start;

    @Option(name = "-finish", aliases = "-f", usage = "Timestamp when to end the analysys", required = true)
    private String finish;

    @Option(name = "-input", aliases = "-i", usage = "Log file of agatha to analyze", required = true)
    private String inputFilePath;

}
