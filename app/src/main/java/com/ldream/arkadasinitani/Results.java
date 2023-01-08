package com.ldream.arkadasinitani;

public class Results {


    String maker;
    String solver;
    String trues;
    String falses;
    String id;

    public Results() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Results(String maker, String solver, String trues, String falses, String id) {
        this.id = id;
        this.maker = maker;
        this.solver = solver;
        this.trues = trues;
        this.falses = falses;

    }


    public String getMaker() {
        return maker;
    }

    public void setMaker(String maker) {
        this.maker = maker;
    }

    public String getSolver() {
        return solver;
    }

    public void setSolver(String solver) {
        this.solver = solver;
    }

    public String getTrues() {
        return trues;
    }

    public void setTrues(String trues) {
        this.trues = trues;
    }

    public String getFalses() {
        return falses;
    }

    public void setFalses(String falses) {
        this.falses = falses;
    }


}
