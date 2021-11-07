package jalil.demo.bankaccount.api.accountCreation.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AccountCreationRequest
{
    private AccountToCreateDto account;
}
