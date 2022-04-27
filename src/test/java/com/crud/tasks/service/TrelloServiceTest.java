package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.*;
import com.crud.tasks.trello.client.TrelloClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static java.util.Optional.ofNullable;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TrelloServiceTest {

    @InjectMocks
    private TrelloService trelloService;

    @Mock
    private TrelloClient trelloClient;

    @Mock
    private SimpleEmailService simpleEmailService;

    @Mock
    private AdminConfig adminConfig;

    @Test
    public void fetchTrelloBoardsTest() {
        //GIVEN
        TrelloListDto trelloListDto = new TrelloListDto("1","TrelloListDto 1",true);
        List<TrelloListDto> listDto = new ArrayList<>();
        listDto.add(trelloListDto);
        TrelloBoardDto trelloBoardDto = new TrelloBoardDto("1","TrelloBoardDto 1",listDto);
        List<TrelloBoardDto> boardList = new ArrayList<>();
        boardList.add(trelloBoardDto);

        when(trelloClient.getTrelloBoards()).thenReturn(boardList);

        //WHEN
        List<TrelloBoardDto> result = trelloService.fetchTrelloBoards();

        //THEN
        assertThat(result).isNotNull();
        result.stream().forEach(boardDtoList -> {
            assertThat(boardDtoList.getId()).isEqualTo("1");
            assertThat(boardDtoList.getName()).isEqualTo("TrelloBoardDto 1");
            assertThat(boardDtoList.getLists()).isEqualTo(listDto);
        });

    }

    @Test
    public void createTrelloCardTest() {
        //GIVEN
        TrelloCardDto cardDto = new TrelloCardDto("TrelloCardDto 1","description","pos","listId");
        CreatedTrelloCardDto createdCard = new CreatedTrelloCardDto("1","CreatedTrelloCard 1","url.cardDto");

        Mail mail = Mail.builder()
                .mailTo("test@test.com")
                .subject("Test")
                .message("Test Message")
                .toCc("Test CC")
                .build();

        when(trelloClient.createNewCard(cardDto)).thenReturn(createdCard);
        simpleEmailService.send(mail);

        //WHEN
        CreatedTrelloCardDto result = trelloService.createTrelloCard(cardDto);

        //THEN
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("CreatedTrelloCard 1");

    }

}
