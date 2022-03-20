package com.jewelry.api.product.controller.admin;

import com.jewelry.api.product.dto.goldtype.GoldTypeCreateDto;
import com.jewelry.api.product.entity.GoldType;
import com.jewelry.api.product.projection.goldtype.GoldTypeProjection;
import com.jewelry.api.product.service.itf.GoldTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin/gold-types")
public class AdminGoldTypeController {

    @Autowired
    private GoldTypeService goldTypeService;

    @GetMapping("")
    public ResponseEntity<Object> findAll() {

        List<GoldTypeProjection> goldTypeProjections = goldTypeService.findAllBy(GoldTypeProjection.class);
        if (goldTypeProjections.isEmpty()) {
            return ResponseEntity.ok("Empty list!");
        }
        return ResponseEntity.ok(goldTypeProjections);
    }

    @PostMapping("")
    public ResponseEntity<Object> addGoldType(@Valid @RequestBody GoldTypeCreateDto dto,
                                              BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult);
        }

        GoldType newGoldType = goldTypeService.save(dto);
        return ResponseEntity.ok(newGoldType);
    }

    @DeleteMapping("/{gold-type-id}")
    public ResponseEntity<Object> deleteGoldType(@PathVariable("gold-type-id") Long id) {

        Optional<GoldType> goldType = goldTypeService.findById(id);
        if (!goldType.isPresent()) {
            return ResponseEntity.badRequest().body("Gold type not found!");
        }

        goldTypeService.deleteById(id);
        return ResponseEntity.ok("Successful delete!");
    }
}
