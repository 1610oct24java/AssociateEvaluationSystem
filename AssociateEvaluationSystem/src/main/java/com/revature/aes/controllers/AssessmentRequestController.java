package com.revature.aes.controllers;

import com.revature.aes.beans.AssessmentRequest;
import com.revature.aes.beans.User;
import com.revature.aes.beans.UserUpdateHolder;
import com.revature.aes.logging.Logging;
import com.revature.aes.service.AssessmentRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 *
 * Rest Controller to submit a new assessment request template.
 * Add any functionality here that would be related to the
 * AssessmentRequest and should be exposed.
 *
 * Access is limited to admin.
 *
 * @author Nick "LURCH" Jurczak
 *
 */
@RestController
public class AssessmentRequestController {

    @Autowired
    Logging log;

    @Autowired
    AssessmentRequestService assessmentRequestService;

    /**
     * Endpoint used to update and Assessment Request object.
     *
     * @param id
     * 		The id of the AssessmentRequest object to be altered
     */
    @RequestMapping(value="assessmentrequest/{id}/", method= RequestMethod.PUT)
    public void updateEmployee(@RequestBody AssessmentRequest assessmentRequest, @PathVariable Integer id){
        log.info("Updating AssessmentRequest with id: " + id);
        log.info("New AssessmentRequest: " + assessmentRequest);
        assessmentRequestService.saveAssessmentRequest(assessmentRequest);
        log.info("Assessment Request Updated");

    }

}
