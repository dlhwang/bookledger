package com.test.bookledger.domain.model;

import com.test.global.model.enumtype.YN;
import com.test.utills.NanoId;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "author") // 테이블 이름 설정
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE author SET delete_yn = 'Y' WHERE author_id = ?")
public class Author implements Serializable {
    @Serial
    private static final long serialVersionUID = 698864714734820117L;

    @Id
    @NanoId
    @Column(name = "author_id", columnDefinition = "varchar(500)", nullable = false)
    private String id;

    /**
     * 제목
     */
    @Column(name="name", nullable = false)
    private String name;

    /**
     * 삭제 여부
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "delete_yn", columnDefinition = "char(1) default 'N'", nullable = false, length = 1)
    private YN deleteYN;

    protected Author(String name) {
        this.name = name;
        this.deleteYN = YN.N;
    }

    public static Author newInstance(String name){
        return new Author(name);
    }

    boolean isDeleted(){
        return YN.Y == this.deleteYN;
    }
}
