package jalil.demo.bankaccount.api.account.dto.response.transaction;

import jalil.demo.bankaccount.api.common.dto.Response;
import jalil.demo.bankaccount.domain.account.model.Transaction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionsResponse implements Response
{
    private List<TransactionResponse> transactions;

    public static TransactionsResponse fromTransactions(List<Transaction> transactions)
    {
        List<TransactionResponse> transactionResponses =
                transactions
                        .stream()
                        .map(TransactionResponse::fromTransaction)
                        .collect(Collectors.toList());

        return new TransactionsResponse(transactionResponses);
    }
}
