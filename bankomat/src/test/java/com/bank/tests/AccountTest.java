package com.bank.tests;
import com.bank.entities.Account;
import com.bank.exceptions.BusinessException;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class AccountTest {

    @Test
    public void testDeposit() throws BusinessException {
        Account account = new Account("123");
        account.deposit(100);
        assertEquals(100, account.getBalance(),0.001);
    }

    @Test
    public void testWithdraw() throws BusinessException {
        Account account = new Account("123");
        account.deposit(200);
        account.withdraw(100);
        assertEquals(100, account.getBalance(),0.001);
    }

    @Test
    public void testWithdrawInsufficientFunds() {
        Account account = new Account("123");
        Exception exception = assertThrows(BusinessException.class, () -> account.withdraw(50));
        assertEquals("Insufficient funds", exception.getMessage());
    }
}