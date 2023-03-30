package ru.kata.spring.boot_security.demo.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.kata.spring.boot_security.demo.service.LoadUserService;

@Configuration
@EnableWebSecurity//(debug = true)
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final SuccessUserHandler successUserHandler;
    private final PasswordEncoder passwordEncoder;
    private final LoadUserService loadUserService;

    public WebSecurityConfig(SuccessUserHandler successUserHandler,
                             PasswordEncoder passwordEncoder,
                             LoadUserService loadUserService
                            ) {
        this.successUserHandler = successUserHandler;
        this.passwordEncoder = passwordEncoder;
        this.loadUserService = loadUserService;
    }

    /**
     * Nikita Nesterenko: админ должен иметь доступ к юзеру, поменять местами,
     *                      от более открытого к более закрытому вниз
     *
     * Konstantin Harin: админ и так имел доступ к юзеру, я проверял
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /**
         * ну для твоего случая сработает
         * но лучше раскоментить строку и убрать аутификейтед
         * захочешь добавлять новые роли и будет трэш
         */
        http
                .cors().and().csrf().disable()
                .authorizeRequests()
                .antMatchers("/user/**").hasAnyRole("USER", "ADMIN")
                //.antMatchers("/user/**").authenticated()
                .and()
                .authorizeRequests()
                .antMatchers("/admin/**", "/api/**").hasRole("ADMIN")
                .and()
                .authorizeRequests()
                .antMatchers("/", "/index").permitAll()
                //.anyRequest().authenticated()
                .and()
                .formLogin().successHandler(successUserHandler)
                .permitAll()
                .and()
                .logout()
                .logoutSuccessUrl("/")
                .permitAll();
    }

    // аутентификация inMemory
/*    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        UserDetails user =
                User.withDefaultPasswordEncoder()
                        .username("user")
                        .password("user")
                        .roles("USER")
                        .build();

        return new InMemoryUserDetailsManager(user);
    }*/
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    protected DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        daoAuthenticationProvider.setUserDetailsService(loadUserService);
        return daoAuthenticationProvider;
    }

    /**
     * лоад юзер для секьюрити вынеси в отдельный сервис и используй его в конфиге
     * + оставь один сервис ( который  jpa ) у тебя, больше быть не должно
     */
    @Bean(name = "loadUserServiceProvider")
    protected LoadUserService loadUserServiceProvider() {
        return new LoadUserService();
    }
}