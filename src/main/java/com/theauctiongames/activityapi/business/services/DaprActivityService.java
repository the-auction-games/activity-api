package com.theauctiongames.activityapi.business.services;


import com.theauctiongames.activityapi.business.models.ActivityModel;
import com.theauctiongames.activityapi.data.daos.ActivityDao;
import com.theauctiongames.activityapi.data.entities.ActivityEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * The activity service implementation utilizing Dapr's sidecar.
 */
@Service
public class DaprActivityService implements ActivityService {

    /**
     * The injected activity DAO.
     */
    private final ActivityDao dao;

    /**
     * Construct the dapr activity service.
     *
     * @param dao the activity DAO
     */
    public DaprActivityService(ActivityDao dao) {
        this.dao = dao;
    }

    /**
     * Get all activities.
     *
     * @param limit the list size limit
     * @return a list of activities
     */
    @Override
    public List<ActivityModel> getActivity(int limit) {
        return this.dao.getActivity(limit).stream()
                .map(ActivityModel::fromEntity)
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
    public List<ActivityModel> getActivityByUserId(String userId, int limit) {
        return this.dao.getActivityByUserId(userId, limit).stream()
                .map(ActivityModel::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * Create an activity.
     *
     * @param activity the activity
     * @return true if the activity was created, false otherwise
     */
    @Override
    public boolean createActivity(ActivityModel activity) {
        return this.dao.createActivity(ActivityEntity.fromModel(activity));
    }
}
