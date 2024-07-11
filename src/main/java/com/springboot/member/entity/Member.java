package com.springboot.member.entity;

import com.springboot.board.entity.Board;
import com.springboot.reply.entity.Reply;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long memberId;

    @Column(nullable = false,updatable = false,unique = true)
    private String email;

    @Column(nullable = false,length = 30)
    private String password;

    @Column(nullable = false,updatable = false)
    private String name;

    @Column(nullable = false,length = 13)
    private String phone;

    @Column(nullable = false,updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @OneToMany(mappedBy = "member")
    private List<Board> boards = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false,length = 20)
    private MemberStatus memberStatus = MemberStatus.MEMBER_ACTIVE;

    @OneToOne(mappedBy = "member")
    private Reply reply;

    public void setBoard(Board board){
        boards.add(board);
        if(board.getMember() != this){
            board.setMember(this);
        }
    }
    public enum MemberStatus{
        MEMBER_ACTIVE("활동중"),
        MEMBER_SLEEP("휴면"),
        MEMBER_QUIT("탈퇴");
        @Getter
        private String status;

        MemberStatus(String status) {
            this.status = status;
        }
    }
}
