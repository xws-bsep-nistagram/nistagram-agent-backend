package rs.ac.uns.ftn.nistagram.auth.domain;

import org.hibernate.annotations.Type;
import org.springframework.security.core.GrantedAuthority;
import rs.ac.uns.ftn.nistagram.shopping.domain.cart.ShoppingCart;
import rs.ac.uns.ftn.nistagram.shopping.domain.checkout.Order;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
public class User {

    @Id
    private String username;
    private String email;
    private String fullName;
    private String passwordHash;
    @Type(type = "uuid-char")
    private UUID uuid;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Role> roles = new ArrayList<>();
    private boolean activated;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "owner")
    private ShoppingCart shoppingCart;
    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<Order> orders;

    public Collection<? extends GrantedAuthority> getAuthorities() {
        var authorities = new ArrayList<GrantedAuthority>();
        for(var role : roles) {
            authorities.addAll(role.getAllowedPermissions());
        }
        return authorities;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public List<Role> getRoles(){
        return roles;
    }

    public void addRoles(Role role) {
        roles.add(role);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        throw new RuntimeException("Password is not available in its pure form.");
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public ShoppingCart getShoppingCart() {
        if(shoppingCart.isEmpty())
            return new ShoppingCart(this);

        return shoppingCart;
    }

    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    public void activate(){
        if(!activated)
            activated = true;
    }

}
