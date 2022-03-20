package com.jewelry.api.role.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jewelry.api.commondata.model.AbstractEntity;
import com.jewelry.api.user.entity.User;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "jewelry_role")
public class Role extends AbstractEntity {

    @NonNull
    @NotBlank
    private String name;

    @JsonIgnore
    @ManyToMany(mappedBy = "roles")
    private Set<User> users = new HashSet<>();

}
