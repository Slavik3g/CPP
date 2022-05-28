package com.example.shift;

import com.example.shift.entity.ShiftData;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface Reposutory  extends MongoRepository<ShiftData, String> {
}
