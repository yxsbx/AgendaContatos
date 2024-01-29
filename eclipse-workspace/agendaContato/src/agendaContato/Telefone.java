package agendaContato;

import java.util.Objects;

public class Telefone {
    private String ddd;
    private Long numero;

    public Telefone(String ddd, Long numero) {
        this.ddd = ddd;
        this.numero = numero;
    }

    public String getDdd() {
        return ddd;
    }

    public Long getNumero() {
        return numero;
    }

    public void setDdd(String ddd) {
        this.ddd = ddd;
    }

    public void setNumero(Long numero) {
        this.numero = numero;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Telefone telefone = (Telefone) obj;
        return ddd.equals(telefone.ddd) && numero.equals(telefone.numero);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ddd, numero);
    }

    @Override
    public String toString() {
        return "Telefone{" +
                "ddd='" + ddd + '\'' +
                ", numero=" + numero +
                '}';
    }
}