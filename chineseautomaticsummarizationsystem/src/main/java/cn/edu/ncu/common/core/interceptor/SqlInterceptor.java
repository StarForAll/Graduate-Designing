package cn.edu.ncu.common.core.interceptor;

import cn.edu.ncu.common.util.basic.ReflectUtil;
import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.expr.SQLBinaryOpExpr;
import com.alibaba.druid.sql.ast.expr.SQLBinaryOperator;
import com.alibaba.druid.sql.ast.statement.*;
import com.alibaba.druid.sql.parser.SQLExprParser;
import com.alibaba.druid.sql.parser.SQLParserUtils;
import com.alibaba.druid.sql.parser.SQLStatementParser;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.sql.Connection;
import java.util.*;
/**
 * @Author: XiongZhiCong
 * @Description: 暂时不使用这个mybatis拦截器
 * @Date: Created in 14:54 2021/4/8
 * @Modified By:
 */
//@Intercepts({
//        @Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})
//})
//@Configuration
//@Component
public class SqlInterceptor implements Interceptor {


    @PostConstruct
    public void init() {
    }

    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        Map<String,Object> map  = new HashMap<>();
        RoutingStatementHandler handler = (RoutingStatementHandler) invocation.getTarget();
        StatementHandler delegate = (StatementHandler) ReflectUtil.getFieldValue(handler, "delegate");
        MappedStatement mappedStatement = (MappedStatement) ReflectUtil.getFieldValue(delegate,"mappedStatement") ;
        //获取请求路径
        String requestPath = mappedStatement.getResource();
        //获取路径对应的请求mapper
        String requestMapper = requestPath.substring(requestPath.lastIndexOf('\\')+1,requestPath.lastIndexOf('.'));
        BoundSql boundSql = delegate.getBoundSql();
        String sql = boundSql.getSql();
        String mSql =sql;
        if(map.size() != 0) {
            mSql = contactConditions(sql,map);
        }
        //再通过反射把新sql设置进去
        ReflectUtil.setFieldValue(boundSql, "sql", mSql);
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, new SqlInterceptor());
    }

    @Override
    public void setProperties(Properties properties) {
        //nothing to do
    }

    private static String contactConditions(String sql, Map<String, Object> columnMap) {
        SQLStatementParser parser = SQLParserUtils.createSQLStatementParser(sql, "mysql");
        List<SQLStatement> stmtList = parser.parseStatementList();
        SQLStatement stmt = stmtList.get(0);
        StringBuffer constraintsBuffer = new StringBuffer();
        Set<String> keys = columnMap.keySet();
        Iterator<String> keyIter = keys.iterator();
        if (keyIter.hasNext()) {
            String key = keyIter.next();
            constraintsBuffer.append(key).append(" = " + getSqlByClass(columnMap.get(key)));
        }
        while (keyIter.hasNext()) {
            String key = keyIter.next();
            constraintsBuffer.append(" AND ").append(key).append(" = " + getSqlByClass(columnMap.get(key)));
        }
        SQLExprParser constraintsParser = SQLParserUtils.createExprParser(constraintsBuffer.toString(), "mysql");
        SQLExpr constraintsExpr = constraintsParser.expr();
        if (stmt instanceof SQLSelectStatement) {
            SQLSelectStatement selectStmt = (SQLSelectStatement) stmt;
            // 拿到SQLSelect
            SQLSelect sqlselect = selectStmt.getSelect();
            SQLSelectQueryBlock query = (SQLSelectQueryBlock) sqlselect.getQuery();
            SQLExpr whereExpr = query.getWhere();
            // 修改where表达式
            if (whereExpr == null) {
                query.setWhere(constraintsExpr);
            } else {
                SQLBinaryOpExpr newWhereExpr = new SQLBinaryOpExpr(whereExpr, SQLBinaryOperator.BooleanAnd, constraintsExpr);
                query.setWhere(newWhereExpr);
            }
            sqlselect.setQuery(query);
            return sqlselect.toString();

        }
        if (stmt instanceof SQLUpdateStatement) {
            SQLUpdateStatement updateStmt = (SQLUpdateStatement) stmt;
            SQLExpr whereExpr = updateStmt.getWhere();
            if (whereExpr == null) {
                updateStmt.setWhere(constraintsExpr);
            } else {
                SQLBinaryOpExpr newWhereExpr = new SQLBinaryOpExpr(whereExpr, SQLBinaryOperator.BooleanAnd, constraintsExpr);
                updateStmt.setWhere(newWhereExpr);
            }
            return updateStmt.toString();
        }
        if (stmt instanceof SQLDeleteStatement) {
            SQLDeleteStatement deleteStmt = (SQLDeleteStatement) stmt;
            SQLExpr whereExpr = deleteStmt.getWhere();
            if (whereExpr == null) {
                deleteStmt.setWhere(constraintsExpr);
            } else {
                SQLBinaryOpExpr newWhereExpr = new SQLBinaryOpExpr(whereExpr, SQLBinaryOperator.BooleanAnd, constraintsExpr);
                deleteStmt.setWhere(newWhereExpr);
            }
            return deleteStmt.toString();
        }

        return sql;
    }

    private static String getSqlByClass(Object value){

        if(value instanceof Number){
            return value + "";
        }else if(value instanceof String){
            return "'" + value + "'";
        }

        return "'" + value.toString() + "'";
    }
}