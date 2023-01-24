package com.theauctiongames.activityapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.theauctiongames.activityapi.business.models.ActivityModel;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.UUID;

/**
 * The testing clas for the activity api.
 */
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ActivityApiApplicationTests {

    /**
     * The base uri for the following unit tests.
     */
    private final String baseUri;

    /**
     * The mock mvc.
     */
    private final MockMvc mvc;

    /**
     * The json object mapper.
     */
    private final ObjectMapper mapper;

    /**
     * Construct the activity api application tests.
     */
    public ActivityApiApplicationTests(WebApplicationContext context) {
        // Set up the base uri
        this.baseUri = "/api/v1/activity";

        // Set up the mock mvc
        this.mvc = MockMvcBuilders.webAppContextSetup(context).build();

        // Set up the json object mapper
        this.mapper = new ObjectMapper();
    }

    /**
     * Create activities in the database before the tests.
     *
     * @throws Exception if the request fails
     */
    @BeforeAll
    public void createActivity() throws Exception {
        // Create 20 activities
        for (int i = 0; i < 20; i++) {
            // Create the activity model
            ActivityModel activity = new ActivityModel(UUID.randomUUID().toString(),
                    i < 15 ? "tester_1" : "tester_2",
                    "Test Type",
                    "Test Description",
                    "/test/path",
                    System.currentTimeMillis()
            );

            // Post the auction
            MvcResult result = this.mvc.perform(MockMvcRequestBuilders.post(this.baseUri)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(this.mapper.writeValueAsString(activity)))
                    .andReturn();

            // Assert that the status is 201
            assert result.getResponse().getStatus() == 201;
        }
    }

    /**
     * Test the get all activities endpoint.
     *
     * @throws Exception if the request fails
     */
    @Test
    public void getActivity() throws Exception {
        // Get the activity
        MvcResult result = this.mvc.perform(MockMvcRequestBuilders.get(this.baseUri))
                .andReturn();

        // Assert that the status is 200
        assert result.getResponse().getStatus() == 200;

        // Map the result to an array of activity models
        ActivityModel[] activities = this.mapper.readValue(result.getResponse().getContentAsString(), ActivityModel[].class);

        // Assert the length does not exceed the default 10
        assert activities.length <= 10;
    }

    /**
     * Test the get all activities endpoint with a limit.
     *
     * @throws Exception if the request fails
     */
    @Test
    public void getActivityWithLimit() throws Exception {
        // Get the activity
        MvcResult result = this.mvc.perform(MockMvcRequestBuilders.get(this.baseUri)
                        .queryParam("limit", "5"))
                .andReturn();

        // Assert that the status is 200
        assert result.getResponse().getStatus() == 200;

        // Map the result to an array of activity models
        ActivityModel[] activities = this.mapper.readValue(result.getResponse().getContentAsString(), ActivityModel[].class);

        // Assert the length does not exceed the default 10
        assert activities.length <= 5;
    }

    /**
     * Test the get all user activity endpoint.
     *
     * @throws Exception if the request fails
     */
    @Test
    public void getUserActivity() throws Exception {
        // Get the activity
        MvcResult result = this.mvc.perform(MockMvcRequestBuilders.get(this.baseUri + "/user/tester_1"))
                .andReturn();

        // Assert that the status is 200
        assert result.getResponse().getStatus() == 200;

        // Map the result to an array of activity models
        ActivityModel[] activities = this.mapper.readValue(result.getResponse().getContentAsString(), ActivityModel[].class);

        // Assert the length does not exceed the default 10
        assert activities.length <= 10;

        // Assert that all the activity is from the user
        for (ActivityModel activity : activities) {
            assert activity.getUserId().equals("tester_1");
        }
    }

    /**
     * Test the get all user activity endpoint with a limit.
     *
     * @throws Exception if the request fails
     */
    @Test
    public void getUserActivityWithLimit() throws Exception {
        // Get the activity
        MvcResult result = this.mvc.perform(MockMvcRequestBuilders.get(this.baseUri + "/user/tester_1")
                        .queryParam("limit", "5"))
                .andReturn();

        // Assert that the status is 200
        assert result.getResponse().getStatus() == 200;

        // Map the result to an array of activity models
        ActivityModel[] activities = this.mapper.readValue(result.getResponse().getContentAsString(), ActivityModel[].class);

        // Assert the length does not exceed the default 10
        assert activities.length <= 5;

        // Assert that all the activity is from the user
        for (ActivityModel activity : activities) {
            assert activity.getUserId().equals("tester_1");
        }
    }
}
