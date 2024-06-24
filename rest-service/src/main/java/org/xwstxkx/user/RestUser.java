package org.xwstxkx.user;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.xwstxkx.entity.*;
import org.xwstxkx.util.Role;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class RestUser implements UserDetails {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id_seq")
    @SequenceGenerator(name = "user_id_seq", sequenceName = "user_id_seq", allocationSize = 1)
    private Long id;
    @Column(name = "username", unique = true, nullable = false)
    private String username;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "email", unique = true, nullable = false)
    private String email;
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;
    @Column(name = "telegram_user_id")
    private Long telegramUserId;
    @Column(name = "first_login_date")
    @CreationTimestamp
    private LocalDateTime firstLoginDate;
    @Column(name = "firstname")
    private String firstname;
    @Column(name = "lastname")
    private String lastname;
    @Column(name = "is_active")
    private Boolean isActive;
    //TODO в дальнейшем расширить данный функционал
    @Enumerated(EnumType.STRING)
    @Column(name = "user_state")
    private UserState userState;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<BudgetEntity> budgets;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<SavingsBudgetEntity> savingsBudget;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "user")
    private MainBudgetEntity mainBudget;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<DebtEntity> debts;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<ExpenseEntity> expenses;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<IncomeEntity> incomes;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<LoanEntity> loans;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<CategoryEntity> categories;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public List<BudgetEntity> getBudgets() {
        if (budgets == null) {
            budgets = new ArrayList<>();
        }
        return budgets;
    }
}