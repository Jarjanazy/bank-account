package jalil.demo.bankaccount.api.account.dto.response;

import jalil.demo.bankaccount.api.common.dto.Response;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AccountQueryResponse implements Response
{
    private CreatedAccountDto account;
}
