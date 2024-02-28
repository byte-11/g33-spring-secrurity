package uz.pdp.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedNativeQueries;
import jakarta.persistence.NamedNativeQuery;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@NamedQueries({
        @NamedQuery(name = "book.find.by.title", query = "select b from Book b where b.title=:title"),
        @NamedQuery(name = "book.find.by.description.contains", query = "select b from Book b where b.description ilike '%'||:word||'%' ")
})
@NamedNativeQueries({
        @NamedNativeQuery(
                name = "book.find.by.price.between",
                query = "select b from book b where price >= :low and price <= :high ",
                resultClass = Book.class
        )
})
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(unique = true, nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    private double price;

    @CreationTimestamp
    @Column(insertable = true, updatable = false, columnDefinition = "timestamp default now()")
    private LocalDateTime createdTime;
}
