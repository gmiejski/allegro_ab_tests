package org.miejski.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.miejski.AllegroAbTestingApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.ConfigFileApplicationContextInitializer;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static com.jayway.restassured.RestAssured.given;
import static org.fest.assertions.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AllegroAbTestingApplication.class, initializers = ConfigFileApplicationContextInitializer.class)
@WebAppConfiguration
@IntegrationTest
public class GroupsControllerIntegrationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    private static final String GROUP_URL = "/rest/group";
    private static final String ID_PARAM = "id";
    private static final String USER_ID = "userId";
    private static final String GROUP_NAME_FIELD = "name";

    @Before
    public void setup() {
        mockMvc = webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    public void shouldBeAbleToRetrieveGroupForUser() throws Exception {
        mockMvc.perform(get(GROUP_URL).param(ID_PARAM, USER_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").exists());
    }

    @Test
    public void shouldAlwaysRetrieveSameGroupForSpecificUser() throws Exception {
        String newGroupForUser = retrieveGroupForUser(USER_ID);
        String alreadyAssignedGroup = retrieveGroupForUser(USER_ID);

        assertThat(newGroupForUser).isEqualTo(alreadyAssignedGroup);
    }

    private String retrieveGroupForUser(String userId) {
        return given().param(ID_PARAM, userId)
                .when().get(GROUP_URL)
                .then().extract().body().jsonPath().get(GROUP_NAME_FIELD);
    }
}