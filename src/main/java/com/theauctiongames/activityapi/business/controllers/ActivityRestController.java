package com.theauctiongames.activityapi.business.controllers;

import com.theauctiongames.activityapi.business.services.ActivityService;

/**
 * The controller for the activity api.
 */
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

    // Add endpoint to get all recent activity

    // Add endpoint to display all user related activity

    // Get endpoint to post new activity

}
