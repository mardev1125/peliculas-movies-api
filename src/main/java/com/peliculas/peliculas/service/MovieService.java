package com.peliculas.peliculas.service;

import com.peliculas.peliculas.dto.MovieDTO;
import com.peliculas.peliculas.entity.Movie;
import com.peliculas.peliculas.entity.Tag;
import com.peliculas.peliculas.exception.ResourceNotFoundException;
import com.peliculas.peliculas.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    public List<MovieDTO> getAllMovies() {
        return movieRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public MovieDTO getMovieById(Long id) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Película", "id", id));
        return convertToDTO(movie);
    }

    public List<MovieDTO> searchMoviesByTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("El título de búsqueda no puede estar vacío");
        }
        return movieRepository.findByTitleContainingIgnoreCase(title).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Convertir Movie entity a MovieDTO
    private MovieDTO convertToDTO(Movie movie) {
        MovieDTO dto = new MovieDTO();
        dto.setMovieId(movie.getMovieId());
        dto.setSlug(movie.getSlug());
        dto.setTitle(movie.getTitle());
        dto.setDirector(movie.getDirector());
        dto.setReleaseYear(movie.getReleaseYear());
        dto.setDurationMinutes(movie.getDurationMinutes());
        dto.setGenreId(movie.getGenreId());
        dto.setLanguageId(movie.getLanguageId());
        dto.setRentPrice(movie.getRentPrice());
        dto.setBuyPrice(movie.getBuyPrice());
        dto.setCoverUrl(movie.getCoverUrl());
        dto.setSynopsis(movie.getSynopsis());
        dto.setStatus(movie.getStatus());
        dto.setCreatedAt(movie.getCreatedAt());
        dto.setUpdatedAt(movie.getUpdatedAt());

        // Convertir tags a lista de strings
        List<String> tagNames = movie.getTags().stream()
                .map(Tag::getName)
                .collect(Collectors.toList());
        dto.setTags(tagNames);

        return dto;
    }
}