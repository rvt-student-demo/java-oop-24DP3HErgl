package rvt;

public class Money {
    private final int euros;
    private final int cents;

    public Money(int euros, int cents) {
        this.euros = euros;
        this.cents = cents;
    }

    public int euros() {
        return euros;
    }

    public int cents() {
        return cents;
    }

    public String toString() {
        String zero = "";
        if (cents <= 10) {
            zero = "0";
        }

        return euros + "." + zero + cents + "e";
    }

    public Money plus(Money addition) {
        Money newMoney = new Money(euros, cents); 
        newMoney = new Money(newMoney.euros + addition.euros, newMoney.cents + addition.cents);

        if (newMoney.cents >= 100) {
            newMoney = new Money(newMoney.euros + 1, newMoney.cents - 100);
        }

        return newMoney;
        
    }

    public boolean less(Money compared) {
        if (this.euros < compared.euros) {
            return true;
        } else if (this.euros == compared.euros && this.cents < compared.cents) {
            return true;
        } else {
            return false;
        }
    }

    public Money minus(Money decreaser) {
        Money newMoney = new Money(euros, cents); 
        newMoney = new Money(newMoney.euros - decreaser.euros, newMoney.cents - decreaser.cents);

        if (newMoney.cents < 0) {
            newMoney = new Money(newMoney.euros - 1, newMoney.cents + 100);
        }

        if (newMoney.euros < 0) {
            newMoney = new Money(0, 0);
        }

        return newMoney;
    }


}
