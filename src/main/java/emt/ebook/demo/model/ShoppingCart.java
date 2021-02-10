package emt.ebook.demo.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "ShoppingCarts")
public class ShoppingCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    private LocalDateTime dateCreated = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    private ShoppingCartStatus shoppingCartStatus = ShoppingCartStatus.CREATED;

    @ManyToMany
    private List<Book> books = new ArrayList<>();

    public ShoppingCart() {}

    public ShoppingCart(User user, LocalDateTime dateCreated, ShoppingCartStatus shoppingCartStatus) {
        this.user = user;
        this.dateCreated = dateCreated;
        this.shoppingCartStatus = shoppingCartStatus;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public ShoppingCartStatus getShoppingCartStatus() {
        return shoppingCartStatus;
    }

    public void setShoppingCartStatus(ShoppingCartStatus shoppingCartStatus) {
        this.shoppingCartStatus = shoppingCartStatus;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
