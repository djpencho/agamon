package ugis.age.analysys;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/monitor.analysys.spring.xml" })
public class HostLineAnalyzerTest {

    @Autowired
    private HostLineAnalyzer hostLineAnalyzer;

    @Autowired
    private HostHolder hostHolder;

    @Test
    public void lineIpTest() {

        String line = "2014-07-23 03:30:27,325 [main] INFO  ConfigurationController - ugis.age.slave.host.BLADE_1 = 10.58.61.150";
        hostLineAnalyzer.analyze(line);

        Host host = hostHolder.getHost("BLADE_1");

        assertNotNull(host);

        assertEquals("10.58.61.150", host.getIp());
    }

    @Test
    public void lineMemoryTest() {

        String line = "2014-07-23 03:30:27,326 [main] INFO  ConfigurationController - ugis.age.slave.memory.BLADE_1 = 400000";
        hostLineAnalyzer.analyze(line);

        Host host = hostHolder.getHost("BLADE_1");

        assertNotNull(host);

        assertEquals(400000, host.getMemory());
    }

}
