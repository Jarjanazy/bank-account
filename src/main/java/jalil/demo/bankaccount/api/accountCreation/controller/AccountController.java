package jalil.demo.bankaccount.api.accountCreation.controller;

import jalil.demo.bankaccount.api.accountCreation.dto.request.AccountCreationRequest;
import jalil.demo.bankaccount.api.accountCreation.dto.request.AccountToCreateDto;
import jalil.demo.bankaccount.api.accountCreation.dto.response.AccountCreationResponse;
import jalil.demo.bankaccount.api.accountCreation.service.AccountApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AccountController
{
    private final AccountApiService accountApiService;

    @PostMapping("/accounts")
    public ResponseEntity<AccountCreationResponse> createAccount(@RequestBody AccountCreationRequest accountCreationRequest)
    {
        AccountCreationResponse newAccount = accountApiService.createNewAccount(accountCreationRequest);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(newAccount);
    }
}
