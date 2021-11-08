package jalil.demo.bankaccount.api.account.dto.response;

import jalil.demo.bankaccount.api.account.dto.AmountDto;
import jalil.demo.bankaccount.api.common.dto.Response;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class BalanceResponse implements Response
{
    private AmountDto balance;
}
