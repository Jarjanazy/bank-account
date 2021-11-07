package jalil.demo.bankaccount.api.account.controller;

import jalil.demo.bankaccount.api.account.dto.request.AccountCreationRequest;
import jalil.demo.bankaccount.api.account.dto.response.AccountCreationResponse;
import jalil.demo.bankaccount.api.account.dto.response.AccountQueryResponse;
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
}
