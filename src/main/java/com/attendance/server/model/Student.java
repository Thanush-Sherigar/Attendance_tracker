package com.attendance.server.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name="students")
public class Student {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message="Name is mandatory")
    private String name;
    @NotBlank(message="email is mandatory")
    private String email;
    @NotBlank(message="password is mandatory")
    private String password;
    public Student(){}
    public Student(String name,String email){
        this.name=name;
        this.email=email;
    }
    public Long getID(){return id;}
    public String getName(){return name;}
    public void setName(String name){this.name=name;}
    public String getEmail(){return email;}
    public String getPassword(){return password;}
    public void setPassword(String password){this.password=password;}
    public void setEmail(String email){this.email=email;}
}
