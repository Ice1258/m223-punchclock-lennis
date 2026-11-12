package ch.zli.m223.service;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import ch.zli.m223.model.ApplicationUser;
import ch.zli.m223.repository.ApplicationUserRepository;
import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

/**
 * Legt beim Start Test-User an (nur im Dev-Profil aktiviert, siehe application.properties).
 * Die Passwörter werden mit BCrypt gehasht gespeichert.
 */
@ApplicationScoped
public class UserDataLoader {

    @ConfigProperty(name = "punchclock.demo-users.enabled", defaultValue = "false")
    boolean enabled;

    @Inject
    ApplicationUserRepository userRepository;

    void start(@Observes StartupEvent event) {
        if (enabled) {
            seed();
        }
    }

    @Transactional
    public void seed() {
        // Bereits vorhandene User nicht erneut anlegen
        if (userRepository.count() > 0) {
            return;
        }

        createUser("admin@punchclock.ch", "admin123", "Admin");
        createUser("user@punchclock.ch", "user123", "User");
    }

    private void createUser(String username, String plainPassword, String role) {
        ApplicationUser user = new ApplicationUser();
        user.setUsername(username);
        user.setPassword(BcryptUtil.bcryptHash(plainPassword));
        user.setRole(role);
        userRepository.persist(user);
    }
}
