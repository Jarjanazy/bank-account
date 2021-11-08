package jalil.demo.bankaccount.api.service;

import jalil.demo.bankaccount.api.account.dto.AmountDto;
import jalil.demo.bankaccount.api.account.dto.request.AccountCreationRequest;
import jalil.demo.bankaccount.api.account.dto.request.AccountToCreateDto;
import jalil.demo.bankaccount.api.account.dto.request.DepositRequest;
import jalil.demo.bankaccount.api.account.dto.request.WithdrawlRequest;
import jalil.demo.bankaccount.api.account.dto.response.*;
import jalil.demo.bankaccount.api.account.service.AccountApiService;
import jalil.demo.bankaccount.api.common.dto.ErrorResponse;
import jalil.demo.bankaccount.api.common.dto.Response;
import jalil.demo.bankaccount.domain.account.model.Account;
import jalil.demo.bankaccount.domain.account.repo.IAccountRepository;
import jalil.demo.bankaccount.domain.account.service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
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

    @Test
    public void givenAccountId_WhenIsFound_ThenReturnIt()
    {
        Account account = new Account(2, "testName", 13.1f, LocalDateTime.now());

        when(accountRepository.findById(2)).thenReturn(Optional.of(account));

        AccountQueryResponse response = (AccountQueryResponse) accountApiService.getAccountById(2).getBody();

        assertThat(response.getAccount().getName()).isEqualTo("testName");
        assertThat(response.getAccount().getId()).isEqualTo(2);
        assertThat(response.getAccount().getLimit()).isEqualTo(13.1f);
        assertThat(response.getAccount().getCreatedAt()).isNotNull();
    }

    @Test
    public void givenAccountId_WhenNotFound_ThenReturnErrorResponse()
    {
        when(accountRepository.findById(3)).thenReturn(Optional.empty());

        ErrorResponse response = (ErrorResponse) accountApiService.getAccountById(3).getBody();

        assertThat(response.getErrorCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(response.getErrorMessage()).isEqualTo("The account of id 3 wasn't found");
    }

    @Test
    public void givenAccountIdAndDeposit_WhenAccountNotFound_ThenReturnErrorResponse()
    {
        when(accountRepository.findById(5)).thenReturn(Optional.empty());

        ErrorResponse response = (ErrorResponse) accountApiService.deposit(5, new DepositRequest()).getBody();

        assertThat(response.getErrorCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(response.getErrorMessage()).isEqualTo("The account of id 5 wasn't found");
    }

    @Test
    public void givenAccountIdAndDeposit_WhenAccountIsFound_ThenDeposit()
    {
        Account account = Account.builder().limit(2f).build();

        DepositRequest depositRequest = new DepositRequest(new AmountDto(9f));

        when(accountRepository.findById(7)).thenReturn(Optional.of(account));

        DepositResponse response = (DepositResponse) accountApiService.deposit(7, depositRequest).getBody();

        assertThat(response.getBalance().getAmount()).isEqualTo(11f);
    }

    @Test
    public void givenAccountIdAndWithdrawl_WhenAmountIsBeyondLimit_ThenReturnErrorResponse()
    {
        Account account = Account.builder().limit(9f).build();

        WithdrawlRequest withdrawlRequest = new WithdrawlRequest(new AmountDto(10f));

        when(accountRepository.findById(2)).thenReturn(Optional.of(account));

        ErrorResponse errorResponse = (ErrorResponse) accountApiService.withdrawl(2, withdrawlRequest).getBody();

        assertThat(errorResponse.getErrorCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(errorResponse.getErrorMessage()).isEqualTo("Limit is not enough");
    }

    @Test
    public void givenAccountIdAndWithdrawl_WhenAmountIsEnough_ThenWithdraw()
    {
        Account account = Account.builder().limit(11f).build();

        WithdrawlRequest withdrawlRequest = new WithdrawlRequest(new AmountDto(10f));

        when(accountRepository.findById(2)).thenReturn(Optional.of(account));

        WithdrawalResponse withdrawalResponse = (WithdrawalResponse) accountApiService.withdrawl(2, withdrawlRequest).getBody();

        assertThat(withdrawalResponse.getBalance().getAmount()).isEqualTo(1f);
    }

    @Test
    public void givenAccountId_WhenBalanceIsRequested_ThenReturnIt()
    {
        Account account = Account.builder().limit(22.5f).build();

        when(accountRepository.findById(11)).thenReturn(Optional.of(account));

       BalanceResponse balance = (BalanceResponse) accountApiService.getBalance(11).getBody();

       assertThat(balance.getBalance().getAmount()).isEqualTo(22.5f);
    }
}
