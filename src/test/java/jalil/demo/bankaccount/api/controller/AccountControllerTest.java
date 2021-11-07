package jalil.demo.bankaccount.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jalil.demo.bankaccount.api.account.controller.AccountController;
import jalil.demo.bankaccount.api.account.dto.request.AccountCreationRequest;
import jalil.demo.bankaccount.api.account.dto.request.AccountToCreateDto;
import jalil.demo.bankaccount.api.account.dto.response.AccountQueryResponse;
import jalil.demo.bankaccount.api.account.dto.response.CreatedAccountDto;
import jalil.demo.bankaccount.api.account.service.AccountApiService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import java.time.LocalDateTime;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AccountController.class)
@ContextConfiguration(classes = AccountController.class)
public class AccountControllerTest
{
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AccountApiService accountApiService;

    @Captor
    private ArgumentCaptor<AccountCreationRequest> argumentCaptor;

    @Test
    public void givenAccountInPostRequest_WhenLimitExists_ThenReturn201() throws Exception
    {
        var accountToCreateDto = new AccountToCreateDto("testAccount", 200.5f);

        var accountCreationRequest = new AccountCreationRequest(accountToCreateDto);

        mockMvc
                .perform(post("/accounts")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(accountCreationRequest)))
                .andExpect(status().isCreated());

        verify(accountApiService).createNewAccount(argumentCaptor.capture());

        AccountCreationRequest value = argumentCaptor.getValue();

        assertThat(value.getAccount().getLimit()).isEqualTo(200.5f);
        assertThat(value.getAccount().getName()).isEqualTo("testAccount");
    }

    @Test
    public void givenAccountInPostRequest_WhenLimitDoesntExist_ThenReturn201() throws Exception
    {
        var accountToCreateDto = AccountToCreateDto.builder().name("testAccount").build();

        var accountCreationRequest = new AccountCreationRequest(accountToCreateDto);

        mockMvc
                .perform(post("/accounts")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(accountCreationRequest)))
                .andExpect(status().isCreated());

        verify(accountApiService).createNewAccount(argumentCaptor.capture());

        AccountCreationRequest value = argumentCaptor.getValue();

        assertThat(value.getAccount().getLimit()).isNull();
        assertThat(value.getAccount().getName()).isEqualTo("testAccount");
    }

    @Test
    public void givenAnAccountId_WhenAccountExists_ThenReturnAccount() throws Exception
    {
        CreatedAccountDto dto = new CreatedAccountDto(1, "testName", 20.4f, LocalDateTime.now().toString());
        AccountQueryResponse response = new AccountQueryResponse(dto);

        when(accountApiService.getAccountById(1))
                .thenReturn(ResponseEntity.ok().body(response));

        MvcResult mvcResult = mockMvc.perform(get("/accounts/1"))
                .andExpect(status().isOk())
                .andReturn();

        CreatedAccountDto createdAccountDto = objectMapper
                .readValue(mvcResult.getResponse().getContentAsString(), AccountQueryResponse.class)
                .getAccount();

        assertThat(createdAccountDto.getId()).isEqualTo(1);
        assertThat(createdAccountDto.getName()).isEqualTo("testName");
        assertThat(createdAccountDto.getLimit()).isEqualTo(20.4f);
        assertThat(createdAccountDto.getCreatedAt()).isNotNull();
    }

}
