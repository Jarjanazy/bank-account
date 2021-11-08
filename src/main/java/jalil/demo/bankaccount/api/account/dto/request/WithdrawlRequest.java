package jalil.demo.bankaccount.api.account.dto.request;

import jalil.demo.bankaccount.api.account.dto.AmountDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class WithdrawlRequest
{
    private AmountDto withdrawal;
}
