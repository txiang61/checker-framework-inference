package checkers.inference.solver.backend.maxsat.encoder;

import checkers.inference.model.ComparableConstraint.ComparableOperationKind;
import checkers.inference.model.ConstantSlot;
import checkers.inference.model.VariableSlot;
import checkers.inference.solver.backend.encoder.binary.ComparableConstraintEncoder;
import checkers.inference.solver.backend.maxsat.MathUtils;
import checkers.inference.solver.backend.maxsat.VectorUtils;
import checkers.inference.solver.frontend.Lattice;
import org.sat4j.core.VecInt;

import javax.lang.model.element.AnnotationMirror;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MaxSATComparableConstraintEncoder extends MaxSATAbstractConstraintEncoder implements ComparableConstraintEncoder<VecInt[]> {

    public MaxSATComparableConstraintEncoder(Lattice lattice, Map<AnnotationMirror, Integer> typeToInt) {
        super(lattice, typeToInt);
    }

    @Override
    public VecInt[] encodeVariable_Variable(VariableSlot fst, VariableSlot snd) {
        // a <=> !b which is the same as (!a v !b) & (b v a)
        List<VecInt> list = new ArrayList<VecInt>();
        for (AnnotationMirror type : lattice.allTypes) {
            if (lattice.incomparableType.keySet().contains(type)) {
                for (AnnotationMirror notComparable : lattice.incomparableType.get(type)) {
                    list.add(VectorUtils.asVec(
                            -MathUtils.mapIdToMatrixEntry(fst.getId(), typeToInt.get(type), lattice),
                            -MathUtils.mapIdToMatrixEntry(snd.getId(), typeToInt.get(notComparable), lattice),
                            MathUtils.mapIdToMatrixEntry(snd.getId(), typeToInt.get(notComparable), lattice),
                            MathUtils.mapIdToMatrixEntry(fst.getId(), typeToInt.get(type), lattice)));
                }
            }
        }
        VecInt[] result = list.toArray(new VecInt[list.size()]);
        return result;
    }

    @Override
    public VecInt[] encodeVariable_Constant(VariableSlot fst, ConstantSlot snd) {
        if (lattice.incomparableType.keySet().contains(snd.getValue())) {
            List<VecInt> resultList = new ArrayList<>();
            for (AnnotationMirror incomparable : lattice.incomparableType.get(snd.getValue())) {
                // Should not be equal to incomparable
                resultList.add(
                    VectorUtils.asVec(
                        -MathUtils.mapIdToMatrixEntry(fst.getId(), typeToInt.get(incomparable), lattice)));
            }
            VecInt[] resultArray = new VecInt[resultList.size()];
            return resultList.toArray(resultArray);
        } else {
            return emptyValue;
        }
    }

    @Override
    public VecInt[] encodeConstant_Variable(ConstantSlot fst, VariableSlot snd) {
        return encodeVariable_Constant(snd, fst);
    }

	@Override
	public VecInt[] encodeVariable_Variable(ComparableOperationKind operation, VariableSlot fst, VariableSlot snd) {
		return encodeVariable_Variable(fst, snd);
	}

	@Override
	public VecInt[] encodeVariable_Constant(ComparableOperationKind operation, VariableSlot fst, ConstantSlot snd) {
		return encodeVariable_Constant(fst, snd);
	}

	@Override
	public VecInt[] encodeConstant_Variable(ComparableOperationKind operation, ConstantSlot fst, VariableSlot snd) {
		return encodeConstant_Variable(fst, snd);
	}

	@Override
	public VecInt[] encodeConstant_Constant(ComparableOperationKind operation, ConstantSlot fst, ConstantSlot snd) {
		return null;
	}
}
