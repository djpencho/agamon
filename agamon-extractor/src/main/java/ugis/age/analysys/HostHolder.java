package ugis.age.analysys;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.google.gson.Gson;

@Service("hostHolder")
public class HostHolder implements JsonResultHolder, ResultHolder<String, Host> {

    private Map<String, Host> hostMap;

    @PostConstruct
    public void init() {
        this.hostMap = new HashMap<String, Host>();
    }

    public void addHost(Host host) {
        hostMap.put(host.getName(), host);
    }

    public Host getHost(String name) {
        return hostMap.get(name);
    }

    public Collection<Host> getAllHosts() {
        return hostMap.values();
    }

    @Override
    public String getJsonResult() {
        Gson gson = new Gson();
        String json = gson.toJson(hostMap);
        return json;
    }

    @Override
    public Map<String, Host> getResultMap() {
        return hostMap;
    }
}
