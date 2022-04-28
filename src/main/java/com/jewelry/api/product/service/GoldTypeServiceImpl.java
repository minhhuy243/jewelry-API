package com.jewelry.api.product.service;

import com.jewelry.api.commondata.GenericServiceImpl;
import com.jewelry.api.product.dto.goldtype.GoldTypeCreateDto;
import com.jewelry.api.product.entity.GoldType;
import com.jewelry.api.product.repository.GoldTypeRepository;
import com.jewelry.api.product.service.itf.GoldTypeService;
import com.jewelry.api.util.MapDtoToModel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class GoldTypeServiceImpl extends GenericServiceImpl<GoldType, Long> implements GoldTypeService {

    private GoldTypeRepository goldTypeRepository;
    private MapDtoToModel<Object, GoldType> mapper;

    @Override
    public GoldType save(GoldTypeCreateDto dto) {
        GoldType newGoldType = new GoldType();
        newGoldType = mapper.map(dto, newGoldType);
        return goldTypeRepository.save(newGoldType);
    }

}
