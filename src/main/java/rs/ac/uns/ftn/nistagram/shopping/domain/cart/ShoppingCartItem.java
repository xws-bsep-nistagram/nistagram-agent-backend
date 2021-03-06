package rs.ac.uns.ftn.nistagram.shopping.domain.cart;

import rs.ac.uns.ftn.nistagram.shopping.domain.Product;

import javax.persistence.*;

@Entity
public class ShoppingCartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    @OneToOne
    private Product product;
    @ManyToOne
    private ShoppingCart shoppingCart;
    private int quantity;

    public ShoppingCartItem() {
    }

    public ShoppingCartItem(Product product, int quantity, ShoppingCart shoppingCart) {
        this.product = product;
        this.quantity = quantity;
        this.shoppingCart = shoppingCart;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void incrementBy(int quantity) {
        this.quantity += quantity;
    }

}
