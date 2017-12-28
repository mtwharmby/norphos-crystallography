package uk.co.norphos.crystallography.api;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

public class Atom implements Serializable {

    private static final long serialVersionUID = 3959654678206070372L;

    private double[] coords;
    private String name, type;
    private double occ;
    private Double radius;
    private double[][] uijMatrix;
    private Integer charge, isotope, coordinationNumber;

    public Atom(String name, String type, double x, double y, double z) {
        this(name, type, new double[]{x, y, z}, 1, new double[3][3], null, null, null, null);
        //FIXME uijMatrix should be equivalent to beq = 1 as default
    }

    public Atom(String name, String type, double x, double y, double z, double occ, double[][] uijMatrix) {
        this(name, type, new double[]{x, y, z}, occ, uijMatrix, null, null, null, null);
    }

    public Atom(String name, String type, double x, double y, double z, double occ, double[][] uijMatrix,
                Double radius, Integer coordinationNumber, Integer charge, Integer isotope) {
        this(name, type, new double[]{x, y, z}, occ, uijMatrix, radius, coordinationNumber, charge, isotope);
    }

    public Atom(String name, String type, double[] coords, double occ, double[][] uijMatrix,
                Double radius, Integer coordinationNumber, Integer charge, Integer isotope) {
        this.name = name;
        this.type = type;
        this.coords = coords;
        this.occ = occ;
        this.uijMatrix = uijMatrix;
        this.radius = radius;
        this.charge = charge;
        this.isotope = isotope;
    }

    public double[] getCoords() {
        return coords;
    }

    public void setCoords(double[] coords) {
        this.coords = coords;
    }

    public double getX() {
        return coords[0];
    }

    public void setX(double x) {
        coords[0] = x;
    }

    public double getY() {
        return coords[1];
    }

    public void setY(double y) {
        coords[1] = y;
    }

    public double getZ() {
        return coords[2];
    }

    public void setZ(double z) {
        coords[2] = z;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getOcc() {
        return occ;
    }

    public void setOcc(double occ) {
        this.occ = occ;
    }

    public double[][] getUijMatrix() {
        return uijMatrix;
    }

    public void setUijMatrix(double[][] uijMatrix) {
        this.uijMatrix = uijMatrix;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public Integer getCoordinationNumber() {
        return coordinationNumber;
    }

    public void setCoordinationNumber(Integer coordinationNumber) {
        this.coordinationNumber = coordinationNumber;
    }

    public int getCharge() {
        return charge;
    }

    public void setCharge(int charge) {
        this.charge = charge;
    }

    public int getIsotope() {
        return isotope;
    }

    public void setIsotope(int isotope) {
        this.isotope = isotope;
    }

    @Override
    public String toString() {
        return "Atom [coords=" + Arrays.toString(coords) + ", name=" + name + ", type=" + type + ", occ=" + occ
                + ", uijMatrix=" + Arrays.toString(uijMatrix) + "]";
        //FIXME Add radius, CN, charge & isotope through StringBuffer + if statement
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Atom atom = (Atom) o;
        return Double.compare(atom.occ, occ) == 0 &&
                Arrays.equals(coords, atom.coords) &&
                Objects.equals(name, atom.name) &&
                Objects.equals(type, atom.type) &&
                Objects.equals(radius, atom.radius) &&
                Arrays.equals(uijMatrix, atom.uijMatrix) &&
                Objects.equals(charge, atom.charge) &&
                Objects.equals(isotope, atom.isotope) &&
                Objects.equals(coordinationNumber, atom.coordinationNumber);
    }

    @Override
    public int hashCode() {

        int result = Objects.hash(name, type, occ, radius, charge, isotope, coordinationNumber);
        result = 31 * result + Arrays.hashCode(coords);
        result = 31 * result + Arrays.hashCode(uijMatrix);
        return result;
    }

}
