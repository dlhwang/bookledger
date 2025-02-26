package com.test.bookledger.domain.model;

import com.test.global.model.enumtype.YN;
import com.test.utills.NanoId;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;

import java.io.Serial;
import java.io.Serializable;

@Value
public class AuthorVO {
    String id;
    String name;
    YN deleteYN;
    boolean isDeleted(){
        return YN.Y == this.deleteYN;
    }

    public static AuthorVO to(Author author){
        return new AuthorVO(author.getId(), author.getName(), author.getDeleteYN());
    }
}
