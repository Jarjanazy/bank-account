package jalil.demo.bankaccount.domain.account.service;

import jalil.demo.bankaccount.api.account.dto.request.WithdrawlRequest;
import jalil.demo.bankaccount.api.common.dto.Response;
import jalil.demo.bankaccount.domain.account.model.Account;
import jalil.demo.bankaccount.domain.account.repo.IAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountService
{
    private final IAccountRepository accountRepository;

    public Optional<Account> findById(int id)
    {
        return accountRepository.findById(id);
    }

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

    public float deposit(Account account, float amount)
    {
        account.setLimit(account.getLimit() + amount);

        accountRepository.save(account);

        return account.getLimit();
    }

    public float withdrawl(Account account, WithdrawlRequest withdrawlRequest)
    {
        float amount = withdrawlRequest.getWithdrawal().getAmount();

        if (account.getLimit() < amount)
            throw new RuntimeException();

        account.setLimit(account.getLimit() - amount);

        accountRepository.save(account);

        return account.getLimit();
    }
}
