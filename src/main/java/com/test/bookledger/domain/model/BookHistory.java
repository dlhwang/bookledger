package com.test.bookledger.domain.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.test.bookledger.domain.emnum.BookStatus;
import com.test.utills.NanoId;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "book_history")
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class BookHistory implements Serializable {

    @Serial
    private static final long serialVersionUID = -5326728843604442171L;

    @Id
    @NanoId
    @Column(name = "book_history_id", columnDefinition = "varchar(500)", nullable = false)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @Embedded
    private BookInfo bookInfo;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd kk:mm:ss")
    @CreatedDate
    @Column(name = "create_at", nullable = false, updatable = false,
            columnDefinition = "timestamp DEFAULT CURRENT_TIMESTAMP")
    protected LocalDateTime createAt;

    private BookHistory(Book book) {
        this.book = book;
        this.bookInfo = BookInfo.newInstance(book.getName(), book.authorName(), book.getBookStatus());
    }

    public static BookHistory newInstance(Book book){
        return new BookHistory(book);
    }
}
