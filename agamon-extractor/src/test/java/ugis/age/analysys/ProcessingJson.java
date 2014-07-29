package ugis.age.analysys;

import java.util.LinkedList;
import java.util.Map;

public class ProcessingJson {

    private Map<String, Host> hostMap;
    private Map<String, LinkedList<ProcessingStatus>> slaveProcessingMap;

    public ProcessingJson(Map<String, Host> hostMap, Map<String, LinkedList<ProcessingStatus>> slaveProcessingMap) {
        this.hostMap = hostMap;
        this.slaveProcessingMap = slaveProcessingMap;
    }

    public Map<String, Host> getHostMap() {
        return hostMap;
    }

    public void setHostMap(Map<String, Host> hostMap) {
        this.hostMap = hostMap;
    }

    public Map<String, LinkedList<ProcessingStatus>> getSlaveProcessingMap() {
        return slaveProcessingMap;
    }

    public void setSlaveProcessingMap(Map<String, LinkedList<ProcessingStatus>> slaveProcessingMap) {
        this.slaveProcessingMap = slaveProcessingMap;
    }

}
