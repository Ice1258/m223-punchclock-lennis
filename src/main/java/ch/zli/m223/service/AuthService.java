package ch.zli.m223.service;

import java.time.Duration;
import java.util.Set;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.wildfly.security.password.Password;
import org.wildfly.security.password.PasswordFactory;
import org.wildfly.security.password.WildFlyElytronPasswordProvider;
import org.wildfly.security.password.interfaces.BCryptPassword;
import org.wildfly.security.password.util.ModularCrypt;

import ch.zli.m223.model.ApplicationUser;
import ch.zli.m223.repository.ApplicationUserRepository;
import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotAuthorizedException;

@ApplicationScoped
public class AuthService {

    private static final WildFlyElytronPasswordProvider PASSWORD_PROVIDER = new WildFlyElytronPasswordProvider();

    @Inject
    ApplicationUserRepository userRepository;

    @ConfigProperty(name = "mp.jwt.verify.issuer")
    String issuer;

    /**
     * Prüft Benutzername und Passwort und stellt bei Erfolg ein signiertes JWT aus.
     *
     * @throws NotAuthorizedException (HTTP 401) wenn der User nicht existiert oder das Passwort falsch ist
     */
    public String login(String username, String password) {
        ApplicationUser user = userRepository.findByUsername(username)
                .filter(u -> passwordMatches(password, u.getPassword()))
                // Bewusst dieselbe Meldung für "User unbekannt" und "Passwort falsch",
                // damit man nicht herausfinden kann, welche Benutzernamen existieren.
                .orElseThrow(() -> new NotAuthorizedException("Invalid username or password", "Bearer"));

        return Jwt.upn(user.getUsername())
                .issuer(issuer)
                .groups(Set.of(user.getRole()))
                .expiresIn(Duration.ofHours(1))
                .sign();
    }

    /**
     * Vergleicht ein Klartext-Passwort mit einem gespeicherten BCrypt-Hash.
     */
    private boolean passwordMatches(String plainPassword, String bcryptHash) {
        try {
            PasswordFactory factory = PasswordFactory.getInstance(BCryptPassword.ALGORITHM_BCRYPT, PASSWORD_PROVIDER);
            Password storedPassword = factory.translate(ModularCrypt.decode(bcryptHash));
            return factory.verify(storedPassword, plainPassword.toCharArray());
        } catch (Exception e) {
            // Ungültiger Hash in der DB o.ä. -> Login verweigern
            return false;
        }
    }
}
