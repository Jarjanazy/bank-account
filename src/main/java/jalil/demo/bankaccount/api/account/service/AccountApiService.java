package jalil.demo.bankaccount.api.account.service;

import jalil.demo.bankaccount.api.account.dto.AmountDto;
import jalil.demo.bankaccount.api.account.dto.request.AccountCreationRequest;
import jalil.demo.bankaccount.api.account.dto.request.AccountToCreateDto;
import jalil.demo.bankaccount.api.account.dto.request.DepositRequest;
import jalil.demo.bankaccount.api.account.dto.response.AccountCreationResponse;
import jalil.demo.bankaccount.api.account.dto.response.AccountQueryResponse;
import jalil.demo.bankaccount.api.account.dto.response.CreatedAccountDto;
import jalil.demo.bankaccount.api.account.dto.response.DepositResponse;
import jalil.demo.bankaccount.api.common.dto.ErrorResponse;
import jalil.demo.bankaccount.api.common.dto.Response;
import jalil.demo.bankaccount.domain.account.model.Account;
import jalil.demo.bankaccount.domain.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
}
