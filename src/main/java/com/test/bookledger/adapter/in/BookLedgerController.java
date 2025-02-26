package com.test.bookledger.adapter.in;

import com.test.bookledger.application.dto.BookRequest;
import com.test.bookledger.application.dto.BookResponse;
import com.test.bookledger.application.service.BookLedgerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "도서 관리 장부 v1", description = "도서 관리 장부 컨트롤러 입니다.")
@RequestMapping("/api/book-ledger/books")
@RequiredArgsConstructor
public class BookLedgerController {

    private final BookLedgerService service;

    @GetMapping
    @Operation(summary = "도서 조회", description = "도서를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public ResponseEntity<BookResponse> getBookLedgers(@ModelAttribute BookRequest.BookSearch search, @PageableDefault(sort = {"name"}) Pageable pageable) {
        return ResponseEntity.ok(service.getAll(search, pageable));
    }

    @PostMapping
    @Operation(summary = "신규 도서 등록", description = "신규 도서를 등록합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public ResponseEntity<BookResponse.BookVO> post(@RequestBody BookRequest.BookSave save) {
        return ResponseEntity.ok(service.save(save));
    }

    @PutMapping("/{id}")
    @Operation(summary = "기존 도서 수정", description = "도서 정보를 수정합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public ResponseEntity<BookResponse.BookVO> put(@PathVariable String id, @RequestBody BookRequest.BookModify modify) {
        return ResponseEntity.ok(service.save(modify));
    }

    @PatchMapping("/{id}/rent")
    @Operation(summary = "도서 대여", description = "도서를 대여합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public ResponseEntity<BookResponse.BookVO> rent(@PathVariable String id) {
        return ResponseEntity.ok(service.rent(id));
    }

    @PatchMapping("/{id}/returnBook")
    @Operation(summary = "도서 대여", description = "대여한 도서를 반납합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public ResponseEntity<BookResponse.BookVO> returnBook(@PathVariable String id, @RequestBody BookRequest.BookRent bookRent) {
        return ResponseEntity.ok(service.returnBook(id, bookRent));
    }

    @PatchMapping("/{id}/lost")
    @Operation(summary = "도서 분실", description = "도서를 분실 상태로 변경합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public ResponseEntity<BookResponse.BookVO> lost(@PathVariable String id) {
        return ResponseEntity.ok(service.lost(id));
    }

    @PatchMapping("/{id}/damage")
    @Operation(summary = "도서 훼손", description = "도서를 훼손 상태로 변경합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public ResponseEntity<BookResponse.BookVO> damage(@PathVariable String id) {
        return ResponseEntity.ok(service.damage(id));
    }
}
