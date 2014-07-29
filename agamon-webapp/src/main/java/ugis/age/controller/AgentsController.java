package ugis.age.controller;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AgentsController {

    /**
     * Saves the static list of users in model and renders it via freemarker template.
     * 
     * @param model
     * @return The index view (FTL)
     */
    @RequestMapping(value = "/agents/agathaProcessing", method = RequestMethod.POST)
    public String agathaProcessing(@ModelAttribute("model") ModelMap model) {

        InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream("/agathaProcessing.json");

        String result = null;
        try {
            result = IOUtils.toString(resourceAsStream);
        } catch (IOException e) {
            model.addAttribute("error", e.getMessage());

            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(resourceAsStream);
        }

        model.addAttribute("result", result);

        return "response";
    }

}