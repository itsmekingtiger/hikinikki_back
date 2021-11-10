package com.gehirn.or.kr.hikinikki.nikki;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;


@Data
@Entity
public class Nikki {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    private Date createdAt;

    @PrePersist
    void prePersist() {
        this.createdAt = new Date();
    }
}
