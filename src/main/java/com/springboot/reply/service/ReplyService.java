package com.springboot.reply.service;

import com.springboot.board.entity.Board;
import com.springboot.board.repository.BoardRepository;
import com.springboot.board.service.BoardService;
import com.springboot.exception.BusinessLogicException;
import com.springboot.exception.ExceptionCode;
import com.springboot.reply.entity.Reply;
import com.springboot.reply.repository.ReplyRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ReplyService {
    private final ReplyRepository replyRepository;
    private final BoardRepository boardRepository;
//    private final BoardService boardService;

    public ReplyService(ReplyRepository replyRepository, BoardRepository boardRepository) {
        this.replyRepository = replyRepository;
        this.boardRepository = boardRepository;
    }
    public Reply createReply(Reply reply){
        verifyExistReply(reply.getBoard().getBoardId());
//        updateQuestionStatus(reply);
        Optional<Board> findBoard = boardRepository.findById(reply.getBoard().getBoardId());
        Board board = findBoard.orElseThrow(()->new BusinessLogicException(ExceptionCode.BOARD_NOT_FOUND));
        board.setQuestionStatus(Board.QuestionStatus.QUESTION_ANSWERED);
        return replyRepository.save(reply);
    }
    public Reply updateReply(Reply reply){
        Reply findReply = findVarifiedReply(reply.getReplyId());

        Optional.ofNullable(reply.getComment())
                .ifPresent(comment -> findReply.setComment(comment));

        reply.setModifiedAt(LocalDateTime.now());

        return replyRepository.save(findReply);
    }
    public Reply findReply(long replyId){
        return findVarifiedReply(replyId);
    }

    public void verifyExistReply(long boardId){
        Optional<Reply> Reply  = replyRepository.findById(boardId);
        if(Reply.isPresent()){
            throw new BusinessLogicException(ExceptionCode.REPLY_EXISTS);
        }
    }
    public Reply findVarifiedReply(long replyId){
        Optional<Reply> findReply = replyRepository.findById(replyId);
        return findReply.orElseThrow(()-> new BusinessLogicException(ExceptionCode.REPLY_NOT_FOUND));
    }

    public void deleteReply(long replyId){
        Reply findReply = findVarifiedReply(replyId);
        replyRepository.delete(findReply);
    }

//    public void updateQuestionStatus(Reply reply){
//        Board board = boardService.findBoard(reply.getBoard().getBoardId());
//        board.setQuestionStatus(Board.QuestionStatus.QUESTION_ANSWERED);
//        boardService.updateBoard(board);
//    }
}
