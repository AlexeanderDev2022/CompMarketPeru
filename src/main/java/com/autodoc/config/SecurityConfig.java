package com.autodoc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Configuración de seguridad de AUTODOC.
 *
 * - Autenticación basada en formulario (login.html) contra la tabla USUARIOS. -
 * Contraseñas cifradas con BCrypt (según informe, apartado 2 "Seguridad"). -
 * Autorización por rol: ADMIN tiene acceso total; VENDEDOR solo a ventas,
 * clientes y consulta de catálogo/stock. - El catálogo público de productos
 * (vitrina digital, módulo "Exhibición de Productos" del informe) queda
 * accesible sin autenticarse. - CSRF habilitado (protección integrada de
 * Spring, mencionada en el informe). - Sesión: una sola sesión activa por
 * usuario, expira por inactividad.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http, UsuarioDetailsService usuarioDetailsService,
			PasswordEncoder passwordEncoder) throws Exception {

		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(usuarioDetailsService);
		provider.setPasswordEncoder(passwordEncoder);

		http.authenticationProvider(provider).authorizeHttpRequests(auth -> auth
				.requestMatchers("/","/error","/app","/app/login", "/css/**", "/js/**", "/img/**", "/webjars/**", "/catalogo", "/catalogo/**")
				.permitAll().requestMatchers("/usuarios/**", "/roles/**", "/personas/**").hasRole("ADMIN")
				.requestMatchers("/reportes/**").hasAnyRole("ADMIN", "VENDEDOR").anyRequest().authenticated())
				.formLogin(form -> form.loginPage("/app/login").loginProcessingUrl("/app/login")
						.defaultSuccessUrl("/app/dashboard", true).failureUrl("/app/login?error").permitAll())
				.logout(logout -> logout.logoutUrl("/app/logout").logoutSuccessUrl("/app/login?logout").permitAll())
				.sessionManagement(
						session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED).maximumSessions(1));

		return http.build();
	}
}
