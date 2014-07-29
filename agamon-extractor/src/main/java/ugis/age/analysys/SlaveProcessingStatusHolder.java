package ugis.age.analysys;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.google.gson.Gson;

@Service("slaveProcessingStatusHolder")
public class SlaveProcessingStatusHolder implements JsonResultHolder, ResultHolder<String, LinkedList<ProcessingStatus>> {

    public static final String HOST_PH = "ugis.age.slave.host.";

    private HashMap<String, LinkedList<ProcessingStatus>> slaveMap;

    @PostConstruct
    public void init() throws IOException {
        this.slaveMap = new HashMap<String, LinkedList<ProcessingStatus>>();
    }

    public void add(String hostName, long timestamp, long cpuQty, long memQty) {
        if (hostName == null) {
            throw new IllegalArgumentException("hostName is null");
        }

        LinkedList<ProcessingStatus> list = this.slaveMap.get(hostName);
        if (list == null) {
            list = new LinkedList<ProcessingStatus>();
            slaveMap.put(hostName, list);
        }

        long cpuStatus = cpuQty;
        long memStatus = memQty;

        if (!list.isEmpty()) {
            ProcessingStatus last = list.getLast();
            cpuStatus += last.getCpuInUse();
            memStatus += last.getMemoryInUse();
        }

        ProcessingStatus newStatus = new ProcessingStatus(timestamp, cpuStatus, memStatus);
        list.add(newStatus);
    }

    public LinkedList<ProcessingStatus> getProcessingStatus(String hostName) {
        return this.slaveMap.get(hostName);
    }

    @Override
    public String getJsonResult() {
        Gson gson = new Gson();
        String json = gson.toJson(slaveMap);
        return json;
    }

    @Override
    public Map<String, LinkedList<ProcessingStatus>> getResultMap() {
        return slaveMap;
    }
}
