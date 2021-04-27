package rs.ac.uns.ftn.nistagram.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.nistagram.controllers.DTOs.invoice.InvoiceCollectionDTO;
import rs.ac.uns.ftn.nistagram.controllers.DTOs.invoice.InvoiceRequestDTO;
import rs.ac.uns.ftn.nistagram.controllers.DTOs.invoice.InvoiceDTO;
import rs.ac.uns.ftn.nistagram.services.InvoiceService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/invoice")
public class InvoiceController {

    private final InvoiceService service;
    private final ModelMapper mapper;

    public InvoiceController(InvoiceService service, ModelMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PreAuthorize("hasRole('ADMIN') && hasAuthority('GET_ALL_INVOICES')")
    @GetMapping
    public ResponseEntity<List<InvoiceCollectionDTO>> getAll(){
        var invoiceCollections = service.getAll()
                                .stream()
                                .map(ic -> mapper.map(ic, InvoiceCollectionDTO.class))
                                .collect(Collectors.toList());
        return ResponseEntity.ok(invoiceCollections);
    }

    @PreAuthorize("hasRole('USER') && hasAuthority('GET_USER_CART')")
    @GetMapping("/user/{username}")
    public ResponseEntity<?> get(@PathVariable String username){
        var invoices = mapper.map(service.get(username), InvoiceCollectionDTO.class);
        return ResponseEntity.ok(invoices);
    }

    @PreAuthorize("hasRole('USER') && hasAuthority('CHECKOUT_INVOICE')")
    @PostMapping
    public ResponseEntity<?> checkout(@RequestBody InvoiceRequestDTO invoiceRequest){
        service.checkout(invoiceRequest);
        return ResponseEntity.ok("Successfully checked out");
    }

    @PreAuthorize("hasRole('ADMIN') && hasAuthority('REJECT_INVOICE')")
    @PutMapping("/reject/{invoiceId}")
    public ResponseEntity<?> reject(@PathVariable long invoiceId){
        service.reject(invoiceId);
        return ResponseEntity.ok("Order rejected");
    }

    @PreAuthorize("hasRole('ADMIN') && hasAuthority('ACCEPT_INVOICE')")
    @PutMapping("/accept/{invoiceId}")
    public ResponseEntity<?> accept(@PathVariable long invoiceId){
        service.accept(invoiceId);
        return ResponseEntity.ok("Order accepted");
    }
}
