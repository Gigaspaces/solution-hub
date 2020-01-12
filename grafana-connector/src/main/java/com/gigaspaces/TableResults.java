package com.gigaspaces;

/*Expected Grafana table result
        {
    "columns":[
      {"text":"Time","type":"time"},
      {"text":"Country","type":"string"},
      {"text":"Number","type":"number"}
    ],
    "rows":[
      [1234567,"SE",123],
      [1234567,"DE",231],
      [1234567,"US",321]
    ],
    "type":"table"
  }
*/
public class TableResults {
        Column[] columns;
        Object[][] rows;
        String type = "table";

        public Column[] getColumns() {
            return columns;
        }

        public void setColumns(Column[] columns) {
            this.columns = columns;
        }

    public Object[][] getRows() {
        return rows;
    }

    public void setRows(Object[][] rows) {
        this.rows = rows;
    }

    public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }



    public static class Column{
        String text;
        String type;

        public Column(String text, String type) {
            this.text = text;
            this.type = type;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }

    public static class Row{

        String[] values;

        public Row(String[] values) {
            this.values = values;
        }

        public String[] getValues() {
            return values;
        }

        public void setValues(String[] values) {
            this.values = values;
        }
    }

   }
