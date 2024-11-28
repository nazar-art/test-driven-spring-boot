package com.xpinjection.library.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Alimenkou Mikalai
 */
@Entity
@ToString
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_id_seq")
    @SequenceGenerator(name = "book_id_seq", sequenceName = "book_id_seq", allocationSize = 1)
    private Long id;

    @NonNull
    private String name;

    @NonNull
    private String author;
}
