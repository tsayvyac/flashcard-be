package com.tsayvyac.flashcard.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Learner {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "learner_seq_gen")
    @SequenceGenerator(name = "learner_seq_gen", sequenceName = "learner_seq", allocationSize = 1)
    private Long uid;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;

    @OneToMany(mappedBy = "learner", cascade = CascadeType.REMOVE)
    @ToString.Exclude
    private Set<CardSet> cardSets;

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
        Learner learner = (Learner) o;
        return getUid() != null && Objects.equals(getUid(), learner.getUid());
    }

    @Override
    public final int hashCode() {
        return (this instanceof HibernateProxy proxy)
                ? proxy.getHibernateLazyInitializer().getPersistentClass().hashCode()
                : getClass().hashCode();
    }
}
