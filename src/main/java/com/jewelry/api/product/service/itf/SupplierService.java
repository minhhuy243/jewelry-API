package com.jewelry.api.product.service.itf;

import com.jewelry.api.commondata.GenericService;
import com.jewelry.api.product.dto.supplier.SupplierCreateDto;
import com.jewelry.api.product.dto.supplier.SupplierUpdateDto;
import com.jewelry.api.product.entity.Supplier;

public interface SupplierService extends GenericService<Supplier, Long> {
    Supplier save(SupplierCreateDto dto);

    Supplier updateSupplierInfo(SupplierUpdateDto dto, Long id);
}
