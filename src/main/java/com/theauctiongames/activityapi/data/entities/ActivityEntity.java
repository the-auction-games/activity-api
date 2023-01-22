package com.theauctiongames.activityapi.data.entities;

import com.theauctiongames.activityapi.business.models.ActivityModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * A database entity object for handling activities.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ActivityEntity {

    /**
     * Construct an activity entity from an activity model.
     *
     * @param model the activity model
     * @return the activity model
     */
    public static ActivityEntity fromModel(ActivityModel model) {
        return new ActivityEntity(
                model.getId(),
                model.getUserId(),
                model.getActivityType(),
                model.getActivityDescription(),
                model.getUrlRedirect(),
                model.getTimestamp()
        );
    }

    /**
     * The activity's id.
     */
    private String id;

    /**
     * The user id involved in the activity.
     */
    private String userId;

    /**
     * The activity's type.
     */
    private String activityType;

    /**
     * The activity's description.
     */
    private String activityDescription;

    /**
     * The redirect url to the activity.
     */
    private String urlRedirect;

    /**
     * The activity's timestamp.
     */
    private long timestamp;
}
