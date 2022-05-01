package com.crud.tasks.mapper;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TaskMapperTest {

    @Mock
    TaskMapper taskMapper;

    @Test
    public void mapToTaskTest() {

        //GIVEN
        TaskDto taskDto = new TaskDto(1L, "TaskDto 1","Content 1");
        when(taskMapper.mapToTask(taskDto)).thenReturn(new Task(1L, "TaskDto 1","Content 1"));

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
        when(taskMapper.mapToTaskDto(task)).thenReturn(new TaskDto(1L, "Task 1","Content 1"));

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

        List<TaskDto> taskDtoList = List.of(new TaskDto(1L,"Task1","Task1 content"),(new TaskDto(2L, "Task 2","Content 2")));

        when(taskMapper.mapToTaskDtoList(tasksList)).thenReturn(taskDtoList);

        //WHEN
        List<TaskDto> result = taskMapper.mapToTaskDtoList(tasksList);

        //THEN
        assertEquals(2,result.size());
        assertEquals("Content 2",result.get(1).getContent());

    }

}
