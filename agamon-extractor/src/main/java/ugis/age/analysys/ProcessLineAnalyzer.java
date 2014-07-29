package ugis.age.analysys;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service("processLineAnalyzer")
@Scope("prototype")
@LineAnalyzerBean
public class ProcessLineAnalyzer implements LineAnalyzer {

    private static final Logger logger = Logger.getLogger(ProcessLineAnalyzer.class);

    @Autowired
    private SlaveProcessingStatusHolder slaveProcessingStatusHolder;

    private static final String LOG_TIMESTAMP_PATTERN = "yyyy-MM-dd HH:mm:ss,SSS";

    private static final String PROCESS_ACCEPTED_PH = "Process ACCEPTED in ";
    private static final String ACCEPTED_PH = "Accepted request was";
    private static final String COMPUTATION_INFO_PH = "AgathaComputationInfo [";
    private static final String PROCESS_FINISHED_PH = "End of remote computation";

    private static final String MEM_PH = "memorySize=";
    private static final String CPU_PH = "cpuSize=";

    private LinkedList<String> acceptedQueue;

    private Map<String, String> assignedSlaveMap;

    @PostConstruct
    public void init() {
        acceptedQueue = new LinkedList<String>();
        assignedSlaveMap = new HashMap<String, String>();
    }

    @Override
    public void analyze(String line) throws AnalyzerException {

        try {
            if (StringUtils.contains(line, PROCESS_ACCEPTED_PH)) {

                int indexOf = StringUtils.indexOf(line, PROCESS_ACCEPTED_PH);
                String substring = line.substring(indexOf);

                String acceptingHostName = substring.trim().replace(PROCESS_ACCEPTED_PH, "");

                acceptedQueue.add(acceptingHostName);
            } else if (StringUtils.contains(line, ACCEPTED_PH)) {

                String hostName = acceptedQueue.removeFirst();
                if (hostName != null) {
                    long actionTs = extractTimestamp(line);

                    String cleanLine = extractComputationInfo(line);
                    String[] split = cleanLine.split(",");

                    Long memoryResource = null;
                    Long cpuResource = null;

                    for (String s : split) {

                        if (s.contains(MEM_PH)) {
                            String mem = s.replace(MEM_PH, "").trim();
                            memoryResource = Long.valueOf(mem);
                        } else if (s.contains(CPU_PH)) {
                            String cpu = s.replace(CPU_PH, "").trim();
                            cpuResource = Long.valueOf(cpu);
                        }
                    }

                    assignedSlaveMap.put(cleanLine, hostName);
                    slaveProcessingStatusHolder.add(hostName, actionTs, cpuResource, memoryResource);
                }
            } else if (StringUtils.contains(line, PROCESS_FINISHED_PH)) {

                long actionTs = extractTimestamp(line);
                String cleanLine = extractComputationInfo(line);

                String hostName = assignedSlaveMap.get(cleanLine);

                String[] split = cleanLine.split(",");

                Long memoryResource = null;
                Long cpuResource = null;

                for (String s : split) {

                    if (s.contains(MEM_PH)) {
                        String mem = s.replace(MEM_PH, "").trim();
                        memoryResource = Long.valueOf(mem);
                    } else if (s.contains(CPU_PH)) {
                        String cpu = s.replace(CPU_PH, "").trim();
                        cpuResource = Long.valueOf(cpu);
                    }
                }

                slaveProcessingStatusHolder.add(hostName, actionTs, -cpuResource, -memoryResource);
            }

        } catch (Throwable e) {
            throw new AnalyzerException(e);
        }
    }

    private String extractComputationInfo(String line) {

        int indexOf = StringUtils.indexOf(line, COMPUTATION_INFO_PH);

        String substring = line.substring(indexOf);
        substring = substring.replace(COMPUTATION_INFO_PH, "");

        int indexOfEnd = substring.indexOf("]");

        return substring.substring(0, indexOfEnd);
    }

    private long extractTimestamp(String line) throws ParseException {
        String tsString = line.substring(0, 26);
        tsString = tsString.trim();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(LOG_TIMESTAMP_PATTERN);
        Date parse = simpleDateFormat.parse(tsString);

        return parse.getTime();
    }

}
