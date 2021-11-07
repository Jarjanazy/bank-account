package jalil.demo.bankaccount.api.common.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ErrorResponse implements Response
{
    private final int errorCode;
    private final String errorMessage;
}
