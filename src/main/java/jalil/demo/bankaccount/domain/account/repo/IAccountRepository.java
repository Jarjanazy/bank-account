package jalil.demo.bankaccount.domain.account.repo;

import jalil.demo.bankaccount.domain.account.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAccountRepository extends JpaRepository<Account, Integer>
{
}
