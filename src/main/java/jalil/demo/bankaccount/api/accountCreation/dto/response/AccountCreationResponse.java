package jalil.demo.bankaccount.api.accountCreation.dto.response;

import jalil.demo.bankaccount.domain.account.model.Account;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AccountCreationResponse
{
    private CreatedAccountDto account;

    public static AccountCreationResponse fromAccount(Account account)
    {
        return new AccountCreationResponse(CreatedAccountDto.fromAccount(account));
    }
}
