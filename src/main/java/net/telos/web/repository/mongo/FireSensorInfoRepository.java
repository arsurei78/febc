package net.telos.web.repository.mongo;

import net.telos.web.repository.mongo.entity.FireSensorInfo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface FireSensorInfoRepository extends MongoRepository<FireSensorInfo, String>
 {

    @Query("{$and: [{'reg_date' : ?0}, {'reg_date': ?1}]}")
    List<FireSensorInfo> getFireSensoList();
}
