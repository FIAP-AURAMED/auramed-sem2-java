package br.com.auramed.domain.service;

public interface PasswordService {
    String hashPassword(String plainPassword);
    boolean checkPassword(String plainPassword, String hashedPassword);
    boolean isHashedPassword(String password);
}