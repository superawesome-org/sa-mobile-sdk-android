package tv.superawesome.lib.savast.savastparser.models;

/**
 * Created by gabriel.coman on 22/12/15.
 */
public enum SAVASTCreativeType {
    Linear {
        @Override
        public String toString() {
            return "Linear";
        }
    },
    NonLinear {
        @Override
        public String toString() {
            return "NonLinear";
        }
    },
    CompanionAds {
        @Override
        public String toString() {
            return "CompanionAds";
        }
    }
}
