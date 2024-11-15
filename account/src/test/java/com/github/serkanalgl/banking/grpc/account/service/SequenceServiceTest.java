package com.github.serkanalgl.banking.grpc.account.service;

import com.github.serkanalgl.banking.grpc.account.model.entity.Sequence;
import com.github.serkanalgl.banking.grpc.account.repository.SequenceRepository;
import com.github.serkanalgl.banking.grpc.account.service.impl.SequenceServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SequenceServiceTest {

    @Mock
    SequenceRepository sequenceRepository;

    @InjectMocks
    SequenceServiceImpl sequenceService;

    @Test
    void testCreateSequenceIfNoSequenceFound(){

        Sequence sequence = Sequence.builder()
                .accountNumber(1)
                .build();

        Sequence savedSequence = Sequence.builder()
            .sequenceId(1)
            .accountNumber(1)
            .build();

        when(sequenceRepository.findById(1L)).thenReturn(Optional.empty());
        when(sequenceRepository.save(sequence)).thenReturn(savedSequence);

        Sequence createdSequence = sequenceService.create();

        verify(sequenceRepository, times(1)).save(sequence);
        Assertions.assertEquals(createdSequence.getSequenceId(), 1);
        Assertions.assertEquals(createdSequence.getAccountNumber(), 1);

    }

    @Test
    void testCreateSequenceIfAlreadyCreatedBefore(){

        Sequence sequence = Sequence.builder()
                .sequenceId(1)
                .accountNumber(1)
                .build();

        Sequence updatedSequence = Sequence.builder()
                .sequenceId(1)
                .accountNumber(2)
                .build();

        when(sequenceRepository.findById(1L)).thenReturn(Optional.of(sequence));
        when(sequenceRepository.save(sequence)).thenReturn(updatedSequence);

        Sequence createdSequence = sequenceService.create();

        verify(sequenceRepository, times(1)).save(updatedSequence);
        Assertions.assertEquals(createdSequence.getSequenceId(), 1);
        Assertions.assertEquals(createdSequence.getAccountNumber(), 2);

    }

}
