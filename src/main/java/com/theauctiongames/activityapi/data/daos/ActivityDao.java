package com.theauctiongames.activityapi.data.daos;

import com.theauctiongames.activityapi.business.models.ActivityModel;
import com.theauctiongames.activityapi.data.entities.ActivityEntity;

import java.util.List;
import java.util.Optional;

/**
 * The DAO object for activity manipulation.
 */
public interface ActivityDao {

    /**
     * Get all activities.
     *
     * @param limit the list size limit
     * @return a list of activities
     */
    List<ActivityEntity> getActivity(int limit);

    /**
     * Get all activities related to a user id.
     *
     * @param userId the user id
     * @param limit  the list size limit
     * @return a list of activities
     */
    List<ActivityEntity> getActivityByUserId(String userId, int limit);

    /**
     * Get an activity by id.
     *
     * @param id the id
     * @return the optional activity
     */
    Optional<ActivityEntity> getActivityById(String id);

    /**
     * Create an activity.
     *
     * @param activity the activity
     * @return true if the activity was created, false otherwise
     */
    boolean createActivity(ActivityEntity activity);
}
