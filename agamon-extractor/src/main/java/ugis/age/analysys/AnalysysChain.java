package ugis.age.analysys;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

@Service("analysysChain")
public class AnalysysChain implements ApplicationContextAware {
    private static ApplicationContext applicationContext = null;

    private static final Logger logger = Logger.getLogger(AnalysysChain.class);

    private List<LineAnalyzer> chain;

    @PostConstruct
    public void init() {
        chain = new LinkedList<LineAnalyzer>();
        Map<String, Object> beansWithAnnotation = applicationContext.getBeansWithAnnotation(LineAnalyzerBean.class);

        chain.addAll((Collection<? extends LineAnalyzer>) beansWithAnnotation.values());
    }

    public void analyze(String line) {
        for (LineAnalyzer la : chain) {
            try {
                la.analyze(line);
            } catch (AnalyzerException e) {
                logger.error(e);
            }
        }
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

}
