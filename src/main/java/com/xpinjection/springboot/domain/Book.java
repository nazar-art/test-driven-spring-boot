package com.xpinjection.springboot.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author Alimenkou Mikalai
 */
@Entity
@ToString
@Getter @Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class Book {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String name;

    @NonNull
    private String author;
}
