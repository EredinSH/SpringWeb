package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import com.google.gson.Gson;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringJUnitWebConfig
@WebMvcTest(TaskController.class)
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DbService dbService;

    @MockBean
    private TaskMapper taskMapper;


    @Test
    public void shouldGetTasksTest() throws Exception {

        //GIVEN
        List<Task> taskList = List.of(new Task(1L,"Task1","Task1 content"));

        List<TaskDto> taskDtoList = List.of(new TaskDto(1L,"Task1","Task1 content"));

        when(dbService.getAllTasks()).thenReturn(taskList);
        when(taskMapper.mapToTaskDtoList(taskList)).thenReturn(taskDtoList);

        //WHEN & THEN
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title", is("Task1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].content", is("Task1 content")));

    }

    @Test
    public void shouldGetTaskTest() throws Exception {

        //GIVEN
        Task task1 = new Task(1L,"Task1","Task1 content");
        TaskDto taskDto1 = new TaskDto(1L,"Task1","Task1 content");

        when(dbService.getTask(1L)).thenReturn(task1);
        when(taskMapper.mapToTaskDto(task1)).thenReturn(taskDto1);

        //WHEN & THEN
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/tasks/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", is("Task1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", is("Task1 content")));

    }

    @Test
    public void shouldDeleteTaskTest() throws Exception {

        //GIVEN
        Task task1 = new Task(1L,"Task1","Task1 content");

        //WHEN & THEN
        mockMvc
                .perform(MockMvcRequestBuilders
                        .delete("/v1/tasks/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    public void shouldUpdateTaskTest() throws Exception {

        //GIVEN
        Task task = new Task(1L,"Task1","Task1 content");
        TaskDto taskDto1 = new TaskDto(1L,"Task1","Task1 content");
        TaskDto taskUpdatedDto1 = new TaskDto(2L,"Task1 updated","Task1 content upaded");

        when(taskMapper.mapToTask(taskDto1)).thenReturn(task);
        when(dbService.saveTask(task)).thenReturn(new Task(2L,"Task1 updated","Task1 content upaded"));
        when(taskMapper.mapToTaskDto(any())).thenReturn(taskUpdatedDto1);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(taskUpdatedDto1);

        //WHEN & THEN
        mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", Matchers.is("Task1 updated")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.is("Task1 content upaded")))
                .andExpect(status().isOk());

    }

    @Test
    public void createTaskTest() throws Exception {

        //GIVEN
        Task task1 = new Task(1L,"Task1","Task1 content");
        TaskDto taskDto1 = new TaskDto(1L,"Task1","Task1 content");

        when(taskMapper.mapToTask(taskDto1)).thenReturn(task1);
        when(dbService.saveTask(task1)).thenReturn(new Task(1L,"Task1","Task1 content"));

        Gson gson = new Gson();
        String jsonContent = gson.toJson(taskDto1);

        //WHEN & THEN
        mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(status().isOk());

    }

}
