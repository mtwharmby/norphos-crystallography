package uk.co.norphos.crystallography.tk;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.Answer;
import uk.co.norphos.crystallography.adaptor.apachemaths.ApacheMatrix;
import uk.co.norphos.crystallography.api.Lattice;
import uk.co.norphos.crystallography.api.LatticeException;
import uk.co.norphos.crystallography.api.UnitCell;
import uk.co.norphos.crystallography.api.maths.MathsFactory;
import uk.co.norphos.crystallography.api.maths.Matrix;
import uk.co.norphos.crystallography.api.maths.Vector;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;
import static uk.co.norphos.crystallography.tk.TestUtils.*;

public class UnitCellImplTest {

    private MathsFactory mockMaths;
    private Matrix mockMatrix, mockRecipMatrix;

    private UnitCell uc;

    private Lattice cubicLatt = new Lattice(5.43018, 5.43018, 5.43018, 90, 90, 90),
            orthoLatt = new Lattice(23.49290, 6.34350, 19.63820, 90, 90, 90),
            tricLatt = new Lattice(7.19196, 8.12720, 8.12771, 82.4809, 69.2610, 69.2584);

    private double[][] cubicGMat = new double[][]{{29.4868548324, 0, 0}, {0, 29.4868548324, 0}, {0, 0, 29.4868548324}},
            orthoGMat = new double[][]{{551.91635041, 0, 0}, {0, 40.23999225, 0}, {0, 0, 385.65889924}},
            tricGMat = new double[][]{
                    {51.7242886416, 20.700473696386158, 20.699292030654693},
                    {20.700473696386158, 66.05137984000001, 8.643807381166594},
                    {20.699292030654693, 8.643807381166594, 66.0596698441}
                    };
    private double [][] tricGMatInv = new double[][]{{ 0.02484347, -0.00688511, -0.0068836},
                                                     {-0.00688511,  0.01731163, -0.0001078},
                                                     {-0.0068836 , -0.0001078 ,  0.01730886}};
    /*
     * Derived from https://en.wikipedia.org/wiki/Fractional_coordinates
     * [ a, b cos ga, c cos be      ]
     * [ 0, b sin ga, -c cos al*    ]
     * [ 0,        0, ab sin ga / V ]
     * (ab sin ga / V) = 1/c*
     * ((cos be cos ga - cos al)/sin ga) = cos al*
     */
    private double[][] tricOrthoTensor = new double[][]{{ 7.19196   ,  2.87827987,  2.87811557},
                                                        { 0.        ,  7.60045294,  0.05061718},
                                                        { 0.        ,  0.        ,  7.60091309}};

    private double cubicVol = 160.118936, orthoVol = 2926.626460, tricVol = 415.482298;

    @Before
    public void setUp() {
        mockMaths = mock(MathsFactory.class);
        mockMatrix = mock(Matrix.class);
    }

    @Test
    public void testMetricTensorCalculation() {
        when(mockMaths.createMatrix(any(double[][].class))).thenReturn(mockMatrix);
        when(mockMatrix.getInverse()).thenReturn(mockMatrix);
        when(mockMatrix.toArray()).thenReturn(new double[3][3]);

        //We use Argument capture rather than a verify since maths introduces
        //slight differences in values (<= 1e-10), so the arrays are different
        ArgumentCaptor<double[][]> argCapture = ArgumentCaptor.forClass(double[][].class);

        uc = new UnitCellImpl(mockMaths, cubicLatt);
        List<double[][]> badger = argCapture.getAllValues();
        verify(mockMaths, times(2)).createMatrix(argCapture.capture());
        assertTwoDArrayEquals("Incorrect G-matrix for cubic", cubicGMat, argCapture.getAllValues().get(0), 1e-10);

        uc = new UnitCellImpl(mockMaths, orthoLatt);
        verify(mockMaths, times(4)).createMatrix(argCapture.capture());
        badger = argCapture.getAllValues();
        assertTwoDArrayEquals("Incorrect G-matrix for orthorhombic", orthoGMat, argCapture.getAllValues().get(4), 1e-10);

        uc = new UnitCellImpl(mockMaths, tricLatt);
        verify(mockMaths, times(6)).createMatrix(argCapture.capture());
        badger = argCapture.getAllValues();
        assertTwoDArrayEquals("Incorrect G-matrix for triclinic", tricGMat, argCapture.getAllValues().get(10), 1e-10);
    }

    @Test
    public void testReciprocalMetricTensorSetting() {
        // - calculation of metric tensor
        when(mockMaths.createMatrix(argThat(new twoDArrayAlmostEquals(tricGMat)))).thenReturn(mockMatrix);   //argThat(arrayAllElementsNonZero)
        // - inversion of G-matrix
        Matrix mockRecipMat = mock(Matrix.class);
        when(mockMatrix.getInverse()).thenReturn(mockRecipMat);
        // - determination of reciprocal Lattice
        when(mockRecipMat.toArray()).thenReturn(tricGMatInv);

        uc = new UnitCellImpl(mockMaths, tricLatt);
        verify(mockMatrix, times(1)).getInverse();
    }

    @Test
    public void testOrthogonalisationMatrixCalculation() {
        // - calculation of metric tensor
        when(mockMaths.createMatrix(argThat(new twoDArrayAlmostEquals(tricGMat)))).thenReturn(mockMatrix);
        // - inversion of G-matrix
        Matrix mockRecipMat = mock(Matrix.class);
        when(mockMatrix.getInverse()).thenReturn(mockRecipMat);
        // - determination of reciprocal Lattice
        when(mockRecipMat.toArray()).thenReturn(tricGMatInv);

        //We use Argument capture rather than a verify since maths introduces
        //slight differences in values (<= 1e-10), so the arrays are different
        ArgumentCaptor<double[][]> argCapture = ArgumentCaptor.forClass(double[][].class);
        uc = new UnitCellImpl(mockMaths, tricLatt);
        verify(mockMaths, times(2)).createMatrix(argCapture.capture());

        double[][] val = argCapture.getValue();
        assertTwoDArrayEquals("Incorrect orthogonalisation matrix for triclinic", tricOrthoTensor, argCapture.getValue(), 1e-5);
    }

//    @Test
//    public void testFractionalisationMatrix() {
//        // - calculation of metric tensor
//        when(mockMaths.createMatrix(argThat(new twoDArrayAlmostEquals(tricGMat)))).thenReturn(mockMatrix);   //argThat(arrayAllElementsNonZero)
//        // - inversion of G-matrix
//        Matrix mockRecipMat = mock(Matrix.class);
//        when(mockMatrix.getInverse()).thenReturn(mockRecipMat);
//        // - determination of reciprocal Lattice
//        when(mockRecipMat.toArray()).thenReturn(tricGMatInv);
//
//
//        when(mockMaths.createMatrix(argThat(arrayLowerLeftZero))).thenReturn(mockMatrix);
//
//        uc = new UnitCellImpl(mockMaths, cubicLatt);
//        verify(mockMatrix, times(1)).getInverse();
//    }

}
