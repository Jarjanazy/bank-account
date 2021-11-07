package jalil.demo.bankaccount.api.accountCreation.service;

import jalil.demo.bankaccount.api.accountCreation.dto.request.AccountCreationRequest;
import jalil.demo.bankaccount.api.accountCreation.dto.request.AccountToCreateDto;
import jalil.demo.bankaccount.api.accountCreation.dto.response.AccountCreationResponse;
import jalil.demo.bankaccount.domain.account.model.Account;
import jalil.demo.bankaccount.domain.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountApiService
{
    private final AccountService accountService;

    public AccountCreationResponse createNewAccount(AccountCreationRequest accountCreationRequest)
    {
        AccountToCreateDto account = accountCreationRequest.getAccount();
        float limit = Optional
                .ofNullable(account.getLimit())
                .orElse(200f);

        Account newAccount = accountService.createNewAccount(account.getName(), limit);

        return AccountCreationResponse.fromAccount(newAccount);
    }
}
