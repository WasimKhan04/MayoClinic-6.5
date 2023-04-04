package com.hospitalManagement.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="patients")
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pid;
    private String name;
    private String city;
    private long mobile;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="doctors_id")
    private Doctor doctor;

}
