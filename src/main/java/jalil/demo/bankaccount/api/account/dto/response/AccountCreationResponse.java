package jalil.demo.bankaccount.api.account.dto.response;

import jalil.demo.bankaccount.api.common.dto.Response;
import jalil.demo.bankaccount.domain.account.model.Account;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AccountCreationResponse implements Response
{
    private CreatedAccountDto account;

    public static AccountCreationResponse fromAccount(Account account)
    {
        return new AccountCreationResponse(CreatedAccountDto.fromAccount(account));
    }
}
