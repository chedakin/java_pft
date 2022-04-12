package sc.stqa.pft.mantis.Models;

import java.math.BigInteger;

public class Project {
    private BigInteger id;
    private String name;

    public String getName() {
        return name;
    }

    public BigInteger getId() {
        return id;
    }

    public Project withName(String name) {
        this.name = name;
        return this;
    }

    public Project withId(BigInteger id) {
        this.id = id;
        return this;
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

}
