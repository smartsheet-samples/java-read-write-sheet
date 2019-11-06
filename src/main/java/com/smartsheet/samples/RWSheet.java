package com.smartsheet.samples;

// Add Maven library "com.smartsheet:smartsheet-sdk-java:2.2.3" to access Smartsheet Java SDK
import com.smartsheet.api.Smartsheet;
import com.smartsheet.api.SmartsheetFactory;
import com.smartsheet.api.models.Cell;
import com.smartsheet.api.models.Column;
import com.smartsheet.api.models.Row;
import com.smartsheet.api.models.Sheet;

import java.io.File;
import java.util.*;


public class RWSheet {
    static {
        // These lines enable logging to the console
        System.setProperty("Smartsheet.trace.parts", "RequestBodySummary,ResponseBodySummary");
        System.setProperty("Smartsheet.trace.pretty", "true");
    }

    // The API identifies columns by Id, but it's more convenient to refer to column names
    private static HashMap<String, Long> columnMap = new HashMap<String, Long>();   // Map from friendly column name to column Id

    public static void main(final String[] args) {

        try {
            // Initialize client. Gets API access token from SMARTSHEET_ACCESS_TOKEN variable
            Smartsheet smartsheet = SmartsheetFactory.createDefaultClient();

            Sheet sheet = smartsheet.sheetResources().importXlsx("Sample Sheet.xlsx", "sample", 0, 0);

            // Load the entire sheet
            sheet = smartsheet.sheetResources().getSheet(sheet.getId(), null, null, null, null, null, null, null);
            System.out.println("Loaded " + sheet.getRows().size() + " rows from sheet: " + sheet.getName());

            // Build the column map for later reference
            for (Column column : sheet.getColumns())
                columnMap.put(column.getTitle(), column.getId());

            // Accumulate rows needing update here
            ArrayList<Row> rowsToUpdate = new ArrayList<Row>();

            for (Row row : sheet.getRows()) {
                Row rowToUpdate = evaluateRowAndBuildUpdates(row);
                if (rowToUpdate != null)
                    rowsToUpdate.add(rowToUpdate);
            }

            if (rowsToUpdate.isEmpty()) {
                System.out.println("No updates required");
            } else {
                // Finally, write all updated cells back to Smartsheet
                System.out.println("Writing " + rowsToUpdate.size() + " rows back to sheet id " + sheet.getId());
                smartsheet.sheetResources().rowResources().updateRows(sheet.getId(), rowsToUpdate);
                System.out.println("Done");
            }
        } catch (Exception ex) {
            System.out.println("Exception : " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    /*
     * TODO: Replace the body of this loop with your code
     * This *example* looks for rows with a "Status" column marked "Complete" and sets the "Remaining" column to zero
     *
     * Return a new Row with updated cell values, else null to leave unchanged
     */
    private static Row evaluateRowAndBuildUpdates(Row sourceRow) {
        Row rowToUpdate = null;

        // Find cell we want to examine
        Cell statusCell = getCellByColumnName(sourceRow, "Status");

        if ("Complete".equals(statusCell.getDisplayValue())) {
            Cell remainingCell = getCellByColumnName(sourceRow, "Remaining");
            if (! "0".equals(remainingCell.getDisplayValue()))                  // Skip if "Remaining" is already zero
            {
                System.out.println("Need to update row #" + sourceRow.getRowNumber());

                Cell cellToUpdate = new Cell();
                cellToUpdate.setColumnId(columnMap.get("Remaining"));
                cellToUpdate.setValue(0);

                List<Cell> cellsToUpdate = Arrays.asList(cellToUpdate);

                rowToUpdate = new Row();
                rowToUpdate.setId(sourceRow.getId());
                rowToUpdate.setCells(cellsToUpdate);
            }
        }
        return rowToUpdate;
    }

    // Helper function to find cell in a row
    static Cell getCellByColumnName(Row row, String columnName) {
        Long colId = columnMap.get(columnName);

        return row.getCells().stream()
                .filter(cell -> colId.equals((Long) cell.getColumnId()))
                .findFirst()
                .orElse(null);

    }

}
