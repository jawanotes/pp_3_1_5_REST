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
import ru.kata.spring.boot_security.demo.service.UserService;

//@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
/*    The prePostEnabled property enables Spring Security pre/post annotations.
      The securedEnabled property determines if the @Secured annotation should be enabled.
      The jsr250Enabled property allows us to use the @RoleAllowed annotation.*/
@Configuration
@EnableWebSecurity//(debug = true)
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final SuccessUserHandler successUserHandler;
    private final PasswordEncoder passwordEncoder;

    private final UserService userService;

    public WebSecurityConfig(SuccessUserHandler successUserHandler,
                             PasswordEncoder passwordEncoder,
                             //@Qualifier("UserServiceJpaImpl")
                             UserService userService) {
        this.successUserHandler = successUserHandler;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    /**
     * Nikita Nesterenko: админ должен иметь доступ к юзеру, поменять местами,
     *                      от более открытого к более закрытому вниз
     *
     * Konstantin Harin: админ и так имел доступ к юзеру, я проверял
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                //.antMatchers("/user/**").hasAnyRole("USER", "ADMIN")
                .antMatchers("/user/**").authenticated()
                .and()
                .authorizeRequests()
                .antMatchers("/admin/**").hasRole("ADMIN")
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
        daoAuthenticationProvider.setUserDetailsService(userService);
        return daoAuthenticationProvider;
    }
    /*@Autowired
    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder);
    }*/
}