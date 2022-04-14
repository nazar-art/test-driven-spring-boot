package com.xpinjection.library.domain;

import lombok.*;

import javax.persistence.*;

/**
 * @author Alimenkou Mikalai
 */
@Entity
@ToString
@Getter @Setter
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
