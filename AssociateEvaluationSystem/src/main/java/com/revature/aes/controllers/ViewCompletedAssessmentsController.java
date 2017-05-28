package com.revature.aes.controllers;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.revature.aes.beans.Assessment;
import com.revature.aes.beans.AssessmentRequest;
import com.revature.aes.beans.CategoryRequest;
import com.revature.aes.beans.Template;
import com.revature.aes.beans.TemplateQuestion;
import com.revature.aes.beans.User;
import com.revature.aes.config.IpConf;
import com.revature.aes.logging.Logging;
import com.revature.aes.service.AssessmentRequestService;
import com.revature.aes.service.AssessmentService;
import com.revature.aes.service.CategoryRequestService;
import com.revature.aes.service.QuestionService;
import com.revature.aes.service.S3Service;
import com.revature.aes.service.SystemTemplate;
import com.revature.aes.service.UserService;
/** Handles the REST requests for making assessments.
 *
 * @author Gabe
 *
 */
@RestController
public class ViewCompletedAssessmentsController {

    @Autowired
    UserService service;

    @RequestMapping(value="/viewCompletedAssessmentsController",method = RequestMethod.GET)
    public String login() {
        return "redirect:viewCompletedAssessmentsController";
    }
}