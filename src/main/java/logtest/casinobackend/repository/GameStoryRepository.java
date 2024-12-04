package logtest.casinobackend.repository;

import logtest.casinobackend.model.GameStory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameStoryRepository extends MongoRepository<GameStory, String> {
    List<GameStory> findByAuthUserId(String authUserId);

}
