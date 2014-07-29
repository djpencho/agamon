package ugis.age.analysys;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.LinkedList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/monitor.analysys.spring.xml" })
public class ProcessLineAnalyzerTest {

    @Autowired
    private ProcessLineAnalyzer processLineAnalyzer;

    @Autowired
    private SlaveProcessingStatusHolder slaveProcessingStatusHolder;

    @Test
    public void lineAcceptedTest() throws AnalyzerException {

        String line0 = "2014-07-23 03:31:15,189 [Thread-1] INFO  SlaveClientHolder - Process ACCEPTED in BLADE_2";
        String line1 = "2014-07-23 03:31:15,189 [Thread-1] INFO  SlaveClientHolder - Accepted request was: [AgathaComputationInfo [launcherClass=ugis.age.process.management.BatchAgathaWorkflowProcess, asOfDate=20140722, generationDate=20140723_033053, simulationDate=20141120, task=1012, workflow=/workflow/workflow_metric_test_gridpoint.step, configuration=/agatha_1012.properties, computationLabel=10, profilingActive=false, additionalArgs=, toString()=ComputationInfo [launcherClass=ugis.age.process.management.BatchAgathaWorkflowProcess, masterHost=10.58.61.57, masterPort=10099, forcedSlave=null, assignedSlave=null, memorySize=20000, cpuSize=3]]]";

        processLineAnalyzer.analyze(line0);
        processLineAnalyzer.analyze(line1);
        LinkedList<ProcessingStatus> psList = slaveProcessingStatusHolder.getProcessingStatus("BLADE_2");

        assertNotNull(psList);

        assertEquals(3, psList.get(0).getCpuInUse());
        assertEquals(20000, psList.get(0).getMemoryInUse());
    }

    @Test
    public void lineEndTest() throws AnalyzerException {

        String line0 = "2014-07-23 03:30:38,121 [Thread-1] INFO  SlaveClientHolder - Process ACCEPTED in BLADE_3";
        String line1 = "2014-07-23 03:30:38,121 [Thread-1] INFO  SlaveClientHolder - Accepted request was: [AgathaComputationInfo [launcherClass=ugis.age.process.management.BatchAgathaWorkflowProcess, asOfDate=20140722, generationDate=20140723_033053, simulationDate=20140722, task=1012, workflow=/workflow/workflow_metric_test_pv.step, configuration=/agatha_1012.properties, computationLabel=0, profilingActive=false, additionalArgs=, toString()=ComputationInfo [launcherClass=ugis.age.process.management.BatchAgathaWorkflowProcess, masterHost=10.58.61.57, masterPort=10099, forcedSlave=null, assignedSlave=null, memorySize=20000, cpuSize=3]]]";

        processLineAnalyzer.analyze(line0);
        processLineAnalyzer.analyze(line1);

        LinkedList<ProcessingStatus> psList = slaveProcessingStatusHolder.getProcessingStatus("BLADE_3");

        assertNotNull(psList);

        assertEquals(3, psList.getLast().getCpuInUse());
        assertEquals(20000, psList.getLast().getMemoryInUse());

        String line2 = "2014-07-23 03:31:12,643 [RMI TCP Connection(2)-10.58.61.152] INFO  MasterRmiImpl - End of remote computation AgathaComputationInfo [launcherClass=ugis.age.process.management.BatchAgathaWorkflowProcess, asOfDate=20140722, generationDate=20140723_033053, simulationDate=20140722, task=1012, workflow=/workflow/workflow_metric_test_pv.step, configuration=/agatha_1012.properties, computationLabel=0, profilingActive=false, additionalArgs=, toString()=ComputationInfo [launcherClass=ugis.age.process.management.BatchAgathaWorkflowProcess, masterHost=10.58.61.57, masterPort=10099, forcedSlave=null, assignedSlave=null, memorySize=20000, cpuSize=3]]";

        processLineAnalyzer.analyze(line2);

        assertEquals(0, psList.getLast().getCpuInUse());
        assertEquals(0, psList.getLast().getMemoryInUse());
    }

}
