package uk.co.norphos.crystallography.api;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

/**
 * A bean-like object which defines the reciprocal lattice vector of a family 
 * of Miller planes in a crystal. Stores the calculated structure factor and 
 * observed scattering intensity of the diffracted beam associated with these 
 * planes. Also stores the interlayer spacing (d-spacing).
 * 
 * @author Michael Wharmby
 *
 */
public class MillerPlane implements Serializable, Comparable<MillerPlane> {

    private static final long serialVersionUID = 1162662794043478530L;

    private final int[] indices;
    private double dSpacing;
    private String label;
    private Double structureFactor, intensity;
    
    /**
     * Construct a basic {@link MillerPlane} object with only Miller indices. 
     * Label defaults to an empty string and d-spacing is set to -1, 
     * indicating it should be calculated.
     * @param h Miller index
     * @param k Miller index
     * @param l Miller index
     */
    public MillerPlane(int h, int k, int l) {
        this(new int[]{h,k,l}, -1d, "", null, null);
    }
    
    /**
     * Construct a {@link MillerPlane} object with Miller indices and an 
     * associated d-spacing set. Also provide a label to describe the planes.
     * @param h Miller index
     * @param k Miller index
     * @param l Miller index
     * @param dSpacing double in Angstrom
     * @param label String
     */
    public MillerPlane(int h, int k, int l, double dSpacing, String label) {
        this(new int[]{h,k,l}, dSpacing, label, null, null);
    }
    
    /**
     * Construct a {@link MillerPlane} object with Miller indices and an 
     * associated d-spacing set. With this constructor, the calculated 
     * structure factor (F<sub>hkl</sub>) and observed scattering intensity 
     * (I<sub>hkl</sub>) can also be supplied.
     * @param h Miller index
     * @param k Miller index
     * @param l Miller index
     * @param dSpacing double in Angstrom
     * @param label String
     * @param fhkl Double calculated structure factor
     * @param intensity Double observed intensity
     */
    public MillerPlane(int h, int k, int l, double dSpacing, String label, Double fhkl, Double intensity) {
        this(new int[]{h,k,l}, dSpacing, label, fhkl, intensity);
    }
    
    /**
     * Construct a {@link MillerPlane} object with Miller indices and an 
     * associated d-spacing set. With this constructor, the calculated 
     * structure factor (F<sub>hkl</sub>) and observed scattering intensity 
     * (I<sub>hkl</sub>) can also be supplied.
     * @param indices int[] containing h, k and l Miller indices
     * @param dSpacing double in Angstrom
     * @param label String
     * @param fhkl Double calculated structure factor
     * @param intensity Double observed intensity
     */
    public MillerPlane(int[] indices, double dSpacing, String label, Double fhkl, Double intensity) {
        this.indices = indices;
        this.dSpacing = dSpacing;
        this.label = label;
        this.structureFactor = fhkl;
        this.intensity = intensity;
    }
    
    /**
     * Return the Miller indices (hkl) of this plane. Miller indices are 
     * immutable as they define the plane.
     * @return int[] indices in reciprocal lattice units
     */
    public int[] getIndices() {
        return indices;
    }
    
    /**
     * Return the h Miller index of this plane.
     * @return int
     */
    public int getH() {
        return indices[0];
    }
    
    /**
     * Return the k Miller index of this plane.
     * @return int
     */
    public int getK() {
        return indices[1];
    }
    
    /**
     * Return the l Miller index of this plane.
     * @return int
     */
    public int getL() {
        return indices[2];
    }
    
    /**
     * Return the real-space interplane spacing (d-spacing) of this family of 
     * {@link MillerPlane}s.
     * @return double d-spacing in Angstrom
     */
    public double getDSpacing() {
        return dSpacing;
    }
    
    /**
     * Set the real-space interplanar spacing of this family of
     * {@link MillerPlane}s. Should be calculated from the Miller indices of 
     * the plane using the {@link UnitCell#calculateDSpacing} method.
     * @param dSpacing double d-spacing of planes in Angstoms
     */
    public void setDSpacing(double dSpacing) {
        this.dSpacing = dSpacing;
    }
    
    /**
     * Return the magnitude of the reciprocal space scattering vector 
     * (Q/q/S/s/h; also known as momentum transfer) which is defined by the 
     * Miller indices of this plane. Q is related to the d-spacing value 
     * by:<br />
     * Q = <sup>2 * &pi;</sup>&frasl;<sub>d</sub>
     * @return double Q magnitude in Angstrom<sup>-1</sup>
     */
    public double getQSpacing() {
        return 2 * Math.PI / dSpacing;
    }

    /**
     * Return a label to represent this {@link MillerPlane}.
     * @return String
     */
    public String getLabel() {
        return label;
    }

    /**
     * Change the label of this plane.
     * @param label String
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * The calculated/simulated structure factor (F<sub>hkl</sub>) of the 
     * diffracted beam associated with this set of {@link MillerPlane}s. 
     * @return double F<sub>hkl</sub> in units of electron scattering power
     * (2.82x10<sup>-15</sup> m) for X-rays or scattering length 
     * (10<sup>-14</sup> m) for neutrons.
     */
    public double getStructureFactor() {
        return structureFactor;
    }

    /**
     * Change the calculated structure factor (F<sub>hkl</sub>) of the 
     * diffracted beam associated with this set of {@link MillerPlane}s. This
     * should be calculated using the {@link Crystal}TODO method.
     * @param structureFactor Double in units of electron scattering power 
     * (2.82x10<sup>-15</sup> m) for X-rays or scattering length 
     * (10<sup>-14</sup> m) for neutrons.
     */
    public void setStructureFactor(double structureFactor) {
        this.structureFactor = structureFactor;
    }

    /**
     * The observed scattering intensity (I<sub>hkl</sub>) measured for a
     * reflection attributed to this set of {@link MillerPlane}s. 
     * I<sub>hkl</sub> is proportional to F<sub>hkl</sub><sup>2</sup>.
     * @return double observed scattering intensity
     */
    public double getIntensity() {
        return intensity;
    }

    /**
     * Set the observed scattering intensity (I<sub>hkl</sub>) of the 
     * reflection associated with this set of {@link MillerPlane}s. This
     * should be determined by peak fitting.
     * @param intensity double
     */
    public void setIntensity(double intensity) {
        this.intensity = intensity;
    }

    @Override
    public int compareTo(MillerPlane arg0) {
        // TODO Auto-generated method stub
        //compare on d-spacing
        return 0;
    }

    @Override
    public String toString() {
        return "MillerPlane [(hkl)=(" + indices[0] + " " + indices[1] + " "+ indices[2] + "), "
                + "label=" + label + ", dSpacing=" + dSpacing + ", qSpacing=" + (2 * Math.PI / dSpacing) 
                + ", structureFactor=" + structureFactor + ", intensity=" + intensity + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MillerPlane that = (MillerPlane) o;
        return Double.compare(that.dSpacing, dSpacing) == 0 &&
                Arrays.equals(indices, that.indices) &&
                Objects.equals(label, that.label) &&
                Objects.equals(structureFactor, that.structureFactor) &&
                Objects.equals(intensity, that.intensity);
    }

    @Override
    public int hashCode() {

        int result = Objects.hash(dSpacing, label, structureFactor, intensity);
        result = 31 * result + Arrays.hashCode(indices);
        return result;
    }

}
