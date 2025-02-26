package com.test.bookledger.domain.model;

import com.test.global.model.enumtype.YN;
import lombok.Value;

@Value
public class CategoryVO {
    String id;
    String name;
    YN deleteYN;
    boolean isDeleted(){
        return YN.Y == this.deleteYN;
    }

    public static CategoryVO to(Category category){
        return new CategoryVO(category.getId(), category.getName(), category.getDeleteYN());
    }
}
