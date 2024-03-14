package com.tsayvyac.flashcard.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Flashcard {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "flashcard_seq_gen")
    @SequenceGenerator(name = "flashcard_seq_gen", sequenceName = "flashcard_seq", allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    private String front;

    @Column(nullable = false)
    private String back;

    @OneToOne(mappedBy = "flashcard", cascade = CascadeType.ALL, orphanRemoval = true)
    @PrimaryKeyJoinColumn
    @JsonManagedReference
    private Progress progress;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        Class<?> oEffectiveClass = (o instanceof HibernateProxy hibernateProxy)
                ? hibernateProxy.getHibernateLazyInitializer().getPersistentClass()
                : o.getClass();
        Class<?> thisEffectiveClass = (this instanceof HibernateProxy hibernateProxy)
                ? hibernateProxy.getHibernateLazyInitializer().getPersistentClass()
                : this.getClass();

        if (thisEffectiveClass != oEffectiveClass) return false;
        Flashcard flashcard = (Flashcard) o;
        return getId() != null && Objects.equals(getId(), flashcard.getId());
    }

    @Override
    public final int hashCode() {
        return (this instanceof HibernateProxy hibernateProxy)
                ? hibernateProxy.getHibernateLazyInitializer().getPersistentClass().hashCode()
                : getClass().hashCode();
    }
}
