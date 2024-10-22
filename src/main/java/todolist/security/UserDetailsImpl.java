package todolist.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import todolist.entity.EmmaUser;

import java.util.*;

@Data
@AllArgsConstructor
public class UserDetailsImpl implements UserDetails {

    private UUID id;
    private String name;
    private String username;

    @JsonIgnore
    private String password;
    private List<SimpleGrantedAuthority> authorities;

    public static UserDetailsImpl build(EmmaUser user) {
        List<SimpleGrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("User"));

        return new UserDetailsImpl(
                user.getId(),
                user.getName(),
                user.getUsername(),
                user.getPassword(),
                authorities
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public int hashCode() {
        return getUsername().hashCode();
    }

    @Override
    public boolean equals(Object object) {
        if (this.equals(object)) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        UserDetailsImpl user = (UserDetailsImpl) object;

        return Objects.equals(id, user.id);
    }
}
