package com.bernardoms.miniclip.repository;

import com.bernardoms.miniclip.model.InitEvent;
import org.bson.types.ObjectId;
import org.springframework.data.repository.CrudRepository;

public interface InitEventRepository extends CrudRepository<InitEvent, ObjectId> {
}
