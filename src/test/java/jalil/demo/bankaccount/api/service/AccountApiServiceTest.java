package jalil.demo.bankaccount.api.service;

import jalil.demo.bankaccount.api.accountCreation.dto.request.AccountCreationRequest;
import jalil.demo.bankaccount.api.accountCreation.dto.request.AccountToCreateDto;
import jalil.demo.bankaccount.api.accountCreation.dto.response.AccountCreationResponse;
import jalil.demo.bankaccount.api.accountCreation.service.AccountApiService;
import jalil.demo.bankaccount.domain.account.model.Account;
import jalil.demo.bankaccount.domain.account.repo.IAccountRepository;
import jalil.demo.bankaccount.domain.account.service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AccountApiServiceTest
{

    private AccountApiService accountApiService;

    @Mock
    private IAccountRepository accountRepository;

    @BeforeEach
    public void setup()
    {
        AccountService accountService = new AccountService(accountRepository);
        accountApiService = new AccountApiService(accountService);
    }

    @Test
    public void givenAccountCreationRequest_WhenLimitExists_ThenCreateNewAccount()
    {
        AccountToCreateDto accountToCreateDto = new AccountToCreateDto("testAccount", 123.7f);
        AccountCreationRequest accountCreationRequest = new AccountCreationRequest(accountToCreateDto);

        Account account = new Account(1, "testAccount", 123.7f, LocalDateTime.now());

        when(accountRepository.save(any(Account.class))).thenReturn(account);

        AccountCreationResponse newAccount = accountApiService.createNewAccount(accountCreationRequest);

        assertThat(newAccount.getAccount().getName()).isEqualTo("testAccount");
        assertThat(newAccount.getAccount().getLimit()).isEqualTo(123.7f);
        assertThat(newAccount.getAccount().getCreatedAt()).isNotNull();
        assertThat(newAccount.getAccount().getId()).isEqualTo(1);
    }
}
