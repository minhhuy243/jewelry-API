package com.jewelry.api.security.entity;

import com.jewelry.api.commondata.model.AbstractEntity;
import com.jewelry.api.user.entity.User;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "jewelry_refreshtoken")
public class RefreshToken extends AbstractEntity {

    @NonNull
    @Column(nullable = false, unique = true)
    private String token;

    @NonNull
    @Column(nullable = false)
    private LocalDateTime expiryDate;

    @OneToOne(fetch = FetchType.LAZY)
    private User user;

}
