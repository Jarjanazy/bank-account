package jalil.demo.bankaccount.domain.account.repo;

import jalil.demo.bankaccount.domain.account.model.Account;
import jalil.demo.bankaccount.domain.account.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ITransactionRepository extends JpaRepository<Transaction, Integer>
{
    List<Transaction> findAllByAccount(Account account);
}
