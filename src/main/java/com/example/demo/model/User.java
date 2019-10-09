package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Date;

@Entity()
@Table(name = "user")
@SequenceGenerator(name = "user_id_seq", sequenceName = "user_id_seq", initialValue = 1, allocationSize = 1)
public class User {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_id_seq")
    private Long id;

    @Column(name = "user_name", unique = true)
    private String name;

    @Column(name = "user_password_hash", nullable = false)
    @JsonIgnore
    private String passwordHash;

    @Column(name = "user_salt", nullable = false)
    @JsonIgnore
    private String salt;

    @Transient
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    public User() {
    }

    public User(String name, String password) {
        this.name = name;
        setPassword(password);
    }

    public static String toSHA1(String password) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-1");
            return byteArrayToHexString(md.digest(password.getBytes()));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static String byteArrayToHexString(byte[] b) {
        String result = "";
        for (int i = 0; i < b.length; i++) {
            result +=
                    Integer.toString((b[i] & 0xff) + 0x100, 16).substring(1);
        }
        return result;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
        this.salt = toSHA1(new Date().toString() + name);
        this.passwordHash = toSHA1(password + this.salt);
    }

    public Boolean isPasswordValid(String password) {
        return passwordHash.equals(
                toSHA1(password + this.salt));
    }
}
