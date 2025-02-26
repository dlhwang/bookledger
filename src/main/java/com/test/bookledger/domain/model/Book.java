package com.test.bookledger.domain.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.test.bookledger.domain.emnum.BookStatus;
import com.test.bookledger.domain.service.BookEventHandler;
import com.test.global.model.Events;
import com.test.global.model.enumtype.YN;
import com.test.utills.NanoId;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.slf4j.LoggerFactory;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@Table(name = "book")
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE book SET delete_yn = 'Y' WHERE book_id = ?")
@EntityListeners(AuditingEntityListener.class)
public class Book implements Serializable {

    @Serial
    private static final long serialVersionUID = 1363929378638504998L;

    @Id
    @NanoId
    @Column(name = "book_id", columnDefinition = "varchar(500)", nullable = false)
    private String id;

    /**
     * 제목
     */
    @Column(name="name", nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private Author author;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BookCategory> bookCategories = new ArrayList<>();

    /**
     * 최근 책 대여 상태
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "book_status", nullable = false)
    private BookStatus bookStatus;

    /**
     * 삭제 여부
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "delete_yn", columnDefinition = "char(1) default 'N'", nullable = false, length = 1)
    private YN deleteYN;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd kk:mm:ss")
    @CreatedDate
    @Column(name = "create_at", nullable = false, updatable = false,
            columnDefinition = "timestamp DEFAULT CURRENT_TIMESTAMP")
    protected LocalDateTime createAt;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd kk:mm:ss")
    @LastModifiedDate
    @Column(name = "update_at", nullable = false, columnDefinition = "timestamp DEFAULT CURRENT_TIMESTAMP")
    protected LocalDateTime updateAt;

    private Book(String name, Author author, List<Category> categories, BookStatus bookStatus, YN deleteYN) {
        needCategories(categories);
        this.name = name;
        this.author = author;
        this.bookStatus = bookStatus;
        this.deleteYN = deleteYN;
        addCategory(categories.stream().map(category -> BookCategory.newInstance(category, this)).toList());
    }

    //TODO 기존 카테고리가 없는 테스트데이터를 위한 임시 처리
    @Deprecated
    private Book(String name, Author author, BookStatus bookStatus, YN deleteYN) {
        this.name = name;
        this.author = author;
        this.bookStatus = bookStatus;
        this.deleteYN = deleteYN;
    }

    public void needCategories(List<Category> categories) {
        if(ObjectUtils.isEmpty(categories)){
            throw new IllegalArgumentException("신규 도서는 항상 카테고리가 필요합니다.");
        }
    }

    public String authorName(){
        return author.getName();
    }

    //TODO 기존 카테고리가 없는 테스트데이터를 위한 임시 처리
    @Deprecated
    public static Book newInstance(String name, Author author) {
        return new Book(name, author, BookStatus.AVAILABLE, YN.N);
    }

    public static Book newInstance(String name, Author author, Category category) {
        return Book.newInstance(name, author, List.of(category), BookStatus.AVAILABLE);
    }

    public static Book newInstance(String name, Author author, Category category, BookStatus bookStatus) {
        return Book.newInstance(name, author, List.of(category), bookStatus);
    }

    public static Book newInstance(String name, Author author, List<Category> categories) {
        return Book.newInstance(name, author, categories, BookStatus.AVAILABLE);
    }

    public static Book newInstance(String name, Author author, List<Category> categories, BookStatus bookStatus) {
        return new Book(name, author, categories, bookStatus, YN.N);
    }

    public void changeName(String name){
        this.name = name;
        Events.publishEvent(new BookEvent(getId()));
    }

    public void rent(){
        if(isNotRentable()){
            throw new IllegalArgumentException("도서 대여가 불가능한 상태입니다.");
        }

        if(BookStatus.AVAILABLE == this.bookStatus){
            this.bookStatus = BookStatus.RENTED;
            Events.publishEvent(new BookEvent(getId()));
        }
    }

    public void lost(){
        if(BookStatus.LOST != this.bookStatus){
            this.bookStatus = BookStatus.LOST;
            Events.publishEvent(new BookEvent(getId()));
        }
    }

    public void damage(){
        if(BookStatus.AVAILABLE == this.bookStatus || BookStatus.RENTED == this.bookStatus){
            this.bookStatus = BookStatus.DAMAGED;
            Events.publishEvent(new BookEvent(getId()));
        }
    }

    public void returnBook(BookStatus bookStatus){

        if(bookStatus == null){
            throw new IllegalArgumentException("도서 반납시 반납 상태를 선택해야합니다.");
        }

        if(BookStatus.RENTED != this.bookStatus){
            throw new IllegalArgumentException("대여되지 않은 도서를 반납할 수 있습니다.");
        }

        this.bookStatus = BookStatus.AVAILABLE;
        Events.publishEvent(new BookEvent(getId()));

        switch (bookStatus) {
            case DAMAGED -> damage();
            case LOST -> lost();
        }

    }

    public boolean isRentable(){
        return this.bookStatus == BookStatus.AVAILABLE;
    }

    public boolean isNotRentable(){
        return this.bookStatus != BookStatus.AVAILABLE;
    }


    public void changeAuthor(Author author) {
        this.author = author;
        Events.publishEvent(new BookEvent(getId()));
    }

    public boolean hasCategories(){
        return !getBookCategories().isEmpty();
    }

    public void changeCategory(BookCategory category){
        if(ObjectUtils.isEmpty(category)){
            return;
        }
        this.bookCategories = new ArrayList<>();
        addCategory(category);

    }
    public void changeCategory(List<BookCategory> categories){
        if(ObjectUtils.isEmpty(categories)){
            return;
        }
        this.bookCategories = new ArrayList<>();
        addCategory(categories);
    }

    public void addCategory(BookCategory category){

        if(ObjectUtils.isEmpty(category)){
            return;
        }

        if(category.getBook() == null){
            category.setBook(this);
        }

        this.bookCategories.add(category);
    }

    public void addCategory(List<BookCategory> categories){
        if(ObjectUtils.isEmpty(categories)){
            return;
        }

        this.bookCategories.addAll(categories);
        categories.forEach(category -> category.setBook(this));
    }

    public BookInfo getBookInfo(){
        return BookInfo.newInstance(name, getAuthorName(), bookStatus);
    }

    public String getAuthorName() {
        return this.author.getName();
    }

    public String getCategoriesName(){
        if(!hasCategories()){
            return "";
        }

        return this.bookCategories.stream().map(BookCategory::getCategoryName).collect(Collectors.joining(", ","[","]"));
    }
}
