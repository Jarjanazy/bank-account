package jalil.demo.bankaccount.api.account.controller;

import jalil.demo.bankaccount.api.account.dto.request.AccountCreationRequest;
import jalil.demo.bankaccount.api.account.dto.request.DepositRequest;
import jalil.demo.bankaccount.api.account.dto.request.WithdrawlRequest;
import jalil.demo.bankaccount.api.account.dto.response.AccountCreationResponse;
import jalil.demo.bankaccount.api.account.service.AccountApiService;
import jalil.demo.bankaccount.api.common.dto.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AccountController
{
    private final AccountApiService accountApiService;

    @PostMapping("/accounts")
    public ResponseEntity<Response> createAccount(@RequestBody AccountCreationRequest accountCreationRequest)
    {
        AccountCreationResponse newAccount = accountApiService.createNewAccount(accountCreationRequest);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(newAccount);
    }

    @GetMapping("/accounts/{id}")
    public ResponseEntity<Response> getAccountById(@PathVariable int id)
    {
        return accountApiService.getAccountById(id);
    }

    @PostMapping("/accounts/{id}/deposit")
    public ResponseEntity<Response> depositToAccount(@PathVariable int id, @RequestBody DepositRequest depositRequest)
    {
        return accountApiService.deposit(id, depositRequest);
    }

    @PostMapping("/accounts/{id}/withdrawal")
    public ResponseEntity<Response> withdrawlFromAccount(@PathVariable int id, @RequestBody WithdrawlRequest withdrawlRequest)
    {
        return accountApiService.withdrawl(id, withdrawlRequest);
    }

    @PostMapping("/accounts/{id}/balance")
    public ResponseEntity<Response> getBalance(@PathVariable int id)
    {
        return accountApiService.getBalance(id);
    }

    @PostMapping("/accounts/{id}/transactions")
    public ResponseEntity<Response> getTransactions(@PathVariable int id)
    {
        return accountApiService.getTransactions(id);
    }
}
