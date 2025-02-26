package com.test.bookledger.domain.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.test.utills.NanoId;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "book_category")
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class BookCategory implements Serializable {

    @Serial
    private static final long serialVersionUID = 8388862945429388212L;

    @Id
    @NanoId
    @Column(name = "book_category_id", columnDefinition = "varchar(500)", nullable = false)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Setter(AccessLevel.PROTECTED)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd kk:mm:ss")
    @CreatedDate
    @Column(name = "create_at", nullable = false, updatable = false,
            columnDefinition = "timestamp DEFAULT CURRENT_TIMESTAMP")
    protected LocalDateTime createAt;

    private BookCategory(Category category, Book book) {
        this.category = category;
        this.book = book;
    }

    public static BookCategory newInstance(Category category, Book book){
        return new BookCategory(category, book);
    }

    public String getCategoryName(){
        return this.category.getName();
    }
}
