package com.theauctiongames.activityapi.data.daos;

import com.theauctiongames.activityapi.data.entities.ActivityEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * The implementation of the activity DAO utilizing Dapr's sidecar.
 */
@Service
public class DaprActivityDao implements ActivityDao {

    /**
     * Get all activities.
     *
     * @param limit the list size limit
     * @return a list of activities
     */
    @Override
    public List<ActivityEntity> getActivity(int limit) {

        // Return list of 10 test activities
        return List.of(
                        new ActivityEntity("1", "-", "Create Account", "- created an account", "user -", System.currentTimeMillis()),
                        new ActivityEntity("2", "-", "Create Account", "- created an account", "user -", System.currentTimeMillis()),
                        new ActivityEntity("3", "-", "Create Account", "- created an account", "user -", System.currentTimeMillis())
                ).stream()
                .limit(limit)
                .collect(Collectors.toList());
    }

    /**
     * Get all activities related to a user id.
     *
     * @param userId the user id
     * @param limit  the list size limit
     * @return a list of activities
     */
    @Override
    public List<ActivityEntity> getActivityByUserId(String userId, int limit) {
        return List.of();
    }

    /**
     * Create an activity.
     *
     * @param activity the activity
     * @return true if the activity was created, false otherwise
     */
    @Override
    public boolean createActivity(ActivityEntity activity) {
        return false;
    }
}
