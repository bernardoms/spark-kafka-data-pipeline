package com.bernardoms.miniclip.repository;

import com.bernardoms.miniclip.model.InAppPurchaseEvent;
import org.bson.types.ObjectId;
import org.springframework.data.repository.CrudRepository;

public interface InAppPurchaseEventRepository extends CrudRepository<InAppPurchaseEvent, ObjectId> {
}
