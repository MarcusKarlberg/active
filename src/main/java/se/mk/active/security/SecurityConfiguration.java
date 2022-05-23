package se.mk.active.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
@RequiredArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private static final String LOGIN_URL = "/api/v1/login";
    private final JwtBuilderParser jwtBuilderParser;
    private final UserDetailsService userDetailsService;
    private  final BCryptPasswordEncoder encoder;
    private final SecurityConstants securityConstants;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(encoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        AuthenticationFilter authFilter = new AuthenticationFilter(authenticationManagerBean(),
                jwtBuilderParser, securityConstants);
        authFilter.setFilterProcessesUrl(LOGIN_URL);

        http.authorizeRequests()
                .antMatchers("/api/v1/**").authenticated()
                .antMatchers(LOGIN_URL.concat("/**")).permitAll()
                .antMatchers("*/**").permitAll()
                .and()
                .addFilter(authFilter)
                .addFilter(new AuthorizationFilter(authenticationManagerBean(),
                        jwtBuilderParser, securityConstants))
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);;

        http.csrf().disable();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
