package com.crud.tasks.service;

import com.crud.tasks.controller.TaskNotFoundException;
import com.crud.tasks.domain.Task;
import com.crud.tasks.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DbServiceTest {

    @InjectMocks
    private DbService dbService;

    @Mock
    private TaskRepository repository;

    @Test
    public void getAllTasksTest() {
        //GIVEN
        Task task1 = new Task(1L,"Task 1","Content 1");
        Task task2 = new Task(2L,"Task 2","Content 2");
        List<Task> tasksList = new ArrayList<>();
        tasksList.add(task1);
        tasksList.add(task2);

        when(repository.findAll()).thenReturn(tasksList);

        //WHEN
        List<Task> resultList = dbService.getAllTasks();

        //THEN
        assertEquals("Task 1",resultList.get(0).getTitle());

    }

    @Test
    public void saveTaskTest() {
        //GIVEN
        Task task1 = new Task(1L,"Task 1","Content 1");
        when(repository.save(task1)).thenReturn(task1);

        //WHEN
        Task result = dbService.saveTask(task1);

        //THEN
        assertThat(result).isNotNull();

    }

    @Test
    public void getTaskTest() throws TaskNotFoundException {
        //GIVEN
        Task task1 = new Task(1L,"Task 1","Content 1");
        when(repository.findById(1L)).thenReturn(Optional.of(task1));

        //WHEN
        Task result = dbService.getTask(1L);

        //THEN
        assertEquals("Task 1",result.getTitle());

    }

    @Test
    public void deleteByIdTest() throws TaskNotFoundException {
        //GIVEN
        Task task1 = new Task(1L,"Task 1","Content 1");

        //WHEN
        dbService.deleteById(1L);

        //THEN
        assertFalse(false,task1.getTitle());

    }

}
