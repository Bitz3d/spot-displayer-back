package pl.rafalab.spotdisplayer.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import pl.rafalab.spotdisplayer.security.old.MySuccessHandler;
import pl.rafalab.spotdisplayer.security.old.RestAuthenticationEntryPoint;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
//@ComponentScan("pl.rafalab.spotdisplayer.security")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private PasswordEncoder encoder;
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;
    private MySuccessHandler mySuccessHandler;
    private SimpleUrlAuthenticationFailureHandler myFailureHandler = new SimpleUrlAuthenticationFailureHandler();


    public SecurityConfig(PasswordEncoder encoder, RestAuthenticationEntryPoint restAuthenticationEntryPoint, MySuccessHandler mySuccessHandler) {
        this.encoder = encoder;
        this.restAuthenticationEntryPoint = restAuthenticationEntryPoint;
        this.mySuccessHandler = mySuccessHandler;
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("admin").password("admin").roles("ADMIN")
                .and()
                .withUser("user").password("user").roles("USER");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.cors().disable();

        http
                .csrf().disable()
                .exceptionHandling()
                .and().httpBasic()
                .authenticationEntryPoint(restAuthenticationEntryPoint)
                .and()
                .authorizeRequests()
                .antMatchers("/").authenticated()
                .antMatchers("/user").hasRole("USER")
                .and()
                .formLogin()
                .successHandler(mySuccessHandler)
                .failureHandler(myFailureHandler)
                .and()
                .logout();
    }


}
