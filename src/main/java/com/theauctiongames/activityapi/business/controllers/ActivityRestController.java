package com.theauctiongames.activityapi.business.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.theauctiongames.activityapi.business.models.ActivityModel;
import com.theauctiongames.activityapi.business.services.ActivityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * The rest controller for providing the front-facing activity api.
 */
@RestController
@RequestMapping("/api/v1")
public class ActivityRestController {

    /**
     * The activity service.
     */
    private final ActivityService service;

    /**
     * Construct an activity rest controller.
     *
     * @param service the activity service
     */
    public ActivityRestController(ActivityService service) {
        this.service = service;
    }

    /**
     * The API endpoint for getting activities.
     *
     * @param limit the max number of activities to retrieve
     * @return a list of activities
     */
    @GetMapping(path = "/activity", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> getActivity(
            @RequestParam(value = "limit", required = false, defaultValue = "10") int limit) {
        try {
            // Return the list of activity
            return new ResponseEntity<>(this.service.getActivity(limit), HttpStatus.OK);
        } catch (Exception exception) {
            // Output error
            exception.printStackTrace();

            // Return internal server error
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * The API endpoint for getting activities by user id.
     *
     * @param userId the user id
     * @param limit  the max number of activities to retrieve
     * @return a list of activities
     */
    @GetMapping(path = "/activity/user/{userId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> getActivityByUserId(
            @PathVariable String userId,
            @RequestParam(value = "limit", required = false, defaultValue = "10") int limit) {
        try {
            // Return the list of activity
            return new ResponseEntity<>(this.service.getActivityByUserId(userId, limit), HttpStatus.OK);
        } catch (Exception exception) {
            // Output error
            exception.printStackTrace();

            // Return internal server error
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * The API endpoint for creating an activity.
     *
     * @param activity the activity
     * @return the created activity
     */
    @PostMapping(path = "/activity", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> postActivity(@RequestBody ActivityModel activity) {
        try {
            // Create the activity
            if (this.service.createActivity(activity)) {
                // Return success
                return new ResponseEntity<>(HttpStatus.CREATED);
            } else {
                // Return conflict
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
        } catch (Exception exception) {
            // Output error
            exception.printStackTrace();

            // Return internal server error
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
