package jalil.demo.bankaccount.api.account.dto.request;

import jalil.demo.bankaccount.api.account.dto.AmountDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class DepositRequest
{
    private AmountDto deposit;
}
