package rvt;

public class Student extends Person {
    private int credits;
    public Student(String name, String address) {
        super(name, address); //Person()
        this.credits = 0;


    }
    public int getCredits() {
        return this.credits;
    }

    public void study() {
        this.credits += 1;
    }

    @Override
    public String toString() {
        return super.toString() + ", " + getaddress() + ", " + "has " + this.credits + " credits";
    }


    


}
