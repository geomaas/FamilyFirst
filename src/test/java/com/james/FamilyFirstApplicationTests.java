package com.james;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.james.entities.ProTip;
import com.james.entities.Task;
import com.james.entities.User;
import com.james.services.MedicationRepository;
import com.james.services.ProTipsRepository;
import com.james.services.TaskRepository;
import com.james.services.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = FamilyFirstApplication.class)
@WebAppConfiguration
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FamilyFirstApplicationTests {

	@Autowired
	WebApplicationContext wac;

    @Autowired
    UserRepository users;

    @Autowired
    TaskRepository tasks;

    @Autowired
    ProTipsRepository tips;

    @Autowired
    MedicationRepository medications;

    MockMvc mockMvc;

    @Before
	public void before() {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}

	@Test
	public void aTestLogin() throws Exception {
        int oldCount = (int) users.count();
        User user = new User("newtestname", "newtestPassword");
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(user);
        mockMvc.perform(
                MockMvcRequestBuilders.post("/login")
                        .content(json)
                        .contentType("application/json")
        );
        int newCount = (int) users.count();

        Assert.assertTrue(newCount == oldCount + 1);
    }


	@Test
	public void bTasksGetRouteTest() throws Exception {
		User testuser = new User("bob", "pass");
		users.save(testuser);
		Task task = new Task(testuser, "thing to do", null, null, LocalDateTime.now());
		tasks.save(task);

		ResultActions ra = mockMvc.perform(
				MockMvcRequestBuilders.get("/tasks")
		);
		MvcResult result = ra.andReturn();
		MockHttpServletResponse response = result.getResponse();
		String json = response.getContentAsString();

        Assert.assertTrue(json.contains("thing to do"));
	}

    @Test
    public void caddTaskTest() throws Exception {
        int oldCount = (int) tasks.count();
        User taskuser = new User("newtestname", "newtestPassword");
        String body = new String ("I like peanuts");
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(body);
        mockMvc.perform(
                MockMvcRequestBuilders.post("/addTask")
                        .content(body)
                        .contentType("application/json")
        );
        int newCount = (int) tasks.count();

        Assert.assertTrue(newCount == oldCount + 1);
        Assert.assertTrue(tasks.findFirstByTaskText("I like peanuts") != null);
    }

	@Test
	public void daddCommentTest() throws Exception {
		User testuser = users.findFirstByUserName("bob");
		Task task = new Task(testuser, "thing to do", null, null, LocalDateTime.now());
		tasks.save(task);

		String comment = "this is the comment";

		mockMvc.perform(
				MockMvcRequestBuilders.post("/comment" + task.getTaskId())
							.content(comment)
							.contentType("application/json")
			);

        Task commentedTask = tasks.findOne(task.getTaskId());

        Assert.assertTrue(commentedTask.getCommentText().equals(comment));
    }

    @Test
    public void ecompleteTest() throws Exception {
        User testuser = users.findFirstByUserName("bob");
        Task task = tasks.findFirstByTaskText("thing to do");

        mockMvc.perform(
                MockMvcRequestBuilders.post("/complete" + task.getTaskId())
                    .sessionAttr("userName", "bob")
        );

        Assert.assertTrue(tasks.findOne(task.getTaskId()).getCompletedByUser() != null);
    }

    @Test
    public void fProtipsTest() throws Exception {
        ResultActions ra = mockMvc.perform(
                MockMvcRequestBuilders.get("/Protip")
        );
        MvcResult result = ra.andReturn();
        MockHttpServletResponse response = result.getResponse();
        String json = response.getContentAsString();

        ObjectMapper om = new ObjectMapper();
        ProTip pt = om.readValue(json, ProTip.class);

        Assert.assertTrue(pt.getTip().equals(tips.findOne(pt.getTipsId()).getTip()));
    }
}
