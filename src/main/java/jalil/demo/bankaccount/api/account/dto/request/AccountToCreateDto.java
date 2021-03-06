package jalil.demo.bankaccount.api.account.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class AccountToCreateDto
{
    private String name;
    private Float limit;
}
