package pl.mjedynak.idea.plugins.builder.psi;

public class Wot {

    private String lol;
    private long asdf;
    private Exception hahaha;

    public Wot(final String lol, final long asdf, final Exception hahaha) {
        this.lol = lol;
        this.asdf = asdf;
        this.hahaha = hahaha;
    }


    public static final class Builder {
        private String lol;
        private long asdf;
        private Exception hahaha;

        private Builder() {
        }

        public static Builder aWot() {
            return new Builder();
        }

        public Builder withLol(String lol) {
            this.lol = lol;
            return this;
        }

        public Builder withAsdf(long asdf) {
            this.asdf = asdf;
            return this;
        }

        public Builder withHahaha(Exception hahaha) {
            this.hahaha = hahaha;
            return this;
        }

        public Builder seededFrom(Wot seed) {
            return aWot().withLol(seed.lol).withAsdf(seed.asdf).withHahaha(seed.hahaha);
        }

        public Wot build() {
            Wot wot = new Wot(lol, asdf, hahaha);
            return wot;
        }
    }
}
