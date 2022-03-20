package com.jewelry.api.product.service.itf;

import com.jewelry.api.commondata.GenericService;
import com.jewelry.api.product.dto.goldtype.GoldTypeCreateDto;
import com.jewelry.api.product.entity.GoldType;

public interface GoldTypeService extends GenericService<GoldType, Long> {

    GoldType save(GoldTypeCreateDto dto);
}
