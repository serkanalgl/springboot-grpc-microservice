package com.github.serkanalgl.banking.grpc.account.repository;

import com.github.serkanalgl.banking.grpc.account.model.entity.Sequence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SequenceRepository extends JpaRepository<Sequence, Long> {
}
