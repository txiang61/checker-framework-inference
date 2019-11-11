package checkers.inference.solver.backend.encoder.binary;

import checkers.inference.model.ComparableConstraint.ComparableOperationKind;
import checkers.inference.model.ConstantSlot;
import checkers.inference.model.Slot;
import checkers.inference.model.VariableSlot;
import checkers.inference.solver.backend.encoder.AbstractConstraintEncoderFactory;

/**
 * A marker interface that all constraint encoders that support encoding {@link checkers.inference.model.ComparableConstraint}
 * should implement. Otherwise, the encoder will be considered not supporting encoding comparable
 * constraint and rejected by the {@link AbstractConstraintEncoderFactory#createComparableConstraintEncoder()}
 *
 * @see checkers.inference.model.ComparableConstraint
 * @see AbstractConstraintEncoderFactory#createComparableConstraintEncoder()
 */
public interface ComparableConstraintEncoder<ConstraintEncodingT> extends BinaryConstraintEncoder<ConstraintEncodingT> {
	ConstraintEncodingT encodeVariable_Variable(ComparableOperationKind operation,
            Slot fst, Slot snd);

    ConstraintEncodingT encodeVariable_Constant(ComparableOperationKind operation,
            Slot fst, ConstantSlot snd);

    ConstraintEncodingT encodeConstant_Variable(ComparableOperationKind operation,
            ConstantSlot fst, Slot snd);

    ConstraintEncodingT encodeConstant_Constant(ComparableOperationKind operation,
            ConstantSlot fst, ConstantSlot snd);
}
