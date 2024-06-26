package com.tsayvyac.flashcard.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"flashcards"})
public class CardSet implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "card_set_seq_gen")
    @SequenceGenerator(name = "card_set_seq_gen", sequenceName = "card_set_seq", allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "learner_id", nullable = false)
    private Learner learner;

    @OneToMany(mappedBy = "cardSet", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Flashcard> flashcards;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        Class<?> oEffectiveClass = (o instanceof HibernateProxy proxy)
                ? proxy.getHibernateLazyInitializer().getPersistentClass()
                : o.getClass();
        Class<?> thisEffectiveClass = (this instanceof HibernateProxy proxy)
                ? proxy.getHibernateLazyInitializer().getPersistentClass()
                : this.getClass();

        if (thisEffectiveClass != oEffectiveClass) return false;
        CardSet cardSet = (CardSet) o;
        return getId() != null && Objects.equals(getId(), cardSet.getId());
    }

    @Override
    public final int hashCode() {
        return (this instanceof HibernateProxy proxy)
                ? proxy.getHibernateLazyInitializer().getPersistentClass().hashCode()
                : getClass().hashCode();
    }
}
