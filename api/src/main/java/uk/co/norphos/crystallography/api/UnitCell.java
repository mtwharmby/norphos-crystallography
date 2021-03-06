package uk.co.norphos.crystallography.api;

import uk.co.norphos.crystallography.api.maths.Matrix;
import uk.co.norphos.crystallography.api.maths.Vector;

/**
 * UnitCell describes the size of the repeating 3d tile of a crystal. It
 * consists of a {@link Lattice} and provides methods to change the lattice 
 * parameters. Furthermore it allows the calculation of values derived or 
 * dependent on from the lattice (e.g. volume or d-spacing). 
 * 
 * @author Michael Wharmby
 *
 */
public interface UnitCell extends Comparable<UnitCell> {

    /**
     * Return the real-space lattice parameters for this UnitCell.
     * @return {@link Lattice}
     */
    Lattice getLattice();

    /**
     * Return the a parameter for the configured lattice.
     * @return double length in Angstrom
     */
    default double getA() {
        return getLattice().getA();
    }

    /**
     * Return the b parameter for the configured lattice.
     * @return double length in Angstrom
     */
    default double getB() {
        return getLattice().getB();
    }

    /**
     * Return the c parameter for the configured lattice.
     * @return double length in Angstrom
     */
    default double getC() {
        return getLattice().getC();
    }

    /**
     * Return the alpha parameter for the configured lattice.
     * @return double angle in degrees
     */
    default double getAlpha() {
        return getLattice().getAl();
    }

    /**
     * Return the beta parameter for the configured lattice.
     * @return double angle in degrees
     */
    default double getBeta() {
        return getLattice().getBe();
    }

    /**
     * Return the gamma parameter for the configured lattice.
     * @return double angle in degrees
     */
    default double getGamma() {
        return getLattice().getGa();
    }

    /**
     * Return the volume of the real-space unit cell of the configured lattice.
     * @return double unit cell volume in Angstrom<sup>3</sup>
     */
    default double getVolume() {
        return getLattice().getVolume();
    }

    /**
     * Return the lattice crystal system.
     * @return {@link CrystalSystem}
     */
    default CrystalSystem getCrystalSystem() {
        return getLattice().getCrystalSystem();
    }

    /**
     * Return the reciprocal-space lattice parameters for this UnitCell.
     * @return {@link Lattice}
     */
    default Lattice getReciprocalLattice() {
        return getReciprocal().getLattice();
    }

    /**
     * Return the a* parameter for the determined reciprocal lattice.
     * @return double length in Angstrom<sup>-1<sup>
     */
    default double getAStar() {
        return getReciprocalLattice().getA();
    }

    /**
     * Return the b* parameter for the determined reciprocal lattice.
     * @return double length in Angstrom<sup>-1<sup>
     */
    default double getBStar() {
        return getReciprocalLattice().getB();
    }

    /**
     * Return the c* parameter for the determined reciprocal lattice.
     * @return double length in Angstrom<sup>-1<sup>
     */
    default double getCStar() {
        return getReciprocalLattice().getC();
    }

    /**
     * Return the alpha* parameter for the determined reciprocal lattice.
     * @return double angle in degrees
     */
    default double getAlphaStar() {
        return getReciprocalLattice().getAl();
    }

    /**
     * Return the beta* parameter for the determined reciprocal lattice.
     * @return double angle in degrees
     */
    default double getBetaStar() {
        return getReciprocalLattice().getBe();
    }

    /**
     * Return the gamma* parameter for the determined reciprocal lattice.
     * @return double angle in degrees
     */
    default double getGammaStar() {
        return getReciprocalLattice().getGa();
    }

    /**
     * Return the metric tensor (G-matrix) for the real-space unit cell.
     * @return Matrix G-matrix
     */
    Matrix getMetricTensor();

    /**
     * Return the metric tensor of the reciprocal-space unit cell.
     * @return Matrix reciprocal-space G-matrix
     */
    default Matrix getReciprocalMetricTensor() {
        return getReciprocal().getMetricTensor();
    }

    /**
     * Return the reciprocal-space equivalent of this UnitCell.
     * @return UnitCell
     */
    UnitCell getReciprocal();

    /**
     * Convert a vector in Cartesian coordinates to its equivalent in the
     * fractional coordinate system of this unit cell.
     *
     * @param cartVector Vector in Cartesian coordinates
     * @return Vector in fractional coordinates of the current lattice
     */
    default Vector fractionalize(Vector cartVector) {
        return getFractionalizationMatrix().multiply(cartVector);
    }

    /**
     * Convert a vector in fractional coordinates of this unit cell into an
     * equivalent vector in Cartesian coordinates.
     *
     * @param fracVector Vector in fractional coordinates
     * @return Vector in Cartesian coordinates
     */
    default Vector orthogonalize(Vector fracVector) {
        return getOrthogonalizationMatrix().multiply(fracVector);
    }

    /**
     * Return matrix to convert Cartesian coordinates into fractional
     * coordinates for this unit cell's lattice.
     * @return Matrix
     */
    Matrix getFractionalizationMatrix();

    /**
     * Return matrix to convert fractional coordinates of this unit cell's
     * lattice into Cartesian coordinates.
     * @return Matrix
     */
    Matrix getOrthogonalizationMatrix();

    /**
     * Calculate the length of a vector specified in fractional coordinates of
     * this unit cell.
     *
     * @param fracVec Vector in fractional coordinates
     * @return double length of vector
     */
    double calculateLength(Vector fracVec);

    /**
     * Calculate the distance between two sites specified in fractional
     * coordinates of this unit cell.
     *
     * @param site1 Vector in fractional coordinates
     * @param site2 Vector in fractional coordinates
     * @return double distance between sites
     */
    default double calculateDistance(Vector site1, Vector site2) {
        return calculateLength(site2.subtract(site1));
    }

    /**
     * Calculate the angle between two vectors specified in fractional
     * coordinates of this unit cell.
     *
     * @param fracVec1 Vector in fractional coordinates
     * @param fracVec2 Vector in fractional coordinates
     * @return double angle between vectors in radians
     */
    double calculateAngle(Vector fracVec1, Vector fracVec2);

    /**
     * Calculate the angle between site 1 and site3 at site2 (i.e. the angle
     * between the vectors site1-site2 and site2-site3, c.f. bond angle).
     *
     * @param site1 Vector in fractional coordinates
     * @param site2 Vector in fractional coordinates
     * @param site3 Vector in fractional coordinates
     * @return double angle at site2 in radians
     */
    default double calculateAngle(Vector site1, Vector site2, Vector site3) {
        return calculateAngle(site2.subtract(site1), site2.subtract(site3));
    }

    /**
     * Calculate the angle between the planes containing site1, site2 and
     * site3 and site2, site3 and site4.
     *
     * @param site1 Vector in fractional coordinates
     * @param site2 Vector in fractional coordinates
     * @param site3 Vector in fractional coordinates
     * @param site4 Vector in fractional coordinates
     * @return double angle between planes in radians
     */
    double calculateDihedralAngle(Vector site1, Vector site2, Vector site3, Vector site4);

    /**
     * Maximum {@link MillerPlane} for the given d-spacing limit.
     *
     * @param dSpacing in Angstrom<sup>-1<sup> TODO Right?
     * @return MillerIndex maximum hkl observable
     */
    MillerPlane getMaxMillerIndex(double dSpacing);

    /**
     * Return the d-space value for a specific {@link MillerPlane}.
     *
     * @param hkl {@link MillerPlane}
     * @return double d-spacing in Angstrom<sup>-1<sup> TODO Right?
     */
    double calculateDSpacing(MillerPlane hkl);
//    
//    /**
//     * Determine whether this UnitCell is similar to another one, within certain tolerances.
//     *  
//     * @param other UnitCell to compare
//     * @param lengthTol Double length tolerance (if null, default to 0.02 - 2%)
//     * @param angleTol Double angle tolerance (if null, default to 1degree)
//     * @return boolean true if this and other are same within tolerance
//     */
//    boolean isSimilar(UnitCell other, Double lengthTol, Double angleTol);
}
