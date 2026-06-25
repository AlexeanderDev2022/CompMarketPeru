package com.autodoc.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.autodoc.entity.Categoria;
import com.autodoc.entity.Cliente;
import com.autodoc.entity.Persona;
import com.autodoc.entity.Producto;
import com.autodoc.entity.Rol;
import com.autodoc.repository.CategoriaRepository;
import com.autodoc.repository.ClienteRepository;
import com.autodoc.repository.PersonaRepository;
import com.autodoc.repository.ProductoRepository;
import com.autodoc.repository.RolRepository;

/**
 * Registra conversores String -> Entidad para que los <select> de los
 * formularios (que envían solo el id) se puedan enlazar directamente a
 * los objetos de las entidades relacionadas (Categoria, Cliente, Producto,
 * Rol, Persona) al hacer el binding del @ModelAttribute.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired private CategoriaRepository categoriaRepository;
    @Autowired private ClienteRepository clienteRepository;
    @Autowired private ProductoRepository productoRepository;
    @Autowired private RolRepository rolRepository;
    @Autowired private PersonaRepository personaRepository;
   

    @Override
    public void addFormatters(FormatterRegistry registry) {

        registry.addConverter(new Converter<String, Categoria>() {
            @Override
            public Categoria convert(String source) {
                if (source == null || source.isBlank()) return null;
                return categoriaRepository.findById(Integer.valueOf(source)).orElse(null);
            }
        });

        registry.addConverter(new Converter<String, Cliente>() {
            @Override
            public Cliente convert(String source) {
                if (source == null || source.isBlank()) return null;
                return clienteRepository.findById(Integer.valueOf(source)).orElse(null);
            }
        });

        registry.addConverter(new Converter<String, Producto>() {
            @Override
            public Producto convert(String source) {
                if (source == null || source.isBlank()) return null;
                return productoRepository.findById(Integer.valueOf(source)).orElse(null);
            }
        });

        registry.addConverter(new Converter<String, Rol>() {
            @Override
            public Rol convert(String source) {
                if (source == null || source.isBlank()) return null;
                return rolRepository.findById(Integer.valueOf(source)).orElse(null);
            }
        });

        registry.addConverter(new Converter<String, Persona>() {
            @Override
            public Persona convert(String source) {
                if (source == null || source.isBlank()) return null;
                return personaRepository.findById(Integer.valueOf(source)).orElse(null);
            }
        });
    }
}
