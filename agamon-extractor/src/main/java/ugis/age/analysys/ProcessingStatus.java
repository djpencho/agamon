package ugis.age.analysys;


public class ProcessingStatus {

    private long timestamp;
    private long cpuInUse;
    private long memoryInUse;

    public ProcessingStatus(long timestamp, long cpuInUse, long memoryInUse) {
        super();
        this.timestamp = timestamp;
        this.cpuInUse = cpuInUse;
        this.memoryInUse = memoryInUse;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public long getCpuInUse() {
        return cpuInUse;
    }

    public void setCpuInUse(long cpuInUse) {
        this.cpuInUse = cpuInUse;
    }

    public long getMemoryInUse() {
        return memoryInUse;
    }

    public void setMemoryInUse(long memoryInUse) {
        this.memoryInUse = memoryInUse;
    }

}
