package com.playssafy.playssafy.domain.user;

import io.swagger.annotations.ApiModel;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@Entity(name="user") // DB 테이블을 해당 Entity로 Mapping.
@NoArgsConstructor
@ApiModel
public class UserEntity {
    @Id // primary key
    private String id;
    private String name;
    private String pass;

    @Builder
    public UserEntity(String id, @NonNull String name, String pass){
        this.id = id;
        this.name = name;
        this.pass = pass;
    }
}
