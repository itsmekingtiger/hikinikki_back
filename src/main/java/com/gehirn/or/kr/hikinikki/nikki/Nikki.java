package com.gehirn.or.kr.hikinikki.nikki;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;


@Data
@Entity
public class Nikki {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 2000)
    private String content;

    private Date createdAt;

    @PrePersist
    void prePersist() {
        this.createdAt = new Date();
    }
}
