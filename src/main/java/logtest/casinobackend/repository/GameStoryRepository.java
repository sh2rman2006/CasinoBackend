package logtest.casinobackend.repository;

import logtest.casinobackend.model.GameStory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameStoryRepository extends MongoRepository<GameStory, String> {
    @Query(sort = "{date: -1}")
    List<GameStory> findByAuthUserId(String authUserId);

    @Query(sort = "{date: -1}")
    List<GameStory> findGameStoriesByGameNameAndAuthUserId(String gameName, String authUserId);
}
