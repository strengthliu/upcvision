package com.surpass.vision.domain;

import java.util.ArrayList;
import java.util.List;

public class PointGroupDataExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public PointGroupDataExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Double value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Double value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Double value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Double value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Double value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Double value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Double> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Double> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Double value1, Double value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Double value1, Double value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andTypeIsNull() {
            addCriterion("type is null");
            return (Criteria) this;
        }

        public Criteria andTypeIsNotNull() {
            addCriterion("type is not null");
            return (Criteria) this;
        }

        public Criteria andTypeEqualTo(String value) {
            addCriterion("type =", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotEqualTo(String value) {
            addCriterion("type <>", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThan(String value) {
            addCriterion("type >", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThanOrEqualTo(String value) {
            addCriterion("type >=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThan(String value) {
            addCriterion("type <", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThanOrEqualTo(String value) {
            addCriterion("type <=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLike(String value) {
            addCriterion("type like", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotLike(String value) {
            addCriterion("type not like", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeIn(List<String> values) {
            addCriterion("type in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotIn(List<String> values) {
            addCriterion("type not in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeBetween(String value1, String value2) {
            addCriterion("type between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotBetween(String value1, String value2) {
            addCriterion("type not between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andNameIsNull() {
            addCriterion("name is null");
            return (Criteria) this;
        }

        public Criteria andNameIsNotNull() {
            addCriterion("name is not null");
            return (Criteria) this;
        }

        public Criteria andNameEqualTo(String value) {
            addCriterion("name =", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotEqualTo(String value) {
            addCriterion("name <>", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThan(String value) {
            addCriterion("name >", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThanOrEqualTo(String value) {
            addCriterion("name >=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThan(String value) {
            addCriterion("name <", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThanOrEqualTo(String value) {
            addCriterion("name <=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLike(String value) {
            addCriterion("name like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotLike(String value) {
            addCriterion("name not like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameIn(List<String> values) {
            addCriterion("name in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotIn(List<String> values) {
            addCriterion("name not in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameBetween(String value1, String value2) {
            addCriterion("name between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotBetween(String value1, String value2) {
            addCriterion("name not between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andOwnerIsNull() {
            addCriterion("owner is null");
            return (Criteria) this;
        }

        public Criteria andOwnerIsNotNull() {
            addCriterion("owner is not null");
            return (Criteria) this;
        }

        public Criteria andOwnerEqualTo(String value) {
            addCriterion("owner =", value, "owner");
            return (Criteria) this;
        }

        public Criteria andOwnerNotEqualTo(String value) {
            addCriterion("owner <>", value, "owner");
            return (Criteria) this;
        }

        public Criteria andOwnerGreaterThan(String value) {
            addCriterion("owner >", value, "owner");
            return (Criteria) this;
        }

        public Criteria andOwnerGreaterThanOrEqualTo(String value) {
            addCriterion("owner >=", value, "owner");
            return (Criteria) this;
        }

        public Criteria andOwnerLessThan(String value) {
            addCriterion("owner <", value, "owner");
            return (Criteria) this;
        }

        public Criteria andOwnerLessThanOrEqualTo(String value) {
            addCriterion("owner <=", value, "owner");
            return (Criteria) this;
        }

        public Criteria andOwnerLike(String value) {
            addCriterion("owner like", value, "owner");
            return (Criteria) this;
        }

        public Criteria andOwnerNotLike(String value) {
            addCriterion("owner not like", value, "owner");
            return (Criteria) this;
        }

        public Criteria andOwnerIn(List<String> values) {
            addCriterion("owner in", values, "owner");
            return (Criteria) this;
        }

        public Criteria andOwnerNotIn(List<String> values) {
            addCriterion("owner not in", values, "owner");
            return (Criteria) this;
        }

        public Criteria andOwnerBetween(String value1, String value2) {
            addCriterion("owner between", value1, value2, "owner");
            return (Criteria) this;
        }

        public Criteria andOwnerNotBetween(String value1, String value2) {
            addCriterion("owner not between", value1, value2, "owner");
            return (Criteria) this;
        }

        public Criteria andCreaterIsNull() {
            addCriterion("creater is null");
            return (Criteria) this;
        }

        public Criteria andCreaterIsNotNull() {
            addCriterion("creater is not null");
            return (Criteria) this;
        }

        public Criteria andCreaterEqualTo(String value) {
            addCriterion("creater =", value, "creater");
            return (Criteria) this;
        }

        public Criteria andCreaterNotEqualTo(String value) {
            addCriterion("creater <>", value, "creater");
            return (Criteria) this;
        }

        public Criteria andCreaterGreaterThan(String value) {
            addCriterion("creater >", value, "creater");
            return (Criteria) this;
        }

        public Criteria andCreaterGreaterThanOrEqualTo(String value) {
            addCriterion("creater >=", value, "creater");
            return (Criteria) this;
        }

        public Criteria andCreaterLessThan(String value) {
            addCriterion("creater <", value, "creater");
            return (Criteria) this;
        }

        public Criteria andCreaterLessThanOrEqualTo(String value) {
            addCriterion("creater <=", value, "creater");
            return (Criteria) this;
        }

        public Criteria andCreaterLike(String value) {
            addCriterion("creater like", value, "creater");
            return (Criteria) this;
        }

        public Criteria andCreaterNotLike(String value) {
            addCriterion("creater not like", value, "creater");
            return (Criteria) this;
        }

        public Criteria andCreaterIn(List<String> values) {
            addCriterion("creater in", values, "creater");
            return (Criteria) this;
        }

        public Criteria andCreaterNotIn(List<String> values) {
            addCriterion("creater not in", values, "creater");
            return (Criteria) this;
        }

        public Criteria andCreaterBetween(String value1, String value2) {
            addCriterion("creater between", value1, value2, "creater");
            return (Criteria) this;
        }

        public Criteria andCreaterNotBetween(String value1, String value2) {
            addCriterion("creater not between", value1, value2, "creater");
            return (Criteria) this;
        }

        public Criteria andSharedIsNull() {
            addCriterion("shared is null");
            return (Criteria) this;
        }

        public Criteria andSharedIsNotNull() {
            addCriterion("shared is not null");
            return (Criteria) this;
        }

        public Criteria andSharedEqualTo(String value) {
            addCriterion("shared =", value, "shared");
            return (Criteria) this;
        }

        public Criteria andSharedNotEqualTo(String value) {
            addCriterion("shared <>", value, "shared");
            return (Criteria) this;
        }

        public Criteria andSharedGreaterThan(String value) {
            addCriterion("shared >", value, "shared");
            return (Criteria) this;
        }

        public Criteria andSharedGreaterThanOrEqualTo(String value) {
            addCriterion("shared >=", value, "shared");
            return (Criteria) this;
        }

        public Criteria andSharedLessThan(String value) {
            addCriterion("shared <", value, "shared");
            return (Criteria) this;
        }

        public Criteria andSharedLessThanOrEqualTo(String value) {
            addCriterion("shared <=", value, "shared");
            return (Criteria) this;
        }

        public Criteria andSharedLike(String value) {
            addCriterion("shared like", value, "shared");
            return (Criteria) this;
        }

        public Criteria andSharedNotLike(String value) {
            addCriterion("shared not like", value, "shared");
            return (Criteria) this;
        }

        public Criteria andSharedIn(List<String> values) {
            addCriterion("shared in", values, "shared");
            return (Criteria) this;
        }

        public Criteria andSharedNotIn(List<String> values) {
            addCriterion("shared not in", values, "shared");
            return (Criteria) this;
        }

        public Criteria andSharedBetween(String value1, String value2) {
            addCriterion("shared between", value1, value2, "shared");
            return (Criteria) this;
        }

        public Criteria andSharedNotBetween(String value1, String value2) {
            addCriterion("shared not between", value1, value2, "shared");
            return (Criteria) this;
        }

        public Criteria andPointsIsNull() {
            addCriterion("points is null");
            return (Criteria) this;
        }

        public Criteria andPointsIsNotNull() {
            addCriterion("points is not null");
            return (Criteria) this;
        }

        public Criteria andPointsEqualTo(String value) {
            addCriterion("points =", value, "points");
            return (Criteria) this;
        }

        public Criteria andPointsNotEqualTo(String value) {
            addCriterion("points <>", value, "points");
            return (Criteria) this;
        }

        public Criteria andPointsGreaterThan(String value) {
            addCriterion("points >", value, "points");
            return (Criteria) this;
        }

        public Criteria andPointsGreaterThanOrEqualTo(String value) {
            addCriterion("points >=", value, "points");
            return (Criteria) this;
        }

        public Criteria andPointsLessThan(String value) {
            addCriterion("points <", value, "points");
            return (Criteria) this;
        }

        public Criteria andPointsLessThanOrEqualTo(String value) {
            addCriterion("points <=", value, "points");
            return (Criteria) this;
        }

        public Criteria andPointsLike(String value) {
            addCriterion("points like", value, "points");
            return (Criteria) this;
        }

        public Criteria andPointsNotLike(String value) {
            addCriterion("points not like", value, "points");
            return (Criteria) this;
        }

        public Criteria andPointsIn(List<String> values) {
            addCriterion("points in", values, "points");
            return (Criteria) this;
        }

        public Criteria andPointsNotIn(List<String> values) {
            addCriterion("points not in", values, "points");
            return (Criteria) this;
        }

        public Criteria andPointsBetween(String value1, String value2) {
            addCriterion("points between", value1, value2, "points");
            return (Criteria) this;
        }

        public Criteria andPointsNotBetween(String value1, String value2) {
            addCriterion("points not between", value1, value2, "points");
            return (Criteria) this;
        }

        public Criteria andOtherrule1IsNull() {
            addCriterion("otherRule1 is null");
            return (Criteria) this;
        }

        public Criteria andOtherrule1IsNotNull() {
            addCriterion("otherRule1 is not null");
            return (Criteria) this;
        }

        public Criteria andOtherrule1EqualTo(String value) {
            addCriterion("otherRule1 =", value, "otherrule1");
            return (Criteria) this;
        }

        public Criteria andOtherrule1NotEqualTo(String value) {
            addCriterion("otherRule1 <>", value, "otherrule1");
            return (Criteria) this;
        }

        public Criteria andOtherrule1GreaterThan(String value) {
            addCriterion("otherRule1 >", value, "otherrule1");
            return (Criteria) this;
        }

        public Criteria andOtherrule1GreaterThanOrEqualTo(String value) {
            addCriterion("otherRule1 >=", value, "otherrule1");
            return (Criteria) this;
        }

        public Criteria andOtherrule1LessThan(String value) {
            addCriterion("otherRule1 <", value, "otherrule1");
            return (Criteria) this;
        }

        public Criteria andOtherrule1LessThanOrEqualTo(String value) {
            addCriterion("otherRule1 <=", value, "otherrule1");
            return (Criteria) this;
        }

        public Criteria andOtherrule1Like(String value) {
            addCriterion("otherRule1 like", value, "otherrule1");
            return (Criteria) this;
        }

        public Criteria andOtherrule1NotLike(String value) {
            addCriterion("otherRule1 not like", value, "otherrule1");
            return (Criteria) this;
        }

        public Criteria andOtherrule1In(List<String> values) {
            addCriterion("otherRule1 in", values, "otherrule1");
            return (Criteria) this;
        }

        public Criteria andOtherrule1NotIn(List<String> values) {
            addCriterion("otherRule1 not in", values, "otherrule1");
            return (Criteria) this;
        }

        public Criteria andOtherrule1Between(String value1, String value2) {
            addCriterion("otherRule1 between", value1, value2, "otherrule1");
            return (Criteria) this;
        }

        public Criteria andOtherrule1NotBetween(String value1, String value2) {
            addCriterion("otherRule1 not between", value1, value2, "otherrule1");
            return (Criteria) this;
        }

        public Criteria andOtherrule2IsNull() {
            addCriterion("otherRule2 is null");
            return (Criteria) this;
        }

        public Criteria andOtherrule2IsNotNull() {
            addCriterion("otherRule2 is not null");
            return (Criteria) this;
        }

        public Criteria andOtherrule2EqualTo(String value) {
            addCriterion("otherRule2 =", value, "otherrule2");
            return (Criteria) this;
        }

        public Criteria andOtherrule2NotEqualTo(String value) {
            addCriterion("otherRule2 <>", value, "otherrule2");
            return (Criteria) this;
        }

        public Criteria andOtherrule2GreaterThan(String value) {
            addCriterion("otherRule2 >", value, "otherrule2");
            return (Criteria) this;
        }

        public Criteria andOtherrule2GreaterThanOrEqualTo(String value) {
            addCriterion("otherRule2 >=", value, "otherrule2");
            return (Criteria) this;
        }

        public Criteria andOtherrule2LessThan(String value) {
            addCriterion("otherRule2 <", value, "otherrule2");
            return (Criteria) this;
        }

        public Criteria andOtherrule2LessThanOrEqualTo(String value) {
            addCriterion("otherRule2 <=", value, "otherrule2");
            return (Criteria) this;
        }

        public Criteria andOtherrule2Like(String value) {
            addCriterion("otherRule2 like", value, "otherrule2");
            return (Criteria) this;
        }

        public Criteria andOtherrule2NotLike(String value) {
            addCriterion("otherRule2 not like", value, "otherrule2");
            return (Criteria) this;
        }

        public Criteria andOtherrule2In(List<String> values) {
            addCriterion("otherRule2 in", values, "otherrule2");
            return (Criteria) this;
        }

        public Criteria andOtherrule2NotIn(List<String> values) {
            addCriterion("otherRule2 not in", values, "otherrule2");
            return (Criteria) this;
        }

        public Criteria andOtherrule2Between(String value1, String value2) {
            addCriterion("otherRule2 between", value1, value2, "otherrule2");
            return (Criteria) this;
        }

        public Criteria andOtherrule2NotBetween(String value1, String value2) {
            addCriterion("otherRule2 not between", value1, value2, "otherrule2");
            return (Criteria) this;
        }

        public Criteria andOtherrule3IsNull() {
            addCriterion("otherRule3 is null");
            return (Criteria) this;
        }

        public Criteria andOtherrule3IsNotNull() {
            addCriterion("otherRule3 is not null");
            return (Criteria) this;
        }

        public Criteria andOtherrule3EqualTo(String value) {
            addCriterion("otherRule3 =", value, "otherrule3");
            return (Criteria) this;
        }

        public Criteria andOtherrule3NotEqualTo(String value) {
            addCriterion("otherRule3 <>", value, "otherrule3");
            return (Criteria) this;
        }

        public Criteria andOtherrule3GreaterThan(String value) {
            addCriterion("otherRule3 >", value, "otherrule3");
            return (Criteria) this;
        }

        public Criteria andOtherrule3GreaterThanOrEqualTo(String value) {
            addCriterion("otherRule3 >=", value, "otherrule3");
            return (Criteria) this;
        }

        public Criteria andOtherrule3LessThan(String value) {
            addCriterion("otherRule3 <", value, "otherrule3");
            return (Criteria) this;
        }

        public Criteria andOtherrule3LessThanOrEqualTo(String value) {
            addCriterion("otherRule3 <=", value, "otherrule3");
            return (Criteria) this;
        }

        public Criteria andOtherrule3Like(String value) {
            addCriterion("otherRule3 like", value, "otherrule3");
            return (Criteria) this;
        }

        public Criteria andOtherrule3NotLike(String value) {
            addCriterion("otherRule3 not like", value, "otherrule3");
            return (Criteria) this;
        }

        public Criteria andOtherrule3In(List<String> values) {
            addCriterion("otherRule3 in", values, "otherrule3");
            return (Criteria) this;
        }

        public Criteria andOtherrule3NotIn(List<String> values) {
            addCriterion("otherRule3 not in", values, "otherrule3");
            return (Criteria) this;
        }

        public Criteria andOtherrule3Between(String value1, String value2) {
            addCriterion("otherRule3 between", value1, value2, "otherrule3");
            return (Criteria) this;
        }

        public Criteria andOtherrule3NotBetween(String value1, String value2) {
            addCriterion("otherRule3 not between", value1, value2, "otherrule3");
            return (Criteria) this;
        }

        public Criteria andOtherrule4IsNull() {
            addCriterion("otherRule4 is null");
            return (Criteria) this;
        }

        public Criteria andOtherrule4IsNotNull() {
            addCriterion("otherRule4 is not null");
            return (Criteria) this;
        }

        public Criteria andOtherrule4EqualTo(String value) {
            addCriterion("otherRule4 =", value, "otherrule4");
            return (Criteria) this;
        }

        public Criteria andOtherrule4NotEqualTo(String value) {
            addCriterion("otherRule4 <>", value, "otherrule4");
            return (Criteria) this;
        }

        public Criteria andOtherrule4GreaterThan(String value) {
            addCriterion("otherRule4 >", value, "otherrule4");
            return (Criteria) this;
        }

        public Criteria andOtherrule4GreaterThanOrEqualTo(String value) {
            addCriterion("otherRule4 >=", value, "otherrule4");
            return (Criteria) this;
        }

        public Criteria andOtherrule4LessThan(String value) {
            addCriterion("otherRule4 <", value, "otherrule4");
            return (Criteria) this;
        }

        public Criteria andOtherrule4LessThanOrEqualTo(String value) {
            addCriterion("otherRule4 <=", value, "otherrule4");
            return (Criteria) this;
        }

        public Criteria andOtherrule4Like(String value) {
            addCriterion("otherRule4 like", value, "otherrule4");
            return (Criteria) this;
        }

        public Criteria andOtherrule4NotLike(String value) {
            addCriterion("otherRule4 not like", value, "otherrule4");
            return (Criteria) this;
        }

        public Criteria andOtherrule4In(List<String> values) {
            addCriterion("otherRule4 in", values, "otherrule4");
            return (Criteria) this;
        }

        public Criteria andOtherrule4NotIn(List<String> values) {
            addCriterion("otherRule4 not in", values, "otherrule4");
            return (Criteria) this;
        }

        public Criteria andOtherrule4Between(String value1, String value2) {
            addCriterion("otherRule4 between", value1, value2, "otherrule4");
            return (Criteria) this;
        }

        public Criteria andOtherrule4NotBetween(String value1, String value2) {
            addCriterion("otherRule4 not between", value1, value2, "otherrule4");
            return (Criteria) this;
        }

        public Criteria andOtherrule5IsNull() {
            addCriterion("otherRule5 is null");
            return (Criteria) this;
        }

        public Criteria andOtherrule5IsNotNull() {
            addCriterion("otherRule5 is not null");
            return (Criteria) this;
        }

        public Criteria andOtherrule5EqualTo(String value) {
            addCriterion("otherRule5 =", value, "otherrule5");
            return (Criteria) this;
        }

        public Criteria andOtherrule5NotEqualTo(String value) {
            addCriterion("otherRule5 <>", value, "otherrule5");
            return (Criteria) this;
        }

        public Criteria andOtherrule5GreaterThan(String value) {
            addCriterion("otherRule5 >", value, "otherrule5");
            return (Criteria) this;
        }

        public Criteria andOtherrule5GreaterThanOrEqualTo(String value) {
            addCriterion("otherRule5 >=", value, "otherrule5");
            return (Criteria) this;
        }

        public Criteria andOtherrule5LessThan(String value) {
            addCriterion("otherRule5 <", value, "otherrule5");
            return (Criteria) this;
        }

        public Criteria andOtherrule5LessThanOrEqualTo(String value) {
            addCriterion("otherRule5 <=", value, "otherrule5");
            return (Criteria) this;
        }

        public Criteria andOtherrule5Like(String value) {
            addCriterion("otherRule5 like", value, "otherrule5");
            return (Criteria) this;
        }

        public Criteria andOtherrule5NotLike(String value) {
            addCriterion("otherRule5 not like", value, "otherrule5");
            return (Criteria) this;
        }

        public Criteria andOtherrule5In(List<String> values) {
            addCriterion("otherRule5 in", values, "otherrule5");
            return (Criteria) this;
        }

        public Criteria andOtherrule5NotIn(List<String> values) {
            addCriterion("otherRule5 not in", values, "otherrule5");
            return (Criteria) this;
        }

        public Criteria andOtherrule5Between(String value1, String value2) {
            addCriterion("otherRule5 between", value1, value2, "otherrule5");
            return (Criteria) this;
        }

        public Criteria andOtherrule5NotBetween(String value1, String value2) {
            addCriterion("otherRule5 not between", value1, value2, "otherrule5");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}