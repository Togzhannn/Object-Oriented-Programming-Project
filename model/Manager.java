package model;

import enums.ManagerType;

import java.io.Serializable;

public class Manager extends Employee implements Serializable {
    private static final long serialVersionUID = 1L;

    private ManagerType managerType;

    public Manager(int id, String firstName, String lastName,
                   String email, String password,
                   String department, double salary,
                   ManagerType managerType) {
        super(id, firstName, lastName, email, password, department, salary);
        this.managerType = managerType;
    }

    public ManagerType getManagerType() { return managerType; }

    @Override
    public String toString() {
        return "Manager{name='" + getFullName() + "', type=" + managerType + "}";
    }
}
