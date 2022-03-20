package com.jewelry.api.commondata;

import com.jewelry.api.commondata.model.SearchCriteria;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Getter
public class SearchSpecification <T> implements Specification<T> {

    @NonNull
    private SearchCriteria searchCriteria;

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        for (String key : searchCriteria.getKeys()) {
            predicates.add(cb.like(
                    cb.lower(root.get(key)),
                    "%" + searchCriteria.getValue().toLowerCase() + "%"));
        }

        return cb.or(predicates.toArray(new Predicate[0]));
    }
}
