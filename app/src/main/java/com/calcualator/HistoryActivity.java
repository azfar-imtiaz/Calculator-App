package com.calcualator;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.android.material.divider.MaterialDivider;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        TableLayout tableLayout = (TableLayout) findViewById(R.id.historyTableLayout);
        tableLayout.setGravity(Gravity.CENTER_VERTICAL);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            ArrayList<String> historyCommands = extras.getStringArrayList("listOfCommands");
            for (String cmd: historyCommands) {
                TableRow row = new TableRow(this);
                row.setLayoutParams(new TableLayout.LayoutParams(
                        TableLayout.LayoutParams.MATCH_PARENT,
                        TableLayout.LayoutParams.WRAP_CONTENT
                    )
                );
                row.setGravity(Gravity.CENTER);
                row.setPadding(0, 10, 0, 10);
                TextView textView = new TextView(this);
                textView.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT
                ));
                textView.setGravity(Gravity.CENTER_HORIZONTAL);
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 40);
                textView.setText(cmd);
                row.addView(textView);
                row.setBackgroundResource(R.drawable.row_divider);
                /*
                TODO: Make the rows clickable, add OnClick event where this sub-activity sends
                selected data back to MainActivity and then finishes. Follow this link: https://stackoverflow.com/questions/7581870/how-to-get-click-event-of-table-row-table-item
                 */
                tableLayout.addView(row);
            }
        }
    }
}