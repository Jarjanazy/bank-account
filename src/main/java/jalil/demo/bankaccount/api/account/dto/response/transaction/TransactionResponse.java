package jalil.demo.bankaccount.api.account.dto.response.transaction;

import jalil.demo.bankaccount.domain.account.model.Transaction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionResponse
{
    private String type;
    private float amount;

    public static TransactionResponse fromTransaction(Transaction transaction)
    {
        return new TransactionResponse(transaction.getType(), transaction.getAmount());
    }
}
