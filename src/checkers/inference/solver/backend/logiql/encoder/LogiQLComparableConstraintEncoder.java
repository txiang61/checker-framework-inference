package checkers.inference.solver.backend.logiql.encoder;

import checkers.inference.model.ComparableConstraint.ComparableOperationKind;
import checkers.inference.model.ConstantSlot;
import checkers.inference.model.Slot;
import checkers.inference.solver.backend.encoder.ComparableConstraintEncoder;
import checkers.inference.solver.frontend.Lattice;
import checkers.inference.solver.util.NameUtils;

public class LogiQLComparableConstraintEncoder extends LogiQLAbstractConstraintEncoder implements ComparableConstraintEncoder<String> {

    public LogiQLComparableConstraintEncoder(Lattice lattice) {
        super(lattice);
    }

	@Override
	public String encodeVariable_Variable(ComparableOperationKind operation, Slot fst, Slot snd) {
		String logiQLData = "+comparableConstraint(v1, v2), +variable(v1), +hasvariableName[v1] = "
                + fst.getId() + ", +variable(v2), +hasvariableName[v2] = " + snd.getId() + ".\n";
        return logiQLData;
	}

	@Override
	public String encodeVariable_Constant(ComparableOperationKind operation, Slot fst, ConstantSlot snd) {
		String constantName = NameUtils.getSimpleName(snd.getValue());
        int variableId = fst.getId();
        String logiQLData = "+comparableConstraint(v, c), +variable(v), +hasvariableName[v] = \""
                + variableId + "\", +constant(c), +hasconstantName[c] = " + constantName + ".\n";
        return logiQLData;
	}

	@Override
	public String encodeConstant_Variable(ComparableOperationKind operation, ConstantSlot fst, Slot snd) {
		String constantName = NameUtils.getSimpleName(fst.getValue());
        int variableId = snd.getId();
        String logiQLData = "+comparableConstraint(c, v), +constant(c), +hasconstantName[c] = \""
                + constantName + "\", +variable(v), +hasvariableName[v] = " + variableId + ".\n";
        return logiQLData;
	}

	@Override
	public String encodeConstant_Constant(ComparableOperationKind operation, ConstantSlot fst, ConstantSlot snd) {
		String constantNamefst = NameUtils.getSimpleName(fst.getValue());
		String constantNamesnd = NameUtils.getSimpleName(snd.getValue());
        String logiQLData = "+comparableConstraint(c1, c2), +constant(c1), +hasconstantName[c1] = \""
                + constantNamefst + "\", +constant(c2), +hasconstantName[c2] = " + constantNamesnd + ".\n";
        return logiQLData;
	}
}
