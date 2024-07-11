package com.springboot.board.mapper;

import com.springboot.board.dto.BoardPatchDto;
import com.springboot.board.dto.BoardPostDto;
import com.springboot.board.dto.BoardResponseDto;
import com.springboot.board.entity.Board;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BoardMapper {
    @Mapping(source = "memberId",target = "member.memberId")
    Board boardPostDtoToBoard(BoardPostDto boardPostDto);
    @Mapping(source = "member.name",target = "username")
    @Mapping(source = "reply.comment",target = "comment")
    BoardResponseDto boardToboardResponseDto(Board board);
    Board boardPatchDtoToBoard(BoardPatchDto boardPatchDto);
    List<BoardResponseDto> boardsToBoardResponseDtos(List<Board> boards);
}
