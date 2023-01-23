package com.theauctiongames.activityapi.data.daos;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.theauctiongames.activityapi.business.models.ActivityModel;
import com.theauctiongames.activityapi.data.entities.ActivityEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * The implementation of the activity DAO utilizing Dapr's sidecar.
 */
@Service
public class DaprActivityDao implements ActivityDao {

    /**
     * The state store name.
     */
    private final String stateStoreName;

    /**
     * The state store URL.
     */
    private final String stateUrl;

    /**
     * The state store's query URL.
     */
    private final String queryUrl;

    /**
     * Construct the dapr activity DAO.
     */
    public DaprActivityDao() {
        this.stateStoreName = "postgres";
        this.stateUrl = "http://localhost:3502/v1.0/state/" + this.stateStoreName;
        this.queryUrl = "http://localhost:3502/v1.0-alpha1/state/" + this.stateStoreName + "/query";
    }

    /**
     * A private inner class to map the response to from the get all query.
     */
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    private static class DaprResponse {
        private ResponseEntry[] results;
    }

    /**
     * A private inner class to map the response entries to from the get all query.
     */
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    private static class ResponseEntry {
        private ActivityEntity data;
    }

    /**
     * Get all activities.
     *
     * @param limit the list size limit
     * @return a list of activities
     */
    @Override
    public List<ActivityEntity> getActivity(int limit) {
        try {
            // Create the template
            RestTemplate template = new RestTemplate();

            // Set request header
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            // Set request body
            JSONObject json = new JSONObject();
            json.put("filters", new JSONObject());

            // Create the request
            HttpEntity<String> request = new HttpEntity<>(json.toString(), headers);

            // Send request
            ResponseEntity<DaprResponse> daprResponse = template.postForEntity(this.queryUrl, request, DaprResponse.class);

            // Parse the response
            List<ActivityEntity> activities = Arrays.stream(daprResponse.getBody().results)
                    .map(ResponseEntry::getData)
                    .sorted(Comparator.comparing(ActivityEntity::getTimestamp).reversed())
                    .limit(limit)
                    .collect(Collectors.toList());

            // Return list of activities
            return activities;
        } catch (Exception ignored) {
        }

        // Something went wrong, return empty list
        return List.of();
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
        try {
            // Create the template
            RestTemplate template = new RestTemplate();

            // Set request header
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            // Set request body
            JSONObject json = new JSONObject();
            json.put("filters", new JSONObject());

            // Create the request
            HttpEntity<String> request = new HttpEntity<>(json.toString(), headers);

            // Send request
            ResponseEntity<DaprResponse> daprResponse = template.postForEntity(this.queryUrl, request, DaprResponse.class);

            // Parse the response
            List<ActivityEntity> activities = Arrays.stream(daprResponse.getBody().results)
                    .map(ResponseEntry::getData)
                    .filter(activity -> activity.getUserId().equals(userId))
                    .sorted(Comparator.comparing(ActivityEntity::getTimestamp).reversed())
                    .limit(limit)
                    .collect(Collectors.toList());

            // Return list of activities
            return activities;
        } catch (Exception ignored) {
        }

        // Something went wrong, return empty list
        return List.of();
    }

    /**
     * Get an activity by its id.
     *
     * @param id the activity id
     * @return the activity
     */
    @Override
    public Optional<ActivityEntity> getActivityById(String id) {
        try {
            // Create the template
            RestTemplate template = new RestTemplate();

            // Set request header
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            // Send request
            String url = this.stateUrl + "/" + id;
            ResponseEntity<ActivityEntity> activity = template.getForEntity(url, ActivityEntity.class);

            // Return activity
            return Optional.ofNullable(activity.getBody());
        } catch (Exception ignored) {
        }

        // Something went wrong, return empty
        return Optional.empty();
    }

    /**
     * Create an activity.
     *
     * @param activity the activity
     * @return true if the activity was created, false otherwise
     */
    @Override
    public boolean createActivity(ActivityEntity activity) {
        // Confirm the auction is unique
        if (getActivityById(activity.getId()).isPresent()) {
            return false;
        }

        try {
            // Create the template
            RestTemplate template = new RestTemplate();

            // Set request header
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            // create array of JSONObject
            JSONObject json = new JSONObject();
            json.put("key", activity.getId());
            json.put("value", new JSONObject(new ObjectMapper().writeValueAsString(activity)));

            // Create the request
            HttpEntity<String> request = new HttpEntity<>("[" + json + "]", headers);

            // Send request
            ResponseEntity<String> response = template.postForEntity(this.stateUrl, request, String.class);

            // Throw error if the status code is not 200
            if (!response.getStatusCode().is2xxSuccessful()) {
                throw new RuntimeException("Error storing activity: " + request.getBody());
            }

            // Return true
            return true;
        } catch (Exception exception) {
            // Print the error
            exception.printStackTrace();
        }

        // Something went wrong, return false
        return false;
    }
}
