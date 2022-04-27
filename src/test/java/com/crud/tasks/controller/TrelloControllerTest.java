package com.crud.tasks.controller;

import com.crud.tasks.domain.CreatedTrelloCardDto;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloCardDto;
import com.crud.tasks.domain.TrelloListDto;
import com.crud.tasks.trello.facade.TrelloFacade;
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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TrelloControllerTest {

    @InjectMocks
    private TrelloController trelloController;

    @Mock
    private TrelloFacade trelloFacade;

    @Test
    public void getTrelloBoardsTest() {
        //GIVEN
        TrelloListDto listDto1 = new TrelloListDto("1","ListDto 1",true);
        TrelloListDto listDto2 = new TrelloListDto("2","ListDto 2",true);
        List<TrelloListDto> trelloListDtoList = new ArrayList<>();
        trelloListDtoList.add(listDto1);
        trelloListDtoList.add(listDto2);

        TrelloBoardDto trelloBoardDto = new TrelloBoardDto("1","TrelloBoardDto 1",trelloListDtoList);
        List<TrelloBoardDto> trelloBoardDtoList = new ArrayList<>();
        trelloBoardDtoList.add(trelloBoardDto);

        when(trelloFacade.fetchTrelloBoards()).thenReturn(trelloBoardDtoList);

        //WHEN
        ResponseEntity<List<TrelloBoardDto>> result = trelloController.getTrelloBoards();

        //THEN
        assertThat((result)).isNotNull();
        result.getBody().stream().forEach(trelloBoardDtoLists -> {
            assertThat(trelloBoardDtoLists.getId()).isEqualTo("1");
            assertThat(trelloBoardDtoLists.getName()).isEqualTo("TrelloBoardDto 1");
            assertThat(trelloBoardDtoLists.getLists()).isEqualTo(trelloListDtoList);
        });

    }

    @Test
    public void createTrelloCardTest() {
        //GIVEN
        TrelloCardDto cardDto = new TrelloCardDto("TrelloCardDto 1","description","pos","listId");
        CreatedTrelloCardDto createdTrelloCardDto = new CreatedTrelloCardDto("1","CreatedTrelloCardDto 1","url.dto1");
        when(trelloFacade.createCard(cardDto)).thenReturn(createdTrelloCardDto);

        //WHEN
        ResponseEntity<CreatedTrelloCardDto> result = trelloController.createTrelloCard(cardDto);

        //THEN
        assertThat(result).isNotNull();
        assertEquals("CreatedTrelloCardDto 1",result.getBody().getName());

    }
}
