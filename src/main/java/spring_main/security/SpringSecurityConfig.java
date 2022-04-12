package spring_main.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import spring_main.security.jwt.JwtSecurityConfigurer;
import spring_main.security.jwt.JwtTokenProvider;

@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic().disable()
                .csrf().disable()
                .formLogin().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) //не нужно создавать сессию тк храним пользователя по токену
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST,"/company/auth/signIn").permitAll()
                .antMatchers(HttpMethod.GET,"/company/goods").permitAll()
                .antMatchers(HttpMethod.GET,"/company/goods/byId={id}").permitAll()
                .antMatchers(HttpMethod.GET,"/company/goods/byName={name}").permitAll()
                .antMatchers(HttpMethod.POST,"/company/goods/createGood").hasAnyRole("ADMIN")

                .antMatchers(HttpMethod.GET, "/company/sales").hasAnyRole("ADMIN", "USER")
                .antMatchers(HttpMethod.GET, "/company/sales/salesAndGoods").hasAnyRole("ADMIN", "USER")
                .antMatchers(HttpMethod.GET, "/company/sales/byGoodId={good_id}").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/company/sales/byDate={date}").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/company/sales/byId={id}").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/company/sales/createSale").hasAnyRole("ADMIN", "USER")
                .antMatchers(HttpMethod.PUT, "/company/sales/deleteAll").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/company/sales/updateSaleCount").hasAnyRole("ADMIN", "USER")
                .antMatchers(HttpMethod.PUT, "/company/sales/deleteById={id}").hasRole("ADMIN")

                .antMatchers(HttpMethod.GET, "/company/warehouse1").permitAll()
                .antMatchers(HttpMethod.GET, "/company/warehouse1/byGoodId={good_id}").permitAll()
                .antMatchers(HttpMethod.POST, "/company/warehouse1/addGoodToWarehouse1").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/company/warehouse1/deleteAll").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/company/warehouse1/updateWarehouse1Count").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/company/warehouse1/deleteByGoodId={good_id}").hasRole("ADMIN")

                .antMatchers(HttpMethod.GET, "/company/warehouse2").permitAll()
                .antMatchers(HttpMethod.GET, "/company/warehouse2/byGoodId={good_id}").permitAll()
                .antMatchers(HttpMethod.POST, "/company/warehouse2/addGoodToWarehouse2").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/company/warehouse2/deleteAll").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/company/warehouse2/updateWarehouse2Count").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/company/warehouse2/deleteByGoodId={good_id}").hasRole("ADMIN")

                .anyRequest().authenticated()
                .and()

                .apply(new JwtSecurityConfigurer(jwtTokenProvider));
    }
}
