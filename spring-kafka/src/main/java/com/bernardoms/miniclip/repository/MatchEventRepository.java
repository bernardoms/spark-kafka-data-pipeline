package com.bernardoms.miniclip.repository;

import com.bernardoms.miniclip.model.MatchEvent;
import org.bson.types.ObjectId;
import org.springframework.data.repository.CrudRepository;

public interface MatchEventRepository extends CrudRepository<MatchEvent, ObjectId> {
}
