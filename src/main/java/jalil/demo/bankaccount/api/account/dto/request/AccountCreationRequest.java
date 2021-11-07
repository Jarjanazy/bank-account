package jalil.demo.bankaccount.api.account.dto.request;

import jalil.demo.bankaccount.api.common.dto.Response;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AccountCreationRequest implements Response
{
    private AccountToCreateDto account;
}
