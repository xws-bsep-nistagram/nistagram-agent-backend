package rs.ac.uns.ftn.nistagram.shopping.domain.cart;

import rs.ac.uns.ftn.nistagram.auth.domain.User;
import rs.ac.uns.ftn.nistagram.shopping.domain.Product;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
public class ShoppingCart {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @OneToOne
    private User owner;

    @OneToMany(mappedBy = "shoppingCart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ShoppingCartItem> shoppingCartItems;

    public ShoppingCart(){}

    public ShoppingCart(User owner) {
        this.owner = owner;
        this.shoppingCartItems = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public List<ShoppingCartItem> getShoppingCartItems() {
        return shoppingCartItems;
    }

    public void setShoppingCartItems(List<ShoppingCartItem> shoppingCartItems) {
        this.shoppingCartItems = shoppingCartItems;
    }
    public void addProduct(Product product, int quantity){
        var foundProduct = findProduct(product);
        if(foundProduct.isPresent()) {
            foundProduct.get().incrementBy(quantity);
            return;
        }
        shoppingCartItems.add(new ShoppingCartItem(product,quantity,this));
    }

    public boolean isEmpty(){
        return this.shoppingCartItems == null || this.shoppingCartItems.size() == 0;
    }

    private Optional<ShoppingCartItem> findProduct(Product product) {
        return this.shoppingCartItems.stream()
                .filter(shoppingCartItem -> shoppingCartItem.getProduct().getId() == product.getId())
                .findFirst();
    }

    public boolean hasItem(long shoppingCartItemId) {
        return this.shoppingCartItems
                .stream()
                .anyMatch(shoppingCartItem -> shoppingCartItem.getId() == shoppingCartItemId);
    }

    public void removeItem(long shoppingCartItemId) {
        shoppingCartItems.remove(shoppingCartItems
                                    .stream()
                                    .filter(s-> s.getId() == shoppingCartItemId)
                                    .findFirst()
                                    .get());
    }

    public ShoppingCartItem findCartItemById(long cartItemId) {
        for (var item : this.shoppingCartItems)
            if (item.getId() == cartItemId)
                return item;
        return null;
    }

    public void clearCart() {
        this.shoppingCartItems.clear();
    }
}
