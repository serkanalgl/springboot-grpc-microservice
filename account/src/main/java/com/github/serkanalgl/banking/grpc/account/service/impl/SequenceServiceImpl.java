package com.github.serkanalgl.banking.grpc.account.service.impl;

import com.github.serkanalgl.banking.grpc.account.model.entity.Sequence;
import com.github.serkanalgl.banking.grpc.account.repository.SequenceRepository;
import com.github.serkanalgl.banking.grpc.account.service.SequenceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class SequenceServiceImpl implements SequenceService {

    private final SequenceRepository sequenceRepository;

    @Override
    public Sequence create() {
        return sequenceRepository.findById(1L)
                .map(sequence -> {
                    sequence.setAccountNumber(sequence.getAccountNumber() + 1);
                    return sequenceRepository.save(sequence);
                }).orElseGet(() -> sequenceRepository.save(Sequence.builder().accountNumber(1).build()));
    }
}