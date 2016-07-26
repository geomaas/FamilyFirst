package com.james;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.james.controllers.FamilyController;
import com.james.entities.Task;
import com.james.entities.User;
import com.james.services.MedicationRepository;
import com.james.services.ProTipsRepository;
import com.james.services.TaskRepository;
import com.james.services.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = FamilyFirstApplication.class)
@WebAppConfiguration
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
	public void TestLogin() throws Exception {
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
	public void TasksGetRouteTest() throws Exception {
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
    public void addTaskTest() throws Exception {
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
        Assert.assertTrue(tasks.findByTaskText("I like peanuts") != null);
    }

	@Test
	public void addCommentTest() throws Exception {
		User testuser = new User("bob", "pass");
		users.save(testuser);
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
    public void completeTest() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userName", "bob");
        User testuser = new User("bob", "pass");
        users.save(testuser);
        Task task = new Task(testuser, "thing to do", null, null, LocalDateTime.now());
        tasks.save(task);

        ResultActions ra = mockMvc.perform(
                MockMvcRequestBuilders.post("/complete" + task.getTaskId())
        );

        MvcResult result= ra.andReturn();
        MockHttpServletResponse response = result.getResponse();
        String json = response.getContentAsString();

        System.out.println(json);

    }
}
