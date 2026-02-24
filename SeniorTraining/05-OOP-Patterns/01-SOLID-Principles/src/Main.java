
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

/**
 * ============================================================
 * MÓDULO 05.01 — SOLID Principles: Guía completa
 * ============================================================
 *
 * Los 5 principios SOLID son la base del diseño de software Senior.
 * Aquí los aplicamos al dominio financiero con ejemplos reales.
 */
public class Main {

 

/**
 * ❌ VIOLACIÓN SRP — Esta clase hace TODO:
 * Calcula interés
 * Valida transferencias
 * Formatea reportes
 */
static class AccountServiceBAD {
    BigDecimal calculateInterest(BigDecimal balance, BigDecimal rate) {
        return balance.multiply(rate).setScale(2, RoundingMode.HALF_UP);
    }

    boolean validateTransfer(BigDecimal balance, BigDecimal amount) {
        return balance.compareTo(amount) >= 0;
    }

    String formatReport(String accountId, BigDecimal balance) {
        return String.format("Account %s: $%s", accountId, balance);
    }
}

/**
 * ✅ SRP CORRECTO — Cada clase tiene UNA responsabilidad: 
 * 
 * ic class InterestCalculator {
 *  
 *         return balance.multiply(rate).setSca
 *     }
 * 
 * 
 * 
 * boolean hasSufficientFunds(BigDecimal balance, BigDecimal amoun
 * return balance.compareTo(amou
 * 
 * 
 * 
 * ic class AccountReportFormatter {
 * ng format(String accountId, BigDeci
 * return String.format("Account %s: $%
 * 
 * 
 * 
 * ═════════════════════════════════════════════════════════════════════
 * .
 * 
 * ═════════════════════════════════════════════════════════════════════════
 * 
 * 
 * 
 *  * ❌ VIOLACIÓN OCP — Cada nuevo ti
 *  */
 * ic BigDecimal calculateInterestBAD(St
 * 
 *     case "SAVINGS" -> balance.multiply(new BigDecimal("0.04"));
 * 
 *     // Cada nuevo tipo → modificar este switch. Viola OCP.
 * default -> BigDecimal.ZERO;
 * 
 * 
 * 
 * 
 * 
 *  
 * 
 * interface InterestPolicy {
 *     BigDecimal calculateInterest(BigDecimal balance);
 * 
 * 
 * 
 * 
 *     public BigDecimal calculateInterest(BigDecimal balance) {
 *         return balanc
 *    
 * } 
 * 
 * sta
    public BigDecimal calculateInterest(BigDecimal balance
        return balance.multiply(new BigDecimal("0.01")).setScale(2, 
   
}
    
    uevo tipo: solo agrega una 
    ic class PremiumInterest i
    public BigDecimal calculateIntere
        // Premium: 5% base + bonus si ba
        BigDecimal base = balance.
        if (balance.compareTo(new
            base = base.add(new BigD

        return base.setScale(2, Roundi
        
        
        
        ═══════════════════════════════
        P — Liskov Substitution Principle
        i S es subtipo de T, entonces obj
        er reemplazados por objetos d
        ═════════════════════════════════════════════════════════════════════
    

     VIOLACIÓN
    
        
    

    ic class BankAccoun
         {
    

    protected BigDecim
        l balance;
    

    BankAccount(BigDecimal ba
        ance) { this.ba
    a

    void deposit(BigDecimal amoun
        ) { balance = balan
    e

    void withdraw(BigDecim
        l amount) { bala
    c

    BigDecimal getBalanc
        () { return ba
    a

    
        
    

    ic class 
    ReadOnlyAccountBAD(BigDeci
        rride
                w(BigDecimal amount) {
     

    }
    
        
        
         CORRECTO — Separar interfa

        e Depositable {
         deposit(BigDecimal amount);
        ecimal getBalance();
        
        
        e Withdrawable extends Deposit
         withdraw(BigDecimal amount);

        
             FullAccount implements Withdrawable {
            BigDecimal balance;
        A

        ic void withdraw(BigDecimal
            amount) { balance
            = balance.su
        t

        ic BigDecimal getBalance() { return b
            lance; }
            
        

        
            
            
        

        
            
            
        

        lass DepositOnlyAccount implemen
            s Depositable {
            
        

        ate BigDecimal balance;
            
            
        

        sitOnlyAccount(Bi
            oid deposit(BigDecimal amoun
            igDecimal getBalance() { return balance; }
                withdraw → no puede romper ningún contrato.
            
            
        ═
    .
/


/**
 * IOLACIÓN ISP — Una interfaz "gorda" que obliga a todos a implementar tod
// .
 */

   
    void applyInterest();
    void generateReport();
 * vo
 * }
 
   
/**
     ISP CORRECTO — Interfaces peque

    
i

    void withdraw(BigDecima
    


    void applyInterest
    


    String generateReport();  
    }  
  
    interface Notifiable {  
        void sendNotification(String message);
    }

    // Cada Account implementa lo que NECESITA:
    static class SavingsAccount implements TransactionCapable, InterestBearing, Reportable {
        private BigDecimal balance = BigDecimal.ZERO;
        private final BigDecimal rate = new BigDecimal("0.04");

        public void deposit(BigDecimal amount) { balance = balance.add(amount); }
        public void withdraw(BigDecimal amount) { balance = balance.subtract(amount); }
        public void applyInterest() {
            balance = balance.add(balance.multiply(rate)).setScale(2, RoundingMode.HALF_UP);
        }
        public String generateReport() {
            return "Savings Balance: $" + balance;
        }
        // No implementa Notifiable → no está obligada a tener sendNotification
    }

    // ══════════════════════════════════════════════════════════════════════════
    // 5. DIP — Dependency Inversion Principle
    //    "Los módulos de alto nivel no deben depender de los de bajo nivel.
    //     Ambos deben depender de abstracciones."
    // ══════════════════════════════════════════════════════════════════════════

    /**
     * ❌ VIOLACIÓN DIP — TransferService depende directamente de implementaciones concretas.
     */
    static class MySQLAccountRepository {
        BankAccount findById(String id) { return new BankAccount(new BigDecimal("1000")); }
    }

    static class TransferServiceBAD {
        private final MySQLAccountRepository repo = new MySQLAccountRepository(); // ⚠️ Acoplamiento
        void transfer(String fromId, String toId, BigDecimal amount) {
            // Siempre usa MySQL. ¿Qué pasa si cambias a PostgreSQL?
        }
    }

    /**
     * ✅ DIP CORRECTO — Depende de abstracciones, no de implementaciones.
     */
    interface AccountRepository {
        Optional<BankAccount> findById(String id);
        void save(BankAccount account);
    }

    static class TransferService {
        private final AccountRepository repository; // ← Abstracción

        TransferService(AccountRepository repository) { // ← Inyección de dependencia
            this.repository = repository;
        }

        String transfer(String fromId, String toId, BigDecimal amount) {
            Optional<BankAccount> from = repository.findById(fromId);
            Optional<BankAccount> to = repository.findById(toId);
            if (from.isEmpty() || to.isEmpty()) return "Account not found";
            if (from.get().getBalance().compareTo(amount) < 0) return "Insufficient funds";
            from.get().withdraw(amount);
            to.get().deposit(amount);
            repository.save(from.get());
            repository.save(to.get());
            return "Transfer successful";
        }
    }

    // Ahora puedes tener cualquier implementación:
    static class InMemoryAccountRepository implements AccountRepository {
        private final Map<String, BankAccount> store = new HashMap<>();
        public Optional<BankAccount> findById(String id) { return Optional.ofNullable(store.get(id)); }
        public void save(BankAccount account) { /* guardar en memoria */ }
    }

    // ══════════════════════════════════════════════════════════════════════════
    // RESUMEN Y DEMO
    // ══════════════════════════════════════════════════════════════════════════

    public static void main(String[] args) {
        System.out.println("═══════════════════════════════════════════════");
        System.out.println("  MÓDULO 05.01 — SOLID Principles");
        System.out.println("═══════════════════════════════════════════════\n");

        // --- SRP Demo ---
        System.out.println("1. SRP — Single Responsibility");
        InterestCalculator calc = new InterestCalculator();
        TransferValidator validator = new TransferValidator();
        AccountReportFormatter formatter = new AccountReportFormatter();

        BigDecimal interest = calc.calculate(new BigDecimal("10000"), new BigDecimal("0.05"));
        System.out.println("   Interés calculado: $" + interest);
        System.out.println("   ¿Fondos suficientes (10k para 5k)? " +
                validator.hasSufficientFunds(new BigDecimal("10000"), new BigDecimal("5000")));
        System.out.println("   Reporte: " + formatter.format("ACC-001", new BigDecimal("10000")));

        // --- OCP Demo ---
        System.out.println("\n2. OCP — Open/Closed");
        List<InterestPolicy> policies = List.of(
                new SavingsInterest(),
                new CheckingInterest(),
                new PremiumInterest()
        );
        BigDecimal balance = new BigDecimal("50000");
        for (InterestPolicy p : policies) {
            System.out.println("   " + p.getClass().getSimpleName() +
                    " sobre $50,000 = $" + p.calculateInterest(balance));
        }

        // --- LSP Demo ---
        System.out.println("\n3. LSP — Liskov Substitution");
        FullAccount full = new FullAccount(new BigDecimal("5000"));
        full.withdraw(new BigDecimal("1000"));
        System.out.println("   FullAccount después de withdraw $1000: $" + full.getBalance());

        DepositOnlyAccount depOnly = new DepositOnlyAccount(new BigDecimal("3000"));
        depOnly.deposit(new BigDecimal("500"));
        System.out.println("   DepositOnlyAccount después de deposit $500: $" + depOnly.getBalance());
        // No hay withdraw → no puede romper el contrato.

        // --- ISP Demo ---
        System.out.println("\n4. ISP — Interface Segregation");
        SavingsAccount savings = new SavingsAccount();
        savings.deposit(new BigDecimal("10000"));
        savings.applyInterest();
        System.out.println("   " + savings.generateReport());

        // --- DIP Demo ---
        System.out.println("\n5. DIP — Dependency Inversion");
        AccountRepository repo = new InMemoryAccountRepository();
        TransferService service = new TransferService(repo);
        System.out.println("   TransferService usa: " + repo.getClass().getSimpleName());
        System.out.println("   (Se puede cambiar a PostgreSQL sin tocar TransferService)");

        System.out.println("\n═══════════════════════════════════════════════");
        System.out.println("  RESUMEN SOLID");
        System.out.println("═══════════════════════════════════════════════");
        System.out.println("  S → Single Responsibility: una razón para cambiar");
        System.out.println("  O → Open/Closed: extensión sin modificación");
        System.out.println("  L → Liskov Substitution: subtipos cumplen contrato");
        System.out.println("  I → Interface Segregation: interfaces pequeñas");
        System.out.println("  D → Dependency Inversion: depender de abstracciones");
    }
}
