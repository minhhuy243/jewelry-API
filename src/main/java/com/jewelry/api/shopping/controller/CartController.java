package com.jewelry.api.shopping.controller;

import com.jewelry.api.security.dto.UserDetailsDto;
import com.jewelry.api.shopping.dto.CartDetailsDto;
import com.jewelry.api.shopping.dto.CartItemCreateDto;
import com.jewelry.api.shopping.dto.CartItemUpdateDto;
import com.jewelry.api.shopping.entity.Cart;
import com.jewelry.api.shopping.entity.CartItem;
import com.jewelry.api.shopping.service.itf.CartService;
import com.jewelry.api.util.ErrorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api/carts")
public class CartController {

    @Autowired
    private CartService cartService;

//    @GetMapping("/mine")
//    public ResponseEntity<Object> findCartDetails(@AuthenticationPrincipal CustomerPrincipalDto curUser) {
//        CartDetailsDto cartDetailsDto = cartService.findCartDetails(curUser.getEmail());
//        if(cartDetailsDto.getItems() == null) {
//            return ResponseHandler.getResponse("Không có sản phẩm trong giỏ hàng", HttpStatus.OK);
//        }
//        return ResponseHandler.getResponse(cartDetailsDto, HttpStatus.OK);
//    }

    @GetMapping("/mine")
    public ResponseEntity<Object> checkAndUpdateItemInStock(@AuthenticationPrincipal UserDetailsDto curUser) {
        CartDetailsDto cartDetailsDto = cartService.checkAndUpdateItemInStock(curUser.getUsername());
        if(cartDetailsDto.getItems() == null) {
            return ResponseEntity.ok("Empty cart!");
        }
        return ResponseEntity.ok(cartDetailsDto);
    }

    @PostMapping("/mine/items")
    public ResponseEntity<Object> addItemToCart(@Valid @RequestBody CartItemCreateDto itemDto,
                                                        @AuthenticationPrincipal UserDetailsDto curUser,
                                                        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(ErrorUtils.getErrorMessages(bindingResult));
        }

        CartDetailsDto cartDetailsDto = cartService.addItemToCart(itemDto, curUser.getUsername());
        return ResponseEntity.ok(cartDetailsDto);
    }

    @PutMapping("/mine/items")
    public ResponseEntity<Object> updateItemToCart(@Valid @RequestBody CartItemUpdateDto itemDto,
                                                   @AuthenticationPrincipal UserDetailsDto curUser,
                                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(ErrorUtils.getErrorMessages(bindingResult));
        }

        CartDetailsDto cartDetailsDto = cartService.updateItemToCart(itemDto, curUser.getUsername());
        return ResponseEntity.ok(cartDetailsDto);
    }


    @DeleteMapping("/mine/items/{productSku}")
    public ResponseEntity<Object> deleteItemInCart(@PathVariable String productSku,
                                                   @AuthenticationPrincipal UserDetailsDto curUser) {
        Cart cart = cartService.findCartByEmail(curUser.getUsername());
        Optional<CartItem> cartItemOpt
                = cart.getItems().stream()
                .filter(cartItem -> cartItem.getProduct().getSku().equals(productSku)).findFirst();
        if(!cartItemOpt.isPresent()) {
            return ResponseEntity.badRequest().body("The product does not exists in the cart!");
        }

        CartDetailsDto cartDetailsDto = cartService.deleteItemToCart(cart, cartItemOpt.get());
        return ResponseEntity.ok(cartDetailsDto);
    }
}
