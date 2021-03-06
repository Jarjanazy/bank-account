package jalil.demo.bankaccount.api.account.service;

import jalil.demo.bankaccount.api.account.dto.AmountDto;
import jalil.demo.bankaccount.api.account.dto.request.AccountCreationRequest;
import jalil.demo.bankaccount.api.account.dto.request.AccountToCreateDto;
import jalil.demo.bankaccount.api.account.dto.request.DepositRequest;
import jalil.demo.bankaccount.api.account.dto.request.WithdrawlRequest;
import jalil.demo.bankaccount.api.account.dto.response.*;
import jalil.demo.bankaccount.api.account.dto.response.transaction.TransactionsResponse;
import jalil.demo.bankaccount.api.common.dto.ErrorResponse;
import jalil.demo.bankaccount.api.common.dto.Response;
import jalil.demo.bankaccount.domain.account.model.Account;
import jalil.demo.bankaccount.domain.account.model.Transaction;
import jalil.demo.bankaccount.domain.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public ResponseEntity<Response> getAccountById(int id)
    {
        Optional<Account> account = accountService.findById(id);

        if (account.isPresent())
        {
            CreatedAccountDto createdAccountDto = CreatedAccountDto.fromAccount(account.get());

            AccountQueryResponse accountQueryResponse = new AccountQueryResponse(createdAccountDto);

            return ResponseEntity.ok(accountQueryResponse);
        }
        else return createAccountNotFoundResponse(id);
    }

    public ResponseEntity<Response> deposit(int accountId, DepositRequest depositRequest)
    {
        Optional<Account> account = accountService.findById(accountId);

        if (account.isPresent())
        {
            float amount = accountService.deposit(account.get(), depositRequest.getDeposit().getAmount());

            return ResponseEntity.ok(new DepositResponse(new AmountDto(amount)));
        }

        else return createAccountNotFoundResponse(accountId);
    }

    private ResponseEntity<Response> createAccountNotFoundResponse(int id)
    {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(HttpStatus.NOT_FOUND.value(),
                        String.format("The account of id %s wasn't found", id)));
    }

    public ResponseEntity<Response> withdrawl(int accountId, WithdrawlRequest withdrawlRequest)
    {
        Optional<Account> account = accountService.findById(accountId);

        if (account.isPresent())
        {
            try
            {
                float amount =  accountService.withdrawl(account.get(), withdrawlRequest);

                return ResponseEntity.ok(new WithdrawalResponse(new AmountDto(amount)));

            } catch (RuntimeException e)
            {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Limit is not enough"));
            }
        }

        else return createAccountNotFoundResponse(accountId);
    }

    public ResponseEntity<Response> getBalance(int accountId)
    {
        Float balance = accountService
                .findById(accountId)
                .map(Account::getLimit)
                .orElseThrow(RuntimeException::new);

        return ResponseEntity.ok(new BalanceResponse(new AmountDto(balance)));
    }

    public ResponseEntity<Response> getTransactions(int accountId)
    {
        List<Transaction> transactions = accountService.getTransactions(accountId);

        return ResponseEntity
                .ok()
                .body(TransactionsResponse.fromTransactions(transactions));
    }
}
