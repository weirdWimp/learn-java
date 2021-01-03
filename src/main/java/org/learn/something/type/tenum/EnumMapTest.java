package org.learn.something.type.tenum;

import java.util.EnumMap;
import java.util.Map;

public class EnumMapTest {

    public enum Phase {
        SOLID, LIQUID;

        public enum Transaction {
            MELT(SOLID, LIQUID),
            FREEZE(LIQUID, SOLID);

            private Phase from;

            private Phase to;

            Transaction(Phase from, Phase to) {
                this.from = from;
                this.to = to;
            }

            public Phase getFrom() {
                return from;
            }

            public Phase getTo() {
                return to;
            }

            public static final Map<Phase, Map<Phase, Transaction>> m =
                    new EnumMap<Phase, Map<Phase, Transaction>>(Phase.class);

            static {
                for (Phase phase : Phase.values()) {
                    m.put(phase, new EnumMap<Phase, Transaction>(Phase.class));
                }

                for (Transaction transaction : values()) {
                    m.get(transaction.from).put(transaction.to, transaction);
                }
            }

            public static Transaction from(Phase from, Phase to) {
                return m.get(from).get(to);
            }
        }

    }

    public static void main(String[] args) {

    }


}
