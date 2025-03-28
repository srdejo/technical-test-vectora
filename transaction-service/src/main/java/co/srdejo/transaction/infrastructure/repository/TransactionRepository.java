package co.srdejo.transaction.infrastructure.repository;

import co.srdejo.transaction.domain.model.Transaction;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Scope("prototype")
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByFromAccount(long fromAccount);

}
