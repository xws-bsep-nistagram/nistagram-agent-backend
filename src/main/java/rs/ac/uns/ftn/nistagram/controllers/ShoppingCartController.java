package rs.ac.uns.ftn.nistagram.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.nistagram.controllers.DTOs.cart.ShoppingCartEntryDTO;
import rs.ac.uns.ftn.nistagram.controllers.DTOs.cart.ShoppingCartItemRemovalDTO;
import rs.ac.uns.ftn.nistagram.controllers.DTOs.cart.ShoppingCartItemDTO;
import rs.ac.uns.ftn.nistagram.services.ShoppingCartService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/shopping-cart")
public class ShoppingCartController {

    private final ShoppingCartService service;
    private final ModelMapper mapper;

    public ShoppingCartController(ShoppingCartService shoppingCartService, ModelMapper mapper) {
        this.service = shoppingCartService;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<?> add(@RequestBody ShoppingCartEntryDTO shoppingCartEntry){
        service.add(shoppingCartEntry);
        return ResponseEntity.ok("New shopping cart entry has been successfully added");
    }
    @DeleteMapping
    public ResponseEntity<?> delete(@RequestBody ShoppingCartItemRemovalDTO shoppingCartItemRemoval){
        service.delete(shoppingCartItemRemoval);
        return ResponseEntity.ok("Shopping cart entry has been successfully removed");
    }
    //TODO: remove when jwt is implemented
    @GetMapping("/user/{username}")
    public ResponseEntity<List<ShoppingCartItemDTO>> getShoppingCartItems(@PathVariable String username){

        return ResponseEntity.ok(service.getShoppingCartItems(username)
                                        .stream()
                                        .map(item -> mapper.map(item, ShoppingCartItemDTO.class))
                                        .collect(Collectors.toList()));
    }

}