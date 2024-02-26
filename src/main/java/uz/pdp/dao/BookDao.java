package uz.pdp.dao;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import uz.pdp.domain.Book;

import java.util.List;

@Repository
@Transactional
@RequiredArgsConstructor
public class BookDao {

    private final SessionFactory sessionFactory;

    public Book save(Book book){
        sessionFactory.getCurrentSession()
                .persist(book);
        return book;
    }

    public Book getBookByTitle(String title) {
        return sessionFactory.getCurrentSession()
                .createNamedQuery("book.find.by.title", Book.class)
                .setParameter("title", title)
                .uniqueResult();

        /*return sessionFactory.getCurrentSession()
                .createQuery("select b from Book b where b.title = :title", Book.class)
                .setParameter("title", title)
                .uniqueResult();*/
    }

    public List<Book> getAllByDescriptionPartialContains(String word){
        return sessionFactory.getCurrentSession()
                .createNamedQuery("book.find.by.description.contains", Book.class)
                .setParameter("word", word)
                .getResultList();
    }

    public List<Book> getAllByPriceBetween(double low, double high){
        return sessionFactory.getCurrentSession()
                .createNamedQuery("book.find.by.price.between", Book.class)
                .setParameter("low", low)
                .setParameter("high", high)
                .getResultList();
    }

    public List<Book> getAll(){
        return sessionFactory.getCurrentSession()
                .createQuery("from Book", Book.class)
                .getResultList();
    }
}
