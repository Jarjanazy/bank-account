package jalil.demo.bankaccount.domain.account.service;

import jalil.demo.bankaccount.api.account.dto.request.WithdrawlRequest;
import jalil.demo.bankaccount.api.common.dto.Response;
import jalil.demo.bankaccount.domain.account.enums.TransactionType;
import jalil.demo.bankaccount.domain.account.model.Account;
import jalil.demo.bankaccount.domain.account.model.Transaction;
import jalil.demo.bankaccount.domain.account.repo.IAccountRepository;
import jalil.demo.bankaccount.domain.account.repo.ITransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountService
{
    private final IAccountRepository accountRepository;

    private final ITransactionRepository transactionRepository;

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

        Transaction transaction = createTransaction(account, amount, TransactionType.DEPOSIT);
        transactionRepository.save(transaction);

        return account.getLimit();
    }

    public float withdrawl(Account account, WithdrawlRequest withdrawlRequest)
    {
        float amount = withdrawlRequest.getWithdrawal().getAmount();

        if (account.getLimit() < amount)
            throw new RuntimeException();

        account.setLimit(account.getLimit() - amount);

        accountRepository.save(account);

        Transaction transaction = createTransaction(account, amount, TransactionType.WITHDRAW);
        transactionRepository.save(transaction);

        return account.getLimit();
    }

    public List<Transaction> getTransactions(int accountId)
    {
        Optional<Account> account = accountRepository.findById(accountId);

        if (account.isPresent())
        {
            return transactionRepository.findAllByAccount(account.get());
        }
        else throw new RuntimeException();
    }

    private Transaction createTransaction(Account account, float amount, TransactionType transactionType)
    {
        return Transaction
                .builder()
                .account(account)
                .type(transactionType.toString())
                .amount(amount)
                .build();
    }
}
