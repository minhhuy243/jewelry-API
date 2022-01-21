package springboot.jewelry.api.commondata;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        SecurityContext currentSecurityContext = SecurityContextHolder.getContext();
        String currentUsername = currentSecurityContext.getAuthentication().getName();
        return Optional.ofNullable(currentUsername);
    }
}
