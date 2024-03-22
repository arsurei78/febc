package egovframework.web.repository.comm;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;


/**
 * 읽기전용 Repository
 * @param <T>
 * @param <L>
 */
@NoRepositoryBean
public interface BaseReadOnlyRepository<T, L> extends Repository<T, L> {
    Optional<T> findById(L id);
    List<T> findAll();
    boolean existsById(L id);
    long count();

}
