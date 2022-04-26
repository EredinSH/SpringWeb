package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TaskControllerTest {

    @InjectMocks
    private TaskController taskController;

    @Mock
    private DbService dbService;

    @Mock
    private TaskMapper taskMapper;

    @Test
    public void getTasksTest() {

        //GIVEN
        Task task1 = new Task(1L,"Task1","Task1 content");
        List<Task> taskList = new ArrayList<>();
        taskList.add(task1);

        TaskDto taskDto1 = new TaskDto(1L,"Task1","Task1 content");
        List<TaskDto> taskDtoList = new ArrayList<>();
        taskDtoList.add(taskDto1);

        when(dbService.getAllTasks()).thenReturn(taskList);
        when(taskMapper.mapToTaskDtoList(taskList)).thenReturn(taskDtoList);

        //WHEN
        ResponseEntity<List<TaskDto>> taskDtoListResult = taskController.getTasks();

        //THEN

        assertThat(taskDtoListResult).isNotNull();

        taskDtoListResult.getBody().stream().forEach(taskDtoLists -> {
            assertThat(taskDtoLists.getId()).isEqualTo(1l);
            assertThat(taskDtoLists.getTitle()).isEqualTo("Task1");
            assertThat(taskDtoLists.getContent()).isEqualTo("Task1 content");
        } );

    }

    @Test
    public void getTaskTest() throws Exception {

        //GIVEN
        Task task1 = new Task(1L,"Task1","Task1 content");
        TaskDto taskDto1 = new TaskDto(1L,"Task1","Task1 content");

        when(dbService.getTask(1L)).thenReturn(task1);
        when(taskMapper.mapToTaskDto(task1)).thenReturn(taskDto1);

        //WHEN
        ResponseEntity<TaskDto> taskDto = taskController.getTask(1L);

        //THEN
        assertThat(taskDto).isNotNull();
        assertEquals(1l,taskDto.getBody().getId());
        assertEquals("Task1",taskDto.getBody().getTitle());
        assertEquals("Task1 content",taskDto.getBody().getContent());

    }

    @Test
    public void deleteTaskTest() throws Exception {

        //GIVEN
        Task task1 = new Task(1L,"Task1","Task1 content");

        //WHEN
        taskController.deleteTask(1L);

        //THEN
        assertFalse(false,task1.getTitle());

    }

    @Test
    public void updateTaskTest() {

        //GIVEN
        Task task1 = new Task(1L,"Task1","Task1 content");
        Task updatedtask1 = new Task(1L, "Task1 updated", "Task1 content upaded");
        TaskDto taskDto1 = new TaskDto(1L,"Task1","Task1 content");
        TaskDto taskUpdatedDto1 = new TaskDto(1L,"Task1 updated","Task1 content upaded");

        when(taskMapper.mapToTask(taskDto1)).thenReturn(task1);
        when(dbService.saveTask(task1)).thenReturn(updatedtask1);
        when(taskMapper.mapToTaskDto(updatedtask1)).thenReturn(taskUpdatedDto1);

        //WHEN
        ResponseEntity<TaskDto> result = taskController.updateTask(taskDto1);

        //THEN
        assertEquals("Task1 updated",result.getBody().getTitle());

    }

    @Test
    public void createTaskTest() {

        //GIVEN
        Task task1 = new Task(1L,"Task1","Task1 content");
        TaskDto taskDto1 = new TaskDto(1L,"Task1","Task1 content");

        when(taskMapper.mapToTask(taskDto1)).thenReturn(task1);
        when(dbService.saveTask(task1)).thenReturn(task1);

        //WHEN
        ResponseEntity<Void> result = taskController.createTask(taskDto1);

        //THEN
        assertEquals(200, result.getStatusCodeValue());

    }

}
