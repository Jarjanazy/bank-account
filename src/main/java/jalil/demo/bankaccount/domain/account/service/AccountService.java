package jalil.demo.bankaccount.domain.account.service;

import jalil.demo.bankaccount.api.accountCreation.dto.response.AccountCreationResponse;
import jalil.demo.bankaccount.domain.account.model.Account;
import jalil.demo.bankaccount.domain.account.repo.IAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AccountService
{
    private final IAccountRepository accountRepository;

    public Account createNewAccount(String name, float limit)
    {
        Account account = Account
                .builder()
                .name(name)
                .limit(limit)
                .createdAt(LocalDateTime.now())
                .build();

        return accountRepository.save(account);
    }
}
