package uz.pdp.config.sercurity;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true,
        jsr250Enabled = true
)
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final UserDetailsServiceImpl userDetailsService;
    private final CustomAuthenticationFailureHandler authenticationFailureHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(final HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(register ->
                        register.requestMatchers("/home/**", "/auth/**").permitAll()
//                                .requestMatchers("/admin/**").hasAnyRole("ADMIN","MANAGER")
//                                .requestMatchers("/users/**").hasAnyRole("USER","ADMIN","MANAGER")
//                                .requestMatchers("/managers/**").hasRole("MANAGER")
                                .anyRequest().authenticated())
                .authenticationProvider(authenticationProvider())
                .formLogin(config -> config
                        .loginPage("/auth/login")
                        .usernameParameter("u_name")
                        .passwordParameter("psd")
                        .defaultSuccessUrl("/home", false)
                        .failureHandler(authenticationFailureHandler))
                .rememberMe(config -> config
                        .rememberMeParameter("remember-me")
                        .rememberMeCookieName("remember-me")
                        .tokenValiditySeconds(3600 * 24)
                        .key("secret_key:CMAOcnaomxoaMXOAMdad12d2XxaAxaXAxxasaMOAMDaSMXOAxoMxoamx120mx")
                        .alwaysRemember(false))
                .logout(config -> config
                        .logoutUrl("/auth/logout")
                        .deleteCookies("JSESSIONID", "remember-me")
                        .clearAuthentication(true)
                        .logoutRequestMatcher(new AntPathRequestMatcher("/auth/logout", "POST")));
        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        final var provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        provider.setHideUserNotFoundExceptions(false);
        return provider;
    }

    /*@Bean
    public UserDetailsService userDetails(){
        UserDetails userDetails = User.builder()
                .username("admin")
                .password("ASDasdsacOMC2dxpas")
                .roles("ADMIN")
                .build();
        UserDetails userDetails2 = User.builder()
                .username("simple")
                .password("ASDasdsacOMC2dxpas")
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(userDetails, userDetails2);
    }*/

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
