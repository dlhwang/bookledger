package com.test.config;

import com.test.bookledger.domain.emnum.BookStatus;
import com.test.bookledger.domain.model.*;
import com.test.bookledger.domain.repository.*;
import com.test.global.model.Events;
import jdk.jfr.Event;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;
    private final BookRepository bookRepository;
    private final BookHistoryRepository bookHistoryRepository;

    @Override
    @Transactional
    public void run(String... args) throws Exception {

        if (bookRepository.count() != 0) {
            return;
        }

        Category category1 = Category.newInstance("문학");
        Category category2 = Category.newInstance("경제경영");
        Category category3 = Category.newInstance("인문학");
        Category category4 = Category.newInstance("IT");
        Category category5 = Category.newInstance("과학");

        List<Book> bookList = new ArrayList<>();

        Author author1 = Author.newInstance("권태영");
        Book book11 = Book.newInstance("너에게 해주지 못한 말들", author1, List.of(category1, category2));
        Book book12 = Book.newInstance("게으른 사랑", author1, List.of(category1, category2));
        Book book13 = Book.newInstance("트렌드 코리아 2322", author1, category2);
        Book book14 = Book.newInstance("Skye가 알려주는 피부 채색의 비결", author1, List.of(category2, category4));
        bookList.add(book11);
        bookList.add(book12);
        bookList.add(book13);
        bookList.add(book14);

        Author author2 = Author.newInstance("현영서");
        Book book21 = Book.newInstance("단순하게 배부르게", author2, category1);
        bookList.add(book21);

        Author author3 = Author.newInstance("장동혁");
        Book book31 = Book.newInstance("초격자 투자", author3, List.of(category2, category4));
        Book book32 = Book.newInstance("인공지능1", author3, List.of(category2, category4));
        Book book33 = Book.newInstance("인공지능2", author3, List.of(category2, category4));
        Book book34 = Book.newInstance("인공지능3", author3, category4, BookStatus.RENTED);
        Book book35 = Book.newInstance("인공지능4", author3, category4, BookStatus.LOST);
        Book book36 = Book.newInstance("인공지능5", author3, category4, BookStatus.DAMAGED);
        Book book37 = Book.newInstance("인공지능6", author3, category4);
        Book book38 = Book.newInstance("인공지능7", author3, category4);
        Book book39 = Book.newInstance("인공지능8", author3, category4);
        Book book310 = Book.newInstance("인공지능9", author3, category4);
        Book book311 = Book.newInstance("인공지능10", author3, category4);
        Book book312 = Book.newInstance("인공지능11", author3, category4);
        Book book313 = Book.newInstance("인공지능12", author3);

        bookList.add(book31);
        bookList.add(book32);
        bookList.add(book33);
        bookList.add(book34);
        bookList.add(book35);
        bookList.add(book36);
        bookList.add(book37);
        bookList.add(book38);
        bookList.add(book39);
        bookList.add(book310);
        bookList.add(book311);
        bookList.add(book312);
        bookList.add(book313);

        Author author4 = Author.newInstance("홍길동");
        Book book41 = Book.newInstance("파이어족 강환국의 하면 되지 않는다! 퀀트 투자", author4, category2);
        bookList.add(book41);

        Author author5 = Author.newInstance("이서연");
        Book book51 = Book.newInstance("진심보다 밥", author5, category3);
        bookList.add(book51);

        Author author6 = Author.newInstance("위성원");
        Book book61 = Book.newInstance("실패에 대하여 생각하지 마라", author6, category3);
        Book book62 = Book.newInstance("-1년차 게임 개발", author6, category4);
        bookList.add(book61);
        bookList.add(book62);

        Author author7 = Author.newInstance("지승열");
        Book book71 = Book.newInstance("실리콘밸리 리더십 쉽다", author7, category4);
        Book book72 = Book.newInstance("데이터분석을 위한 A 프로그래밍", author7, category4);
        bookList.add(book71);
        bookList.add(book72);

        Author author8 = Author.newInstance("장지명");
        Book book81 = Book.newInstance("자연의 발전", author8, category5);
        bookList.add(book81);

        Author author9 = Author.newInstance("이승열");
        Book book91 = Book.newInstance("코스모스 필 무렵", author9, category5);
        bookList.add(book91);

        authorRepository.saveAll(List.of(author1, author2, author3, author4, author5, author6, author7, author8, author9));
        categoryRepository.saveAll(List.of(category1, category2, category3, category4, category5));
        bookRepository.saveAll(bookList);

        List<BookHistory> list = bookList.stream().map(BookHistory::newInstance).toList();
        bookHistoryRepository.saveAll(list);
    }
}
