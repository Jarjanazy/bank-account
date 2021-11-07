package jalil.demo.bankaccount.api.account.dto.response;

import jalil.demo.bankaccount.domain.account.model.Account;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CreatedAccountDto
{
    private int id;
    private String name;
    private Float limit;
    private String createdAt;

    public static CreatedAccountDto fromAccount(Account account)
    {
        return new CreatedAccountDto(account.getId(), account.getName(), account.getLimit(), account.getCreatedAt().toString());
    }
}
