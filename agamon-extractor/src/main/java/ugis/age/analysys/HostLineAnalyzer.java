package ugis.age.analysys;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service("hostLineAnalyzer")
@Scope("prototype")
@LineAnalyzerBean
public class HostLineAnalyzer implements LineAnalyzer {

    @Autowired
    private HostHolder hostHolder;

    private static final String HOST_PH = "ugis.age.slave.host.";
    private static final String MEMORY_PH = "ugis.age.slave.memory.";

    @Override
    public void analyze(String line) {
        if (StringUtils.contains(line, HOST_PH)) {

            int indexOf = StringUtils.indexOf(line, HOST_PH);
            String substring = line.substring(indexOf);
            String[] split = substring.split("=");

            String hostName = split[0].trim().replace(HOST_PH, "");
            String hostIp = split[1].trim();

            Host host = getHost(hostName);
            host.setIp(hostIp);
        }

        if (StringUtils.contains(line, MEMORY_PH)) {

            int indexOf = StringUtils.indexOf(line, MEMORY_PH);
            String substring = line.substring(indexOf);
            String[] split = substring.split("=");

            String hostName = split[0].trim().replace(MEMORY_PH, "");
            String hostMemory = split[1].trim();

            Host host = getHost(hostName);
            host.setMemory(Long.valueOf(hostMemory));

        }

    }

    private Host getHost(String hostName) {
        Host host = hostHolder.getHost(hostName);
        if (host == null) {
            host = new Host();
            host.setName(hostName);
            hostHolder.addHost(host);
        }
        return host;
    }

}
