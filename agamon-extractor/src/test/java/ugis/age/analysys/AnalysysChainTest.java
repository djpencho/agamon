package ugis.age.analysys;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.LineIterator;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ugis.age.TestUtils;

import com.google.gson.Gson;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/monitor.analysys.spring.xml" })
public class AnalysysChainTest {

    @Autowired
    private AnalysysChain analysysChain;

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private SlaveProcessingStatusHolder slaveProcessingStatusHolder;

    @Test
    public void test() throws IOException {

        String inputFile = "/master.log";
        String resourceAbsolutePath = TestUtils.getResourceAbsolutePath(inputFile);

        LineIterator it = FileUtils.lineIterator(new File(resourceAbsolutePath), "UTF-8");
        try {
            while (it.hasNext()) {
                String line = it.nextLine();
                analysysChain.analyze(line);
            }
        } finally {
            it.close();
        }

        Collection<Host> allHosts = hostHolder.getAllHosts();
        Assert.assertEquals(3, allHosts.size());

        for (Host h : allHosts) {
            LinkedList<ProcessingStatus> processingStatus = slaveProcessingStatusHolder.getProcessingStatus(h.getName());
            Assert.assertTrue(processingStatus.size() > 1);
        }

        String jsonFile = "/home/pencho/temp/processing/agathaProcessing.json";

        ProcessingJson pj = new ProcessingJson(hostHolder.getResultMap(), slaveProcessingStatusHolder.getResultMap());
        Gson g = new Gson();
        String json = g.toJson(pj);

        json = "var agathaProcessingData = " + json;

        BufferedWriter writer = null;
        try {
            File logFile = new File(jsonFile);

            System.out.println(logFile.getCanonicalPath());

            writer = new BufferedWriter(new FileWriter(logFile));
            writer.write(json);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(writer);
        }

    }

}
