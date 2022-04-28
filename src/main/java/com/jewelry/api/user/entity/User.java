package com.jewelry.api.user.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jewelry.api.commondata.model.AbstractEntity;
import com.jewelry.api.order.entity.Order;
import com.jewelry.api.role.entity.Role;
import com.jewelry.api.security.entity.RefreshToken;
import com.jewelry.api.shopping.entity.Cart;
import com.jewelry.api.user.util.UserGender;
import com.jewelry.api.util.DateUtils;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "jewelry_user")
public class User extends AbstractEntity {

    @Email(message = "{user.email.format}")
    @Column(unique = true, nullable = false)
    private String email;

    @NotBlank(message = "{user.password.not-blank}")
    private String password;

    @NotBlank(message = "{user.full-name.not-blank}")
    @Size(min = 3, max = 50, message = "{user.full-name.size}")
    private String fullName;

    @NotNull
    @Enumerated(EnumType.STRING)
    private UserGender gender;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateUtils.BIRTHDAY_FORMAT)
    private LocalDate birthday;

    @NotBlank(message = "{user.phone-number.not-blank}")
    @Pattern(regexp = "(^((?=(0))[0-9]{10})$)", message = "{user.phone-number.pattern}")
    @Column(unique = true)
    private String phoneNumber;

    @NotBlank(message = "{user.address.not-blank}")
    @Size(min = 20, max = 100, message = "{user.address.size}")
    private String address;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Cart cart;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private RefreshToken refreshToken;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Order> orders = new HashSet<>();

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(name = "jewelry_user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    public void addRole(Role role) {
        this.roles.add(role);
        role.getUsers().add(this);
    }

    public void addCart(Cart cart) {
        this.cart = cart;
        cart.setUser(this);
    }

    public void addOrder(Order order) {
        this.orders.add(order);
        order.setUser(this);
    }

    public void addRefreshToken(RefreshToken refreshToken) {
        this.refreshToken = refreshToken;
        refreshToken.setUser(this);
    }

}
