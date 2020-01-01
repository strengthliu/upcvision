package com.surpass.vision.domain;

import java.util.ArrayList;
import java.util.List;

public class DepartmentInfoExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public DepartmentInfoExample() {
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

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andDepartnameIsNull() {
            addCriterion("departName is null");
            return (Criteria) this;
        }

        public Criteria andDepartnameIsNotNull() {
            addCriterion("departName is not null");
            return (Criteria) this;
        }

        public Criteria andDepartnameEqualTo(String value) {
            addCriterion("departName =", value, "departname");
            return (Criteria) this;
        }

        public Criteria andDepartnameNotEqualTo(String value) {
            addCriterion("departName <>", value, "departname");
            return (Criteria) this;
        }

        public Criteria andDepartnameGreaterThan(String value) {
            addCriterion("departName >", value, "departname");
            return (Criteria) this;
        }

        public Criteria andDepartnameGreaterThanOrEqualTo(String value) {
            addCriterion("departName >=", value, "departname");
            return (Criteria) this;
        }

        public Criteria andDepartnameLessThan(String value) {
            addCriterion("departName <", value, "departname");
            return (Criteria) this;
        }

        public Criteria andDepartnameLessThanOrEqualTo(String value) {
            addCriterion("departName <=", value, "departname");
            return (Criteria) this;
        }

        public Criteria andDepartnameLike(String value) {
            addCriterion("departName like", value, "departname");
            return (Criteria) this;
        }

        public Criteria andDepartnameNotLike(String value) {
            addCriterion("departName not like", value, "departname");
            return (Criteria) this;
        }

        public Criteria andDepartnameIn(List<String> values) {
            addCriterion("departName in", values, "departname");
            return (Criteria) this;
        }

        public Criteria andDepartnameNotIn(List<String> values) {
            addCriterion("departName not in", values, "departname");
            return (Criteria) this;
        }

        public Criteria andDepartnameBetween(String value1, String value2) {
            addCriterion("departName between", value1, value2, "departname");
            return (Criteria) this;
        }

        public Criteria andDepartnameNotBetween(String value1, String value2) {
            addCriterion("departName not between", value1, value2, "departname");
            return (Criteria) this;
        }

        public Criteria andDepartdescIsNull() {
            addCriterion("departDesc is null");
            return (Criteria) this;
        }

        public Criteria andDepartdescIsNotNull() {
            addCriterion("departDesc is not null");
            return (Criteria) this;
        }

        public Criteria andDepartdescEqualTo(String value) {
            addCriterion("departDesc =", value, "departdesc");
            return (Criteria) this;
        }

        public Criteria andDepartdescNotEqualTo(String value) {
            addCriterion("departDesc <>", value, "departdesc");
            return (Criteria) this;
        }

        public Criteria andDepartdescGreaterThan(String value) {
            addCriterion("departDesc >", value, "departdesc");
            return (Criteria) this;
        }

        public Criteria andDepartdescGreaterThanOrEqualTo(String value) {
            addCriterion("departDesc >=", value, "departdesc");
            return (Criteria) this;
        }

        public Criteria andDepartdescLessThan(String value) {
            addCriterion("departDesc <", value, "departdesc");
            return (Criteria) this;
        }

        public Criteria andDepartdescLessThanOrEqualTo(String value) {
            addCriterion("departDesc <=", value, "departdesc");
            return (Criteria) this;
        }

        public Criteria andDepartdescLike(String value) {
            addCriterion("departDesc like", value, "departdesc");
            return (Criteria) this;
        }

        public Criteria andDepartdescNotLike(String value) {
            addCriterion("departDesc not like", value, "departdesc");
            return (Criteria) this;
        }

        public Criteria andDepartdescIn(List<String> values) {
            addCriterion("departDesc in", values, "departdesc");
            return (Criteria) this;
        }

        public Criteria andDepartdescNotIn(List<String> values) {
            addCriterion("departDesc not in", values, "departdesc");
            return (Criteria) this;
        }

        public Criteria andDepartdescBetween(String value1, String value2) {
            addCriterion("departDesc between", value1, value2, "departdesc");
            return (Criteria) this;
        }

        public Criteria andDepartdescNotBetween(String value1, String value2) {
            addCriterion("departDesc not between", value1, value2, "departdesc");
            return (Criteria) this;
        }

        public Criteria andGraphsIsNull() {
            addCriterion("graphs is null");
            return (Criteria) this;
        }

        public Criteria andGraphsIsNotNull() {
            addCriterion("graphs is not null");
            return (Criteria) this;
        }

        public Criteria andGraphsEqualTo(String value) {
            addCriterion("graphs =", value, "graphs");
            return (Criteria) this;
        }

        public Criteria andGraphsNotEqualTo(String value) {
            addCriterion("graphs <>", value, "graphs");
            return (Criteria) this;
        }

        public Criteria andGraphsGreaterThan(String value) {
            addCriterion("graphs >", value, "graphs");
            return (Criteria) this;
        }

        public Criteria andGraphsGreaterThanOrEqualTo(String value) {
            addCriterion("graphs >=", value, "graphs");
            return (Criteria) this;
        }

        public Criteria andGraphsLessThan(String value) {
            addCriterion("graphs <", value, "graphs");
            return (Criteria) this;
        }

        public Criteria andGraphsLessThanOrEqualTo(String value) {
            addCriterion("graphs <=", value, "graphs");
            return (Criteria) this;
        }

        public Criteria andGraphsLike(String value) {
            addCriterion("graphs like", value, "graphs");
            return (Criteria) this;
        }

        public Criteria andGraphsNotLike(String value) {
            addCriterion("graphs not like", value, "graphs");
            return (Criteria) this;
        }

        public Criteria andGraphsIn(List<String> values) {
            addCriterion("graphs in", values, "graphs");
            return (Criteria) this;
        }

        public Criteria andGraphsNotIn(List<String> values) {
            addCriterion("graphs not in", values, "graphs");
            return (Criteria) this;
        }

        public Criteria andGraphsBetween(String value1, String value2) {
            addCriterion("graphs between", value1, value2, "graphs");
            return (Criteria) this;
        }

        public Criteria andGraphsNotBetween(String value1, String value2) {
            addCriterion("graphs not between", value1, value2, "graphs");
            return (Criteria) this;
        }

        public Criteria andXygraphIsNull() {
            addCriterion("xygraph is null");
            return (Criteria) this;
        }

        public Criteria andXygraphIsNotNull() {
            addCriterion("xygraph is not null");
            return (Criteria) this;
        }

        public Criteria andXygraphEqualTo(String value) {
            addCriterion("xygraph =", value, "xygraph");
            return (Criteria) this;
        }

        public Criteria andXygraphNotEqualTo(String value) {
            addCriterion("xygraph <>", value, "xygraph");
            return (Criteria) this;
        }

        public Criteria andXygraphGreaterThan(String value) {
            addCriterion("xygraph >", value, "xygraph");
            return (Criteria) this;
        }

        public Criteria andXygraphGreaterThanOrEqualTo(String value) {
            addCriterion("xygraph >=", value, "xygraph");
            return (Criteria) this;
        }

        public Criteria andXygraphLessThan(String value) {
            addCriterion("xygraph <", value, "xygraph");
            return (Criteria) this;
        }

        public Criteria andXygraphLessThanOrEqualTo(String value) {
            addCriterion("xygraph <=", value, "xygraph");
            return (Criteria) this;
        }

        public Criteria andXygraphLike(String value) {
            addCriterion("xygraph like", value, "xygraph");
            return (Criteria) this;
        }

        public Criteria andXygraphNotLike(String value) {
            addCriterion("xygraph not like", value, "xygraph");
            return (Criteria) this;
        }

        public Criteria andXygraphIn(List<String> values) {
            addCriterion("xygraph in", values, "xygraph");
            return (Criteria) this;
        }

        public Criteria andXygraphNotIn(List<String> values) {
            addCriterion("xygraph not in", values, "xygraph");
            return (Criteria) this;
        }

        public Criteria andXygraphBetween(String value1, String value2) {
            addCriterion("xygraph between", value1, value2, "xygraph");
            return (Criteria) this;
        }

        public Criteria andXygraphNotBetween(String value1, String value2) {
            addCriterion("xygraph not between", value1, value2, "xygraph");
            return (Criteria) this;
        }

        public Criteria andRealtimedataIsNull() {
            addCriterion("realtimedata is null");
            return (Criteria) this;
        }

        public Criteria andRealtimedataIsNotNull() {
            addCriterion("realtimedata is not null");
            return (Criteria) this;
        }

        public Criteria andRealtimedataEqualTo(String value) {
            addCriterion("realtimedata =", value, "realtimedata");
            return (Criteria) this;
        }

        public Criteria andRealtimedataNotEqualTo(String value) {
            addCriterion("realtimedata <>", value, "realtimedata");
            return (Criteria) this;
        }

        public Criteria andRealtimedataGreaterThan(String value) {
            addCriterion("realtimedata >", value, "realtimedata");
            return (Criteria) this;
        }

        public Criteria andRealtimedataGreaterThanOrEqualTo(String value) {
            addCriterion("realtimedata >=", value, "realtimedata");
            return (Criteria) this;
        }

        public Criteria andRealtimedataLessThan(String value) {
            addCriterion("realtimedata <", value, "realtimedata");
            return (Criteria) this;
        }

        public Criteria andRealtimedataLessThanOrEqualTo(String value) {
            addCriterion("realtimedata <=", value, "realtimedata");
            return (Criteria) this;
        }

        public Criteria andRealtimedataLike(String value) {
            addCriterion("realtimedata like", value, "realtimedata");
            return (Criteria) this;
        }

        public Criteria andRealtimedataNotLike(String value) {
            addCriterion("realtimedata not like", value, "realtimedata");
            return (Criteria) this;
        }

        public Criteria andRealtimedataIn(List<String> values) {
            addCriterion("realtimedata in", values, "realtimedata");
            return (Criteria) this;
        }

        public Criteria andRealtimedataNotIn(List<String> values) {
            addCriterion("realtimedata not in", values, "realtimedata");
            return (Criteria) this;
        }

        public Criteria andRealtimedataBetween(String value1, String value2) {
            addCriterion("realtimedata between", value1, value2, "realtimedata");
            return (Criteria) this;
        }

        public Criteria andRealtimedataNotBetween(String value1, String value2) {
            addCriterion("realtimedata not between", value1, value2, "realtimedata");
            return (Criteria) this;
        }

        public Criteria andAlertdataIsNull() {
            addCriterion("alertdata is null");
            return (Criteria) this;
        }

        public Criteria andAlertdataIsNotNull() {
            addCriterion("alertdata is not null");
            return (Criteria) this;
        }

        public Criteria andAlertdataEqualTo(String value) {
            addCriterion("alertdata =", value, "alertdata");
            return (Criteria) this;
        }

        public Criteria andAlertdataNotEqualTo(String value) {
            addCriterion("alertdata <>", value, "alertdata");
            return (Criteria) this;
        }

        public Criteria andAlertdataGreaterThan(String value) {
            addCriterion("alertdata >", value, "alertdata");
            return (Criteria) this;
        }

        public Criteria andAlertdataGreaterThanOrEqualTo(String value) {
            addCriterion("alertdata >=", value, "alertdata");
            return (Criteria) this;
        }

        public Criteria andAlertdataLessThan(String value) {
            addCriterion("alertdata <", value, "alertdata");
            return (Criteria) this;
        }

        public Criteria andAlertdataLessThanOrEqualTo(String value) {
            addCriterion("alertdata <=", value, "alertdata");
            return (Criteria) this;
        }

        public Criteria andAlertdataLike(String value) {
            addCriterion("alertdata like", value, "alertdata");
            return (Criteria) this;
        }

        public Criteria andAlertdataNotLike(String value) {
            addCriterion("alertdata not like", value, "alertdata");
            return (Criteria) this;
        }

        public Criteria andAlertdataIn(List<String> values) {
            addCriterion("alertdata in", values, "alertdata");
            return (Criteria) this;
        }

        public Criteria andAlertdataNotIn(List<String> values) {
            addCriterion("alertdata not in", values, "alertdata");
            return (Criteria) this;
        }

        public Criteria andAlertdataBetween(String value1, String value2) {
            addCriterion("alertdata between", value1, value2, "alertdata");
            return (Criteria) this;
        }

        public Criteria andAlertdataNotBetween(String value1, String value2) {
            addCriterion("alertdata not between", value1, value2, "alertdata");
            return (Criteria) this;
        }

        public Criteria andHistorydataIsNull() {
            addCriterion("historydata is null");
            return (Criteria) this;
        }

        public Criteria andHistorydataIsNotNull() {
            addCriterion("historydata is not null");
            return (Criteria) this;
        }

        public Criteria andHistorydataEqualTo(String value) {
            addCriterion("historydata =", value, "historydata");
            return (Criteria) this;
        }

        public Criteria andHistorydataNotEqualTo(String value) {
            addCriterion("historydata <>", value, "historydata");
            return (Criteria) this;
        }

        public Criteria andHistorydataGreaterThan(String value) {
            addCriterion("historydata >", value, "historydata");
            return (Criteria) this;
        }

        public Criteria andHistorydataGreaterThanOrEqualTo(String value) {
            addCriterion("historydata >=", value, "historydata");
            return (Criteria) this;
        }

        public Criteria andHistorydataLessThan(String value) {
            addCriterion("historydata <", value, "historydata");
            return (Criteria) this;
        }

        public Criteria andHistorydataLessThanOrEqualTo(String value) {
            addCriterion("historydata <=", value, "historydata");
            return (Criteria) this;
        }

        public Criteria andHistorydataLike(String value) {
            addCriterion("historydata like", value, "historydata");
            return (Criteria) this;
        }

        public Criteria andHistorydataNotLike(String value) {
            addCriterion("historydata not like", value, "historydata");
            return (Criteria) this;
        }

        public Criteria andHistorydataIn(List<String> values) {
            addCriterion("historydata in", values, "historydata");
            return (Criteria) this;
        }

        public Criteria andHistorydataNotIn(List<String> values) {
            addCriterion("historydata not in", values, "historydata");
            return (Criteria) this;
        }

        public Criteria andHistorydataBetween(String value1, String value2) {
            addCriterion("historydata between", value1, value2, "historydata");
            return (Criteria) this;
        }

        public Criteria andHistorydataNotBetween(String value1, String value2) {
            addCriterion("historydata not between", value1, value2, "historydata");
            return (Criteria) this;
        }

        public Criteria andLinealertdataIsNull() {
            addCriterion("linealertdata is null");
            return (Criteria) this;
        }

        public Criteria andLinealertdataIsNotNull() {
            addCriterion("linealertdata is not null");
            return (Criteria) this;
        }

        public Criteria andLinealertdataEqualTo(String value) {
            addCriterion("linealertdata =", value, "linealertdata");
            return (Criteria) this;
        }

        public Criteria andLinealertdataNotEqualTo(String value) {
            addCriterion("linealertdata <>", value, "linealertdata");
            return (Criteria) this;
        }

        public Criteria andLinealertdataGreaterThan(String value) {
            addCriterion("linealertdata >", value, "linealertdata");
            return (Criteria) this;
        }

        public Criteria andLinealertdataGreaterThanOrEqualTo(String value) {
            addCriterion("linealertdata >=", value, "linealertdata");
            return (Criteria) this;
        }

        public Criteria andLinealertdataLessThan(String value) {
            addCriterion("linealertdata <", value, "linealertdata");
            return (Criteria) this;
        }

        public Criteria andLinealertdataLessThanOrEqualTo(String value) {
            addCriterion("linealertdata <=", value, "linealertdata");
            return (Criteria) this;
        }

        public Criteria andLinealertdataLike(String value) {
            addCriterion("linealertdata like", value, "linealertdata");
            return (Criteria) this;
        }

        public Criteria andLinealertdataNotLike(String value) {
            addCriterion("linealertdata not like", value, "linealertdata");
            return (Criteria) this;
        }

        public Criteria andLinealertdataIn(List<String> values) {
            addCriterion("linealertdata in", values, "linealertdata");
            return (Criteria) this;
        }

        public Criteria andLinealertdataNotIn(List<String> values) {
            addCriterion("linealertdata not in", values, "linealertdata");
            return (Criteria) this;
        }

        public Criteria andLinealertdataBetween(String value1, String value2) {
            addCriterion("linealertdata between", value1, value2, "linealertdata");
            return (Criteria) this;
        }

        public Criteria andLinealertdataNotBetween(String value1, String value2) {
            addCriterion("linealertdata not between", value1, value2, "linealertdata");
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