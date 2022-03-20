package com.jewelry.api.product.controller.admin;

import com.jewelry.api.product.dto.supplier.SupplierCreateDto;
import com.jewelry.api.product.dto.supplier.SupplierUpdateDto;
import com.jewelry.api.product.entity.Supplier;
import com.jewelry.api.product.projection.supplier.SupplierProjection;
import com.jewelry.api.product.service.itf.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin/suppliers")
public class AdminSupplierController {

    @Autowired
    private SupplierService supplierService;

    @GetMapping("")
    public ResponseEntity<Object> findAll() {

        List<SupplierProjection> supplierProjections = supplierService.findAllBy(SupplierProjection.class);
        if (supplierProjections.isEmpty()) {
            return ResponseEntity.ok("Empty list!");
        }
        return ResponseEntity.ok(supplierProjections);
    }

    @PostMapping("")
    public ResponseEntity<Object> addSupplier(@Valid @RequestBody SupplierCreateDto dto,
                                              BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult);
        }

        Supplier newSupplier = supplierService.save(dto);
        return ResponseEntity.ok(newSupplier);
    }

    @PutMapping("/{supplier-id}")
    public ResponseEntity<Object> updateSupplier(@PathVariable("supplier-id") Long id,
                                                 @Valid @RequestBody SupplierUpdateDto dto,
                                                 BindingResult bindingResult) {
        Optional<Supplier> supplier = supplierService.findById(id);
        if(!supplier.isPresent()){
            return ResponseEntity.badRequest().body("Supplier not found!");
        }

        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult);
        }

        Supplier supplierUpdate = supplierService.updateSupplierInfo(dto, id);
        return ResponseEntity.ok(supplierUpdate);
    }

    @DeleteMapping("/{supplier-id}")
    public ResponseEntity<Object> deleteSupplier(@PathVariable("supplier-id") Long id) {

        Optional<Supplier> supplier = supplierService.findById(id);
        if (!supplier.isPresent()) {
            return ResponseEntity.badRequest().body("Supplier not found!");
        }
        supplierService.deleteById(id);
        return ResponseEntity.ok("Successfull delete!");
    }
}
