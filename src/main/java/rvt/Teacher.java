package rvt;

public class Teacher extends Person {
    private int salary;

    public Teacher(String name, String address, int salary) {
        super(name, address); //Person()
        this.salary = salary;
    }

    public int getSalary() {
        return this.salary;
    }

    @Override
    public String toString() {
        return super.toString() + ", " + getaddress() + ", " + "earns " + this.salary + " euros ";
    }


}
