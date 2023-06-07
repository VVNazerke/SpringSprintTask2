package techorda.bitlab.springSprintTask2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import techorda.bitlab.springSprintTask2.model.ApplicationRequest;
import techorda.bitlab.springSprintTask2.repository.RequestRepository;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private RequestRepository requestRepository;

    @GetMapping(value = "/")
    public String index(Model model) {
        ArrayList<ApplicationRequest> requests = (ArrayList<ApplicationRequest>) requestRepository.findAll(Sort.by(Sort.Direction.ASC, "handled"));
        model.addAttribute("zaprosy", requests);
        return "index";
    }

    @GetMapping(value = "/add-request")
    public String addRequestPage() {
        return "addRequest";
    }

    @PostMapping(value = "add-request")
    public String addRequest(@RequestParam(name = "a_username") String username,
                             @RequestParam(name = "a_course") String course,
                             @RequestParam(name = "a_comment") String comment,
                             @RequestParam(name = "a_phone") String phone) {
        ApplicationRequest applicationRequest = new ApplicationRequest();
        applicationRequest.setUserName(username);
        applicationRequest.setCourseName(course);
        applicationRequest.setCommentary(comment);
        applicationRequest.setPhone(phone);
        requestRepository.save(applicationRequest);
        return "redirect:/";
    }

    @GetMapping(value = "/details/{id}")
    public String detailsPage(Model model,
                              @PathVariable(name = "id") Long id) {
        ApplicationRequest applicationRequest = requestRepository.findById(id).orElse(null);
        model.addAttribute("zapros", applicationRequest);
        return "details";
    }

    @PostMapping(value = "/toHandle")
    public String toHandleRequest(@RequestParam(name = "h_id") Long id) {
        ApplicationRequest applicationRequest = requestRepository.findById(id).orElse(null);
        applicationRequest.setHandled(true);
        requestRepository.save(applicationRequest);
        return "redirect:/details/" + id;
    }

    @PostMapping(value = "/delete")
    public String deleteRequest(@RequestParam(name = "d_id") Long id) {
        requestRepository.deleteById(id);
        return "redirect:/";
    }

    @GetMapping(value = "/new-requests")
    public String newRequests(Model model) {
        List<ApplicationRequest> requests = requestRepository.findAllByHandledEquals(false);
        model.addAttribute("zaps", requests);
        return "newRequests";
    }

    @GetMapping(value = "/handled-requests")
    public String handledRequests(Model model) {
        List<ApplicationRequest> requests = requestRepository.findAllByHandledEquals(true);
        model.addAttribute("zaps", requests);
        return "handledRequests";
    }

}
