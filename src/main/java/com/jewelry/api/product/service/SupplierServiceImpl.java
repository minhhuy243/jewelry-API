package com.jewelry.api.product.service;

import com.jewelry.api.commondata.GenericServiceImpl;
import com.jewelry.api.product.dto.supplier.SupplierCreateDto;
import com.jewelry.api.product.dto.supplier.SupplierUpdateDto;
import com.jewelry.api.product.entity.Supplier;
import com.jewelry.api.product.repository.SupplierRepository;
import com.jewelry.api.product.service.itf.SupplierService;
import com.jewelry.api.util.MapDtoToModel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class SupplierServiceImpl extends GenericServiceImpl<Supplier, Long> implements SupplierService {

    private SupplierRepository supplierRepository;
    private MapDtoToModel<Object, Supplier> mapper;


    @Override
    public Supplier save(SupplierCreateDto dto) {
        Supplier supplier = new Supplier();
        supplier = mapper.map(dto, supplier);

        return supplierRepository.save(supplier);
    }

    @Override
    public Supplier updateSupplierInfo(SupplierUpdateDto dto, Long id) {
        Supplier supplier = supplierRepository.getOne(id);
        supplier = mapper.map(dto, supplier);

        return supplierRepository.save(supplier);
    }
}
