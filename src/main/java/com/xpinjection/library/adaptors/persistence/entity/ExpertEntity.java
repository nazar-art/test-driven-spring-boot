package com.xpinjection.library.adaptors.persistence.entity;

import com.xpinjection.library.domain.Book;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "expert")
@RequiredArgsConstructor
public class ExpertEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "expert_id_seq")
    @SequenceGenerator(name = "expert_id_seq", sequenceName = "expert_id_seq", allocationSize = 1)
    private Long id;

    @NonNull
    private String name;

    @NonNull
    private String contact;

    @OneToMany
    @JoinTable(name = "recommendations",
            joinColumns = {@JoinColumn(name = "expert_id")},
            inverseJoinColumns = {@JoinColumn(name = "book_id")})
    private Set<Book> recommendations;
}
