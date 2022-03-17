package web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/bt")
public class BugTrackerController {
//    private ApplicationService applicationService;
//
//    @GetMapping("/applications")
//    public ResponseEntity<List<Application>> getAllApplications() {
//        List<Application> list = applicationService.listApplications();
//        return new ResponseEntity<>(list, HttpStatus.OK);
//    }
//
//    @Autowired
//    public void setApplicationService(ApplicationService applicationService) {
//        this.applicationService = applicationService;
//    }
}
