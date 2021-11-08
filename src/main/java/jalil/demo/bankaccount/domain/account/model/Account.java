package jalil.demo.bankaccount.domain.account.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(nullable = false, name = "name")
    private String name;

    @Setter
    @Column(nullable = false, name = "account_limit")
    private Float limit;

    @Column(nullable = false, name = "created_date")
    private LocalDateTime createdAt;
}
