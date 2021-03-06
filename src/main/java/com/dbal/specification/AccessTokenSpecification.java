package com.dbal.specification;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

public abstract class AccessTokenSpecification {
    private AccessTokenSpecification() {
        throw new IllegalStateException("Utility class");
    }

    public static Specifiable getByAccessToken(String accessToken) {
        return new AbstractSpecification() {
            @Override
            public Criterion toCriterion() {
                return Restrictions.eq("accessToken", accessToken);
            }
        };
    }

    public static Specifiable getByPlayerId(int playerId) {
        return new AbstractSpecification() {
            @Override
            public Criterion toCriterion() {
                return Restrictions.eq("player", playerId);
            }
        };
    }
}
