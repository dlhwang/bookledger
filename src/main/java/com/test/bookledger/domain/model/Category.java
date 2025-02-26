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
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "category") // 테이블 이름 설정
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE author SET delete_yn = 'Y' WHERE author_id = ?")
public class Category implements Serializable {
    @Serial
    private static final long serialVersionUID = -6596969318734804529L;

    @Id
    @NanoId
    @Column(name = "category_id", columnDefinition = "varchar(500)", nullable = false)
    private String id;

    /**
     * 제목
     */
    @Column(name="name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BookCategory> bookCategories = new ArrayList<>();

    /**
     * 삭제 여부
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "delete_yn", columnDefinition = "char(1) default 'N'", nullable = false, length = 1)
    private YN deleteYN;

    protected Category(String name) {
        this.name = name;
        this.deleteYN = YN.N;
    }

    boolean isDeleted(){
        return YN.Y == this.deleteYN;
    }

    public static Category newInstance(String name){
        return new Category(name);
    }
}
