package br.com.fiap.auramed.infrastructure.main;

import io.quarkus.runtime.Quarkus;

public class Main {
    static void main(String[] args) {
        Quarkus.run(AuramedApplication.class, args);
    }
}
