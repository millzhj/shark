/*
 * Copyright 1999-2101 Alibaba Group Holding Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.sharksharding.sql.ast.statement;

import java.util.ArrayList;
import java.util.List;

import com.sharksharding.sql.ast.SQLName;
import com.sharksharding.sql.ast.SQLStatementImpl;
import com.sharksharding.sql.visitor.SQLASTVisitor;

public class SQLAlterTableStatement extends SQLStatementImpl implements SQLDDLStatement {

    private SQLExprTableSource      tableSource;
    private List<SQLAlterTableItem> items = new ArrayList<SQLAlterTableItem>();
    
    public SQLAlterTableStatement() {
        
    }
    
    public SQLAlterTableStatement(String dbType) {
        super (dbType);
    }

    public List<SQLAlterTableItem> getItems() {
        return items;
    }

    public void setItems(List<SQLAlterTableItem> items) {
        this.items = items;
    }

    public SQLExprTableSource getTableSource() {
        return tableSource;
    }

    public void setTableSource(SQLExprTableSource tableSource) {
        this.tableSource = tableSource;
    }

    public SQLName getName() {
        if (getTableSource() == null) {
            return null;
        }
        return (SQLName) getTableSource().getExpr();
    }

    public void setName(SQLName name) {
        this.setTableSource(new SQLExprTableSource(name));
    }

    @Override
    protected void accept0(SQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            acceptChild(visitor, getTableSource());
            acceptChild(visitor, getItems());
        }
        visitor.endVisit(this);
    }
}
