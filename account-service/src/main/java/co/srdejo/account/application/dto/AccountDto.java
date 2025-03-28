package co.srdejo.account.application.dto;

public class AccountDto {
    private Long id;
    private String nombre;
    private double saldo;

    public AccountDto(Long id, String nombre, double saldo) {
        this.id = id;
        this.nombre = nombre;
        this.saldo = saldo;
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public double getSaldo() {
        return saldo;
    }
}
