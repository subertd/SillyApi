package subertd.assignment03;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CelebrityRepository extends CrudRepository<Celebrity, String> {

    Celebrity findOneByImdbId(String imdbId);

    List<Celebrity> findByName(String name);
}