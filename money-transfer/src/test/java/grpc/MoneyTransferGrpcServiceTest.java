package grpc;

import com.github.serkanalgl.banking.grpc.account.AccountResponse;
import com.github.serkanalgl.banking.grpc.account.AccountStatus;
import com.github.serkanalgl.banking.grpc.account.CreateAccountRequest;
import com.github.serkanalgl.banking.grpc.account.GetAccountRequest;
import com.github.serkanalgl.banking.grpc.moneytransfer.MoneyTransferRequest;
import com.github.serkanalgl.banking.grpc.moneytransfer.MoneyTransferResponse;
import com.github.serkanalgl.banking.grpc.moneytransfer.grpc.MoneyTransferGrpcService;
import com.github.serkanalgl.banking.grpc.moneytransfer.service.MoneyTransferService;
import io.grpc.stub.StreamObserver;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.UUID;

import static com.github.serkanalgl.banking.grpc.account.AccountType.FIXED_DEPOSIT;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MoneyTransferGrpcServiceTest {

    @Mock
    MoneyTransferService moneyTransferService;

    @InjectMocks
    MoneyTransferGrpcService moneyTransferGrpcService;

    @Test
    void testTransferSuccess(){

        MoneyTransferRequest moneyTransferRequest = MoneyTransferRequest.newBuilder()
                .setFromAccount("70007001")
                .setToAccount("70007002")
                .setAmount(10)
                .build();

        MoneyTransferResponse moneyTransferResponse = MoneyTransferResponse.newBuilder()
                .setFromAccount("70007001")
                .setToAccount("70007002")
                .setAmount(10)
                .setTransactionReference(UUID.randomUUID().toString())
                .setTransferAt(Instant.now().getEpochSecond())
                .build();

        when(moneyTransferService.transfer(moneyTransferRequest)).thenReturn(moneyTransferResponse);

        StreamObserver<MoneyTransferResponse> observer = mock(StreamObserver.class);

        moneyTransferGrpcService.transfer(moneyTransferRequest, observer);

        verify(moneyTransferService, times(1)).transfer(moneyTransferRequest);
        verify(observer, times(1)).onNext(moneyTransferResponse);
        verify(observer, times(1)).onCompleted();


    }

}
