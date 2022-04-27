package com.crud.tasks.mapper;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class TaskMapperTest {

    @Autowired
    TaskMapper taskMapper;

    @Test
    public void mapToTaskTest() {

        //GIVEN
        TaskDto taskDto = new TaskDto(1L, "TaskDto 1","Content 1");

        //WHEN
        Task task = taskMapper.mapToTask(taskDto);

        //THEN
        assertThat(task).isNotNull();
        assertEquals(1l,task.getId());
        assertEquals("TaskDto 1",task.getTitle());
        assertEquals("Content 1",task.getContent());

    }

    @Test
    public void mapToTaskDtoTest() {

        //GIVEN
        Task task = new Task(1L, "Task 1","Content 1");

        //WHEN
        TaskDto taskDto = taskMapper.mapToTaskDto(task);

        //THEN
        assertEquals("Task 1",taskDto.getTitle());

    }

    @Test
    public void mapToTaskDtoListTest() {

        //GIVEN
        Task task1 = new Task(1L, "Task 1","Content 1");
        Task task2 = new Task(2L, "Task 2","Content 2");
        List<Task> tasksList = new ArrayList<>();
        tasksList.add(task1);
        tasksList.add(task2);

        //WHEN
        List<TaskDto> taskDtoList = taskMapper.mapToTaskDtoList(tasksList);

        //THEN
        assertEquals(2,taskDtoList.size());
        assertEquals("Content 2",taskDtoList.get(1).getContent());

    }

}
