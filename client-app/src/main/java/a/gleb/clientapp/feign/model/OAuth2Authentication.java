package a.gleb.clientapp.feign.model;

import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Collections;

@AllArgsConstructor
public class OAuth2Authentication implements Authentication {

    private final String registrationId;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptySet();
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return this;
    }

    @Override
    public boolean isAuthenticated() {
        return false;
    }

    @Override
    public void setAuthenticated(boolean b) throws IllegalArgumentException {
    }

    /**
     * Получить имя principal
     *
     * @return заглушку для OAuth2, иначе будет {@link IllegalArgumentException}. Можно вернуть любое значение
     */
    @Override
    public String getName() {
        return registrationId;
    }
}
