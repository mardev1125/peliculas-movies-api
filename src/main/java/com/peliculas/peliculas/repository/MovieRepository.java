package com.peliculas.peliculas.repository;
import com.peliculas.peliculas.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    List<Movie> findByTitleContainingIgnoreCase(String title);
    List<Movie> findByDirectorContainingIgnoreCase(String director);
    List<Movie> findByReleaseYear(Integer year);
    List<Movie> findByGenreId(Long genreId);
    List<Movie> findByStatus(String status);
}