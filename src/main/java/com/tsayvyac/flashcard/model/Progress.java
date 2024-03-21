package com.tsayvyac.flashcard.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.proxy.HibernateProxy;

import java.util.Date;
import java.util.Objects;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "flashcard")
public class Progress {
    @Id
    @Column(name = "flashcard_id")
    private Long id;

    @Column(nullable = false)
    private Integer repetitions;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date nextDate;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "flashcard_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Flashcard flashcard;

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
        Progress progress = (Progress) o;
        return getId() != null && Objects.equals(getId(), progress.getId());
    }

    @Override
    public final int hashCode() {
        return (this instanceof HibernateProxy hibernateProxy)
                ? hibernateProxy.getHibernateLazyInitializer().getPersistentClass().hashCode()
                : getClass().hashCode();
    }
}
