package model;

import java.sql.Date;

public abstract class Person implements Comparable<Person> {
    protected int nif;
    protected String pwd;
    protected String name;
    protected Date birht_date;
    protected String address;
    protected boolean deactivated = false;
    protected String email;


    public boolean isDeactivated() {
        return deactivated;
    }

    public boolean setDeactivated(int deactivated) {
        if (deactivated == 0) {
            this.deactivated = true;
            return true;
        } else {
            return false;
        }
    }

    public int getNif() {
        return nif;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return this.email;
    }

    public void setNif(int nif) {
        if(nif < 100000000) {
            throw new IllegalArgumentException("NIF must have 9 digits");
        }
        this.nif = nif;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirht_date() {
        return birht_date;
    }

    public void setBirht_date(Date birht_date) {
        this.birht_date = birht_date;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public void deactivate() {
        this.deactivated = true;
    }

    public void setDeactivated(boolean deactivated) {
        this.deactivated = deactivated;
    }

}
