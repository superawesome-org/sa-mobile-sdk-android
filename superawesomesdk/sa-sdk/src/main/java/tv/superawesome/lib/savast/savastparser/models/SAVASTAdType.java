package tv.superawesome.lib.savast.savastparser.models;

/**
 * Created by gabriel.coman on 22/12/15.
 */
public enum SAVASTAdType {
    Invalid {
        @Override
        public String toString() {
            return "Invalid";
        }
    },
    InLine {
        @Override
        public String toString() {
            return "InLine";
        }
    },
    Wrapper {
        @Override
        public String toString() {
            return "Wrapper";
        }
    }
}
