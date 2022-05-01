package com.crud.tasks.mapper;

import com.crud.tasks.domain.*;
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
public class TrelloMapperTest {

    @Mock
    TrelloMapper trelloMapper;

    @Test
    public void mapToBoardTest() {
        //GIVEN

        TrelloListDto listDto1 = new TrelloListDto("1","TrelloListDto 1",true);
        TrelloListDto listDto2 = new TrelloListDto("2","TrelloListDto 2",true);
        List<TrelloListDto> list1 = new ArrayList<>();
        list1.add(listDto1);
        List<TrelloListDto> list2 = new ArrayList<>();
        list2.add(listDto2);

        TrelloBoardDto boardDto1 = new TrelloBoardDto("1","TrelloBoardDto 1",list1);
        TrelloBoardDto boardDto2 = new TrelloBoardDto("2","TrelloBoardDto 2",list2);
        List<TrelloBoardDto> listTrelloBoard = new ArrayList<>();
        listTrelloBoard.add(boardDto1);
        listTrelloBoard.add(boardDto2);

        TrelloList list11 = new TrelloList("1","TrelloListDto 1",true);
        TrelloList list22 = new TrelloList("2","TrelloListDto 2",true);
        List<TrelloList> trelloList11 = new ArrayList<>();
        trelloList11.add(list11);
        List<TrelloList> trelloList22 = new ArrayList<>();
        trelloList22.add(list22);

        TrelloBoard board11 = new TrelloBoard("1","TrelloBoardDto 1",trelloList11);
        TrelloBoard board22 = new TrelloBoard("2","TrelloBoardDto 2",trelloList22);
        List<TrelloBoard> listTrelloBoards = new ArrayList<>();
        listTrelloBoards.add(board11);
        listTrelloBoards.add(board22);

        when(trelloMapper.mapToBoards(listTrelloBoard)).thenReturn(listTrelloBoards);

        //WHEN
        List<TrelloBoard> board1 = trelloMapper.mapToBoards(listTrelloBoard);

        //THEN
        assertEquals("TrelloBoardDto 1",board1.get(0).getName());
        assertEquals("1",board1.get(0).getId());

    }

    @Test
    public void mapToBoardsDtoTest() {
        //GIVEN
        TrelloList list1 = new TrelloList("1","TrelloList 1",true);
        TrelloList list2 = new TrelloList("2","TrelloList 2",true);
        List<TrelloList> normalList1 = new ArrayList<>();
        normalList1.add(list1);
        List<TrelloList> normalList2 = new ArrayList<>();
        normalList2.add(list2);

        TrelloBoard normalBoard1 = new TrelloBoard("1","TrelloBoard 1",normalList1);
        TrelloBoard normalBoard2 = new TrelloBoard("2","TrelloBoard 2",normalList2);
        List<TrelloBoard> listTrelloBoard = new ArrayList<>();
        listTrelloBoard.add(normalBoard1);
        listTrelloBoard.add(normalBoard2);

        TrelloListDto listDto1 = new TrelloListDto("1","TrelloList 1",true);
        TrelloListDto listDto2 = new TrelloListDto("2","TrelloList 2",true);
        List<TrelloListDto> list11 = new ArrayList<>();
        list11.add(listDto1);
        List<TrelloListDto> list22 = new ArrayList<>();
        list22.add(listDto2);

        TrelloBoardDto boardDto1 = new TrelloBoardDto("1","TrelloBoard 1",list11);
        TrelloBoardDto boardDto2 = new TrelloBoardDto("2","TrelloBoard 2",list22);
        List<TrelloBoardDto> listTrelloBoards = new ArrayList<>();
        listTrelloBoards.add(boardDto1);
        listTrelloBoards.add(boardDto2);

        when(trelloMapper.mapToBoardsDto(listTrelloBoard)).thenReturn(listTrelloBoards);

        //WHEN
        List<TrelloBoardDto> boardDto = trelloMapper.mapToBoardsDto(listTrelloBoard);

        //THEN
        assertEquals("TrelloBoard 2",boardDto.get(1).getName());

    }
    @Test
    public void mapToListTest() {
        //GIVEN
        TrelloListDto listDto1 = new TrelloListDto("1","TrelloListDto 1",true);
        TrelloListDto listDto2 = new TrelloListDto("2","TrelloListDto 2",true);
        List<TrelloListDto> listDto = new ArrayList<>();
        listDto.add(listDto1);
        listDto.add(listDto2);

        TrelloList list1 = new TrelloList("1","TrelloListDto 1",true);
        TrelloList list2 = new TrelloList("2","TrelloListDto 2",true);
        List<TrelloList> list = new ArrayList<>();
        list.add(list1);
        list.add(list2);

        when(trelloMapper.mapToList(listDto)).thenReturn(list);

        //WHEN
        List<TrelloList> result = trelloMapper.mapToList(listDto);

        //THEN
        assertEquals("1",result.get(0).getId());
        assertEquals("TrelloListDto 1",result.get(0).getName());
        assertEquals(true,result.get(0).isClosed());

    }
    @Test
    public void mapToListDtoTest() {
        //GIVEN
        TrelloList list1 = new TrelloList("1","TrelloListDto 1",true);
        TrelloList list2 = new TrelloList("2","TrelloListDto 2",true);
        List<TrelloList> list = new ArrayList<>();
        list.add(list1);
        list.add(list2);

        TrelloListDto listDto1 = new TrelloListDto("1","TrelloListDto 1",true);
        TrelloListDto listDto2 = new TrelloListDto("2","TrelloListDto 2",true);
        List<TrelloListDto> dtoList = new ArrayList<>();
        dtoList.add(listDto1);
        dtoList.add(listDto2);

        when(trelloMapper.mapToListDto(list)).thenReturn(dtoList);

        //WHEN
        List<TrelloListDto> listDto = trelloMapper.mapToListDto(list);

        //THEN
        assertEquals(true,listDto.get(1).isClosed());

    }
    @Test
    public void mapToCardDtoTest() {
        //GIVEN
        TrelloCard card = new TrelloCard("TrelloCard 1","Card 1","Pos 1","1");

        TrelloCardDto cardDto = new TrelloCardDto("TrelloCard 1","Card 1","Pos 1","1");

        when(trelloMapper.mapToCardDto(card)).thenReturn(cardDto);

        //WHEN
        TrelloCardDto result = trelloMapper.mapToCardDto(card);

        //THEN
        assertEquals("Card 1",result.getDescription());

    }
    @Test
    public void mapToCardTest() {
        //GIVEN
        TrelloCardDto cardDto = new TrelloCardDto("TrelloCardDto 1","CardDto 1","PosDto 1","1");

        TrelloCard card = new TrelloCard("TrelloCardDto 1","CardDto 1","PosDto 1","1");

        when(trelloMapper.mapToCard(cardDto)).thenReturn(card);

        //WHEN
        TrelloCard result = trelloMapper.mapToCard(cardDto);

        //THEN
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("TrelloCardDto 1");
        assertThat(result.getDescription()).isEqualTo("CardDto 1");
        assertThat(result.getPos()).isEqualTo("PosDto 1");
        assertThat(result.getListId()).isEqualTo("1");

    }

}
