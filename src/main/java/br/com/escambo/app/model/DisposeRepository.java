package br.com.escambo.app.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.escambo.app.model.entities.Dispose;

public interface DisposeRepository extends JpaRepository<Dispose, Long> {
    List<Dispose> findByItemId(Long itemId);

    List<Dispose> findByUserUsernameNotAndItemItemnameContainingIgnoreCase(String username, String itemname);
    List<Dispose> findByUserUsernameNotAndItemCategoryContainingIgnoreCase(String username, String category);
    List<Dispose> findByUserUsernameNotAndItemVolumeContainingIgnoreCase(String username, String volume);
    List<Dispose> findByUserUsernameNotAndItemAuthorContainingIgnoreCase(String username, String author);
}
