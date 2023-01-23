package com.theauctiongames.activityapi.business.models;

import com.theauctiongames.activityapi.data.entities.ActivityEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * A model object for handling activities.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ActivityModel {

    /**
     * Construct an activity model from an activity entity.
     *
     * @param entity the activity entity
     * @return the activity model
     */
    public static ActivityModel fromEntity(ActivityEntity entity) {
        return new ActivityModel(
                entity.getId(),
                entity.getUserId(),
                entity.getType(),
                entity.getDescription(),
                entity.getUrlRedirect(),
                entity.getTimestamp()
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
    private String type;

    /**
     * The activity's description.
     */
    private String description;

    /**
     * The redirect url to the activity.
     */
    private String urlRedirect;

    /**
     * The activity's timestamp.
     */
    private long timestamp;
}
