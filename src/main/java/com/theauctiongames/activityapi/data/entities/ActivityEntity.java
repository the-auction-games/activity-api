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
                model.getType(),
                model.getDescription(),
                model.getUrlRedirect(),
                model.getCreationTimestamp()
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
    private long creationTimestamp;
}
