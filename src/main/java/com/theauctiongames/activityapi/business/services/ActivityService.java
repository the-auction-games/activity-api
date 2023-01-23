package com.theauctiongames.activityapi.business.services;


import com.theauctiongames.activityapi.business.models.ActivityModel;

import java.util.List;

/**
 * The service object for activity manipulation.
 */
public interface ActivityService {

    /**
     * Get all activities.
     *
     * @param limit the list size limit
     * @return a list of activities
     */
    List<ActivityModel> getActivity(int limit);

    /**
     * Get all activities related to a user id.
     *
     * @param userId the user id
     * @param limit  the list size limit
     * @return a list of activities
     */
    List<ActivityModel> getActivityByUserId(String userId, int limit);

    /**
     * Create an activity.
     *
     * @param activity the activity
     * @return true if the activity was created, false otherwise
     */
    boolean createActivity(ActivityModel activity);
}
