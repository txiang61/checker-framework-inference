import dataflow.qual.DataFlow;

public class TestUpperBound4Invalid {

    public @DataFlow(typeNames = {"java.lang.Object"})
    Object invalidUpperBound(int c) {
        if (c > 0) {
            // :: error: (return.type.incompatible)
            return "I am a String!";
        }
        // :: error: (return.type.incompatible)
        return "I am a String too!";
    }
}
