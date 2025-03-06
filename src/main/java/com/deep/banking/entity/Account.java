package com.deep.banking.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "accounts")
@Entity
public class Account
{

    @GeneratedValue(strategy = GenerationType.IDENTITY)  //this will mark my ID as primary key
    @Id
    private long id;

    @Column(name = "account_holder_name")
    private String accountHolderName;
    private double balance;
}
