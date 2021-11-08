package jalil.demo.bankaccount.api.service;

import jalil.demo.bankaccount.api.account.dto.AmountDto;
import jalil.demo.bankaccount.api.account.dto.request.DepositRequest;
import jalil.demo.bankaccount.api.account.dto.request.WithdrawlRequest;
import jalil.demo.bankaccount.api.account.dto.response.transaction.TransactionResponse;
import jalil.demo.bankaccount.api.account.dto.response.transaction.TransactionsResponse;
import jalil.demo.bankaccount.api.account.service.AccountApiService;
import jalil.demo.bankaccount.api.common.dto.Response;
import jalil.demo.bankaccount.domain.account.enums.TransactionType;
import jalil.demo.bankaccount.domain.account.model.Account;
import jalil.demo.bankaccount.domain.account.repo.IAccountRepository;
import jalil.demo.bankaccount.domain.account.repo.ITransactionRepository;
import jalil.demo.bankaccount.domain.account.service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class AccountTransactionTest
{
    @Autowired
    IAccountRepository accountRepository;

    @Autowired
    ITransactionRepository transactionRepository;

    private AccountApiService accountApiService;

    @BeforeEach
    public void setup()
    {
        AccountService accountService = new AccountService(accountRepository, transactionRepository);
        accountApiService = new AccountApiService(accountService);
    }

    @Test
    public void given2DepositsAnd1WithdrawAreMade_WhenTransactionsAreRequested_ReturnThem()
    {
        Account account = Account.builder().name("accountName").limit(20f).createdAt(LocalDateTime.now()).build();
        accountRepository.save(account);

        accountApiService.deposit(account.getId(), new DepositRequest(new AmountDto(2f)));

        accountApiService.withdrawl(account.getId(), new WithdrawlRequest(new AmountDto(3f)));

        accountApiService.deposit(account.getId(), new DepositRequest(new AmountDto(5f)));

        TransactionsResponse transactions = (TransactionsResponse) accountApiService.getTransactions(account.getId()).getBody();

        List<TransactionResponse> transactionsFromDB = transactions.getTransactions();

        assertThat(transactionsFromDB).hasSize(3);
        assertThat(account.getLimit()).isEqualTo(24f);

        assertThat(transactionsFromDB.get(0).getType()).isEqualTo(TransactionType.DEPOSIT.toString());
        assertThat(transactionsFromDB.get(0).getAmount()).isEqualTo(2f);

        assertThat(transactionsFromDB.get(1).getType()).isEqualTo(TransactionType.WITHDRAW.toString());
        assertThat(transactionsFromDB.get(1).getAmount()).isEqualTo(3f);

        assertThat(transactionsFromDB.get(2).getType()).isEqualTo(TransactionType.DEPOSIT.toString());
        assertThat(transactionsFromDB.get(2).getAmount()).isEqualTo(5f);
    }

}
