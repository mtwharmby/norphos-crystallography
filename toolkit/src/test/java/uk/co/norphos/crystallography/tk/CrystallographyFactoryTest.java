package uk.co.norphos.crystallography.tk;

import org.junit.Before;
import org.junit.Test;
import uk.co.norphos.crystallography.api.Lattice;
import uk.co.norphos.crystallography.api.maths.Matrix;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static uk.co.norphos.crystallography.api.CrystalSystem.*;

public class CrystallographyFactoryTest {

    private Matrix mockMatrix;

    private Lattice cubicLatt = new Lattice(5.43018, 5.43018, 5.43018, 90, 90, 90, 160.118936, CUBIC),
            orthoLatt = new Lattice(23.49290, 6.34350, 19.63820, 90, 90, 90, 2926.626460, ORTHORHOMBIC),
            monoLatt = new Lattice(5.145, 5.2075, 5.3107, 90, 99.23, 90, 140.445, MONOCLINIC),
            tricLatt = new Lattice(7.19196, 8.12720, 8.12771, 82.4809, 69.2610, 69.2584, 415.482298, TRICLINIC);

    private double[][] cubicGMat = new double[][]{{29.4868548324, 0, 0}, {0, 29.4868548324, 0}, {0, 0, 29.4868548324}},
            orthoGMat = new double[][]{{551.91635041, 0, 0}, {0, 40.23999225, 0}, {0, 0, 385.65889924}},
            tricGMat = new double[][]{
                    {51.7242886416, 20.700473696386158, 20.699292030654693},
                    {20.700473696386158, 66.05137984000001, 8.643807381166594},
                    {20.699292030654693, 8.643807381166594, 66.0596698441}
            };


    @Before
    public void setUp() {
        mockMatrix = mock(Matrix.class);
    }

    @Test
    public void testCrystalSystemDetermination() {
        Double[] cubicLen = new Double[]{3d, null, null}, cubicAng = new Double[]{90d, 90d, 90d},
                rhombLen = new Double[]{3d, null, null}, rhombAng = new Double[]{60d, 60d, 60d},
                hexaLen = new Double[]{5d, null, 2d}, hexaAng = new Double[]{90d, 90d, 120d},
                tetraLen = new Double[]{2d, 2d, 5d},
                orthoLen = new Double[]{5d, 3d, 2d},
                monoLen = new Double[]{2d, 3d, 5d}, monoAng = new Double[]{90d, 30d, 90d},
                tricLen = new Double[]{3d, 5d, 2d}, tricAng = new Double[]{30d, 45d, 60d};

        assertEquals("Wrong crystal system for cubic", CUBIC, CrystallographyFactory.getCrystalSystem(cubicLen, cubicAng));
        assertEquals("Wrong crystal system for rhombohedral", RHOMBOHEDRAL, CrystallographyFactory.getCrystalSystem(rhombLen, rhombAng));
        assertEquals("Wrong crystal system for hexagonal", HEXAGONAL, CrystallographyFactory.getCrystalSystem(hexaLen, hexaAng));
        assertEquals("Wrong crystal system for tetragonal", TETRAGONAL, CrystallographyFactory.getCrystalSystem(tetraLen, cubicAng));
        assertEquals("Wrong crystal system for orthorhombic", ORTHORHOMBIC, CrystallographyFactory.getCrystalSystem(orthoLen, cubicAng));
        assertEquals("Wrong crystal system for monoclinic", MONOCLINIC, CrystallographyFactory.getCrystalSystem(monoLen, monoAng));
        assertEquals("Wrong crystal system for triclinic", TRICLINIC, CrystallographyFactory.getCrystalSystem(tricLen, tricAng));
    }

    @Test
    public void testLatticeFromParameters() {
        Lattice createdCubic = CrystallographyFactory.createLattice(5.43018, null, null, null, null, null);
        assertEquals("Wrong lattice created for cubic", cubicLatt, createdCubic);

        Lattice createdOrtho = CrystallographyFactory.createLattice(23.49290, 6.34350, 19.63820, null, null, null);
        assertEquals("Wrong lattice created for orthorhombic", orthoLatt, createdOrtho);

        Lattice createdMono = CrystallographyFactory.createLattice(5.145, 5.2075, 5.3107, null, 99.23, null);
        assertEquals("Wrong lattice created for monoclinic", monoLatt, createdMono);

        Lattice createdTric = CrystallographyFactory.createLattice(7.19196, 8.12720, 8.12771, 82.4809, 69.2610, 69.2584);
        assertEquals("Wrong lattice created for triclinic", tricLatt, createdTric);
    }

    @Test
    public void testLatticeFromMetricTensor() {
        when(mockMatrix.toArray()).thenReturn(cubicGMat);
        Lattice createdCubic = CrystallographyFactory.createLattice(mockMatrix);
        assertEquals("Wrong lattice created from cubic metric tensor", cubicLatt, createdCubic);

        when(mockMatrix.toArray()).thenReturn(orthoGMat);
        Lattice createdOrtho = CrystallographyFactory.createLattice(mockMatrix);
        assertEquals("Wrong lattice created from orthorhombic metric tensor", orthoLatt, createdOrtho);

        when(mockMatrix.toArray()).thenReturn(tricGMat);
        Lattice createdTric = CrystallographyFactory.createLattice(mockMatrix);
        assertEquals("Wrong lattice created from triclinic metric tensor", tricLatt, createdTric);
    }
}
