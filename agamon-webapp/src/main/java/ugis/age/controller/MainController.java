package ugis.age.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MainController {
    /**
     * Static list of users to simulate Database
     */
    private static List<String> userList = new ArrayList<String>();

    // Initialize the list with some data for index screen
    static {
        userList.add("Gates");
        userList.add("Jobs");
        userList.add("Page");
        userList.add("Brin");
        userList.add("Ellison");
    }

    /**
     * Saves the static list of users in model and renders it via freemarker template.
     * 
     * @param model
     * @return The index view (FTL)
     */
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(@ModelAttribute("model") ModelMap model) {

        model.addAttribute("userList", userList);

        return null;
    }

    @RequestMapping(value = "/tab/**", method = RequestMethod.GET)
    public String tab(@ModelAttribute("model") ModelMap model) {

        System.out.println("bab");
        model.addAttribute("userList", userList);

        return null;
    }

    /**
     * Add a new user into static user lists and display the same into FTL via redirect
     * 
     * @param user
     * @return Redirect to /index page to display user list
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(@ModelAttribute("user") String user) {

        synchronized (userList) {
            userList.add(user);
        }

        return "redirect:index.html";
    }

}